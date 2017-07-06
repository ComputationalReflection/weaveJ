package example.component;

import java.util.Random;

public class CreditCard extends Thread {

	private double balance;

	private long ID;

	public CreditCard(double balance) {
		this.balance = balance;
		ID = CardManager.ID_COUNTER++;
	}

	public double deposit(double amount) {
		return this.balance += amount;
	}

	@Override
	public void run() {
		try {
			Random random = new Random();
			int i = 0;
			while (true) {
				Thread.sleep(1000);
				double amount = random.nextInt(50);
				if (i++ % 2 == 0)
					System.out.printf("Card ID %d, withdraw %.2f.\n", ID, withdraw(amount));
				else
					System.out.printf("Card ID %d, credit %.2f, balance: %.2f.\n", ID, amount, deposit(amount));

			}
		} catch (Throwable t) {
		}
	}

	@Override
	public String toString() {
		return String.format("Credit card %d (balance %.2f)", this.ID, this.balance);
	}

	public double withdraw(double amount) {
		if (amount <= this.balance) {
			this.balance -= amount;
			return amount;
		}
		return 0;
	}

}
