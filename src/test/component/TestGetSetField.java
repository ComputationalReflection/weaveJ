package test.component;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;

import test.aspects.RationalFieldCounter;
import test.aspects.RationalMultipliedBy;
import weaver.Pointcut;
import weaver.PointcutForObjects;
import weaver.Weaver;

public class TestGetSetField {

	private static final String ASPECT_COUNTER_CLASS = "test.aspects.RationalFieldCounter";
	private static final String ASPECT_SAFE_CLASS = "test.aspects.RationalAspects";
	private static final String COMPONENT_CLASS = "test.component.RationalNumber";

	private void fieldAccessCounter() throws Throwable {
		RationalNumber r1 = new RationalNumber(5, 5);
		RationalNumber r2 = new RationalNumber(10, 5);
		RationalNumber r3 = new RationalNumber(15, 5);
		Pointcut p4 = Weaver.weaveAspectForFieldAccessSetAfter(COMPONENT_CLASS, "denominator", ASPECT_COUNTER_CLASS,
				"countFieldAcces", int.class);

		PointcutForObjects p1 = Weaver.weaveAspectForFieldAccessGetBefore(COMPONENT_CLASS, "numerator",
				ASPECT_COUNTER_CLASS, "countFieldAcces", int.class, r1, r2);
		Weaver.weaveAspectForFieldAccessGetBefore(COMPONENT_CLASS, "denominator", ASPECT_COUNTER_CLASS,
				"countFieldAcces", int.class, r2, r3);
		Weaver.weaveAspectForFieldAccessSetAfter(COMPONENT_CLASS, "numerator", ASPECT_COUNTER_CLASS, "countFieldAcces",
				int.class, r1, r3);
		for (RationalNumber r : new RationalNumber[] { r1, r2, r3 }) {
			r.numerator = r.numerator + 1;
			r.denominator = r.denominator + 1;
		}

		assertEquals(RationalFieldCounter.getCountFor("numerator"), 4);
		assertEquals(RationalFieldCounter.getCountFor("denominator"), 5);
		p1.unweave();
		p4.unweave();
		for (RationalNumber r : new RationalNumber[] { r1, r2, r3 }) {
			r.numerator = r.numerator + 1;
			r.denominator = r.denominator + 1;
		}
		assertEquals(RationalFieldCounter.getCountFor("numerator"), 6);
		assertEquals(RationalFieldCounter.getCountFor("denominator"), 7);

	}

	private void getAroundMultiply() throws Throwable {
		RationalNumber r2 = new RationalNumber(4, 2);
		RationalNumber r3 = new RationalNumber(12, 4);
		RationalMultipliedBy by2 = new RationalMultipliedBy(r2);
		RationalMultipliedBy by3 = new RationalMultipliedBy(r3);

		assertEquals(r2.getValue(), 2, 0);
		Pointcut p1Num = Weaver.weaveAspectForFieldAccessGetAround(COMPONENT_CLASS, "numerator", by2, "getMultiplied",
				int.class);
		Pointcut p1Den = Weaver.weaveAspectForFieldAccessGetAround(COMPONENT_CLASS, "denominator", by2, "getMultiplied",
				int.class);
		assertEquals(r3.getValue(), 6, 0);
		assertEquals(r2.getValue(), 4, 0);
		Weaver.weaveAspectForFieldAccessGetAround(COMPONENT_CLASS, "numerator", by3, "getMultiplied", int.class);
		Weaver.weaveAspectForFieldAccessGetAround(COMPONENT_CLASS, "denominator", by3, "getMultiplied", int.class);
		assertEquals(r3.getValue(), 18, 0);
		assertEquals(r2.getValue(), 12, 0);
		p1Num.unweave();
		assertEquals(r3.getValue(), 4.5, 0);
		assertEquals(r2.getValue(), 3, 0);
		p1Den.unweave();
		assertEquals(r3.getValue(), 9, 0);
		assertEquals(r2.getValue(), 6, 0);

	}

	private void setRequirement() throws Throwable {
		// para preservar una invariante de clase -> precondición(es) para el
		// setter
		RationalNumber r1 = new RationalNumber(1, 4);
		r1.denominator = 0;
		assertEquals(r1.denominator, 0);
		Weaver.weaveAspectForFieldAccessSetAround(COMPONENT_CLASS, "denominator", ASPECT_SAFE_CLASS,
				"safeDenominatorSetter", int.class);
		try {
			r1.denominator = 0;
			fail();
		} catch (IllegalArgumentException e) {
			Weaver.weaveAspectForFieldAccessSetAround(COMPONENT_CLASS, "denominator", ASPECT_SAFE_CLASS,
					"exceptionController", int.class);
			r1.denominator = 0;
			assertEquals(r1.denominator, 1);
		}
	}

	@Test
	public void test() throws Throwable {
		fieldAccessCounter();
		setRequirement();
		getAroundMultiply();
	}
}
