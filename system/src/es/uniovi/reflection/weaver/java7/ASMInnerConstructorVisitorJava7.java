package es.uniovi.reflection.weaver.java7;

import org.objectweb.asm.MethodVisitor;

public class ASMInnerConstructorVisitorJava7 extends ASMMethodVisitorJava7 {
	private boolean firstPutFieldInInnerCons = true;

	public ASMInnerConstructorVisitorJava7(int arg0, MethodVisitor arg1) {
		super(arg0, arg1);
	}

	@Override
	public void visitFieldInsn(int opcode, String owner, String name, String desc) {
		if (firstPutFieldInInnerCons) {
			firstPutFieldInInnerCons = false;
			super.superVisitField(opcode, owner, name, desc);
		} else {
			super.visitFieldInsn(opcode, owner, name, desc);
		}
	}

}
