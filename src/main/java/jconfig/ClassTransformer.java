package jconfig;

import java.util.Properties;
import java.util.Map;
import java.util.HashMap;

import jdk.internal.org.objectweb.asm.AnnotationVisitor;
import jdk.internal.org.objectweb.asm.ClassVisitor;
import jdk.internal.org.objectweb.asm.FieldVisitor;
import jdk.internal.org.objectweb.asm.MethodVisitor;
import jdk.internal.org.objectweb.asm.Type;

import static jdk.internal.org.objectweb.asm.Opcodes.*;

public class ClassTransformer extends ClassVisitor {
	private boolean isInterface;
	private String className;
	private String targetMethodName = "<init>";
  private Properties prop;
  private Map<FieldMapKey, Object> fieldDefaultValues = new HashMap<>();

	public ClassTransformer(ClassVisitor cv, Properties prop) {
		super(ASM5, cv);
    this.prop = prop;
	}

	@Override
	public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
		cv.visit(version, access, name, signature, superName, interfaces);
		isInterface = (access & ACC_INTERFACE) != 0;
		className = name;
	}

	@Override
  public FieldVisitor visitField(int access, String name, String desc, String signature, Object value) {
    boolean isStatic = (access & ACC_STATIC) != 0;
    String key = className.replace('/', '.') + "." + name;
    String newValue = prop.getProperty(key);
    if (newValue != null) {
      Object converted = convertType(desc, newValue);
      if (converted != null) {
        if (isStatic) {
          //for static field
          value = converted;
        } else {
          //for instance field (set at <init> method)
          FieldMapKey mapKey = new FieldMapKey(className, name, desc);
          fieldDefaultValues.put(mapKey, converted);
        }
      } else {
        System.err.println("Illegal type: " + key + "=" + newValue);
      }
    }
    return cv.visitField(access, name, desc, signature, value);
  }

	@Override
	public MethodVisitor visitMethod(int access, String name, String desc,
			String signature, String[] exceptions)
  {
		MethodVisitor mv = cv.visitMethod(access, name, desc, signature, exceptions);
		if (!isInterface && mv != null && "<init>".equals(name)) {
			mv = new MethodTransformer(access, className, name, signature, desc, mv);
		}
		return mv;
	}

  private Object convertType(String desc, String value) {
    try {
      switch(desc) {
      case "I": //int
        return new Integer(Integer.parseInt(value));
      case "J": //long
        return new Long(Long.parseLong(value));
      case "F": //float
        return new Float(Float.parseFloat(value));
      case "D": //double
        return new Double(Double.parseDouble(value));
      case "C": //char
        if (value.length() != 0) {
          return new Character(value.charAt(0));
        }
        break;
      case "S": //short
        return new Short(Short.parseShort(value));
      case "B": //byte
        return Byte.decode(value);
      case "Ljava/lang/String;": //java.lang.String
        return value;
      default:
        System.err.println("Not support type: " + desc);
      }
    } catch(NumberFormatException e) {
      System.err.println(e + ": " + value);
    }
    return null;
  }

	private final class MethodTransformer extends MethodVisitor {
		public MethodTransformer(int access, String className, String methodName, String signature, String desc, MethodVisitor mv) {
			super(ASM5, mv);
		}

    @Override
    public void visitCode() {
      super.visitCode();
      // set default values of instance fields at first of the code
      for (Map.Entry<FieldMapKey, Object> entry: fieldDefaultValues.entrySet()) {
        FieldMapKey key = entry.getKey();
        Object value = entry.getValue();
        String owner = key.getOwner();
        String name = key.getName();
        String desc = key.getDesc();
        super.visitVarInsn(ALOAD, 0);
        super.visitLdcInsn(value);
        super.visitFieldInsn(PUTFIELD, owner, name, desc);
      }
    }

 		@Override
		public void visitMaxs(int maxStack, int maxLocals) {
			super.visitMaxs(maxStack + 8, maxLocals);
		}
	}

  private final class FieldMapKey {
    final private String owner;
    final private String name;
    final private String desc;

    private FieldMapKey(String owner, String name, String desc) {
      this.owner = owner;
      this.name = name;
      this.desc = desc;
    }

    public String getOwner() { return owner; }
    public String getName() { return name; }
    public String getDesc() { return desc; }

    @Override
    public boolean equals(Object other) {
      if (other instanceof FieldMapKey) {
        FieldMapKey otherKey = (FieldMapKey)other;
        boolean ownerEqual = owner == null
          ? owner == otherKey.owner
          : owner.equals(otherKey.owner);
        boolean nameEqual = name == null
          ? name == otherKey.name
          : name.equals(otherKey.name);
        boolean descEqual = desc == null
          ? desc == otherKey.desc
          : desc.equals(otherKey.desc);
        return ownerEqual && nameEqual && descEqual;
      } else {
        return false;
      }
    }

    @Override
    public int hashCode() {
      return owner.hashCode() + name.hashCode() + desc.hashCode();
    }
  }
}
