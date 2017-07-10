package es.uniovi.reflection.test.aspects;

import java.util.HashMap;
import java.util.Map;

import es.uniovi.reflection.test.component.RationalNumber;

public class RationalFieldCounter {

	private static Map<String, Integer> fieldCount = new HashMap<String, Integer>();

	public static void countFieldAcces(String fieldName, RationalNumber r) {
		fieldCount.put(fieldName, fieldCount.get(fieldName) + 1);
	}

	static {
		fieldCount.put("numerator", 0);
		fieldCount.put("denominator", 0);
	}

	public static int getCountFor(String fieldName) {
		return fieldCount.get(fieldName);
	}
}
