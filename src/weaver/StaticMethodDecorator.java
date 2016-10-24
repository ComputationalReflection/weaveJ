package weaver;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;

import weaver.Decorator;
import weaver.methods.Method;


public class StaticMethodDecorator implements Decorator {

	private MethodHandle aspectMethod;

	public StaticMethodDecorator(Method method) throws NoSuchMethodException,
			IllegalAccessException, NoSuchFieldException {
		super();
		aspectMethod = method.getMethodHandle();
	}


	@Override
	public MethodHandle decorate(MethodHandle componentMethod)  {
		return MethodHandles.insertArguments(aspectMethod, 0, componentMethod);
	}

}
