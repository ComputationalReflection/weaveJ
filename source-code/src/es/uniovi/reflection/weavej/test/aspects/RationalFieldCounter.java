package es.uniovi.reflection.weavej.test.aspects;

import java.util.HashMap;
import java.util.Map;

import es.uniovi.reflection.weavej.test.component.RationalNumber;

/**
 * 
 * Static class with an aspect method to be used in the unit tests. These
 * methods are designed to be woven along with
 * {@link es.uniovi.reflection.weavej.test.component.RationalNumber RationalNumber}
 * getters and setters. Due to its signature the method only can be applied to
 * getters combined with the before advice type or setters combined with the
 * after advice type. Therefore, it could be used to count both field accesses
 * and modifications.
 * 
 * @author Oscar Rodriguez-Prieto Date: 2017/07/11
 * 
 * @version 1.1.0
 *
 */
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
