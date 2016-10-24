package weaver.javaAgent;

import org.objectweb.asm.MethodVisitor;

public class ASMConstructorVisitor extends ASMMethodVisitor {

	private boolean firstInvokeespecial = false;

	public ASMConstructorVisitor(int arg0, MethodVisitor arg1) {
		super(arg0, arg1);
		if (DEBUG)
			System.out.println("VISITOR CONSTRUCTOR");
	}

	@Override
	public void anew(org.objectweb.asm.Type arg0) {
		if (firstInvokeespecial) {

			isLastRemovedNew = false;

			if (DEBUG)
				System.out.println("NEW con $ PERMITIDO por ser el primero dentro de un constructor (super). "
						+ arg0.getClassName());
			super.superAnew(arg0);

		} else {
			super.anew(arg0);

		}

	}

	@Override
	public void invokespecial(String owner, String name, String desc, boolean itf) {
		if (DEBUG)
			System.out.println("INVOKE_ESPECIAL CONSTRUCTOR " + owner + " " + name);
		if (firstInvokeespecial) {
			super.superInvokespecial(owner, name, desc, itf);
			firstInvokeespecial = false;
		} else
			super.invokespecial(owner, name, desc, itf);
	}

}
