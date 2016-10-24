package weaver.javaAgent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import weaver.dynamicGeneration.MyClassLoader;
import weaver.dynamicGeneration.OpcodesUtil;

public class ConstructorAdapterGeneration {
	private static Map<String, Class<?>> adaptersCache = new HashMap<String, Class<?>>();
	private static Map<Character, Class<?>> charToClass = new HashMap<Character, Class<?>>();

	private static Set<String> classesWithPublicConstructors = new HashSet<String>();

	private static boolean DEBUG = false;

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

	public static Class<?> get(String className, String desc) {
		String key = className + "%%" + desc;
		if (!adaptersCache.containsKey(key))
			adaptersCache.put(key, getClass(className, desc));
		return adaptersCache.get(key);
	}

	public static Class<?> getClass(String className, String desc) {
		byte[] code = getCode(className, desc);
		if (DEBUG)
			System.out.println(
					"Clase del constructor generado dinámicamente:\n" + InstrumentorTransformer.byteCodeSpy(code));
		return MyClassLoader.getClassLoader().includeClass("DynamicGeneratedInitAdapterFor" + className, code);
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

	private static byte[] getCode(String className, String desc) {

		if (DEBUG)
			System.out.println(
					"Construyendo dinámicamente la clase para el adaptador del constructor." + className + " " + desc);
		ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
		cw.visit(Opcodes.V1_8, Opcodes.ACC_PUBLIC, "DynamicGeneratedInitAdapterFor" + className, null,
				"java/lang/Object", new String[] {});
		visitInit(cw, desc, className);
		cw.visitEnd();
		return cw.toByteArray();
	}

	public static boolean hasConstructor(String className, String desc) {
		return adaptersCache.containsKey(className + "%%" + desc);
	}

	public static void visitInit(ClassWriter cw, String desc, String className) {
		MethodVisitor methodVisitor;
		{
			methodVisitor = cw.visitMethod(Opcodes.ACC_STATIC + Opcodes.ACC_PUBLIC, "init", desc, null, null);
			methodVisitor.visitCode();
			methodVisitor.visitTypeInsn(Opcodes.NEW, className);
			methodVisitor.visitInsn(Opcodes.DUP);
			int i = 0;
			for (Class<?> c : getClassParamsFromDescriptor(desc.substring(1)))
				methodVisitor.visitIntInsn(OpcodesUtil.getLoadOpcode(c), i++);
			String[] paramsRet = desc.split("\\)");
			methodVisitor.visitMethodInsn(Opcodes.INVOKESPECIAL, className, "<init>",
					"(" + paramsRet[0].substring(1, paramsRet[0].length()) + ")V", false);
			methodVisitor.visitInsn(Opcodes.ARETURN);
			methodVisitor.visitMaxs(10, 10);
			methodVisitor.visitEnd();
		}
	}
}
