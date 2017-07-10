package es.uniovi.reflection.weaver.java7;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;

import es.uniovi.reflection.weaver.javaAgent.PackageGroupTransformer;
import jdk.internal.org.objectweb.asm.Opcodes;

public class PackageGroupTransformerJava7 extends PackageGroupTransformer {
	
	public PackageGroupTransformerJava7(String[] packageNames) {
		super(packageNames);
	}
  
 	@Override
	protected ClassVisitor getClassVisitor(ClassWriter writer) {
		return new ASMClassVisitorJava7(Opcodes.ASM5, writer);
	}
}
