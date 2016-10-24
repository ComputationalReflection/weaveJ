package test.aspects;

import java.lang.invoke.MethodHandle;

import test.component.RationalNumber;

public class RationalValueAspect {

	private int limit;
	private boolean upLimit;

	public RationalValueAspect(int limit, boolean upLimit) {
		this.limit = limit;
		this.upLimit = upLimit;
	}

	public double getAcotedValue(MethodHandle mh, RationalNumber r)
			throws Throwable {
		double value = (double) mh.invoke(r);
		boolean control = upLimit ? (value > limit) : (value < limit);
		return control ? limit : value;

	}
}
