package es.uniovi.reflection.weaver.methods;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

public class Getter extends Method {

	public Getter(String nameField, Class<?> fieldType, Class<?> componentClass) {
		super(nameField, false, componentClass, MethodType
				.methodType(fieldType));
	}

	@Override
	public String getBootstrapMethodType() {
		return "Getter";
	}

	
	@Override
public MethodHandle getMethodHandle() throws NoSuchMethodException,
			IllegalAccessException, NoSuchFieldException {
		return MethodHandles.lookup().findGetter(getKlass(), getName(),
				getReturnType());
		
}
}
