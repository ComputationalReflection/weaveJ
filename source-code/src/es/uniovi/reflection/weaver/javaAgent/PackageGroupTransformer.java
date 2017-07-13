package es.uniovi.reflection.weaver.javaAgent;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;

import jdk.internal.org.objectweb.asm.Opcodes;

/**
 * Subclass of
 * {@link es.uniovi.reflection.weaver.javaAgent.InstrumentorTransformer
 * InstrumentorTransformer} to implement the concrete class selection strategy
 * for the instrumentation at load time. This class is instantiated with a
 * String array, containing different (class or package) names that were
 * introduced by the user. Only classes whose complete name matches any of these
 * strings will be selection for the instrumentation.
 * 
 * 
 * 
 * @author Oscar Rodriguez-Prieto Date: 2017/07/11
 * 
 * @version 1.1.0
 */
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
