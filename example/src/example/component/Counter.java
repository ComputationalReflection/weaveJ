package example.component;

public class Counter {

	public int counter = 0;

	public Counter(int uds, int limit) {
		if (uds > limit)
			throw new IllegalArgumentException();
		counter = uds;
	}

	public double div(int uds) {
		return counter / (double) uds;
	}

	public int getCounter() {
		return counter;
	}

	public int inc(int uds) {
		counter += uds;
		return counter;
	}

	public int multi(int uds) {
		counter *= uds;
		return counter;
	}

	public void setCounter(int counter) {
		this.counter = counter;
	}

	public int unsafeInc(int uds) {
		counter += uds;
		throw new IllegalArgumentException();

	}

}
