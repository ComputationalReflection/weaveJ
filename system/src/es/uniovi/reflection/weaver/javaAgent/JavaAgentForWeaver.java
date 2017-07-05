package es.uniovi.reflection.weaver.javaAgent;

import java.lang.instrument.Instrumentation;

 
public class JavaAgentForWeaver {

	public static void premain(String agentArgs, Instrumentation inst)
			throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, NoSuchFieldException {

		String propertyName = "formatting";
		inst.addTransformer(new PackageGroupTransformer(System.getProperties().get(propertyName).toString().split(";")));

	}
}
