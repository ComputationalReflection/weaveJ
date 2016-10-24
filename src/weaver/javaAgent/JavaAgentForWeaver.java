package weaver.javaAgent;

import java.lang.instrument.Instrumentation;
import java.util.Scanner;

public class JavaAgentForWeaver {
	private static final boolean DEBUG = false;

	public static void premain(String agentArgs, Instrumentation inst)
			throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, NoSuchFieldException {

		if (DEBUG)
			System.out.println("JavaAgentForWeaver > Executing weaver premain.");
		try {
			@SuppressWarnings("resource")
			Scanner keyboard = new Scanner(System.in);
			System.out
					.println("Introduce the class names or package(s) that you want to transform (';' as separator) :");

			inst.addTransformer(new PackageGroupTransformer(keyboard.nextLine().split(";")));

		} catch (Exception | Error e) {
			e.printStackTrace();
		}
	}
}
