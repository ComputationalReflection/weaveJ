package es.uniovi.reflection.weavej.weaver;

import java.lang.invoke.CallSite;
import java.lang.invoke.MethodHandles.Lookup;

import es.uniovi.reflection.weavej.weaver.methods.Constructor;
import es.uniovi.reflection.weavej.weaver.methods.Getter;
import es.uniovi.reflection.weavej.weaver.methods.Method;
import es.uniovi.reflection.weavej.weaver.methods.Setter;

import java.lang.invoke.MethodType;
import java.lang.invoke.VolatileCallSite;

/**
 * 
 * Bootstrap class used to handle the invoke-dynamic calls. All the
 * invoke-dynamic instructions introduced during the load time formatting refer
 * this class. Before the execution of the method referred by the invoke-dynamic
 * instruction, the call is intercepted here in one of these methods. This
 * method is chosen depending on the arguments passed to the invoke-dynamic
 * instruction. Then a java.lang.invoke.Callsite object encapsulating the method
 * call is returned and the invocation is performed.
 * 
 * @author Oscar Rodriguez-Prieto Date: 2017/07/11
 * 
 * @version 1.1.0
 *
 *
 */

public class DefaultBootstrap {
	private static CallSite addCallSite(Method method)
			throws NoSuchMethodException, IllegalAccessException, NoSuchFieldException {
		CallSite c = Weaver.getCallSite(method);
		return c == null ? Weaver.addCallSite(method, new VolatileCallSite(method.getMethodHandle())) : c;
	}

	public static CallSite constructor(Lookup lookup, String name, MethodType type) throws Throwable {
		return addCallSite(new Constructor(type.parameterType(0), type.parameterArray()));
	}

	public static CallSite constructor(Lookup lookup, String name, MethodType type, Class<?> nameClass)
			throws Throwable {
		return addCallSite(new Constructor(nameClass, type.parameterArray()));
	}

	public static CallSite get(Lookup lookup, String name, MethodType type)
			throws IllegalAccessException, NoSuchFieldException, NoSuchMethodException {
		return addCallSite(new Getter(name, type.returnType(), type.parameterType(0)));
	}

	public static CallSite method(Lookup lookup, String name, MethodType methodType) throws Throwable {

		return addCallSite(new Method(name, false, methodType.parameterType(0), methodType.dropParameterTypes(0, 1)));
	}

	public static CallSite set(Lookup lookup, String name, MethodType type)
			throws IllegalAccessException, NoSuchFieldException, NoSuchMethodException {
		return addCallSite(new Setter(name, type.parameterType(1), type.parameterType(0)));
	}

	public static CallSite staticMethod(Lookup lookup, String name, MethodType methodType) throws Throwable {

		return addCallSite(new Method(name, true, methodType.returnType(), methodType));
	}
}
