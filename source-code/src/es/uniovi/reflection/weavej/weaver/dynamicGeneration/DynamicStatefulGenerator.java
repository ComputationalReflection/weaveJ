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
 * implementation to dynamically generate woven methods when stateful aspects
 * are required. The functionality of this class is partially implemented
 * delegating to other
 * {@link es.uniovi.reflection.weavej.weaver.dynamicGeneration.Wrapper Wrapper }. This
 * {@link es.uniovi.reflection.weavej.weaver.dynamicGeneration.Wrapper Wrapper } can be
 * any of the ones used to generate other woven methods except the
 * {@link es.uniovi.reflection.weavej.weaver.dynamicGeneration.DynamicGeneratorForSingleObjectsGeneric
 * DynamicGeneratorForSingleObjectsGeneric}. The reason behind this is that, at
 * this moment, is not possible to create stateful aspects at instance level.
 * 
 * 
 * @author Oscar Rodriguez-Prieto Date: 2017/07/11
 * 
 * @version 1.1.0
 *
 */
public class DynamicStatefulGenerator extends DefaultComponentGenerator {

	private Object aspect;

	public DynamicStatefulGenerator(Object aspect) {
		this.aspect = aspect;
	}

	public DynamicStatefulGenerator(Wrapper gen, Object aspect) {
		super(gen);
		this.aspect = aspect;
	}

	private byte[] generateCode(Method aspectMethod) {
		ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
		final String GENERATED_CLASS_NAME = "DynamicAspectStatefulClass" + DynamicGeneratorManager.generatedClassIndex;
		final String classDescriptor = "L" + aspect.getClass().getName().replace(".", "/") + ";";

		cw.visit(Opcodes.V1_7, Opcodes.ACC_PUBLIC, GENERATED_CLASS_NAME, null, "java/lang/Object", new String[] {});

		final String handleClassDesc = DynamicGeneratorForSingleObjectsGeneric.handleDesc;
		cw.visitField(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, "mh", handleClassDesc, null, null).visitEnd();
		cw.visitField(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, "instance", classDescriptor, null, null).visitEnd();
		MethodVisitor methodVisitor;
		{
			methodVisitor = cw.visitMethod(Opcodes.ACC_STATIC + Opcodes.ACC_PUBLIC, "aspect",
					aspectMethod.getType().dropParameterTypes(0, 1).toMethodDescriptorString(), null, null);
			methodVisitor.visitCode();
			methodVisitor.visitFieldInsn(Opcodes.GETSTATIC, GENERATED_CLASS_NAME, "mh", handleClassDesc);
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
		final String GENERATED_CLASS_NAME = "DynamicAspectStatefulClass" + DynamicGeneratorManager.generatedClassIndex;
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
