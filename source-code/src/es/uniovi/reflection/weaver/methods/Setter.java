package es.uniovi.reflection.weaver.methods;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

/**
 * Subclass of {@link es.uniovi.reflection.weaver.methods.Method Method} to
 * model only the methods that are setters of an specific field.
 * 
 * @author Oscar Rodriguez-Prieto Date: 2017/07/11
 * 
 * @version 1.1.0
 *
 */
public class Setter extends Method {

	public Setter(String nameField, Class<?> fieldType, Class<?> componentClass) {
		super(nameField, false, componentClass, MethodType.methodType(void.class, fieldType));
	}

	@Override
	public String getBootstrapMethodType() {
		return "Setter";
	}

	@Override
	public MethodHandle getMethodHandle() throws NoSuchMethodException, IllegalAccessException, NoSuchFieldException {
		return MethodHandles.lookup().findSetter(getKlass(), getName(),
				getType().parameterType(getType().parameterCount() - 1));
	}
}
