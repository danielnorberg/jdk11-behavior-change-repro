# JDK11 Javac Behavior Change Repro

## Background

We encountered a Javac behavior change when trying to get [Styx](https://github.com/spotify/styx/) to compile on JDK 11. Styx had compiled on JDK 9 and 10 but JDK 11 Javac complained about this test code using mockito: https://github.com/spotify/styx/blob/4a5b8fff8226bbf690c5f4579fd9af2fa4f8d904/styx-api-service/src/test/java/com/spotify/styx/api/workflow/WorkflowInitializerTest.java#L68

> unreported exception java.lang.Exception; must be caught or declared to be thrown

This is an attempt to provide a minimal repro of the observed behavior change.

bugs.java.com internal review ID: 9057494

## Travis

https://travis-ci.org/danielnorberg/jdk11-behavior-change-repro

![Travis](travis.png?raw=true "Travis")

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

    // The below code compiles on JDK 11. Perhaps when() wrapping the call to f(any()) breaks type inference?
    // var v = f(any());
    // when(v);
  }

  interface G<T, E extends Exception> { }

  <T, E extends Exception> T f(G<T, E> g) throws IOException, E {
    return null;
  }

  static <T> T any() {
    return null;
  }

  static <T> void when(T methodCall) {
  }
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
