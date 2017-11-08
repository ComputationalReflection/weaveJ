package es.uniovi.reflection.weavej.weaver.dynamicGeneration;

import org.objectweb.asm.MethodVisitor;

import es.uniovi.reflection.weavej.weaver.dynamicGeneration.BaseDynamicGenerator;
import es.uniovi.reflection.weavej.weaver.dynamicGeneration.DynamicGeneratorManager;
import es.uniovi.reflection.weavej.weaver.dynamicGeneration.OpcodesUtil;
import es.uniovi.reflection.weavej.weaver.methods.Method;

/**
 * {@link es.uniovi.reflection.weavej.weaver.dynamicGeneration.Wrapper Wrapper }
 * implementation to dynamically generate woven methods, getters or setters when
 * before advice type is required. Its functionality is partially implemented
 * delegating to the
 * {@link es.uniovi.reflection.weavej.weaver.dynamicGeneration.DynamicGeneratorManager
 * DynamicGeneratorManager } and its superclass.
 * 
 * 
 * @author Oscar Rodriguez-Prieto Date: 2017/07/11
 * 
 * @version 1.1.0
 *
 */
public class DynamicGeneratorBefore extends BaseDynamicGenerator {

	@Override
	public void invokeMethodsBefore(MethodVisitor methodVisitor, Method aspectMethod) {
		DynamicGeneratorManager.loadLocals(methodVisitor, aspectMethod.getType());
		DynamicGeneratorManager.invokeMethod(methodVisitor, aspectMethod);
		if (!aspectMethod.getReturnType().equals(void.class))
			methodVisitor.visitInsn(OpcodesUtil.getPopOpcode(aspectMethod.getReturnType()));
	}
}
