package es.uniovi.reflection.weaver.dynamicGeneration;

import org.objectweb.asm.MethodVisitor;

import es.uniovi.reflection.weaver.dynamicGeneration.BaseDynamicGenerator;
import es.uniovi.reflection.weaver.dynamicGeneration.DynamicGeneratorManager;
import es.uniovi.reflection.weaver.dynamicGeneration.OpcodesUtil;
import es.uniovi.reflection.weaver.methods.Method;


public class DynamicGeneratorBefore extends BaseDynamicGenerator{

	@Override
	public void invokeMethodsBefore(MethodVisitor methodVisitor,Method aspectMethod){
		DynamicGeneratorManager.loadLocals(methodVisitor, aspectMethod.getType());
		DynamicGeneratorManager.invokeMethod(methodVisitor, aspectMethod);
		if(!aspectMethod.getReturnType().equals(void.class))
			methodVisitor.visitInsn(OpcodesUtil.getPopOpcode(aspectMethod.getReturnType()));
	} 
}
