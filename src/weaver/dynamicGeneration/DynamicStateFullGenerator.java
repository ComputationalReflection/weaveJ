package weaver.dynamicGeneration;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import weaver.methods.Method;

public class DynamicStateFullGenerator extends DefaultComponentGenerator {

	private Object aspect;

	public DynamicStateFullGenerator(Object aspect) {
		this.aspect = aspect;
	}

	public DynamicStateFullGenerator(Wrapper gen, Object aspect) {
		super(gen);
		this.aspect = aspect;
	}

	private byte[] generateCode(Method aspectMethod) {
		ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
		final String GENERATED_CLASS_NAME = "DynamicAspectStateFullClass" + DynamicGeneratorManager.generatedClassIndex;
		final String classDescriptor = "L" + aspect.getClass().getName().replace(".", "/") + ";";
		cw.visit(Opcodes.V1_7, Opcodes.ACC_PUBLIC, GENERATED_CLASS_NAME, null, "java/lang/Object", new String[] {});
		cw.visitField(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, "mh", "Ljava/lang/invoke/MethodHandle;", null, null)
				.visitEnd();
		cw.visitField(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, "instance", classDescriptor, null, null).visitEnd();
		MethodVisitor methodVisitor;
		{
			methodVisitor = cw.visitMethod(Opcodes.ACC_STATIC + Opcodes.ACC_PUBLIC, "aspect",
					aspectMethod.getType().dropParameterTypes(0, 1).toMethodDescriptorString(), null, null);
			methodVisitor.visitCode();
			methodVisitor.visitFieldInsn(Opcodes.GETSTATIC, GENERATED_CLASS_NAME, "mh",
					"Ljava/lang/invoke/MethodHandle;");
			methodVisitor.visitFieldInsn(Opcodes.GETSTATIC, GENERATED_CLASS_NAME, "instance", classDescriptor);
			DynamicGeneratorManager.loadLocals(methodVisitor, aspectMethod.getType().dropParameterTypes(0, 1));
			methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/invoke/MethodHandle", "invokeExact",
					aspectMethod.getType().toMethodDescriptorString(), false);
			methodVisitor.visitInsn(OpcodesUtil.getReturnOpcode(aspectMethod.getType().returnType()));
			methodVisitor.visitMaxs(3, 3);
			methodVisitor.visitEnd();
		}
		cw.visitEnd();
		return cw.toByteArray();
	}

	@Override
	public MethodHandle prepareMethods(MethodHandle component, Method aspectMethod)
			throws NoSuchFieldException, IllegalAccessException, NoSuchMethodException, Throwable {
		final String GENERATED_CLASS_NAME = "DynamicAspectStateFullClass" + DynamicGeneratorManager.generatedClassIndex;
		Class<?> aspectClass = DynamicGeneratorManager.getDynamicClass(generateCode(aspectMethod),
				GENERATED_CLASS_NAME);
		MethodHandles.lookup().findStaticSetter(aspectClass, "mh", MethodHandle.class)
				.invoke(aspectMethod.getMethodHandle());
		MethodHandles.lookup().findStaticSetter(aspectClass, "instance", aspect.getClass()).invoke(aspect);
		aspectMethod.setType(aspectMethod.getType().dropParameterTypes(0, 1));
		aspectMethod.setKlass(aspectClass);
		aspectMethod.setName("aspect");
		aspectMethod.setStatic(true);
		return super.prepareMethods(component, aspectMethod);
	}
}
