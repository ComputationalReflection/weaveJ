package es.uniovi.reflection.weaver.dynamicGeneration;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import es.uniovi.reflection.weaver.dynamicGeneration.BaseDynamicGenerator;
import es.uniovi.reflection.weaver.dynamicGeneration.DynamicGeneratorManager;
import es.uniovi.reflection.weaver.methods.Method;

/**
 * {@link es.uniovi.reflection.weaver.dynamicGeneration.Wrapper Wrapper }
 * implementation to dynamically generate woven constructors when before advice
 * type is required. Its functionality is partially implemented delegating to
 * the {@link es.uniovi.reflection.weaver.dynamicGeneration.BaseDynamicGenerator
 * BaseDynamicGenerator}, its superclass.
 * 
 * 
 * @author Oscar Rodriguez-Prieto Date: 2017/07/11
 * 
 * @version 1.1.0
 *
 */
public class DynamicGeneratorConstructorBefore extends BaseDynamicGenerator {

	@Override
	public void invokeMethodsBefore(MethodVisitor methodVisitor, Method aspectMethod) {
		DynamicGeneratorManager.loadLocals(methodVisitor, aspectMethod.getType());
		methodVisitor.visitMethodInsn(Opcodes.INVOKESTATIC, aspectMethod.getKlass().getName().replace(".", "/"),
				aspectMethod.getName(), aspectMethod.getDesc(), false);
	}
}
