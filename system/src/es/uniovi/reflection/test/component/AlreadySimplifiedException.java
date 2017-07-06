package es.uniovi.reflection.test.component;

 public class AlreadySimplifiedException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public AlreadySimplifiedException() {
		super("Attempted to simplify a rational number that is already simplified. ");
	}
}
