package weaver.dynamicGeneration;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodType;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;

import weaver.PointcutImpl;
import weaver.methods.Method;

public interface Wrapper {


	MethodHandle initAndGetWovenMethod(Class<?> dynamicClass,
			MethodType generatedMethodType, PointcutImpl pointcut) throws Throwable;

	void visitMethodHandleSetter(ClassWriter cw);

	void invokeMethodsBefore(MethodVisitor methodVisitor, Method aspectMethod) throws NoSuchFieldException, IllegalAccessException, NoSuchMethodException, Throwable;

	void invokeMethodsAfter(MethodVisitor methodVisitor, Method aspectMethod);

	MethodHandle prepareMethods(MethodHandle componentMethod,
			Method aspectMethod) throws NoSuchFieldException, IllegalAccessException, NoSuchMethodException, Throwable;

}
