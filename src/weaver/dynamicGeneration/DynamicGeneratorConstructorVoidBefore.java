package weaver.dynamicGeneration;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import weaver.methods.Method;

public class DynamicGeneratorConstructorVoidBefore extends BaseDynamicGenerator {

	@Override
	public void invokeMethodsBefore(MethodVisitor methodVisitor, Method aspectMethod) {
		DynamicGeneratorManager.loadLocals(methodVisitor, aspectMethod.getType().dropParameterTypes(0, 1));
		methodVisitor.visitMethodInsn(Opcodes.INVOKESTATIC, aspectMethod.getKlass().getName().replace(".", "/"),
				aspectMethod.getName(), aspectMethod.getDesc(), false);
	}
}
