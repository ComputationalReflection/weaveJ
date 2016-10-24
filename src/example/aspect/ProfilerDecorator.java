package example.aspect;

import example.component.CreditCard;

public class ProfilerDecorator {

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
}
