package es.uniovi.reflection.weaver;

import java.lang.invoke.MethodHandle;

import es.uniovi.reflection.util.GenericSet;
import es.uniovi.reflection.weaver.methods.Method;

/**
 * 
 * {@link es.uniovi.reflection.weaver.Pointcut Pointcut } implementation used
 * when instance-level weaving is required. With this specific type of
 * {@link es.uniovi.reflection.weaver.Pointcut Pointcut }, objects can be
 * included in or excluded from the set of objects affected by the pointcut
 * definition.
 * 
 * @author Oscar Rodriguez-Prieto Date: 2017/07/11
 * 
 * @version 1.1.0
 *
 *
 */
public class PointcutForObjects extends PointcutImpl {

	private GenericSet set;

	public PointcutForObjects(MethodHandle component, Method method) {
		super(component, method);
	}

	public void setSet(GenericSet set) {
		this.set = set;
	}

	public void addObjects(Object... objects) {
		for (Object o : objects)
			set.add(o);
	}

	public void removeObjects(Object... objects) {
		for (Object o : objects)
			set.remove(o);
	}

}
