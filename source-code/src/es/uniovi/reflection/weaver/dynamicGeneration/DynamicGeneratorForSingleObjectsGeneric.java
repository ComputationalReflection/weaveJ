package es.uniovi.reflection.weaver.dynamicGeneration;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import es.uniovi.reflection.util.GenericSet;
import es.uniovi.reflection.weaver.PointcutForObjects;
import es.uniovi.reflection.weaver.PointcutImpl;
import es.uniovi.reflection.weaver.dynamicGeneration.DefaultComponentGenerator;
import es.uniovi.reflection.weaver.dynamicGeneration.DynamicGeneratorAround;
import es.uniovi.reflection.weaver.dynamicGeneration.DynamicGeneratorManager;
import es.uniovi.reflection.weaver.dynamicGeneration.OpcodesUtil;
import es.uniovi.reflection.weaver.dynamicGeneration.Wrapper;
import es.uniovi.reflection.weaver.methods.Method;

/**
 * {@link es.uniovi.reflection.weaver.dynamicGeneration.Wrapper Wrapper }
 * implementation to dynamically generate woven methods when instance-level
 * weaving is required. The class {@link es.uniovi.reflection.util.GenericSet
 * GenericSet } is used only here to store the objects which are affected by the
 * weaving. The functionality of this class is partially implemented delegating
 * to other {@link es.uniovi.reflection.weaver.dynamicGeneration.Wrapper Wrapper
 * }. This {@link es.uniovi.reflection.weaver.dynamicGeneration.Wrapper Wrapper
 * } can be any of the ones used to generate other woven methods except the
 * constructors generators or the
 * {@link es.uniovi.reflection.weaver.dynamicGeneration.DynamicStatefulGenerator
 * DynamicStatefulGenerator} . The reason behind this is that instance
 * level-weaving can't be understood for constructors, as the single objects
 * affected must exist in the moment the pointcut is declared. Also, at this
 * moment, is not possible to create stateful aspects that are declared at
 * instance level.
 * 
 * 
 * @author Oscar Rodriguez-Prieto Date: 2017/07/11
 * 
 * @version 1.1.0
 *
 */
public class DynamicGeneratorForSingleObjectsGeneric extends DefaultComponentGenerator implements Wrapper {

	private static final String setClassDesc = "Les/uniovi/reflection/util/GenericSet;";
	static final String handleDesc = "Ljava/lang/invoke/MethodHandle;";
	private MethodHandle component;
	private Label falseLabel;
	private MethodType methodType;
	private Object[] objects;

	public DynamicGeneratorForSingleObjectsGeneric(Object[] objects) {
		super();
		falseLabel = new Label();
		this.objects = objects;
	}

	public DynamicGeneratorForSingleObjectsGeneric(Wrapper gen, Object[] objects) {
		super(gen);
		falseLabel = new Label();
		this.objects = objects;
	}

	@Override
	public MethodHandle initAndGetWovenMethod(Class<?> dynamicClass, MethodType generatedMethodType,
			PointcutImpl pointcut) throws Throwable {
		GenericSet set = new GenericSet();
		MethodHandles.lookup().findStaticSetter(dynamicClass, "set", GenericSet.class).invoke(set);
		if (generator instanceof DynamicGeneratorAround)
			MethodHandles.lookup().findStaticSetter(dynamicClass, "mhComponent", MethodHandle.class).invoke(component);

		PointcutForObjects pObj = (PointcutForObjects) pointcut;
		pObj.setSet(set);
		pObj.addObjects(objects);
		return super.initAndGetWovenMethod(dynamicClass, generatedMethodType, pointcut);
	}

	@Override
	public void invokeMethodsAfter(MethodVisitor methodVisitor, Method aspectMethod) {

		generator.invokeMethodsAfter(methodVisitor, aspectMethod);
		methodVisitor.visitInsn(OpcodesUtil.getReturnOpcode(methodType.returnType()));
		methodVisitor.visitLabel(falseLabel);

		methodVisitor.visitFieldInsn(Opcodes.GETSTATIC, DynamicGeneratorManager.currentClassName,
				generator instanceof DynamicGeneratorAround ? "mhComponent" : "mh", handleDesc);
		DynamicGeneratorManager.loadLocals(methodVisitor, component.type());
		methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/invoke/MethodHandle", "invoke",
				component.type().toMethodDescriptorString(), false);
	}

	@Override
	public void invokeMethodsBefore(MethodVisitor methodVisitor, Method aspectMethod)
			throws NoSuchFieldException, IllegalAccessException, NoSuchMethodException, Throwable {
		methodVisitor.visitFieldInsn(Opcodes.GETSTATIC, DynamicGeneratorManager.currentClassName, "set", setClassDesc);
		methodVisitor.visitVarInsn(Opcodes.ALOAD, 0);
		methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "es/uniovi/reflection/util/GenericSet", "contains",
				MethodType.methodType(boolean.class, Object.class).toMethodDescriptorString(), false);

		methodVisitor.visitJumpInsn(Opcodes.IFEQ, falseLabel);
		generator.invokeMethodsBefore(methodVisitor, aspectMethod);
	}

	@Override
	public MethodHandle prepareMethods(MethodHandle componentMethod, Method aspectMethod)
			throws NoSuchFieldException, IllegalAccessException, NoSuchMethodException, Throwable {
		methodType = componentMethod.type();
		component = componentMethod;
		return generator.prepareMethods(componentMethod, aspectMethod);
	}

	@Override
	public void visitMethodHandleSetter(ClassWriter cw) {
		generator.visitMethodHandleSetter(cw);

		cw.visitField(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, "set", setClassDesc, null, null).visitEnd();
		if (generator instanceof DynamicGeneratorAround) {
			cw.visitField(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, "mhComponent", handleDesc, null, null).visitEnd();
		}

	}
}
