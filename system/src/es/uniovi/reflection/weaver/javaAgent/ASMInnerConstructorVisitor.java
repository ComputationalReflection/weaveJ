package es.uniovi.reflection.weaver.javaAgent;

import org.objectweb.asm.MethodVisitor;

public class ASMInnerConstructorVisitor extends ASMConstructorVisitor {
	private boolean firstPutFieldInInnerCons = true;

	public ASMInnerConstructorVisitor(int arg0, MethodVisitor arg1) {
		super(arg0, arg1);
	}

	@Override
	public void visitFieldInsn(int opcode, String owner, String name, String desc) {
		if (firstPutFieldInInnerCons) {
			firstPutFieldInInnerCons = false;
			super.superVisitField(opcode, owner, name, desc);
		}else{
			super.visitFieldInsn(opcode, owner, name, desc);
		}
	}

}
