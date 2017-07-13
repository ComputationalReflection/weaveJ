package es.uniovi.reflection.weaver.javaAgent;

import java.util.Set;

import org.objectweb.asm.MethodVisitor;

/**
 * 
 * Subclass of
 * {@link es.uniovi.reflection.weaver.javaAgent.ASMConstructorVisitor
 * ASMConstructorVisitor } to transform specific class constructors in load
 * time. These classes must have any final field, otherwise other adapter is
 * used instead. This adapter checks the field name of every putfield opcode
 * and, if this name is not contained in the list of final fields, the
 * replacement by invoke-dynamic is done.
 * 
 * @author Oscar Rodriguez-Prieto Date: 2017/07/11
 * 
 * @version 1.1.0
 *
 */
public class ASMConstructorWithFinalFields extends ASMConstructorVisitor {

	private Set<String> finalFields;

	public ASMConstructorWithFinalFields(int arg0, MethodVisitor arg1, Set<String> finalFields) {
		super(arg0, arg1);
		this.finalFields = finalFields;
	}

	@Override
	public void visitFieldInsn(int opcode, String owner, String name, String desc) {
		if (finalFields.contains(name))
			super.normalVisitFieldInsn(opcode, owner, name, desc);
		else
			super.visitFieldInsn(opcode, owner, name, desc);
	}
}
