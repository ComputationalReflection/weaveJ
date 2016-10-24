package weaver.dynamicGeneration;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodType;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import weaver.PointcutImpl;
import weaver.methods.Method;

public class DynamicGeneratorManager {
	protected static BaseDynamicGenerator baseGen = new BaseDynamicGenerator();
	public static int generatedClassIndex = 0;

	public static byte[] generateCode(Method aspectMethod, MethodHandle componentMethod, MethodType generatedMethodType,
			Wrapper wrapper) throws NoSuchFieldException, Throwable {
		ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
		visitClass(cw, aspectMethod, generatedMethodType, wrapper);
		cw.visitEnd();
		return cw.toByteArray();
	}

	public static Class<?> getDynamicClass(byte[] code, String className) {
		return MyClassLoader.getClassLoader().includeClass(className, code);
	}

	public static MethodHandle getDynamicGenerateMethod(Method aspectMethod, MethodHandle componentMethod,
			PointcutImpl p, Wrapper wrapper) throws Throwable {
		componentMethod = wrapper.prepareMethods(componentMethod, aspectMethod);
		MethodType generatedMethodType = componentMethod.type();
		byte[] code = generateCode(aspectMethod, componentMethod, generatedMethodType, wrapper);
		Class<?> dynamicClass = getDynamicClass(code, "DynamicClass" + generatedClassIndex);
		generatedClassIndex++;
		return wrapper.initAndGetWovenMethod(dynamicClass, generatedMethodType, p);
	}

	protected static void invokeMethod(MethodVisitor methodVisitor, Method method) {
		methodVisitor.visitMethodInsn(method.isStatic() ? Opcodes.INVOKESTATIC : Opcodes.INVOKEVIRTUAL,
				method.getKlass().getName().replace(".", "/"), method.getName(), method.getDesc(), false);
	}

	protected static void loadLocals(MethodVisitor methodVisitor, MethodType type) {
		Class<?>[] paramTypes = type.parameterArray();
		int total = paramTypes.length;
		for (int i = 0; i < total; i++)
			methodVisitor.visitVarInsn(OpcodesUtil.getLoadOpcode(paramTypes[i]), i);
	}

	private static void visitClass(ClassWriter cw, Method aspectMethod, MethodType generatedMethodType, Wrapper wrapper)
			throws NoSuchFieldException, IllegalAccessException, NoSuchMethodException, Throwable {
		cw.visit(Opcodes.V1_7, Opcodes.ACC_PUBLIC, "DynamicClass" + generatedClassIndex, null, "java/lang/Object",
				new String[] {});
		cw.visitField(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, "mh", "Ljava/lang/invoke/MethodHandle;", null, null)
				.visitEnd();
		wrapper.visitMethodHandleSetter(cw);
		visitWovenMethod(cw, generatedMethodType, aspectMethod, wrapper);
	}

	static void visitComponentMethod(MethodVisitor methodVisitor, MethodType generatedMethodType) {
		methodVisitor.visitFieldInsn(Opcodes.GETSTATIC, "DynamicClass" + generatedClassIndex, "mh",
				"Ljava/lang/invoke/MethodHandle;");
		loadLocals(methodVisitor, generatedMethodType);
		methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/invoke/MethodHandle", "invokeExact",
				generatedMethodType.toMethodDescriptorString(), false);
	}

	private static void visitWovenMethod(ClassWriter cw, MethodType generatedMethodType, Method aspectMethod,
			Wrapper wrapper) throws NoSuchFieldException, IllegalAccessException, NoSuchMethodException, Throwable {
		MethodVisitor methodVisitor;
		{
			methodVisitor = cw.visitMethod(Opcodes.ACC_STATIC + Opcodes.ACC_PUBLIC, "aspect",
					generatedMethodType.toMethodDescriptorString(), null, null);
			methodVisitor.visitCode();
			wrapper.invokeMethodsBefore(methodVisitor, aspectMethod);

			visitComponentMethod(methodVisitor, generatedMethodType);

			wrapper.invokeMethodsAfter(methodVisitor, aspectMethod);

			methodVisitor.visitInsn(OpcodesUtil.getReturnOpcode(generatedMethodType.returnType()));
			methodVisitor.visitMaxs(10, 10);
			methodVisitor.visitEnd();
		}
	}

}