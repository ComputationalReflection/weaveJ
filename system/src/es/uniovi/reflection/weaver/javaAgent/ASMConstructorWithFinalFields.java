package es.uniovi.reflection.weaver.javaAgent;

import java.util.Set;

import org.objectweb.asm.MethodVisitor;

public class ASMConstructorWithFinalFields extends ASMConstructorVisitor {

	private Set<String> finalFields;

	public ASMConstructorWithFinalFields(int arg0, MethodVisitor arg1, Set<String> finalFields) {
		super(arg0, arg1);
		this.finalFields = finalFields;
	}

	@Override
	public void visitFieldInsn(int opcode, String owner, String name, String desc) {
		if(finalFields.contains(name))
			super.normalVisitFieldInsn(opcode, owner, name, desc);
		else
			super.visitFieldInsn(opcode, owner, name, desc);
	}
}
