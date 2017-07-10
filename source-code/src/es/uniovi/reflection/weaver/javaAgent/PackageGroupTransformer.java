package es.uniovi.reflection.weaver.javaAgent;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;

import jdk.internal.org.objectweb.asm.Opcodes;

public class PackageGroupTransformer extends InstrumentorTransformer {
	public static PackageGroupTransformer CLASS_TRANSFORMER;
	private String[] packageNames;

	public PackageGroupTransformer(String[] packageNames) {
		super();
		this.packageNames = packageNames;
		CLASS_TRANSFORMER = this;
	}

	@Override
	protected boolean transformClass(String className) {

		for (String name : packageNames) {
			if (className.contains(name))
				return true;
		}
		return false;
	}

	@Override
	protected ClassVisitor getClassVisitor(ClassWriter writer) {
		return new ASMClassVisitor(Opcodes.ASM5, writer);
	}
}
