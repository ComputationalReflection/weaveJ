package test.component;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

import weaver.Pointcut;
import weaver.Weaver;

public class TestAspectMethod {

	private static final String ASPECT_CLASS = "test.aspects.RationalAspects";
	private static final String RATIONAL_CLASS = "test.component.RationalNumber";

	@Test
	public void test() throws Throwable {
		testAround();
		testAfter();
		testBefore();
	}

	private void testAfter() throws Throwable {
		RationalNumber r1 = new RationalNumber(5, 5);
		assertTrue(r1.toMatrix(5, new RationalNumber[] {})[0][0] == 25);
		Pointcut pointcut = Weaver.weaveAspectForMethodAfter(RATIONAL_CLASS, "toMatrix", ASPECT_CLASS,
				"toZerosMatrixAfter", int[][].class, int.class, RationalNumber[].class);
		assertTrue(r1.toMatrix(100, new RationalNumber[] {})[0][0] == 0);
		pointcut.unweave();
		assertTrue(r1.toMatrix(5, new RationalNumber[] {})[0][0] == 25);
	}

	private void testAround() throws Throwable {
		RationalNumber r1 = new RationalNumber(5, 5);
		assertFalse(r1.toString().contains(":"));
		Pointcut pointcut = Weaver.weaveAspectForMethodAround(RATIONAL_CLASS, "toString", ASPECT_CLASS,
				"wrappedToString", String.class);
		assertTrue(r1.toString().contains(":"));
		pointcut.unweave();
		assertFalse(r1.toString().contains(":"));
	}

	private void testBefore() throws Throwable {
		RationalNumber r1 = new RationalNumber(1, 1);
		assertTrue(r1.toMatrix(5, new RationalNumber[] {})[0][0] == 5);
		Weaver.weaveAspectForMethodBefore(RATIONAL_CLASS, "toMatrix", ASPECT_CLASS, "checkBeforeMatrix",
				int[][].class, int.class, RationalNumber[].class);
		try {
			r1.toMatrix(0, (RationalNumber[]) null);
			fail();
		} catch (NullPointerException e) {
			// Tejido compuesto
			Weaver.weaveExceptionHandlerForMethod(RATIONAL_CLASS, "toMatrix", ASPECT_CLASS, "handleExceptionMatrix",
					"java.lang.RuntimeException", int[][].class, int.class, RationalNumber[].class);
			assertEquals(r1.toMatrix(0, new RationalNumber[] {})[0][0], 0);

		}
	}

}
