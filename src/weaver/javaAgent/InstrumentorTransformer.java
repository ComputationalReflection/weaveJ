package weaver.javaAgent;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.util.TraceClassVisitor;

public class InstrumentorTransformer implements ClassFileTransformer {
	private static final boolean DEBUG = false;

	static String byteCodeSpy(byte[] bytes) {
		ClassNode classNode = new ClassNode();
		ClassReader reader = null;
		reader = new ClassReader(bytes);
		reader.accept(classNode, 0);
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		TraceClassVisitor visitor = new TraceClassVisitor(new PrintWriter(os));
		classNode.accept(visitor);
		return os.toString() + "\nEND";
	}

	@Override
	public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
			ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
		try {
			if (className == null)
				return classfileBuffer;

			if (transformClass(className)) {
				ClassReader reader = new ClassReader(classfileBuffer);

				ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_FRAMES);

				ASMClassVisitor visitor = new ASMClassVisitor(Opcodes.ASM5, writer);
				try {
					reader.accept(visitor, ClassReader.EXPAND_FRAMES);
				} catch (Exception | Error e) {
					System.err.println(e.getMessage());
					System.err.println(e.getCause());
				}
				byte[] modifiedBytes = writer.toByteArray();
				if (DEBUG) {
					BufferedWriter bw = new BufferedWriter(new FileWriter(new File("output.txt"), true));
					bw.write(byteCodeSpy(modifiedBytes));
					bw.close();
					bw = new BufferedWriter(new FileWriter(new File("beforeTransform.txt")));
					bw.write(byteCodeSpy(classfileBuffer));
					bw.close();
					System.out.println("Clase " + className + " instrumentada.");
				}
				return modifiedBytes;
			}

		} catch (Exception | Error e) {
			e.printStackTrace();
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return classfileBuffer;
	}

	protected boolean transformClass(String className) {
		return true;
	}
}
