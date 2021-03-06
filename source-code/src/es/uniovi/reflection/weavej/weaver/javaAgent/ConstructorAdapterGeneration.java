package es.uniovi.reflection.weavej.weaver.javaAgent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Static class used by the classes involved in the transformations at load lime
 * to get information relating java.lang.Class objects with different prefixes
 * and descriptors of VM opcodes.
 * 
 * @author Oscar Rodriguez-Prieto Date: 2017/07/11
 * 
 * @version 1.1.0
 *
 */
public class ConstructorAdapterGeneration {
	private static Map<Character, Class<?>> charToClass = new HashMap<Character, Class<?>>();

	private static Set<String> classesWithPublicConstructors = new HashSet<String>();

	static {
		charToClass.put('I', int.class);
		charToClass.put('Z', boolean.class);
		charToClass.put('F', float.class);
		charToClass.put('D', double.class);
		charToClass.put('C', char.class);
		charToClass.put('J', long.class);
		charToClass.put('S', short.class);
		charToClass.put('B', byte.class);

	}

	public static void addClassWithPublicConstructors(String className) {
		classesWithPublicConstructors.add(className);
	}

	public static boolean allPublicConstructors(String className) {
		return classesWithPublicConstructors.contains(className);
	}

	static List<Class<?>> getClassParamsFromDescriptor(String desc) {
		String[] classParams = desc.split(";");
		List<Class<?>> l = new ArrayList<Class<?>>();
		for (String paramGroup : classParams)
			for (char c : paramGroup.toCharArray())
				if (c != '[') {
					if (c == ')')
						break;
					if (c != 'L' && charToClass.containsKey(c))
						l.add(charToClass.get(c));
					else {
						l.add(ConstructorAdapterGeneration.class);
						break;
					}
				}
		return l;
	}

}
