package es.uniovi.reflection.weavej.weaver.javaAgent;

import org.objectweb.asm.MethodVisitor;

/**
 * 
 * Subclass of
 * {@link es.uniovi.reflection.weavej.weaver.javaAgent.ASMConstructorVisitor
 * ASMConstructorVisitor} to transform specific class constructors in load time.
 * These classes must be inner classes, otherwise other adapter is used instead.
 * This adapter omits the first putfield instruction in the constructor, in
 * order to make inner classes work correctly.
 * 
 * @author Oscar Rodriguez-Prieto Date: 2017/07/11
 * 
 * @version 1.1.0
 *
 */
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
		} else {
			super.visitFieldInsn(opcode, owner, name, desc);
		}
	}

}
