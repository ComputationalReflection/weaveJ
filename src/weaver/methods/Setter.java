package weaver.methods;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

public class Setter extends Method{

	public Setter(String nameField, Class<?> fieldType, Class<?> componentClass) {
		super(nameField, false, componentClass, MethodType.methodType(
				void.class, fieldType));
	}

	@Override
	public String getBootstrapMethodType() {
		return "Setter";
	}


	@Override
public MethodHandle getMethodHandle() throws NoSuchMethodException,
			IllegalAccessException, NoSuchFieldException {
		return MethodHandles.lookup().findSetter(getKlass(), getName(),
				getType().parameterType(getType().parameterCount() - 1));
}
}
