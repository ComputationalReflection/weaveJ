package aspects;

import java.lang.invoke.MethodHandle;

import application.Program;
import application.Util;

public class LoggingAspect {
	private boolean isEnabled = true;
	static public long sumTotal = 0;
	public static int i = 0;

	public boolean isEnabled() {
		return isEnabled;
	}

	public void isEnabled(boolean enabled) {
		isEnabled = enabled;
	}

	public static void methodAround(String methodName, MethodHandle m, Program p, Integer p1, Integer p2, Integer p3) {
		// Full
		// Problemas con String lenght hashCode etc
		Object[] args = new Object[] { p1, p2, p3 };
		sumTotal += Util.length(methodName) + Util.length(p.toString()) + args.length;

		for (Object o : args) {
			sumTotal += Util.length(o.toString());// +
													// Util.length(o.getClass());
		} // sumTotal += p2.toString().length() +
			// sumTotal += p2.toString().length() +
			// p2.getClass().toString().length();
			// sumTotal += p3.toString().length() +
			// p3.getClass().toString().length();

		// return proceed();
	}

	public static void methodBefore(String methodName, Program p, Integer p1, Integer p2, Integer p3) {
		// Full
		// Problemas con String lenght hashCode etc
		Object[] args = new Object[] { p1, p2, p3 };
		sumTotal += Util.length(methodName) + Util.length(p.toString()) + args.length;

		for (Object o : args) {
			sumTotal += Util.length(o.toString());// +
													// Util.length(o.getClass());
		} // sumTotal += p2.toString().length() +
			// sumTotal += p2.toString().length() +
			// p2.getClass().toString().length();
			// sumTotal += p3.toString().length() +
			// p3.getClass().toString().length();

		// return proceed();
	}

	public static void methodAfter(String methodName, Program p) {
		// Full
		// Problemas con String lenght hashCode etc
		sumTotal += Util.length(methodName) + Util.length(p.toString());
		Object[] args = new Object[] { 5, 6, 23 };
		for (Object o : args) {
			sumTotal += Util.length(o.toString());// +
													// Util.length(o.getClass());
		}
		// Object[] args = new Object[] { 5, 6, 23 };
		// for (Object o : args) {
		// sumTotal += Util.length(o.toString()) +
		// Util.length(o.getClass());
		// }
		// sumTotal += p2.toString().length() +
		// sumTotal += p2.toString().length() +
		// p2.getClass().toString().length();
		// sumTotal += p3.toString().length() +
		// p3.getClass().toString().length();

		// return proceed();
	}
}
