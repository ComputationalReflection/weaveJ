package test.component;

public class ZeroAsDenominatorException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ZeroAsDenominatorException() {
		super(
				"Attempted to create a rational number with denominator equals to 0. ");
	}
}
