package weaver;
import java.lang.invoke.MethodHandle;


public interface Decorator {

	MethodHandle decorate(MethodHandle componentMethod);
}
