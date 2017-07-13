package es.uniovi.reflection.test.component;

/**
 * java.lang.Exception created to use in the
 * {@link es.uniovi.reflection.test.component.TestExceptionHandler
 * after-throwing test}. It is thrown when a rational number is intended to be
 * created but zero is specified as denominator.
 * 
 * @author Oscar Rodriguez-Prieto Date: 2017/07/11
 * 
 * @version 1.1.0
 *
 *
 */
public class ZeroAsDenominatorException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ZeroAsDenominatorException() {
		super("Attempted to create a rational number with denominator equals to 0. ");
	}
}
