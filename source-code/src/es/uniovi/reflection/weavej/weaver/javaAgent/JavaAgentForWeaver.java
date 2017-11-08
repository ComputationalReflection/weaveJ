package es.uniovi.reflection.weavej.weaver.javaAgent;

import java.lang.instrument.Instrumentation;

import es.uniovi.reflection.weavej.weaver.java7.PackageGroupTransformerJava7;

/**
 * Static class containing the premain method of the java agent. This method
 * will be executed right before the main method of the instrumented program. In
 * this method, the java version is checked and an appropriate subclass of
 * {@link es.uniovi.reflection.weavej.weaver.javaAgent.InstrumentorTransformer
 * InstrumentorTransformer } is added to the instrumentation. System properties
 * are also checked looking for the "formatting" property, whose value indicate
 * which classes have to be instrumented.
 * 
 * @author Oscar Rodriguez-Prieto Date: 2017/07/11
 * 
 * @version 1.1.0
 *
 */
public class JavaAgentForWeaver {

	public static void premain(String agentArgs, Instrumentation inst)
			throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, NoSuchFieldException {

		String propertyName = "formatting";
		String versionProp = "java.version";

		if (System.getProperty(versionProp).substring(0, 3).contentEquals("1.8"))

			inst.addTransformer(
					new PackageGroupTransformer(System.getProperties().get(propertyName).toString().split(";")));
		else
			inst.addTransformer(
					new PackageGroupTransformerJava7(System.getProperties().get(propertyName).toString().split(";")));

	}
}
