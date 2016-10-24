package weaver.dynamicGeneration;

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
