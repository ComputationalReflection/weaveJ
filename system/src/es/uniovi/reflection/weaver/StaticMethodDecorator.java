package es.uniovi.reflection.weaver;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;

import es.uniovi.reflection.weaver.Decorator;
import es.uniovi.reflection.weaver.methods.Method;


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
