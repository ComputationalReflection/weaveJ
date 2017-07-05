package es.uniovi.reflection.weaver.java7;

import java.lang.reflect.Modifier;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;


public class ASMClassVisitorJava7 extends ClassVisitor implements Opcodes {

	private static boolean DEBUG = false;
	private String className;

	public ASMClassVisitorJava7(int arg0, ClassWriter arg1) {
		super(arg0, arg1);
	}

	@Override
	public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
		className = name;
		if (DEBUG)
			System.out.println(
					"Superclass de " + name + ":" + superName + "(" + Modifier.isPublic(access) + "-" + access + ")");
		super.visit(version, access, name, signature, superName, interfaces);

	}

	@Override
	public FieldVisitor visitField(int access, String name, String desc, String signature, Object value) {
		if (DEBUG)
			System.out.println("Atributo " + name + "(" + desc + ")" + signature);

		if (!Modifier.isPublic(access)) {
			int newAccess = 0;
			if (Modifier.isFinal(access))
				newAccess += Opcodes.ACC_FINAL;
			if (Modifier.isStatic(access))
				newAccess += Opcodes.ACC_STATIC;
			access = newAccess + ACC_PUBLIC;
		}
		return super.visitField(access, name, desc, signature, value);
	}

	@Override

	public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {

		MethodVisitor visitor;

		if (className.contains("$") && name.contentEquals("<init>"))
			visitor = new ASMInnerConstructorVisitorJava7(api, super.visitMethod(access, name, desc, signature, exceptions));
		else
			visitor = new ASMMethodVisitorJava7(api, super.visitMethod(access, name, desc, signature, exceptions));
		return visitor;
	}

}
