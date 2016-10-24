package example.aspect;

import java.lang.invoke.MethodHandle;

import example.component.CreditCard;

public class CommissionDecorator {
	private double commission;

	public CommissionDecorator() {
		commission = 0.1;
	}

	public CommissionDecorator(String com) {
		commission = Double.parseDouble(com);
	}

	public CommissionDecorator(double commission) {
		this.commission = commission;
	}

	public double operationWithCommission(MethodHandle mh, CreditCard card,
			double amount) throws Throwable {
		return (double) mh.invokeWithArguments(card, amount - commission);

	}

	public static double operationWithCommissionStateless(MethodHandle mh,
			CreditCard card, double amount) throws Throwable {
		return new CommissionDecorator().operationWithCommission(mh, card,
				amount);

	}
}
