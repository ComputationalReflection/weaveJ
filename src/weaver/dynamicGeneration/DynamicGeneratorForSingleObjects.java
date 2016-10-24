package weaver.dynamicGeneration;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.util.HashSet;
import java.util.Set;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import weaver.PointcutImpl;
import weaver.methods.Method;

public class DynamicGeneratorForSingleObjects extends DefaultComponentGenerator implements Wrapper {

	private Label falseLabel;
	private MethodType methodType;

	public DynamicGeneratorForSingleObjects() {
		super();
		falseLabel = new Label();
	}

	public DynamicGeneratorForSingleObjects(Wrapper gen) {
		super(gen);
		falseLabel = new Label();
	}

	@Override
	public MethodHandle initAndGetWovenMethod(Class<?> dynamicClass, MethodType generatedMethodType, PointcutImpl p)
			throws Throwable {
		MethodHandles.lookup().findStaticSetter(dynamicClass, "set", Set.class).invoke(new HashSet<Object>());
		return super.initAndGetWovenMethod(dynamicClass, generatedMethodType, p);
	}

	@Override
	public void invokeMethodsAfter(MethodVisitor methodVisitor, Method aspectMethod) {
		generator.invokeMethodsAfter(methodVisitor, aspectMethod);

		methodVisitor.visitInsn(Opcodes.RETURN);
		methodVisitor.visitLabel(falseLabel);

		DynamicGeneratorManager.visitComponentMethod(methodVisitor, methodType);
		methodVisitor.visitInsn(OpcodesUtil.getReturnOpcode(methodType.returnType()));
	}

	@Override
	public void invokeMethodsBefore(MethodVisitor methodVisitor, Method aspectMethod)
			throws NoSuchFieldException, IllegalAccessException, NoSuchMethodException, Throwable {
		methodVisitor.visitMethodInsn(Opcodes.GETSTATIC, "DynamicClass" + DynamicGeneratorManager.generatedClassIndex,
				"set", "Ljava/util/Set<L" + methodType.parameterType(0).getName().replace(".", "/") + ";>;", false);
		methodVisitor.visitVarInsn(Opcodes.ALOAD, 0);
		methodVisitor.visitMethodInsn(Opcodes.INVOKESPECIAL, "Ljava/util/Set;", "contains",
				MethodType.methodType(boolean.class, methodType.parameterType(0)).toMethodDescriptorString(), true);
		methodVisitor.visitJumpInsn(Opcodes.IFEQ, falseLabel);
		generator.invokeMethodsBefore(methodVisitor, aspectMethod);
	}

	@Override
	public MethodHandle prepareMethods(MethodHandle componentMethod, Method aspectMethod)
			throws NoSuchFieldException, IllegalAccessException, NoSuchMethodException, Throwable {
		methodType = componentMethod.type();
		return generator.prepareMethods(componentMethod, aspectMethod);
	}

	@Override
	public void visitMethodHandleSetter(ClassWriter cw) {
		generator.visitMethodHandleSetter(cw);
		cw.visitField(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, "set",
				"Ljava/util/Set<L" + methodType.parameterType(0).getName().replace(".", "/") + ";>;", null, null)
				.visitEnd();
	}
}
