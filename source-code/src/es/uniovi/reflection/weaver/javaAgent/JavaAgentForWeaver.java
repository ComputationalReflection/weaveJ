package es.uniovi.reflection.weaver.javaAgent;

import java.lang.instrument.Instrumentation;
import es.uniovi.reflection.weaver.java7.PackageGroupTransformerJava7;

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
