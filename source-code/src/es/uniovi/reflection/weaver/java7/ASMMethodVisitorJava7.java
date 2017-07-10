package es.uniovi.reflection.weaver.java7;

import org.objectweb.asm.MethodVisitor;

import es.uniovi.reflection.weaver.javaAgent.ASMMethodVisitor;

public class ASMMethodVisitorJava7 extends ASMMethodVisitor {

	static boolean DEBUG = false;

	protected boolean isLastRemovedNew;

	public ASMMethodVisitorJava7(int arg0, MethodVisitor arg1) {
		super(arg0, arg1);
	}

	@Override
	public void visitInsn(int arg0) {
		super.superVisitIns(arg0);
	}

	@Override
	public void dup() {
		super.superDup();
	}

	@Override
	public void invokespecial(String owner, String name, String desc, boolean itf) {
		super.superInvokespecial(owner, name, desc, itf);
	}

	@Override
	public void anew(org.objectweb.asm.Type arg0) {
		super.superAnew(arg0);
	}
}
