package es.uniovi.reflection.weaver.dynamicGeneration;

import java.lang.invoke.MethodHandle;

import org.objectweb.asm.MethodVisitor;

import es.uniovi.reflection.weaver.dynamicGeneration.DefaultComponentGenerator;
import es.uniovi.reflection.weaver.dynamicGeneration.DynamicGeneratorManager;
import es.uniovi.reflection.weaver.dynamicGeneration.OpcodesUtil;
import es.uniovi.reflection.weaver.methods.Method;

/**
 * {@link es.uniovi.reflection.weaver.dynamicGeneration.Wrapper Wrapper }
 * implementation to dynamically generate woven methods, getters or setters when
 * after advice type is required. Its functionality is partially implemented
 * delegating to the
 * {@link es.uniovi.reflection.weaver.dynamicGeneration.BaseDynamicGenerator
 * BaseDynamicGenerator}, the
 * {@link es.uniovi.reflection.weaver.dynamicGeneration.DynamicGeneratorManager
 * DynamicGeneratorManager } and also to its superclass.
 * 
 * 
 * @author Oscar Rodriguez-Prieto Date: 2017/07/11
 * 
 * @version 1.1.0
 *
 */
public class DynamicGeneratorAfter extends DefaultComponentGenerator {

	private Class<?> returnType;

	@Override
	public void invokeMethodsAfter(MethodVisitor methodVisitor, Method aspectMethod) {
		if (!returnType.equals(void.class)) {
			methodVisitor.visitInsn(OpcodesUtil.getDupOpcode(returnType));
			methodVisitor.visitVarInsn(OpcodesUtil.getStoreOpcode(returnType), 1);
		}
		DynamicGeneratorManager.loadLocals(methodVisitor, aspectMethod.getType());
		DynamicGeneratorManager.invokeMethod(methodVisitor, aspectMethod);
	}

	@Override
	public MethodHandle prepareMethods(MethodHandle componentMethod, Method aspectMethod)
			throws NoSuchFieldException, IllegalAccessException, NoSuchMethodException, Throwable {
		returnType = componentMethod.type().returnType();
		return generator.prepareMethods(componentMethod, aspectMethod);
	}
}
