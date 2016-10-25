weaveJ
:	Programatic aspect weaver for Java (API) .



For using weaveJ in java:

1. Download 'java-agent.jar' from 'lib' folder, and add it to your project's classpath.

2. Call the API in your code to add pointcuts, which are used to relate aspects with different joinpoints in your program.

3. Execute your java project specifying one VM argument for its execution : -javaagent:<path_to_the_jar>/java-agent.jar.

4. Type the name of the classes/packages where you want AOP features.
