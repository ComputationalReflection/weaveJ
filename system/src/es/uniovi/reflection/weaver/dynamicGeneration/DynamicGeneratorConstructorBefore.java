package es.uniovi.reflection.weaver.dynamicGeneration;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import es.uniovi.reflection.weaver.dynamicGeneration.BaseDynamicGenerator;
import es.uniovi.reflection.weaver.dynamicGeneration.DynamicGeneratorManager;
import es.uniovi.reflection.weaver.methods.Method;

public class DynamicGeneratorConstructorBefore extends BaseDynamicGenerator {

	@Override
	public void invokeMethodsBefore(MethodVisitor methodVisitor,
			Method aspectMethod) {
		DynamicGeneratorManager.loadLocals(methodVisitor,
				aspectMethod.getType());
		methodVisitor.visitMethodInsn(Opcodes.INVOKESTATIC, aspectMethod
				.getKlass().getName().replace(".", "/"),
				aspectMethod.getName(), aspectMethod.getDesc(), false);
	}
}
