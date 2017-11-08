package es.uniovi.reflection.weavej.test.aspects;

/**
 * Stateful aspect to weave constructor-before pointcuts when a
 * {@link es.uniovi.reflection.weavej.test.component.RationalNumber RationalNumber} is
 * instantiated.
 * 
 * @author Oscar Rodriguez-Prieto Date: 2017/07/11
 * 
 * @version 1.1.0
 *
 */
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
