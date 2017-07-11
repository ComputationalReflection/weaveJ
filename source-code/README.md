weaveJ
:	Programmatic aspect weaver for Java (API) .



For using weaveJ in java:

1. Download 'java-agent.jar' from 'lib' folder, and add it to your project's classpath.

2. Call the API in your code to add pointcuts, which are used to relate aspects with different joinpoints in your program.

3. Execute your Java project specifying as VM argument for its execution : -javaagent:[path_to_the_jar]/java-agent.jar.

4. Execute your Java project specifying as VM argument : -Dformatting=[packages_or_classes_you_want_instrument]  (';' as separator)


weaveJ works on top of Java 8, 
it also works  with Java 7, but the constructor joinpoint is not supported.
The Set Field joinpoint is only supported for non-final fields.
