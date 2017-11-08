package es.uniovi.reflection.weavej.weaver.dynamicGeneration;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import es.uniovi.reflection.weavej.weaver.dynamicGeneration.DefaultComponentGenerator;
import es.uniovi.reflection.weavej.weaver.dynamicGeneration.DynamicGeneratorManager;
import es.uniovi.reflection.weavej.weaver.dynamicGeneration.OpcodesUtil;
import es.uniovi.reflection.weavej.weaver.dynamicGeneration.Wrapper;
import es.uniovi.reflection.weavej.weaver.methods.Method;

/**
 * {@link es.uniovi.reflection.weavej.weaver.dynamicGeneration.Wrapper Wrapper }
 * implementation to dynamically generate woven methods when the aspect method
 * receives member name information as argument. The functionality of this class
 * is partially implemented delegating to other
 * {@link es.uniovi.reflection.weavej.weaver.dynamicGeneration.Wrapper Wrapper }. This
 * {@link es.uniovi.reflection.weavej.weaver.dynamicGeneration.Wrapper Wrapper } can be
 * any of the ones used to generate other woven methods.
 * 
 * 
 * @author Oscar Rodriguez-Prieto Date: 2017/07/11
 * 
 * @version 1.1.0
 *
 */
public class DynamicNameFieldGenerator extends DefaultComponentGenerator {

	private String nameField;

	public DynamicNameFieldGenerator(String nameField) {
		this.nameField = nameField;
	}

	public DynamicNameFieldGenerator(Wrapper gen, String nameField) {
		super(gen);
		this.nameField = nameField;
	}

	private byte[] generateCode(Method aspectMethod) {
		ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
		final String GENERATED_CLASS_NAME = "DynamicAspectClass" + DynamicGeneratorManager.generatedClassIndex;

		cw.visit(Opcodes.V1_7, Opcodes.ACC_PUBLIC, GENERATED_CLASS_NAME, null, "java/lang/Object", new String[] {});
		cw.visitField(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, "mh", DynamicGeneratorForSingleObjectsGeneric.handleDesc,
				null, null).visitEnd();
		MethodVisitor methodVisitor;
		{
			methodVisitor = cw.visitMethod(Opcodes.ACC_STATIC + Opcodes.ACC_PUBLIC, "aspect",
					aspectMethod.getType().dropParameterTypes(0, 1).toMethodDescriptorString(), null, null);
			methodVisitor.visitCode();
			methodVisitor.visitFieldInsn(Opcodes.GETSTATIC, GENERATED_CLASS_NAME, "mh",
					DynamicGeneratorForSingleObjectsGeneric.handleDesc);

			methodVisitor.visitLdcInsn(nameField);
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
		final String GENERATED_CLASS_NAME = "DynamicAspectClass" + DynamicGeneratorManager.generatedClassIndex;
		Class<?> aspectClass = DynamicGeneratorManager.getDynamicClass(generateCode(aspectMethod),
				GENERATED_CLASS_NAME);
		MethodHandles.lookup().findStaticSetter(aspectClass, "mh", MethodHandle.class)
				.invoke(aspectMethod.getMethodHandle());
		aspectMethod.setType(aspectMethod.getType().dropParameterTypes(0, 1));
		aspectMethod.setKlass(aspectClass);
		aspectMethod.setName("aspect");
		aspectMethod.setStatic(true);
		return super.prepareMethods(component, aspectMethod);
	}
}
