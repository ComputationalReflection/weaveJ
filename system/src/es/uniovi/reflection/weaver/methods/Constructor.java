package es.uniovi.reflection.weaver.methods;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

public class Constructor extends Method {

	public Constructor(Class<?> klass, Class<?>... parameters) {
		super("<init>", false, klass, MethodType.methodType(void.class,parameters));
	}

	@Override
	public String getBootstrapMethodType() {
		return "Constructor";
	}
@Override 
	public MethodHandle getMethodHandle() throws NoSuchMethodException, IllegalAccessException{
	return MethodHandles.lookup().findConstructor(
			getKlass(),getType().dropParameterTypes(0, 1));
}


}
