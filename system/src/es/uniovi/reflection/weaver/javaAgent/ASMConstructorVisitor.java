package es.uniovi.reflection.weaver.javaAgent;

import org.objectweb.asm.MethodVisitor;

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
				System.out.println(
						"NEW PERMITIDO por ser el primero dentro de un constructor (super). " + arg0.getClassName());
			super.superAnew(arg0);

		} else {
			super.anew(arg0);

		}

	}

	@Override
	public void invokespecial(String owner, String name, String desc, boolean itf) {
		if (DEBUG)
			System.out.println("Visitando INVOKEESPECIAL con " + name + " de " + owner + " con " + firstInvokeespecial);
		if (firstInvokeespecial && name.contains("<init>")) {
			super.superInvokespecial(owner, name, desc, itf);
			firstInvokeespecial = false;

			if (DEBUG)
				System.out.println("INVOKE_ESPECIAL CONSTRUCTOR PERMITIDO POR SER EL PRIMERO en un constructor" + owner
						+ " " + name + " " + desc);
		} else {
			super.invokespecial(owner, name, desc, itf);
			if (DEBUG)
				System.out.println(
						"INVOKE_ESPECIAL TRANSFORMADO A DYNAMIC por no ser el primero en un cosntructor o no ser llamada a constructor"
								+ owner + " " + name + " " + desc);
		}
	}

}
