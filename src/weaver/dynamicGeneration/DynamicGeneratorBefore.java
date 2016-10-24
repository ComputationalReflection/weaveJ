package weaver.dynamicGeneration;

import org.objectweb.asm.MethodVisitor;

import weaver.methods.Method;


public class DynamicGeneratorBefore extends BaseDynamicGenerator{

	@Override
	public void invokeMethodsBefore(MethodVisitor methodVisitor,Method aspectMethod){
		DynamicGeneratorManager.loadLocals(methodVisitor, aspectMethod.getType());
		DynamicGeneratorManager.invokeMethod(methodVisitor, aspectMethod);
		if(!aspectMethod.getReturnType().equals(void.class))
			methodVisitor.visitInsn(OpcodesUtil.getPopOpcode(aspectMethod.getReturnType()));
	} 
}
