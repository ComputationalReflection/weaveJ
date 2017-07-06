package es.uniovi.reflection.util;

import java.util.HashSet;
import java.util.Set;

public class GenericSet {

	private Set<Object> set=new HashSet<Object>();

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
