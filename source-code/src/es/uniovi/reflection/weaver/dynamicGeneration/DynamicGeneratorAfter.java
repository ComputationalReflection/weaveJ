package es.uniovi.reflection.weaver.dynamicGeneration;

import java.lang.invoke.MethodHandle;

import org.objectweb.asm.MethodVisitor;

import es.uniovi.reflection.weaver.dynamicGeneration.DefaultComponentGenerator;
import es.uniovi.reflection.weaver.dynamicGeneration.DynamicGeneratorManager;
import es.uniovi.reflection.weaver.dynamicGeneration.OpcodesUtil;
import es.uniovi.reflection.weaver.methods.Method;

public class DynamicGeneratorAfter extends DefaultComponentGenerator {

	private Class<?> returnType;

	@Override
	public void invokeMethodsAfter(MethodVisitor methodVisitor,
			Method aspectMethod) {
		if (!returnType.equals(void.class)) {
			methodVisitor.visitInsn(OpcodesUtil.getDupOpcode(returnType));
			methodVisitor.visitVarInsn(OpcodesUtil.getStoreOpcode(returnType),
					1);
		}
		DynamicGeneratorManager.loadLocals(methodVisitor,
				aspectMethod.getType());
		DynamicGeneratorManager.invokeMethod(methodVisitor, aspectMethod);
	}

	@Override
	public MethodHandle prepareMethods(MethodHandle componentMethod,
			Method aspectMethod) throws NoSuchFieldException,
			IllegalAccessException, NoSuchMethodException, Throwable {
		returnType = componentMethod.type().returnType();
		return generator.prepareMethods(componentMethod, aspectMethod);
	}
}
