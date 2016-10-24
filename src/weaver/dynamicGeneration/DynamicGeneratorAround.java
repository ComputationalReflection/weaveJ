package weaver.dynamicGeneration;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import weaver.Decorator;
import weaver.PointcutImpl;
import weaver.StaticMethodDecorator;
import weaver.methods.Method;

public class DynamicGeneratorAround extends DefaultComponentGenerator {

	private Decorator decorator;

	public DynamicGeneratorAround() {
	}

	public DynamicGeneratorAround(Wrapper gen) {
		super(gen);
	}

	@Override
	public MethodHandle initAndGetWovenMethod(Class<?> dynamicClass,
			MethodType generatedMethodType, PointcutImpl pointcut) throws Throwable {
		MethodHandles.lookup()
				.findStaticSetter(dynamicClass, "decorator", Decorator.class)
				.invoke(decorator);
		return super
				.initAndGetWovenMethod(dynamicClass, generatedMethodType, pointcut);
	}

	@Override
	public MethodHandle prepareMethods(MethodHandle component,
			Method aspectMethod) throws NoSuchFieldException,
			IllegalAccessException, NoSuchMethodException, Throwable {
		MethodHandle ret = super.prepareMethods(component, aspectMethod);
		decorator = new StaticMethodDecorator(aspectMethod);
		return ret;
	}

	@Override
	public void visitMethodHandleSetter(ClassWriter cw) {
		MethodVisitor methodVisitor;
		{
			methodVisitor = cw.visitMethod(Opcodes.ACC_STATIC
					+ Opcodes.ACC_PUBLIC, "iniciar",
					MethodType.methodType(void.class, MethodHandle.class)
							.toMethodDescriptorString(), null, null);
			methodVisitor.visitCode();
			methodVisitor.visitFieldInsn(Opcodes.GETSTATIC, "DynamicClass"
					+ DynamicGeneratorManager.generatedClassIndex, "decorator",
					"Lweaver/Decorator;");
			methodVisitor.visitVarInsn(Opcodes.ALOAD, 0);
			methodVisitor.visitMethodInsn(
					Opcodes.INVOKEINTERFACE,
					"weaver/Decorator",
					"decorate",
					MethodType.methodType(MethodHandle.class,
 MethodHandle.class).toMethodDescriptorString(), true);
			methodVisitor.visitFieldInsn(Opcodes.PUTSTATIC, "DynamicClass"
					+ DynamicGeneratorManager.generatedClassIndex, "mh",
					"Ljava/lang/invoke/MethodHandle;");
			methodVisitor.visitInsn(Opcodes.RETURN);
			methodVisitor.visitMaxs(3, 3);
			methodVisitor.visitEnd();
		}
		cw.visitField(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, "decorator",
				"Lweaver/Decorator;", null, null);
	}
}