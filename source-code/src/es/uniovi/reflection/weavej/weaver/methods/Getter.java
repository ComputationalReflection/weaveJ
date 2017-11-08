package es.uniovi.reflection.weavej.weaver.methods;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

/**
 * Subclass of {@link es.uniovi.reflection.weavej.weaver.methods.Method Method} to
 * model only the methods that are getters of an specific field.
 * 
 * @author Oscar Rodriguez-Prieto Date: 2017/07/11
 * 
 * @version 1.1.0
 *
 */
public class Getter extends Method {

	public Getter(String nameField, Class<?> fieldType, Class<?> componentClass) {
		super(nameField, false, componentClass, MethodType.methodType(fieldType));
	}

	@Override
	public String getBootstrapMethodType() {
		return "Getter";
	}

	@Override
	public MethodHandle getMethodHandle() throws NoSuchMethodException, IllegalAccessException, NoSuchFieldException {
		return MethodHandles.lookup().findGetter(getKlass(), getName(), getReturnType());

	}
}
