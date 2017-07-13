package es.uniovi.reflection.weaver.java7;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;

import es.uniovi.reflection.weaver.javaAgent.PackageGroupTransformer;
import jdk.internal.org.objectweb.asm.Opcodes;

/**
 * Subclass of
 * {@link es.uniovi.reflection.weaver.javaAgent.PackageGroupTransformer
 * PackageGroupTransformer} which employs a different specific class visitor to
 * use with Java 7.
 * 
 * @author Oscar Rodriguez-Prieto Date: 2017/07/11
 * 
 * @version 1.1.0
 *
 */
public class PackageGroupTransformerJava7 extends PackageGroupTransformer {

	public PackageGroupTransformerJava7(String[] packageNames) {
		super(packageNames);
	}

	@Override
	protected ClassVisitor getClassVisitor(ClassWriter writer) {
		return new ASMClassVisitorJava7(Opcodes.ASM5, writer);
	}
}
