package es.uniovi.reflection.weavej.weaver;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;

import es.uniovi.reflection.weavej.weaver.Decorator;
import es.uniovi.reflection.weavej.weaver.methods.Method;

/**
 * An specific implementation of the
 * {@link es.uniovi.reflection.weavej.weaver.Decorator Decorator} interface.
 * 
 * @author Oscar Rodriguez-Prieto Date: 2017/07/11
 * 
 * @version 1.1.0
 *
 *
 */

public class StaticMethodDecorator implements Decorator {

	private MethodHandle aspectMethod;

	public StaticMethodDecorator(Method method)
			throws NoSuchMethodException, IllegalAccessException, NoSuchFieldException {
		super();
		aspectMethod = method.getMethodHandle();
	}

	@Override
	public MethodHandle decorate(MethodHandle componentMethod) {
		return MethodHandles.insertArguments(aspectMethod, 0, componentMethod);
	}

}
