package es.uniovi.reflection.test.aspects;

import java.lang.invoke.MethodHandle;

import es.uniovi.reflection.test.component.RationalNumber;

/**
 * Stateful aspect to weave Around pointcuts along with
 * {@link es.uniovi.reflection.test.component.RationalNumber RationalNumber}
 * methods.
 * 
 * @author Oscar Rodriguez-Prieto Date: 2017/07/11
 * 
 * @version 1.1.0
 *
 */
public class RationalMultipliedBy {

	private RationalNumber factor;

	public RationalMultipliedBy(RationalNumber factor) {
		this.factor = factor;
	}

	public int getMultiplied(String fieldName, MethodHandle getter, RationalNumber r) throws Throwable {
		return (int) getter.invoke(r) * (fieldName.contentEquals("numerator") ? factor.numerator : factor.denominator);
	}
}
