package es.uniovi.reflection.weaver.test.component;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import es.uniovi.reflection.weaver.Pointcut;
import es.uniovi.reflection.weaver.Weaver;
import es.uniovi.reflection.weaver.test.aspects.RationalAspects;

public  class TestAspectConstructor {

	private final String ASPECT_CLASS = "test.aspects.RationalAspects";

	private final String COMPONENT_CLASS = "test.component.RationalNumber";

	@Test
	public void test() throws Throwable {
		testBefore();
		testAddTwoComposed();
	}

	private void testAddTwoComposed() throws Throwable {
		assertEquals(new RationalNumber(1, 1).numerator, 1);
		Pointcut p1 = Weaver.weaveAspectForConstructorAfter(COMPONENT_CLASS,
				ASPECT_CLASS, "addOneAfter", int.class, int.class);
		assertEquals(new RationalNumber(1, 1).numerator, 2);
		Pointcut p2 = Weaver.weaveAspectForConstructorAround(COMPONENT_CLASS,
				ASPECT_CLASS, "addOneAround", int.class, int.class);
		assertEquals(new RationalNumber(1, 1).numerator, 3);
		p2.unweave();
		assertEquals(new RationalNumber(1, 1).numerator, 2);
		p1.unweave();
		assertEquals(new RationalNumber(1, 1).numerator, 1);

	}

	private void testBefore() throws Throwable {
		assertEquals(RationalAspects.numberInstances, 0);
		new RationalNumber(1, 1);
		assertEquals(RationalAspects.numberInstances, 0);
		Pointcut pointcut = Weaver.weaveAspectForConstructorBefore(
				COMPONENT_CLASS, ASPECT_CLASS, "countBefore", int.class,
				int.class);
		new RationalNumber(1, 1);
		new RationalNumber(1, 1);
		assertEquals(RationalAspects.numberInstances, 2);
		pointcut.unweave();
		new RationalNumber(1, 1);
		assertEquals(RationalAspects.numberInstances, 2);

	}

}
