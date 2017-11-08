package es.uniovi.reflection.weavej.test.component;

/**
 * java.lang.Exception created to use in the
 * {@link es.uniovi.reflection.weavej.test.component.TestExceptionHandler
 * after-throwing test}. It is thrown when a simplification is required but the
 * {@link es.uniovi.reflection.weavej.test.component.RationalNumber RationalNumber} is
 * already completely simplified.
 * 
 * @author Oscar Rodriguez-Prieto Date: 2017/07/11
 * 
 * @version 1.1.0
 *
 *
 */
public class AlreadySimplifiedException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public AlreadySimplifiedException() {
		super("Attempted to simplify a rational number that is already simplified. ");
	}
}
