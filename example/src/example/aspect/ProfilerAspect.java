package example.aspect;

import java.lang.invoke.MethodHandle;

import example.component.CreditCard;

public class ProfilerAspect {
/*
	private long timeBefore;

	public void checkTimeBefore(CreditCard card, double amount) {
		timeBefore = System.nanoTime();
	}

	public void profileAfter(String methodName, CreditCard card, double amount) {
		long after = System.nanoTime();
		System.out.printf("> Elapsed time in a " + methodName
				+ "operation on the " + card.toString()
				+ " credit card: %d nanos.\n", after - timeBefore);

	}
	*/
	public static double profileOperation(String methodName, MethodHandle mh,
			CreditCard card, double amount) throws Throwable {
	    long timeBefore = System.nanoTime();
		double res=(double) mh.invokeWithArguments(card, amount);
		System.out.printf("> Elapsed time in a " + methodName
				+ " operation on the " + card.toString()
				+ " credit card: %d nanos.\n",  System.nanoTime() - timeBefore);
		return res;

	}
}
