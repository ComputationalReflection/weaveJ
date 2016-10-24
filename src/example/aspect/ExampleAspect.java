package example.aspect;

import java.lang.invoke.MethodHandle;

import example.component.Counter;

public class ExampleAspect {
	private static int limit = 100;

	public static int measurementsCounter = 0;

	public static void constructorAfterAspect(Counter c) {
		measurementsCounter++;
	}

	public static void constructorBeforeAspect(int uds, int limit) {
		measurementsCounter++;
	}

	public static Counter constructorNoProceedAround(MethodHandle constructor,
			int valorInicial, int limit) {
		measurementsCounter++;
		return new Counter(valorInicial, limit);
	}

	public static int doubleIncMethodCallAround(MethodHandle componentMethod,
			Counter c, int units) throws Throwable {

		// Proceed
		// Cuando no sabemos el método
		++measurementsCounter;
		return (int) componentMethod.invoke(c, units * 2);
	}

	public static int exceptionHandler(IllegalArgumentException e, Counter c,
			int param) {
		measurementsCounter++;
		return 1000;
	}

	public static Counter execptionHandlerSafeConstructor(
			IllegalArgumentException e, int param, int limit) {
		measurementsCounter++;
		return new Counter(limit, limit);
	}

	public static void getAfterAspect(Counter c, int getValue) {
		measurementsCounter++;
	}

	public static int getAroundAspect(MethodHandle getter, Counter c) {
		return measurementsCounter++;

	}

	public static void incrementedOperation(Counter c) {
		measurementsCounter++;
	}

	public static int limitAndInfiniteGetAround(MethodHandle getter, Counter c)
			throws Throwable {
		int ret = (int) getter.invoke(c);
		return measurementsCounter++ > limit ? Integer.MAX_VALUE : ret;

	}

	public static void limitedIncrementRangeSetAround(MethodHandle setter,
			Counter c, int value) throws Throwable {
		measurementsCounter++;

		if (!(Math.abs(c.getCounter() - value) > limit))
			setter.invoke(c, value);

	}

	public static void methodCallAfter(Counter c, int returnValue) {
		measurementsCounter++;
	}

	public static void methodCallBefore(Counter c, int param) {
		measurementsCounter++;
	}

	public static int noProceedMethodCallAround(MethodHandle componentMethod,
			Counter c, int units) throws Throwable {
		return ++measurementsCounter;
	}


	public static void setBeforeAspect(Counter c, int param) {
		measurementsCounter++;
	}

	public static Counter squaredConstructorAroundAspect(
			MethodHandle constructor, int valorInicial, int limit)
			throws Throwable {
		return (Counter) constructor.invoke(measurementsCounter++,
				measurementsCounter);
	}

}
