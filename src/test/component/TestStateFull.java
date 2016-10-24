package test.component;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import test.aspects.RationalCounter;
import test.aspects.RationalValueAspect;
import weaver.Pointcut;
import weaver.Weaver;

public class TestStateFull {

	private final String COMPONENT_CLASS = "test.component.RationalNumber";

	@Test
	public void test() throws Throwable {
		testConstructor();
		testMethod();
	}

	private void testConstructor() throws Throwable {

		RationalCounter counterGreaterEqThanFive = new RationalCounter(5);
		RationalCounter counterGreaterEqThanSix = new RationalCounter(6);
		RationalCounter counterGreaterEqThanFour = new RationalCounter(4);

		Weaver.weaveAspectForConstructorBefore(COMPONENT_CLASS, counterGreaterEqThanFive,
				"countIfGreater", int.class, int.class);
		Pointcut p6 = Weaver.weaveAspectForConstructorBefore(COMPONENT_CLASS, counterGreaterEqThanSix, "countIfGreater",
				int.class, int.class);
		Pointcut p4 = Weaver.weaveAspectForConstructorBefore(COMPONENT_CLASS, counterGreaterEqThanFour,
				"countIfGreater", int.class, int.class);
		assertEquals(counterGreaterEqThanFive.getCounter(), 0);
		new RationalNumber(6, 1);
		new RationalNumber(11, 2);
		new RationalNumber(4, 1);
		assertEquals(counterGreaterEqThanFive.getCounter(), 2);
		assertEquals(counterGreaterEqThanFour.getCounter(), 3);
		assertEquals(counterGreaterEqThanSix.getCounter(), 1);
		p4.unweave();
		p6.unweave();
		new RationalNumber(10, 2);
		assertEquals(counterGreaterEqThanFive.getCounter(), 3);
		assertEquals(counterGreaterEqThanFour.getCounter(), 3);
		assertEquals(counterGreaterEqThanSix.getCounter(), 1);
	}

	private void testMethod() throws Throwable {
		RationalValueAspect lessEqThanTen = new RationalValueAspect(10, true);
		RationalValueAspect gretaerEqThanFive = new RationalValueAspect(5, false);
		RationalNumber r12 = new RationalNumber(24, 2);
		RationalNumber r3 = new RationalNumber(9, 3);

		assertEquals(r12.getValue(), 12.0, 0);
		assertEquals(r3.getValue(), 3, 0);
		Pointcut p1 = Weaver.weaveAspectForMethodAround(COMPONENT_CLASS, "getValue", lessEqThanTen, "getAcotedValue",
				double.class);
		assertEquals(r12.getValue(), 10, 0);
		assertEquals(r3.getValue(), 3, 0);
		Pointcut p2 = Weaver.weaveAspectForMethodAround(COMPONENT_CLASS, "getValue", gretaerEqThanFive,
				"getAcotedValue", double.class);
		assertEquals(r12.getValue(), 10, 0);
		assertEquals(r3.getValue(), 5, 0);
		p1.unweave();
		assertEquals(r3.getValue(), 5, 0);
		assertEquals(r12.getValue(), 12.0, 0);
		p2.unweave();
		assertEquals(r12.getValue(), 12.0, 0);
		assertEquals(r3.getValue(), 3, 0);
	}

}