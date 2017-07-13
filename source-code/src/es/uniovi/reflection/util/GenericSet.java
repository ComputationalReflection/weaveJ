package es.uniovi.reflection.util;

import java.util.HashSet;
import java.util.Set;

/**
 * Utility class used in the dynamic class generation process. More
 * specifically, it is used when instance level weaving is required.
 * 
 * @author Oscar Rodriguez-Prieto Date: 2017/07/11
 * 
 * @version 1.1.0
 *
 *
 */
public class GenericSet {

	private Set<Object> set = new HashSet<Object>();

	public void add(Object o) {
		set.add(o);
	}

	public void remove(Object o) {
		set.remove(o);
	}

	public boolean contains(Object o) {
		return set.contains(o);
	}
}
