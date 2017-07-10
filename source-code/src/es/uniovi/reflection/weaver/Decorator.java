package es.uniovi.reflection.weaver;
import java.lang.invoke.MethodHandle;


public interface Decorator {

	MethodHandle decorate(MethodHandle componentMethod);
}
