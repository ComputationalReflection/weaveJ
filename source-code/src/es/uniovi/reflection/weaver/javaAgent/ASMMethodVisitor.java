package es.uniovi.reflection.weaver.javaAgent;

import java.lang.invoke.CallSite;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

import org.objectweb.asm.Handle;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.commons.InstructionAdapter;

/**
 * 
 * Subclass of org.objectweb.asm.commons.InstructorAdapter to transform class
 * methods in load time. Opcodes like invokevirtual, invokespecial, putfield,
 * getfield are substituted by the invoke-dynamic instruction. After these
 * transformations, other opcode replacements must be performed to prevent
 * errors in the bytecode.
 * 
 * @author Oscar Rodriguez-Prieto Date: 2017/07/11
 * 
 * @version 1.1.0
 *
 */
public class ASMMethodVisitor extends InstructionAdapter implements Opcodes {

	static boolean DEBUG = false;

	private static final String bootstrapClassDesc = "es/uniovi/reflection/weaver/DefaultBootstrap";

	protected boolean isLastRemovedNew;

	public ASMMethodVisitor(int arg0, MethodVisitor arg1) {
		super(arg0, arg1);
	}

	@Override
	public void anew(org.objectweb.asm.Type arg0) {
		if (PackageGroupTransformer.CLASS_TRANSFORMER.transformClass(arg0.getClassName().replace(".", "/"))) {
			if (DEBUG)
				System.out.println("NEW transformed " + arg0.getClassName());
			isLastRemovedNew = true;

		} else {

			isLastRemovedNew = false;

			if (DEBUG)
				System.out.println("NEW allowed (class not instrumented) " + arg0.getClassName());
			super.anew(arg0);

		}

	}

	@Override
	public void dup() {
		if (isLastRemovedNew) {
			if (DEBUG)
				System.out.println("DUP after NEW transformed.");
		} else {
			if (DEBUG)
				System.out.println("DUP allowed.");
			super.dup();
		}

	}

	@Override
	public void invokespecial(String owner, String name, String desc, boolean itf) {
		if (name.contentEquals("<init>") && PackageGroupTransformer.CLASS_TRANSFORMER.transformClass(owner)) {
			if (DEBUG)
				System.out.println("Transforming INVOKE_SPECIAL :(" + owner + "):" + name + "(" + desc + ")");

			MethodType mt = MethodType.methodType(CallSite.class, MethodHandles.Lookup.class, String.class,
					MethodType.class);
			Handle h = new Handle(H_INVOKESTATIC, bootstrapClassDesc, "staticMethod", mt.toMethodDescriptorString());

			super.visitInvokeDynamicInsn("init", desc.replace(")V", ")L" + owner + ";"), h);

		} else {
			if (DEBUG)
				System.out
						.println("INVOKESPECIAL allowed (super init call, or private method):(" + owner + "):" + name);

			super.invokespecial(owner, name, desc, itf);
		}
	}

	@Override
	public void invokevirtual(String owner, String name, String desc, boolean itf) {
		if (DEBUG)
			System.out.println("Transforming INVOKEVIRTUAL :" + name + " of class " + owner + "(" + desc + ")-"
					+ desc.replace("(", "(L" + owner + ";"));

		MethodType mt = MethodType.methodType(CallSite.class, MethodHandles.Lookup.class, String.class,
				MethodType.class);
		Handle h = new Handle(H_INVOKESTATIC, bootstrapClassDesc, "method", mt.toMethodDescriptorString());

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
			System.out.println("Visiting field " + owner + " " + name + " " + opcode + " " + desc);

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
			System.out.println("Transforming GET :" + name + " of class " + owner + "(" + desc + ")");
		MethodType mt = MethodType.methodType(CallSite.class, MethodHandles.Lookup.class, String.class,
				MethodType.class);
		Handle h = new Handle(H_INVOKESTATIC, bootstrapClassDesc, "get", mt.toMethodDescriptorString());
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
		Handle h = new Handle(H_INVOKESTATIC, bootstrapClassDesc, "set", mt.toMethodDescriptorString());
		super.visitInvokeDynamicInsn(name, "(L" + owner + ";" + desc + ")V", h);

	}
}
