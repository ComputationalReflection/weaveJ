package es.uniovi.reflection.weavej.weaver.javaAgent;

import java.lang.reflect.Modifier;
import java.util.HashSet;
import java.util.Set;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import es.uniovi.reflection.weavej.weaver.dynamicGeneration.OpcodesUtil;

/**
 * 
 * Subclass of org.objectweb.asm.ClassVisitor to transform classes at load time.
 * One public static method called "init" is added to each class per constructor
 * declared in order to implement the constructor joinpoint. This class visitor
 * is also used to track final fields. The reason behind this is that putfield
 * opcodes referring to final fields cannot be replaced by invoke-dynamic
 * instructions. Finally, this class is also used to choose the right subclass
 * of org.objectweb.asm.InstructionAdapter to visit and modify the different
 * method declarations.
 * 
 * @author Oscar Rodriguez-Prieto Date: 2017/07/11
 * 
 * @version 1.1.0
 *
 */
public class ASMClassVisitor extends ClassVisitor implements Opcodes {

	private static boolean DEBUG = false;
	private String className;
	private Set<String> constructorDescs = new HashSet<String>();
	private Set<String> finalFields = new HashSet<String>();
	private ClassWriter writer;

	public ASMClassVisitor(int arg0, ClassWriter arg1) {
		super(arg0, arg1);
		writer = arg1;
	}

	@Override
	public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
		className = name;
		if (!Modifier.isPublic(access)) {
			int newAccess = 0;
			if (Modifier.isFinal(access))
				newAccess += Opcodes.ACC_FINAL;
			if (Modifier.isAbstract(access))
				newAccess += Opcodes.ACC_ABSTRACT;
			if (Modifier.isStatic(access))
				newAccess += Opcodes.ACC_STATIC;
			access = newAccess + ACC_PUBLIC;
		}
		if (DEBUG)
			System.out.println(
					"Superclass of " + name + ":" + superName + "(" + Modifier.isPublic(access) + "-" + access + ")");
		super.visit(version, access, name, signature, superName, interfaces);

	}

	@Override
	public void visitEnd() {

		MethodVisitor methodVisitor;
		for (String desc : constructorDescs) {
			methodVisitor = writer.visitMethod(Opcodes.ACC_STATIC + Opcodes.ACC_PUBLIC, "init", desc, null, null);
			methodVisitor.visitCode();
			methodVisitor.visitTypeInsn(Opcodes.NEW, className);
			methodVisitor.visitInsn(Opcodes.DUP);
			int i = 0;
			for (Class<?> c : ConstructorAdapterGeneration.getClassParamsFromDescriptor(desc.substring(1)))
				methodVisitor.visitIntInsn(OpcodesUtil.getLoadOpcode(c), i++);
			String[] paramsRet = desc.split("\\)");
			methodVisitor.visitMethodInsn(Opcodes.INVOKESPECIAL, className, "<init>",
					"(" + paramsRet[0].substring(1, paramsRet[0].length()) + ")V", false);
			methodVisitor.visitInsn(Opcodes.ARETURN);
			methodVisitor.visitMaxs(10, 10);
			methodVisitor.visitEnd();

		}
		super.visitEnd();
	}

	@Override
	public FieldVisitor visitField(int access, String name, String desc, String signature, Object value) {
		if (DEBUG)
			System.out.println("Attribute " + name + "(" + desc + ")" + signature);

		if (!Modifier.isPublic(access)) {
			int newAccess = 0;
			if (Modifier.isFinal(access)) {
				newAccess += Opcodes.ACC_FINAL;
				finalFields.add(name);
			}
			// Formatting can't be applied to final fields
			if (Modifier.isStatic(access))
				newAccess += Opcodes.ACC_STATIC;
			access = newAccess + ACC_PUBLIC;
		}
		return super.visitField(access, name, desc, signature, value);
	}

	@Override
	public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
		if (DEBUG)
			System.out.println("Method " + name + "(" + desc + ")" + signature + "-->" + finalFields.size());

		MethodVisitor visitor;
		// The constructor in the Object class is the only one visited with a
		// MethodVisitor instead of ConstructorVisitor
		if (name.contentEquals("<init>") && !className.contentEquals("java/lang/Object")) {

			if (className.contains("$"))
				visitor = new ASMInnerConstructorVisitor(api,
						super.visitMethod(access, name, desc, signature, exceptions));
			else if (finalFields.size() > 0)
				visitor = new ASMConstructorWithFinalFields(api,
						super.visitMethod(access, name, desc, signature, exceptions), finalFields);
			else
				visitor = new ASMConstructorVisitor(api, super.visitMethod(access, name, desc, signature, exceptions));

			constructorDescs.add(desc.replace(")V", ")L" + className + ";"));
			// }
		} else
			visitor = new ASMMethodVisitor(api, super.visitMethod(access, name, desc, signature, exceptions));
		return visitor;
	}

}
