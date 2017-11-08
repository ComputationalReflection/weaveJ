package es.uniovi.reflection.weavej.weaver.dynamicGeneration;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodType;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;

import es.uniovi.reflection.weavej.weaver.PointcutImpl;
import es.uniovi.reflection.weavej.weaver.methods.Method;

/**
 * Wrapper interface used to nest different types of dynamic generators. These
 * generators can delegate to more nested generators, each one adding different
 * pieces of information in different places before, after and/or around the
 * component method.
 * 
 * @author Oscar Rodriguez-Prieto Date: 2017/07/11
 * 
 * @version 1.1.0
 *
 */

public interface Wrapper {

	MethodHandle initAndGetWovenMethod(Class<?> dynamicClass, MethodType generatedMethodType, PointcutImpl pointcut)
			throws Throwable;

	void visitMethodHandleSetter(ClassWriter cw);

	void invokeMethodsBefore(MethodVisitor methodVisitor, Method aspectMethod)
			throws NoSuchFieldException, IllegalAccessException, NoSuchMethodException, Throwable;

	void invokeMethodsAfter(MethodVisitor methodVisitor, Method aspectMethod);

	MethodHandle prepareMethods(MethodHandle componentMethod, Method aspectMethod)
			throws NoSuchFieldException, IllegalAccessException, NoSuchMethodException, Throwable;

}
