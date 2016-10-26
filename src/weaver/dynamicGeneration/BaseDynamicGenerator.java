package weaver.dynamicGeneration;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import weaver.PointcutImpl;
import weaver.PointcutManager;
import weaver.methods.Method;

public class BaseDynamicGenerator implements Wrapper {


	public void visitMethodHandleSetter(ClassWriter cw) {
		MethodVisitor methodVisitor;
		{
			methodVisitor = cw.visitMethod(Opcodes.ACC_STATIC
					+ Opcodes.ACC_PUBLIC, "iniciar",
					MethodType.methodType(void.class, MethodHandle.class)
							.toMethodDescriptorString(), null, null);
			methodVisitor.visitCode();
			methodVisitor.visitVarInsn(Opcodes.ALOAD, 0);
			methodVisitor.visitFieldInsn(Opcodes.PUTSTATIC, DynamicGeneratorManager.currentClassName, "mh",
					"Ljava/lang/invoke/MethodHandle;");
			methodVisitor.visitInsn(Opcodes.RETURN);
			methodVisitor.visitMaxs(3, 3);
			methodVisitor.visitEnd();
		}

	}

	public void invokeMethodsAfter(MethodVisitor methodVisitor,
			Method aspectMethod) {
	}

	public void invokeMethodsBefore(MethodVisitor methodVisitor,
			Method aspectMethod) {
	}

	@Override
	public MethodHandle prepareMethods(MethodHandle componentMethod,
			Method aspectMethod) {
		return componentMethod;
	}

	public MethodHandle initAndGetWovenMethod(Class<?> dynamicClass,
			MethodType generatedMethodType, PointcutImpl pointcut) throws Throwable {
		MethodHandle initiator = MethodHandles.lookup().findStatic(
				dynamicClass, "iniciar",
				MethodType.methodType(void.class, MethodHandle.class));
		MethodHandle wovenMethod = MethodHandles.lookup().findStatic(
				dynamicClass, "aspect", generatedMethodType);
		PointcutManager.invokeInitiator(initiator, wovenMethod, pointcut);
		return wovenMethod;
	}
}
