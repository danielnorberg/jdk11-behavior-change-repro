# JDK11 Javac Behavior Change Repro

## Code

**Repro.java**
```java
import java.io.IOException;

public class Repro {

  /**
   * Compiles on JDK 8, 9 and 10 but fails to compile on JDK 11: 
   * 
   *   unreported exception java.lang.Exception; must be caught or declared to be thrown
   */
  void repro() throws IOException {
    when(f(any()));
  }

  interface G<T, E extends Exception> { }

  <T, E extends Exception> T f(G<T, E> g) throws IOException, E {
    return null;
  }

  static <T> T any() {
    return null;
  }

  static <T> OngoingStubbing<T> when(T methodCall) {
    return null;
  }

  interface OngoingStubbing<T> { }
}
```

## Fails

**JDK11**

    JAVA_HOME=$(/usr/libexec/java_home -v 11) mvn clean compile -Djava.version=11
    ...
    [ERROR] Repro.java:[9,11] unreported exception java.lang.Exception; must be caught or declared to be thrown
    ...

**JDK12 (ea)**

    JAVA_HOME=$(/usr/libexec/java_home -v 12) mvn clean compile -Djava.version=12
    ...
    [ERROR] Repro.java:[9,11] unreported exception java.lang.Exception; must be caught or declared to be thrown
    ...


## Works

**JDK8**

    JAVA_HOME=$(/usr/libexec/java_home -v 1.8) mvn clean compile -Djava.version=1.8

**JDK9**

    JAVA_HOME=$(/usr/libexec/java_home -v 9) mvn clean compile -Djava.version=9

**JDK10**

    JAVA_HOME=$(/usr/libexec/java_home -v 10) mvn clean compile -Djava.version=10
