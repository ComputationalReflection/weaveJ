package test.aspects;

public class RationalCounter {

	private int counter;
	private int threshold;

	public RationalCounter(int threshold) {
		this.counter = 0;
		this.threshold = threshold;
	}

	public int getCounter() {
		return counter;
	}

	public void countIfGreater(int numerator, int denominator) {
		if (numerator / denominator >= threshold)
			counter++;
	}
}
