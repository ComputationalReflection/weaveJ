package es.uniovi.reflection.weavej.weaver.javaAgent;

import org.objectweb.asm.MethodVisitor;

/**
 * 
 * Subclass of {@link es.uniovi.reflection.weavej.weaver.javaAgent.ASMMethodVisitor
 * ASMMethodVisitor } to transform class constructors in load time. These
 * classes must have no final fields, otherwise the subclass
 * {@link es.uniovi.reflection.weavej.weaver.javaAgent.ASMConstructorWithFinalFields
 * ASMConstructorWithFinalFields } is used instead. These classes cannot be
 * inner classes, otherwise the subclass
 * {@link es.uniovi.reflection.weavej.weaver.javaAgent.ASMInnerConstructorVisitor
 * ASMInnerConstructorVisitor } is used instead. Different adapters are used
 * depending on whether a method or a constructor will be transformed. This is
 * because all constructor calls (invokespecial opcodes) except super calls are
 * substituted by invokedynamic instructions. This particular adapter for
 * constructors transforms all the invokespecial opcodes except the first, as it
 * is always used in a super call.
 * 
 * @author Oscar Rodriguez-Prieto Date: 2017/07/11
 * 
 * @version 1.1.0
 *
 */
public class ASMConstructorVisitor extends ASMMethodVisitor {
	private static final boolean DEBUG = false;
	private boolean firstInvokeespecial = true;

	public ASMConstructorVisitor(int arg0, MethodVisitor arg1) {
		super(arg0, arg1);
	}

	@Override
	public void anew(org.objectweb.asm.Type arg0) {
		if (firstInvokeespecial) {

			isLastRemovedNew = false;

			if (DEBUG)
				System.out.println("NEW allowed (first in a constructor--->super call). " + arg0.getClassName());
			super.superAnew(arg0);

		} else {
			super.anew(arg0);

		}

	}

	@Override
	public void invokespecial(String owner, String name, String desc, boolean itf) {
		if (DEBUG)
			System.out.println("Visiting INVOKESPECIAL " + name + " of " + owner + " with " + firstInvokeespecial);
		if (firstInvokeespecial && name.contains("<init>")) {
			super.superInvokespecial(owner, name, desc, itf);
			firstInvokeespecial = false;

			if (DEBUG)
				System.out.println("INVOKESPECIAL allowed (first in a cosntructor--->super call)" + owner + " " + name
						+ " " + desc);
		} else {
			super.invokespecial(owner, name, desc, itf);
			if (DEBUG)
				System.out.println("INVOKESPECIAL transformed (not super call)" + owner + " " + name + " " + desc);
		}
	}

	protected void normalVisitFieldInsn(int opcode, String owner, String name, String desc) {
		super.superVisitField(opcode, owner, name, desc);
	}
}
