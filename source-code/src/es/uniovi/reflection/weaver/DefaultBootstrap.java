package es.uniovi.reflection.weaver;

import java.lang.invoke.CallSite;
import java.lang.invoke.MethodHandles.Lookup;

import es.uniovi.reflection.weaver.methods.Constructor;
import es.uniovi.reflection.weaver.methods.Getter;
import es.uniovi.reflection.weaver.methods.Method;
import es.uniovi.reflection.weaver.methods.Setter;

import java.lang.invoke.MethodType;
import java.lang.invoke.VolatileCallSite;

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
