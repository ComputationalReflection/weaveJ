package weaver.dynamicGeneration;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import weaver.methods.Method;

public class DynamicGeneratorConstructorAfter extends BaseDynamicGenerator {

	@Override
	public void invokeMethodsAfter(MethodVisitor methodVisitor, Method aspectMethod) {
		methodVisitor.visitInsn(Opcodes.DUP);
		methodVisitor.visitMethodInsn(Opcodes.INVOKESTATIC, aspectMethod.getKlass().getName().replace(".", "/"),
				aspectMethod.getName(), aspectMethod.getDesc(), false);
	}

}