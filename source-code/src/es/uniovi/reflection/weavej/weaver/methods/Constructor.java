package es.uniovi.reflection.weavej.weaver.methods;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

/**
 * Subclass of {@link es.uniovi.reflection.weavej.weaver.methods.Method Method} to
 * model only the constructor methods.
 * 
 * @author Oscar Rodriguez-Prieto Date: 2017/07/11
 * 
 * @version 1.1.0
 *
 */
public class Constructor extends Method {

	public Constructor(Class<?> klass, Class<?>... parameters) {
		super("<init>", false, klass, MethodType.methodType(void.class, parameters));
	}

	@Override
	public String getBootstrapMethodType() {
		return "Constructor";
	}

	@Override
	public MethodHandle getMethodHandle() throws NoSuchMethodException, IllegalAccessException {
		return MethodHandles.lookup().findConstructor(getKlass(), getType().dropParameterTypes(0, 1));
	}

}
