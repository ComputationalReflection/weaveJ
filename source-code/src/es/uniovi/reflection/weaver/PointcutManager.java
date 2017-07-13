package es.uniovi.reflection.weaver;

import java.lang.invoke.MethodHandle;
import java.util.HashMap;
import java.util.Map;

/**
 * Internal class to manage java.lang.invoke.MethodHandle and
 * {@link es.uniovi.reflection.weaver.Pointcut Pointcut } objects to achieve
 * compound (un)weaving. java.lang.invoke.MethodHandle objects referring to
 * woven methods are binded to its pointcuts. When compound unweaving is
 * required, the woven method (wich may be used as component method in other
 * pointcuts), must be replaced with its component method.
 * 
 * @author Oscar Rodriguez-Prieto Date: 2017/07/11
 * 
 * @version 1.1.0
 *
 *
 */
public class PointcutManager {

	private static Map<MethodHandle, PointcutImpl> pointcuts = new HashMap<MethodHandle, PointcutImpl>();

	public static void changePrevInitiator(MethodHandle component, MethodHandle nextInitiator,
			PointcutImpl nextPointcut) {
		PointcutImpl prevPointcut = pointcuts.get(component);
		if (prevPointcut != null)
			prevPointcut.setInitiator(nextInitiator, nextPointcut);
	}

	public static void invokeInitiator(MethodHandle initiator, MethodHandle wovenMethod, PointcutImpl p)
			throws Throwable {
		initiator.invoke(p.getComponentHandle());
		weavePointcut(initiator, wovenMethod, p);
	}

	public static void weavePointcut(MethodHandle initiator, MethodHandle wovenMethod, PointcutImpl p1) {
		PointcutImpl p0 = pointcuts.get(p1.getComponentHandle());
		if (p0 != null)
			p0.setInitiator(initiator, p1);
		pointcuts.put(wovenMethod, p1);
	}

}
