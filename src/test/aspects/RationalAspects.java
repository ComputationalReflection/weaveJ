package test.aspects;

import java.lang.invoke.MethodHandle;

import test.component.RationalNumber;
import test.component.ZeroAsDenominatorException;

public class RationalAspects {
	public static int numberInstances = 0;

	public static void addOneAfter(RationalNumber r) {
		r.numerator += r.denominator;
	}

	public static RationalNumber addOneAround(MethodHandle mh, int numerator, int denominator) throws Throwable {

		return (RationalNumber) mh.invoke(numerator + denominator, denominator);
	}

	public static void checkBeforeMatrix(RationalNumber r, int factor, RationalNumber[] rationals) {
		if (rationals.length == 0 || factor == 0)
			throw new IllegalArgumentException();
	}

	public static void countBefore(int numerator, int denominator) {
		numberInstances++;
	}

	public static void exceptionController(MethodHandle setter, RationalNumber r, int val) throws Throwable {
		try {
			setter.invoke(r, val);
		} catch (IllegalArgumentException e) {
			setter.invoke(r, 1);
		}
	}

	public static int[][] handleExceptionMatrix(RuntimeException rExp, RationalNumber r, int factor,
			RationalNumber[] rationals) {
		System.out.println(
				"Attempt to build a rational matrix with some null argument, an empty array or a factor of zero.");
		return new int[1][1];
	}

	public static RationalNumber handleExceptionSimplify(RuntimeException e, RationalNumber r) {
		System.out.println("Rational " + r + " cannot be simplified more.");
		return r;
	}

	public static RationalNumber handleExceptionWithOne(ZeroAsDenominatorException e, int numerator, int denominator) {
		return new RationalNumber(numerator, 1);
	}

	public static void safeDenominatorSetter(MethodHandle setter, RationalNumber r, int val) throws Throwable {
		if (val == 0)
			throw new IllegalArgumentException();
		setter.invoke(r, val);
	}

	public static void toZerosMatrixAfter(RationalNumber r, int[][] matrix) {
		for (int i = 0; i < matrix.length; i++)
			for (int j = 0; j < matrix[i].length; j++)
				matrix[i][j] = 0;
	}

	public static String wrappedToString(MethodHandle oldToString, RationalNumber r) throws Throwable {
		return oldToString.invoke(r) + ":" + r.getValue();
	}
}
