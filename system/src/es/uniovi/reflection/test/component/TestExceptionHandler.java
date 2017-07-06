package es.uniovi.reflection.test.component;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

import es.uniovi.reflection.weaver.Pointcut;
import es.uniovi.reflection.weaver.Weaver;

 public class TestExceptionHandler {
	private static final String ASPECT_CLASS = "es.uniovi.reflection.test.aspects.RationalAspects";
	private static final String EXCEPTION_CLASS = "es.uniovi.reflection.test.component.ZeroAsDenominatorException";
	private static final String RATIONAL_CLASS = "es.uniovi.reflection.test.component.RationalNumber";
	private static final String RUNTIME_EXC_CLASS = "java.lang.RuntimeException";

	private void constructorTest() throws ClassNotFoundException, Throwable {
		boolean end = false;
		RationalNumber r = new RationalNumber(6, 6);
		try {
			try {
				r = new RationalNumber(5, 0);
				fail();
			} catch (ZeroAsDenominatorException z) {
				Pointcut pointcut = Weaver.weaveExceptionHandlerForConstructor(RATIONAL_CLASS, ASPECT_CLASS,
						"handleExceptionWithOne", EXCEPTION_CLASS, int.class, int.class);
				RationalNumber r2 = new RationalNumber(5, 0);
				assertEquals(r2.denominator, 1);
				assertEquals(r2.numerator, 5);
				pointcut.unweave();
				end = true;
				r = new RationalNumber(5, 0);
				fail();
			}
		} catch (ZeroAsDenominatorException z) {
			assertTrue(end);
			assertEquals(r.denominator, 6);
		}
	}

	private void methodTest() throws ClassNotFoundException, Throwable {
		Pointcut pointcut = Weaver.weaveExceptionHandlerForMethod(RATIONAL_CLASS, "simplify", ASPECT_CLASS,
				"handleExceptionSimplify", RUNTIME_EXC_CLASS, RationalNumber.class);
		RationalNumber r1 = new RationalNumber(20, 15);
		RationalNumber r2 = r1.simplify();
		assertArrayEquals(new int[] { r2.numerator, r2.denominator }, new int[] { 4, 3 });
		assertEquals(r2, r2.simplify());
		pointcut.unweave();
		try {
			r2.simplify();
			fail();
		} catch (AlreadySimplifiedException e) {
		}
	}

	@Test
	public void test() throws ClassNotFoundException, Throwable {
		constructorTest();
		methodTest();
	}

}
