package es.uniovi.reflection.weaver.dynamicGeneration;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodType;

import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;

import es.uniovi.reflection.weaver.dynamicGeneration.DefaultComponentGenerator;
import es.uniovi.reflection.weaver.dynamicGeneration.DynamicGeneratorManager;
import es.uniovi.reflection.weaver.dynamicGeneration.OpcodesUtil;
import es.uniovi.reflection.weaver.dynamicGeneration.Wrapper;
import es.uniovi.reflection.weaver.methods.Method;

/**
 * {@link es.uniovi.reflection.weaver.dynamicGeneration.Wrapper Wrapper }
 * implementation to dynamically generate woven methods and constructors when
 * the non-basic after-throwing advice type is required. Its functionality is
 * partially implemented delegating to the
 * {@link es.uniovi.reflection.weaver.dynamicGeneration.BaseDynamicGenerator
 * BaseDynamicGenerator}, the
 * {@link es.uniovi.reflection.weaver.dynamicGeneration.DynamicGeneratorManager
 * DynamicGeneratorManager} and also to its superclass.
 * 
 * 
 * @author Oscar Rodriguez-Prieto Date: 2017/07/11
 * 
 * @version 1.1.0
 *
 */
public class DynamicExceptionHandlerGenerator extends DefaultComponentGenerator implements Wrapper {
	private Class<?> exception;
	private MethodType methodType;
	private Label tryLabel = new Label(), catchLabel = new Label();

	public DynamicExceptionHandlerGenerator(Class<?> excepcion) {
		this.exception = excepcion;
	}

	@Override
	public void invokeMethodsAfter(MethodVisitor methodVisitor, Method aspectMethod) {
		methodVisitor.visitInsn(OpcodesUtil.getReturnOpcode(methodType.returnType()));
		methodVisitor.visitLabel(catchLabel);
		for (int i = 0; i < methodType.parameterCount(); i++)
			methodVisitor.visitVarInsn(OpcodesUtil.getLoadOpcode(methodType.parameterType(i)), i);
		DynamicGeneratorManager.invokeMethod(methodVisitor, aspectMethod);
		OpcodesUtil.getReturnOpcode(methodType.returnType());
	}

	@Override
	public void invokeMethodsBefore(MethodVisitor methodVisitor, Method aspectMethod)
			throws NoSuchFieldException, IllegalAccessException, NoSuchMethodException, Throwable {
		methodVisitor.visitTryCatchBlock(tryLabel, catchLabel, catchLabel, exception.getName().replace(".", "/"));
		methodVisitor.visitLabel(tryLabel);
	}

	@Override
	public MethodHandle prepareMethods(MethodHandle componentMethod, Method aspectMethod)
			throws NoSuchFieldException, IllegalAccessException, NoSuchMethodException, Throwable {
		methodType = componentMethod.type();
		return generator.prepareMethods(componentMethod, aspectMethod);
	}
}
