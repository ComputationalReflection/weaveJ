package es.uniovi.reflection.weaver;

import java.lang.invoke.MethodHandle;

import es.uniovi.reflection.weaver.methods.Method;

/**
 * Concrete class for the {@link es.uniovi.reflection.weaver.Pointcut Pointcut }
 * interface implementation. This implementation is used when instance-level
 * weaving is not required. This implementation stores information about the
 * next pointcut defined in the same joinpoint. This implementation also stores
 * a java.lang.invoke.MethodHandle reference to the component method that can be
 * modified at any time.
 * 
 * @author Oscar Rodriguez-Prieto Date: 2017/07/11
 * 
 * @version 1.1.0
 *
 *
 */

public class PointcutImpl implements Pointcut {

	private MethodHandle component;
	private Method method;

	private MethodHandle nextInitiator = null;
	private PointcutImpl nextPointcut = null;

	public PointcutImpl(MethodHandle component, Method method) {
		this.component = component;
		this.method = method;
	}

	public MethodHandle getComponentHandle() {
		return component;
	}

	public void setComponentHandle(MethodHandle comp) {
		component = comp;
	}

	public void setInitiator(MethodHandle ini, PointcutImpl p) {
		nextInitiator = ini;
		nextPointcut = p;
	}

	public void setNextPointcut(PointcutImpl p) {
		nextPointcut = p;
	}

	@Override
	public void unweave() throws Throwable {
		if (nextInitiator == null)
			Weaver.changeCallSite(method, component);

		else {
			nextInitiator.invoke(component);
			nextPointcut.setComponentHandle(component);
		}
		PointcutManager.changePrevInitiator(component, nextInitiator, nextPointcut);
	}
}
