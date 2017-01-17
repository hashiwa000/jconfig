package jconfig;

import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.instrument.Instrumentation;
import java.lang.instrument.ClassFileTransformer;
import java.security.ProtectionDomain;
import java.util.Properties;

import jdk.internal.org.objectweb.asm.ClassWriter;
import jdk.internal.org.objectweb.asm.ClassReader;
import static jdk.internal.org.objectweb.asm.Opcodes.*;

public class InstrumentAgent {
  public static void premain(String agentArgs, Instrumentation instrumentation) {
    if (agentArgs == null || "".equals(agentArgs)) {
      System.err.println("Configuration properties file isn't defined.");
      return;
    }
    final Properties prop = loadProp(agentArgs);
    instrumentation.addTransformer(new ClassFileTransformer() {
        public byte[] transform(ClassLoader loader,
                                String className,
                                Class<?> classBeingRedefined,
                                ProtectionDomain protectionDomain,
                                byte[] classfileBuffer)
        {
          byte[] transformed = null;
          try {
            ClassWriter cw = new ClassWriter(ASM5);
            ClassTransformer transformer = new ClassTransformer(cw, prop);
            ClassReader cr = new ClassReader(classfileBuffer);
            cr.accept(transformer, 0);
            transformed = cw.toByteArray();
          } catch(Throwable e) {
            e.printStackTrace();
          }

          //debug
          //System.out.println("=== end   : classname is " + className);
          //try {
          //  if (className.contains("App")) {
          //    String f = "a.class";
          //    System.out.println("output " + className + " to " + f);
          //    try (java.io.OutputStream os = new java.io.FileOutputStream(f))
          //    {
          //      os.write(transformed);
          //    }
          //  }
          //} catch(Exception e) {}

          return transformed;
        }
    });
  }

  private static Properties loadProp(String args) {
    Properties prop = new Properties();
    try {
      try (InputStream is = new FileInputStream(args)) {
        prop.load(is);
      }
    } catch(Exception e) {
      e.printStackTrace();
    }
    return prop;
  }
}
