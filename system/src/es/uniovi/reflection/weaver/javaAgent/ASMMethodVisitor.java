package es.uniovi.reflection.weaver.javaAgent;

import java.lang.invoke.CallSite;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

import org.objectweb.asm.Handle;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.commons.InstructionAdapter;

public class ASMMethodVisitor extends InstructionAdapter implements Opcodes {

	static boolean DEBUG = false;

	protected boolean isLastRemovedNew;

	public ASMMethodVisitor(int arg0, MethodVisitor arg1) {
		super(arg0, arg1);
	}

	@Override
	public void anew(org.objectweb.asm.Type arg0) {
		if (PackageGroupTransformer.CLASS_TRANSFORMER.transformClass(arg0.getClassName().replace(".", "/"))
		/* && !arg0.getClassName().contains("$") */) {
			if (DEBUG)
				System.out.println("NEW ELIMINADO " + arg0.getClassName());
			isLastRemovedNew = true;

		} else {

			isLastRemovedNew = false;

			if (DEBUG)
				System.out.println("NEW con $ PERMITIDO " + arg0.getClassName());
			super.anew(arg0);

		}

	}

	@Override
	public void dup() {
		if (isLastRemovedNew) {
			if (DEBUG)
				System.out.println("DUP eliminado por ir después de NEW.");
		} else {
			if (DEBUG)
				System.out.println("DUP NO ELIMINADO.");
			super.dup();
		}

	}

	@Override
	public void invokespecial(String owner, String name, String desc, boolean itf) {
		if (name.contentEquals("<init>") && PackageGroupTransformer.CLASS_TRANSFORMER.transformClass(owner)
		/* && !owner.contains("$") */) {
			if (DEBUG)
				System.out.println("Modificando llamada especial :(" + owner + "):" + name + "(" + desc + ")");

			MethodType mt = MethodType.methodType(CallSite.class, MethodHandles.Lookup.class, String.class,
					MethodType.class);
			Handle h = new Handle(H_INVOKESTATIC, "weaver/DefaultBootstrap", "staticMethod",
					mt.toMethodDescriptorString());

			super.visitInvokeDynamicInsn("init", desc.replace(")V", ")L" + owner + ";"), h);

		} else {
			if (DEBUG)
				System.out.println("Llamada especial a método o constructor privado o de superclase sin modificar:("
						+ owner + "):" + name);

			super.invokespecial(owner, name, desc, itf);
		}
	}

	@Override
	public void invokevirtual(String owner, String name, String desc, boolean itf) {
		if (DEBUG)
			System.out.println("Modificando llamada de instancia :" + name + " de la clase " + owner + "(" + desc + ")-"
					+ desc.replace("(", "(L" + owner + ";"));

		MethodType mt = MethodType.methodType(CallSite.class, MethodHandles.Lookup.class, String.class,
				MethodType.class);
		Handle h = new Handle(H_INVOKESTATIC, "weaver/DefaultBootstrap", "method", mt.toMethodDescriptorString());

		super.visitInvokeDynamicInsn(name, desc.replace("(", "(L" + owner + ";"), h);

	}

	public void superAnew(org.objectweb.asm.Type arg0) {
		super.anew(arg0);
	}

	protected void superInvokespecial(String owner, String name, String desc, boolean itf) {
		super.invokespecial(owner, name, desc, itf);
	}
	protected void superDup() {
		super.dup();
	}

	protected void superVisitIns(int arg) {
		super.visitInsn(arg);
	}

	@Override
	public void visitFieldInsn(int opcode, String owner, String name, String desc) {
		if (DEBUG)
			System.out.println("Visitando instrucción de campo " + owner + " " + name + " " + opcode + " " + desc);

		if (desc.contains("[") && opcode == Opcodes.GETFIELD)
			superVisitField(opcode, owner, name, desc);
		else if (opcode == Opcodes.GETFIELD)

			visitGet(owner, name, desc);
		else if (opcode == Opcodes.PUTFIELD)
			visitSet(owner, name, desc);
		else
			superVisitField(opcode, owner, name, desc);
	}

	private void visitGet(String owner, String name, String desc) {
		if (DEBUG)
			System.out.println("Modificando GET al campo :" + name + " de la clase " + owner + "(" + desc + ")");
		MethodType mt = MethodType.methodType(CallSite.class, MethodHandles.Lookup.class, String.class,
				MethodType.class);
		Handle h = new Handle(H_INVOKESTATIC, "weaver/DefaultBootstrap", "get", mt.toMethodDescriptorString());
		super.visitInvokeDynamicInsn(name, "(L" + owner + ";)" + desc, h);

	}

	protected void superVisitField(int opcode, String owner, String name, String desc) {
		super.visitFieldInsn(opcode, owner, name, desc);
	}

	@Override
	public void visitInsn(int arg0) {
		if (DEBUG)
			System.out.println("Instrucción " + arg0 + " visitada.");

		super.visitInsn(arg0);
		if (arg0 != Opcodes.NEW)
			isLastRemovedNew = false;
	}

	private void visitSet(String owner, String name, String desc) {
		if (DEBUG)
			System.out.println("Modificando SET al campo :" + name + " de la clase " + owner + "(" + desc + ")");
		MethodType mt = MethodType.methodType(CallSite.class, MethodHandles.Lookup.class, String.class,
				MethodType.class);
		Handle h = new Handle(H_INVOKESTATIC, "weaver/DefaultBootstrap", "set", mt.toMethodDescriptorString());
		super.visitInvokeDynamicInsn(name, "(L" + owner + ";" + desc + ")V", h);

	}
}
