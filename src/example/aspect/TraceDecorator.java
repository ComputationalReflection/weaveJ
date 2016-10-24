package example.aspect;

import example.component.CreditCard;

public class TraceDecorator {

	public static void traceBefore(String methodName, CreditCard card,
			double amount) {
		System.out.printf("> Begining of the " + methodName
				+ " operation with params: card=\"%s\", amount=%.2f.\n", card,
				amount);
	}

	public static void traceAfter(String methodName, CreditCard card,
			double result)  {
		System.out.printf("> End of the " + methodName
				+ " operation with result: %.2f.\n", result);
	}

}
