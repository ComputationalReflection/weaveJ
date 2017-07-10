package es.uniovi.reflection.weaver.dynamicGeneration;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodType;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;

import es.uniovi.reflection.weaver.PointcutImpl;
import es.uniovi.reflection.weaver.dynamicGeneration.DynamicGeneratorManager;
import es.uniovi.reflection.weaver.dynamicGeneration.Wrapper;
import es.uniovi.reflection.weaver.methods.Method;

public class DefaultComponentGenerator implements Wrapper {
	protected Wrapper generator;

	public DefaultComponentGenerator() {
		generator = DynamicGeneratorManager.baseGen;
	}

	public DefaultComponentGenerator(Wrapper gen) {
		this.generator = gen;
	}

	@Override
	public MethodHandle prepareMethods(MethodHandle componentMethod,
			Method aspectMethod) throws NoSuchFieldException, IllegalAccessException, NoSuchMethodException, Throwable {
		return generator.prepareMethods(componentMethod, aspectMethod);
	}


	@Override
	public void visitMethodHandleSetter(ClassWriter cw) {
		generator.visitMethodHandleSetter(cw);
	}

	@Override
	public void invokeMethodsBefore(MethodVisitor methodVisitor,
			Method aspectMethod) throws NoSuchFieldException, IllegalAccessException, NoSuchMethodException, Throwable {
		generator.invokeMethodsBefore(methodVisitor, aspectMethod);
	}

	@Override
	public void invokeMethodsAfter(MethodVisitor methodVisitor,
			Method aspectMethod) {
		generator.invokeMethodsAfter(methodVisitor, aspectMethod);
	}


	@Override
	public MethodHandle initAndGetWovenMethod(Class<?> dynamicClass,
			MethodType generatedMethodType, PointcutImpl pointcut) throws Throwable {
		return generator.initAndGetWovenMethod(dynamicClass, generatedMethodType, pointcut);
	}
	

}
