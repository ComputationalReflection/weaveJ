package example.aspect;

import java.lang.invoke.MethodHandle;

import example.component.CreditCard;

public class CommissionAspect {
	private static double DEFAULT_MIN_VALUE = 100;
	private static double DEFAULT_COMMISION_PERCENTAGE = 2;
	
	private double commissionPercentage;
	private double minValue;

	public CommissionAspect(double commissionPercentage, double minValue) {
		this.commissionPercentage = commissionPercentage;
		this.minValue = minValue;
	}

	public CommissionAspect(String commissionPercentage, String minValue) {
		this.commissionPercentage = Double.parseDouble(commissionPercentage);
		this.minValue = Double.parseDouble(minValue);
	}

	public CommissionAspect(String commissionPercentage) {
		this.commissionPercentage = Double.parseDouble(commissionPercentage);
		this.minValue = DEFAULT_MIN_VALUE;
	}

	public CommissionAspect() {
		this.commissionPercentage = DEFAULT_COMMISION_PERCENTAGE;
		this.minValue = DEFAULT_MIN_VALUE;
	}

	public CommissionAspect(double commissionPercentage) {
		this.commissionPercentage = commissionPercentage;
		this.minValue = DEFAULT_MIN_VALUE;
	}

	public double applyCommission(MethodHandle mh, CreditCard card, double amount) throws Throwable {
		System.out.printf("+ Aspect: applying commission to %s... %b\n", card, amount<this.minValue);
		double funds = (double) mh.invokeWithArguments(card, amount >= this.minValue ? amount : 
			amount * (1 + this.commissionPercentage / 100));
		return funds > 0 ? amount : 0;
	}

	private static double reward_over_amount = 100_000; 
	private static double reward_percentage = 0.5;
	
	public static double rewardSignificantDeposit(MethodHandle mh,
			CreditCard card, double amount) throws Throwable {
		System.out.printf("+ Aspect: Checking reward for deposit of %s...%b\n", card, amount>=reward_over_amount);
		return (double)mh.invokeWithArguments(card, amount < reward_over_amount ? amount :
				amount * (1 + reward_percentage/100));
	}
}
