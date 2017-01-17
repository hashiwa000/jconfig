# jconfig
Total configuration for java application.

# Environments
- JDK 8

# Build and Test

```
$ git clone https://github.com/hashiwa000/jconfig.git
$ cd jconfig/
$ ./gradlew build
```

See build/libs/jconfig.jar .

# How to use
If you write these application and config file,

```
$ cat MainClass.java
package your.app;

public class MainClass {
  private int value; // default is 0.

  public int getValue() {
    return this.value;
  }

  public static void main(String[] args) {
    System.out.println(new MainClass().getValue());
  }
}

$ cat myconf.properties
your.app.MainClass.value=99
```

you can run your application as follows:

```
$ javac -d . MainClass.java
$ java -javaagent:jconfig.jar=myconf.properties your.app.MainClass
99
```
