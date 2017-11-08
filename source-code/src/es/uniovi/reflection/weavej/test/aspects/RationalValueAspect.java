package es.uniovi.reflection.weavej.test.aspects;

import java.lang.invoke.MethodHandle;

import es.uniovi.reflection.weavej.test.component.RationalNumber;

/**
 * Stateful aspect to weave Around pointcuts along with
 * {@link es.uniovi.reflection.weavej.test.component.RationalNumber RationalNumber}
 * methods.
 * 
 * @author Oscar Rodriguez-Prieto Date: 2017/07/11
 * 
 * @version 1.1.0
 *
 */
public class RationalValueAspect {

	private int limit;
	private boolean upLimit;

	public RationalValueAspect(int limit, boolean upLimit) {
		this.limit = limit;
		this.upLimit = upLimit;
	}

	public double getAcotedValue(MethodHandle mh, RationalNumber r) throws Throwable {
		double value = (double) mh.invoke(r);
		boolean control = upLimit ? (value > limit) : (value < limit);
		return control ? limit : value;

	}
}
