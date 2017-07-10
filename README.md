weaveJ
:	Programatic aspect weaver for Java.



To use weaveJ in Java:

1. Download `java-agent.jar` from `source-code\lib` folder, and add it to your system classpath.

2. Employ the API in your code to use pointcuts, see [documentation](http://htmlpreview.github.com/?https://github.com/ComputationalReflection/weaveJ/blob/master/documentation/index.html).

3. Specify the following arguments to the VM :
  * The java agent: `-javaagent:[path_to_the_jar]/java-agent.jar`.
  * The classes or packages to be instrumented: `-Dformatting=[packages_or_classes_you_want_instrument]`  (`';'` as separator)

Notice: 
weaveJ works with Java 8. The `setField` joinpoint is only supported for non-final fields.

Directories:

 * Benchmarks: Code and binaries of all the programs used in the evaluation.
   * Micro Benchmark
   * Real Applications
 * binaries: Binaries and scripts to use weaveJ and run the example.
 * documentation: Webpage containing the documentation of weaveJ (javadoc).
 * example: Binaries, scripts and code of the example.
 * source-code: Code and binaries of weaveJ.
