# JDK11 Javac Behavior Change Repro

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
