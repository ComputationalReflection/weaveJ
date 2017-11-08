package example.component;

import es.uniovi.reflection.weavej.weaver.Pointcut;
import es.uniovi.reflection.weavej.weaver.Weaver;
import example.aspect.CommissionAspect;

public class Main {

	public static void programaticDemo() throws Throwable {
		// * Example of stateful aspects, for all the instances in a class
		
		CreditCard card1 = new CreditCard(1_000), card2 = new CreditCard(1_000_000);

		System.out.println(card1);
		System.out.println(card2);

		CommissionAspect commission = new CommissionAspect(2, 100);
		Pointcut pointcutWithdraw = Weaver.weaveAspectForMethodAround("example.component.CreditCard", "withdraw",
				commission, "applyCommission", double.class, double.class);

		System.out.println("Withdrawing 50 euros from card 1...");
		card1.withdraw(50);
		System.out.println(card1);

		System.out.println("Withdrawing 50 euros from card 2...");
		card2.withdraw(50);
		System.out.println(card2);

		System.out.println("Withdrawing 200 euros from card 2...");
		card2.withdraw(200);
		System.out.println(card2);

		// * Example of aspectizing a single instance of the component
		// (stateless aspect)
		System.out.println("+ Deposit without reward to card 2...");
		card2.deposit(100_000);
		System.out.println(card2);

		Pointcut pointcutDeposit = Weaver.weaveAspectForMethodAround("example.component.CreditCard", "deposit",
				"example.aspect.CommissionAspect", "rewardSignificantDeposit", new Object[] { card2 }, double.class,
				double.class);

		System.out.println("+ Deposit with reward to card 2...");
		card2.deposit(100_000);
		System.out.println(card2);

		System.out.println("+ Deposit with reward to card 2...");
		card2.deposit(100);
		System.out.println(card2);

		System.out.println("+ Deposit without reward to card 1...");
		card1.deposit(100_000);
		System.out.println(card2);

		pointcutDeposit.unweave();
	}

	public static void cardManagerDemo() throws Throwable {
		CardManager cm = CardManager.getInstance();
		cm.startTargets(10);
		cm.run();
	}

	public static void main(String a[]) throws Throwable {
//		 programaticDemo();

		cardManagerDemo();
	}

}
