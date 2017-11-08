package es.uniovi.reflection.weavej.weaver.methods;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

/**
 * Class to encapsulate all the different information related to an specific
 * method ( constructor, getter or setter). The equals and hashCode methods are
 * also implemented to compare and cache these objects efficiently. This class
 * also offers functionality to retrieve the associated
 * java.lang.invoke.MethodHandle object, using the method information.
 * 
 * @author Oscar Rodriguez-Prieto Date: 2017/07/11
 * 
 * @version 1.1.0
 *
 */
public class Method {
	private String name;
	private boolean isStatic;
	private Class<?> klass;
	private MethodType type;

	public Method(String name, boolean isStatic, Class<?> klass, MethodType type) {
		this.name = name;
		this.klass = klass;
		this.isStatic = isStatic;
		this.type = isStatic ? type : type.insertParameterTypes(0, klass);
	}

	public String getName() {
		return name;
	}

	public boolean isStatic() {
		return isStatic;
	}

	public MethodType getType() {
		return type;
	}

	public Class<?> getReturnType() {
		return type.returnType();
	}

	public Class<?>[] getParameters() {
		return type.parameterArray();
	}

	public Class<?> getKlass() {
		return klass;
	}

	public String getDesc() {
		return type.toMethodDescriptorString();
	}

	public String getBootstrapMethodType() {
		return isStatic ? "Static" : "";
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setKlass(Class<?> klass) {
		this.klass = klass;
	}

	public void setType(MethodType type) {
		this.type = type;
	}

	public void setStatic(boolean isStatic) {
		this.isStatic = isStatic;
	}

	public MethodHandle getMethodHandle() throws NoSuchMethodException, IllegalAccessException, NoSuchFieldException {
		return isStatic ? MethodHandles.lookup().findStatic(getKlass(), getName(), getType())
				: MethodHandles.lookup().findVirtual(getKlass(), getName(), getType().dropParameterTypes(0, 1));
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (isStatic ? 1231 : 1237);
		result = prime * result + ((klass == null) ? 0 : klass.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Method other = (Method) obj;
		if (isStatic != other.isStatic)
			return false;
		if (klass == null) {
			if (other.klass != null)
				return false;
		} else if (!klass.equals(other.klass))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}

}
