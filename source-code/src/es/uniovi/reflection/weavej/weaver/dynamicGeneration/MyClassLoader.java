package es.uniovi.reflection.weavej.weaver.dynamicGeneration;

import es.uniovi.reflection.weavej.weaver.dynamicGeneration.MyClassLoader;

/**
 * Class that extends java.lang.ClassLoader to load dynamically generated or
 * modified classes into memory.
 * 
 * @author Oscar Rodriguez-Prieto Date: 2017/07/11
 * 
 * @version 1.1.0
 *
 */

public class MyClassLoader extends ClassLoader {
	private static MyClassLoader instance = null;

	public static MyClassLoader getClassLoader() {
		if (instance == null)
			instance = new MyClassLoader();
		return instance;
	}

	private MyClassLoader() {
		super(ClassLoader.getSystemClassLoader());
	}

	public MyClassLoader(ClassLoader loader) {
		super(loader);
	}

	public Class<?> includeClass(String name, byte[] code) {
		return defineClass(name.replace("/", "."), code, 0, code.length);

	}
}
