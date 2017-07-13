package es.uniovi.reflection.test.component;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import es.uniovi.reflection.test.aspects.RationalAspects;
import es.uniovi.reflection.weaver.Pointcut;
import es.uniovi.reflection.weaver.Weaver;

/**
 * Unit test to verify the correct operation of the system when the constructor
 * joinpoint is chosen.
 * 
 * @author Oscar Rodriguez-Prieto Date: 2017/07/11
 * 
 * @version 1.1.0
 *
 *
 */
public class TestAspectConstructor {

	private final String ASPECT_CLASS = "es.uniovi.reflection.test.aspects.RationalAspects";

	private final String COMPONENT_CLASS = "es.uniovi.reflection.test.component.RationalNumber";

	@Test
	public void test() throws Throwable {
		testBefore();
		testAddTwoComposed();
	}

	private void testAddTwoComposed() throws Throwable {
		assertEquals(new RationalNumber(1, 1).numerator, 1);
		Pointcut p1 = Weaver.weaveAspectForConstructorAfter(COMPONENT_CLASS, ASPECT_CLASS, "addOneAfter", int.class,
				int.class);
		assertEquals(new RationalNumber(1, 1).numerator, 2);
		Pointcut p2 = Weaver.weaveAspectForConstructorAround(COMPONENT_CLASS, ASPECT_CLASS, "addOneAround", int.class,
				int.class);
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
		Pointcut pointcut = Weaver.weaveAspectForConstructorBefore(COMPONENT_CLASS, ASPECT_CLASS, "countBefore",
				int.class, int.class);
		new RationalNumber(1, 1);
		new RationalNumber(1, 1);
		assertEquals(RationalAspects.numberInstances, 2);
		pointcut.unweave();
		new RationalNumber(1, 1);
		assertEquals(RationalAspects.numberInstances, 2);

	}

}
