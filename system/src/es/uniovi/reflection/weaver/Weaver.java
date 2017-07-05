package es.uniovi.reflection.weaver;

import java.lang.invoke.CallSite;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodType;
import java.lang.invoke.VolatileCallSite;
import java.util.HashMap;
import java.util.Map;

import es.uniovi.reflection.weaver.dynamicGeneration.DynamicExceptionHandlerGenerator;
import es.uniovi.reflection.weaver.dynamicGeneration.DynamicGeneratorAfter;
import es.uniovi.reflection.weaver.dynamicGeneration.DynamicGeneratorAround;
import es.uniovi.reflection.weaver.dynamicGeneration.DynamicGeneratorBefore;
import es.uniovi.reflection.weaver.dynamicGeneration.DynamicGeneratorConstructorAfter;
import es.uniovi.reflection.weaver.dynamicGeneration.DynamicGeneratorConstructorBefore;
import es.uniovi.reflection.weaver.dynamicGeneration.DynamicGeneratorForSingleObjectsGeneric;
import es.uniovi.reflection.weaver.dynamicGeneration.DynamicGeneratorManager;
import es.uniovi.reflection.weaver.dynamicGeneration.DynamicNameFieldGenerator;
import es.uniovi.reflection.weaver.dynamicGeneration.DynamicStatefulGenerator;
import es.uniovi.reflection.weaver.dynamicGeneration.Wrapper;
import es.uniovi.reflection.weaver.methods.Getter;
import es.uniovi.reflection.weaver.methods.Method;
import es.uniovi.reflection.weaver.methods.Setter;

public class Weaver {

	private static Map<Method, CallSite> callSiteMap = new HashMap<Method, CallSite>();

	static CallSite addCallSite(Method m, CallSite c) {
		callSiteMap.put(m, c);
		return c;
	}

	static void changeCallSite(Method m, MethodHandle mh) {
		CallSite llamada = callSiteMap.get(m);
		if (llamada == null) {
			llamada = new VolatileCallSite(mh);
			callSiteMap.put(m, llamada);
		} else {
			llamada.setTarget(mh);
		}
	}

	private static Wrapper checkForNameField(Method aspect, Method componentMethod, Wrapper gen) throws Throwable {
		try {
			aspect.getMethodHandle();
		} catch (Exception e) {
			aspect.setType(aspect.getType().insertParameterTypes(aspect.isStatic() ? 0 : 1, String.class));
			try {
				aspect.getMethodHandle();
				return gen = new DynamicNameFieldGenerator(gen, componentMethod.getName());
			} catch (Exception f) {
				throw e;
			}
		}
		return gen;
	}

	static CallSite getCallSite(Method k) {
		return callSiteMap.get(k);
	}

	private static MethodHandle getHandle(Method m)
			throws NoSuchMethodException, IllegalAccessException, NoSuchFieldException {
		CallSite c = callSiteMap.get(m);
		return c == null ? m.getMethodHandle() : c.getTarget();

	}

	private static Pointcut weave(Method aspect, Method componentMethod, MethodHandle componentHandle, Wrapper gen)
			throws Throwable {
		PointcutImpl p = new PointcutImpl(componentHandle, componentMethod);
		gen = checkForNameField(aspect, componentMethod, gen);
		changeCallSite(componentMethod,
				DynamicGeneratorManager.getDynamicGenerateMethod(aspect, componentHandle, p, gen));
		return p;
	}

	private static Pointcut weave(Method aspect, Method componentMethod, Wrapper gen) throws Throwable {
		return weave(aspect, componentMethod, getHandle(componentMethod), gen);
	}

	public static Pointcut weaveAspectForConstructorAfter(String componentClass, Object aspect, String aspectMethod,
			Class<?>... paramTypes) throws Throwable {
		MethodType methodType = MethodType.methodType(Class.forName(componentClass), paramTypes);
		Method constructor = new Method("init", true, methodType.returnType(), methodType);
		return weaveForFullState(
				new Method(aspectMethod, false, aspect.getClass(),
						MethodType.methodType(void.class, constructor.getKlass())),
				constructor, new DynamicGeneratorConstructorAfter(), aspect);
	}

	public static Pointcut weaveAspectForConstructorAfter(String componentClass, String aspectClass,
			String aspectMethod, Class<?>... paramTypes) throws Throwable {
		MethodType methodType = MethodType.methodType(Class.forName(componentClass), paramTypes);
		Method constructor = new Method("init", true, methodType.returnType(), methodType);
		return weave(
				new Method(aspectMethod, true, Class.forName(aspectClass),
						MethodType.methodType(void.class, constructor.getReturnType())),
				constructor, new DynamicGeneratorConstructorAfter());
	}

	public static Pointcut weaveAspectForConstructorAround(String componentClass, Object aspectInstance,
			String aspectMethod, Class<?>... paramTypes) throws Throwable {
		MethodType methodType = MethodType.methodType(Class.forName(componentClass), paramTypes);
		Method constructor = new Method("init", true, methodType.returnType(), methodType);
		Method aspect = new Method(aspectMethod, false, aspectInstance.getClass(),
				MethodType.methodType(constructor.getKlass(), MethodHandle.class, paramTypes));
		return weaveForFullState(aspect, constructor, new DynamicGeneratorAround(), aspectInstance);
	}

	public static Pointcut weaveAspectForConstructorAround(String componentClass, String aspectClass,
			String aspectMethod, Class<?>... paramTypes) throws Throwable {
		MethodType methodType = MethodType.methodType(Class.forName(componentClass), paramTypes);
		Method constructor = new Method("init", true, methodType.returnType(), methodType);
		Method aspect = new Method(aspectMethod, true, Class.forName(aspectClass),
				MethodType.methodType(constructor.getKlass(), MethodHandle.class, paramTypes));
		return weave(aspect, constructor, new DynamicGeneratorAround());
	}

	public static Pointcut weaveAspectForConstructorBefore(String componentClass, Object aspect, String aspectMethod,
			Class<?>... paramTypes) throws Throwable {
		MethodType methodType = MethodType.methodType(Class.forName(componentClass), paramTypes);
		Method constructor = new Method("init", true, methodType.returnType(), methodType);
		return weaveForFullState(
				new Method(aspectMethod, false, aspect.getClass(), MethodType.methodType(void.class, paramTypes)),
				constructor, new DynamicGeneratorConstructorBefore(), aspect);

	}

	public static Pointcut weaveAspectForConstructorBefore(String componentClass, String aspectClass,
			String aspectMethod, Class<?>... paramTypes) throws Throwable {
		MethodType methodType = MethodType.methodType(Class.forName(componentClass), paramTypes);
		Method constructor = new Method("init", true, methodType.returnType(), methodType);

		return weave(
				new Method(aspectMethod, true, Class.forName(aspectClass),
						MethodType.methodType(void.class, paramTypes)),
				constructor, new DynamicGeneratorConstructorBefore());

	}

	public static Pointcut weaveAspectForFieldAccessGetAfter(String componentClass, String fieldName, Object aspect,
			String aspectMethod, Class<?> fieldType) throws Throwable {
		Getter getter = new Getter(fieldName, fieldType, Class.forName(componentClass));

		return weaveForFullState(
				new Method(aspectMethod, false, aspect.getClass(),
						MethodType.methodType(void.class, getter.getKlass(), fieldType)),
				getter, new DynamicGeneratorAfter(), aspect);
	}

	public static Pointcut weaveAspectForFieldAccessGetAfter(String componentClass, String fieldName,
			String aspectClass, String aspectMethod, Class<?> fieldType) throws Throwable {
		Getter getter = new Getter(fieldName, fieldType, Class.forName(componentClass));

		return weave(
				new Method(aspectMethod, true, Class.forName(aspectClass),
						MethodType.methodType(void.class, getter.getKlass(), fieldType)),
				getter, new DynamicGeneratorAfter());
	}

	public static PointcutForObjects weaveAspectForFieldAccessGetAfter(String componentClass, String fieldName,
			String aspectClass, String aspectMethod, Class<?> fieldType, Object... objs) throws Throwable {
		Getter getter = new Getter(fieldName, fieldType, Class.forName(componentClass));

		return weaveForObject(
				new Method(aspectMethod, true, Class.forName(aspectClass),
						MethodType.methodType(void.class, getter.getKlass(), fieldType)),
				getter, new DynamicGeneratorAfter(), objs);
	}

	public static Pointcut weaveAspectForFieldAccessGetAround(String componentClass, String fieldName,
			Object aspectInstance, String aspectMethod, Class<?> fieldType) throws Throwable {
		Getter getter = new Getter(fieldName, fieldType, Class.forName(componentClass));
		Method aspect = new Method(aspectMethod, false, aspectInstance.getClass(),
				getter.getType().insertParameterTypes(0, MethodHandle.class));
		return weaveForFullState(aspect, getter, new DynamicGeneratorAround(), aspectInstance);
	}

	public static Pointcut weaveAspectForFieldAccessGetAround(String componentClass, String fieldName,
			String aspectClass, String aspectMethod, Class<?> fieldType) throws Throwable {
		Getter getter = new Getter(fieldName, fieldType, Class.forName(componentClass));
		Method aspect = new Method(aspectMethod, true, Class.forName(aspectClass),
				getter.getType().insertParameterTypes(0, MethodHandle.class));
		return weave(aspect, getter, new DynamicGeneratorAround());
	}

	public static PointcutForObjects weaveAspectForFieldAccessGetAround(String componentClass, String fieldName,
			String aspectClass, String aspectMethod, Class<?> fieldType, Object[] objs) throws Throwable {
		Getter getter = new Getter(fieldName, fieldType, Class.forName(componentClass));
		Method aspect = new Method(aspectMethod, true, Class.forName(aspectClass),
				getter.getType().insertParameterTypes(0, MethodHandle.class));
		return weaveForObject(aspect, getter, new DynamicGeneratorAround(), objs);
	}

	public static Pointcut weaveAspectForFieldAccessGetBefore(String componentClass, String fieldName, Object aspect,
			String aspectMethod, Class<?> fieldType) throws Throwable {
		Getter getter = new Getter(fieldName, fieldType, Class.forName(componentClass));
		return weaveForFullState(
				new Method(aspectMethod, false, aspect.getClass(),
						MethodType.methodType(void.class, getter.getKlass())),
				getter, new DynamicGeneratorBefore(), aspect);

	}

	public static Pointcut weaveAspectForFieldAccessGetBefore(String componentClass, String fieldName,
			String aspectClass, String aspectMethod, Class<?> fieldType) throws Throwable {
		Getter getter = new Getter(fieldName, fieldType, Class.forName(componentClass));
		return weave(new Method(aspectMethod, true, Class.forName(aspectClass),
				MethodType.methodType(void.class, getter.getKlass())), getter, new DynamicGeneratorBefore());

	}

	public static PointcutForObjects weaveAspectForFieldAccessGetBefore(String componentClass, String fieldName,
			String aspectClass, String aspectMethod, Class<?> fieldType, Object... objs) throws Throwable {
		Getter getter = new Getter(fieldName, fieldType, Class.forName(componentClass));
		return weaveForObject(
				new Method(aspectMethod, true, Class.forName(aspectClass),
						MethodType.methodType(void.class, getter.getKlass())),
				getter, new DynamicGeneratorBefore(), objs);

	}

	public static Pointcut weaveAspectForFieldAccessSetAfter(String componentClass, String fieldName, Object aspect,
			String aspectMethod, Class<?> fieldType) throws Throwable {
		Setter setter = new Setter(fieldName, fieldType, Class.forName(componentClass));
		return weaveForFullState(
				new Method(aspectMethod, false, aspect.getClass(),
						MethodType.methodType(void.class, setter.getKlass())),
				setter, new DynamicGeneratorAfter(), aspect);

	}

	public static Pointcut weaveAspectForFieldAccessSetAfter(String componentClass, String fieldName,
			String aspectClass, String aspectMethod, Class<?> fieldType) throws Throwable {
		Setter setter = new Setter(fieldName, fieldType, Class.forName(componentClass));
		return weave(new Method(aspectMethod, true, Class.forName(aspectClass),
				MethodType.methodType(void.class, setter.getKlass())), setter, new DynamicGeneratorAfter());

	}

	public static PointcutForObjects weaveAspectForFieldAccessSetAfter(String componentClass, String fieldName,
			String aspectClass, String aspectMethod, Class<?> fieldType, Object... objs) throws Throwable {
		Setter setter = new Setter(fieldName, fieldType, Class.forName(componentClass));
		return weaveForObject(
				new Method(aspectMethod, true, Class.forName(aspectClass),
						MethodType.methodType(void.class, setter.getKlass())),
				setter, new DynamicGeneratorAfter(), objs);

	}

	public static Pointcut weaveAspectForFieldAccessSetAround(String componentClass, String fieldName,
			Object aspectInstance, String aspectMethod, Class<?> fieldType) throws Throwable {
		Setter setter = new Setter(fieldName, fieldType, Class.forName(componentClass));

		Method aspect = new Method(aspectMethod, false, aspectInstance.getClass(),
				setter.getType().insertParameterTypes(0, MethodHandle.class));
		return weaveForFullState(aspect, setter, new DynamicGeneratorAround(), aspectInstance);
	}

	public static Pointcut weaveAspectForFieldAccessSetAround(String componentClass, String fieldName,
			String aspectClass, String aspectMethod, Class<?> fieldType) throws Throwable {
		Setter setter = new Setter(fieldName, fieldType, Class.forName(componentClass));

		Method aspect = new Method(aspectMethod, true, Class.forName(aspectClass),
				setter.getType().insertParameterTypes(0, MethodHandle.class));
		return weave(aspect, setter, new DynamicGeneratorAround());
	}

	public static PointcutForObjects weaveAspectForFieldAccessSetAround(String componentClass, String fieldName,
			String aspectClass, String aspectMethod, Class<?> fieldType, Object... objs) throws Throwable {
		Setter setter = new Setter(fieldName, fieldType, Class.forName(componentClass));

		Method aspect = new Method(aspectMethod, true, Class.forName(aspectClass),
				setter.getType().insertParameterTypes(0, MethodHandle.class));
		return weaveForObject(aspect, setter, getHandle(setter), new DynamicGeneratorAround(), objs);
	}

	public static Pointcut weaveAspectForFieldAccessSetBefore(String componentClass, String fieldName, Object aspect,
			String aspectMethod, Class<?> fieldType) throws Throwable {
		Setter setter = new Setter(fieldName, fieldType, Class.forName(componentClass));
		return weaveForFullState(
				new Method(aspectMethod, false, aspect.getClass(),
						MethodType.methodType(void.class, setter.getKlass(), fieldType)),
				setter, new DynamicGeneratorBefore(), aspect);

	}

	public static Pointcut weaveAspectForFieldAccessSetBefore(String componentClass, String fieldName,
			String aspectClass, String aspectMethod, Class<?> fieldType) throws Throwable {
		Setter setter = new Setter(fieldName, fieldType, Class.forName(componentClass));
		return weave(
				new Method(aspectMethod, true, Class.forName(aspectClass),
						MethodType.methodType(void.class, setter.getKlass(), fieldType)),
				setter, new DynamicGeneratorBefore());

	}

	public static PointcutForObjects weaveAspectForFieldAccessSetBefore(String componentClass, String fieldName,
			String aspectClass, String aspectMethod, Class<?> fieldType, Object... objs) throws Throwable {
		Setter setter = new Setter(fieldName, fieldType, Class.forName(componentClass));
		return weaveForObject(
				new Method(aspectMethod, true, Class.forName(aspectClass),
						MethodType.methodType(void.class, setter.getKlass(), fieldType)),
				setter, new DynamicGeneratorBefore(), objs);

	}

	public static Pointcut weaveAspectForMethodAfter(String componentClass, String componentMethod, Object aspect,
			String aspectMethod, Class<?> RType, Class<?>... PTypes) throws Throwable {
		Class<?> componentKlass = Class.forName(componentClass);
		Method methodComp = new Method(componentMethod, false, componentKlass, MethodType.methodType(RType, PTypes));
		return weaveForFullState(
				new Method(aspectMethod, false, aspect.getClass(),
						RType.equals(void.class) ? MethodType.methodType(void.class, componentKlass)
								: MethodType.methodType(void.class, componentKlass, RType)),
				methodComp, new DynamicGeneratorAfter(), aspect);
	}

	public static Pointcut weaveAspectForMethodAfter(String componentClass, String componentMethod, String aspectClass,
			String aspectMethod, Class<?> RType, Class<?>... PTypes) throws Throwable {
		Class<?> componentKlass = Class.forName(componentClass);
		Method methodComp = new Method(componentMethod, false, componentKlass, MethodType.methodType(RType, PTypes));
		return weave(new Method(aspectMethod, true, Class.forName(aspectClass),
				RType.equals(void.class) ? MethodType.methodType(void.class, componentKlass) :

		MethodType.methodType(void.class, componentKlass, RType)), methodComp, new DynamicGeneratorAfter());
	}

	public static PointcutForObjects weaveAspectForMethodAfter(String componentClass, String componentMethod,
			String aspectClass, String aspectMethod, Object[] objs, Class<?> RType, Class<?>... PTypes)
					throws Throwable {
		Class<?> componentKlass = Class.forName(componentClass);
		Method methodComp = new Method(componentMethod, false, componentKlass, MethodType.methodType(RType, PTypes));
		return weaveForObject(
				new Method(aspectMethod, true, Class.forName(aspectClass),
						RType.equals(void.class) ? MethodType.methodType(void.class, componentKlass)
								: MethodType.methodType(void.class, componentKlass, RType)),
				methodComp, new DynamicGeneratorAfter(), objs);
	}

	public static Pointcut weaveAspectForMethodAround(String componentClass, String componentMethod,
			Object aspectInstance, String aspectMethod, Class<?> RType, Class<?>... PTypes) throws Throwable {

		Method component = new Method(componentMethod, false, Class.forName(componentClass),
				MethodType.methodType(RType, PTypes));
		Method aspect = new Method(aspectMethod, false, aspectInstance.getClass(),
				component.getType().insertParameterTypes(0, MethodHandle.class));
		return weaveForFullState(aspect, component, new DynamicGeneratorAround(), aspectInstance);
	}

	public static Pointcut weaveAspectForMethodAround(String componentClass, String componentMethod, String aspectClass,
			String aspectMethod, Class<?> RType, Class<?>... PTypes) throws Throwable {

		Method component = new Method(componentMethod, false, Class.forName(componentClass),
				MethodType.methodType(RType, PTypes));
		Method aspect = new Method(aspectMethod, true, Class.forName(aspectClass),
				component.getType().insertParameterTypes(0, MethodHandle.class));
		return weave(aspect, component, new DynamicGeneratorAround());
	}

	public static PointcutForObjects weaveAspectForMethodAround(String componentClass, String componentMethod,
			String aspectClass, String aspectMethod, Object[] objs, Class<?> RType, Class<?>... PTypes)
					throws Throwable {

		Method component = new Method(componentMethod, false, Class.forName(componentClass),
				MethodType.methodType(RType, PTypes));
		Method aspect = new Method(aspectMethod, true, Class.forName(aspectClass),
				component.getType().insertParameterTypes(0, MethodHandle.class));
		return weaveForObject(aspect, component, new DynamicGeneratorAround(), objs);
	}

	public static Pointcut weaveAspectForMethodBefore(String componentClass, String componentMethod, Object aspect,
			String aspectMethod, Class<?> RType, Class<?>... PTypes) throws Throwable {
		Class<?> claseComponente = Class.forName(componentClass);
		MethodType componentMethodType = MethodType.methodType(RType, PTypes);
		Method compMethod = new Method(componentMethod, false, claseComponente, componentMethodType);
		return weaveForFullState(
				new Method(aspectMethod, false, aspect.getClass(),
						componentMethodType.insertParameterTypes(0, claseComponente).changeReturnType(void.class)),
				compMethod, new DynamicGeneratorBefore(), aspect);
	}

	public static Pointcut weaveAspectForMethodBefore(String componentClass, String componentMethod, String aspectClass,
			String aspectMethod, Class<?> RType, Class<?>... PTypes) throws Throwable {
		Class<?> claseComponente = Class.forName(componentClass);
		MethodType componentMethodType = MethodType.methodType(RType, PTypes);
		Method compMethod = new Method(componentMethod, false, claseComponente, componentMethodType);
		return weave(
				new Method(aspectMethod, true, Class.forName(aspectClass),
						componentMethodType.insertParameterTypes(0, claseComponente).changeReturnType(void.class)),
				compMethod, new DynamicGeneratorBefore());
	}

	public static PointcutForObjects weaveAspectForMethodBefore(String componentClass, String componentMethod,
			String aspectClass, String aspectMethod, Object[] objs, Class<?> RType, Class<?>... PTypes)
					throws Throwable {
		Class<?> claseComponente = Class.forName(componentClass);
		MethodType componentMethodType = MethodType.methodType(RType, PTypes);
		Method compMethod = new Method(componentMethod, false, claseComponente, componentMethodType);
		return weaveForObject(
				new Method(aspectMethod, true, Class.forName(aspectClass),
						componentMethodType.insertParameterTypes(0, claseComponente).changeReturnType(void.class)),
				compMethod, new DynamicGeneratorBefore(), objs);
	}

	public static Pointcut weaveExceptionHandlerForConstructor(String componentClass, Object aspect,
			String aspectMethod, String exceptionClass, Class<?>... PTypes) throws ClassNotFoundException, Throwable {
		Class<?> klass = Class.forName(componentClass);
		Class<?> exceptionType = Class.forName(exceptionClass);
		Class<?>[] aspectParams = new Class<?>[PTypes.length + 1];
		aspectParams[0] = exceptionType;
		for (int i = 0; i < PTypes.length; i++)
			aspectParams[i + 1] = PTypes[i];

		return weaveForFullState(
				new Method(aspectMethod, false, aspect.getClass(), MethodType.methodType(klass, aspectParams)),
				new Method("init", true, klass, MethodType.methodType(klass, PTypes)),
				new DynamicExceptionHandlerGenerator(exceptionType), aspect);

	}

	public static PointcutForObjects weaveExceptionHandlerForConstructor(String componentClass, String aspectClass,
			String aspectMethod, Object[] objs, String exceptionClass, Class<?>... PTypes)
					throws ClassNotFoundException, Throwable {
		Class<?> klass = Class.forName(componentClass);
		Class<?> exceptionType = Class.forName(exceptionClass);
		Class<?>[] aspectParams = new Class<?>[PTypes.length + 1];
		aspectParams[0] = exceptionType;
		for (int i = 0; i < PTypes.length; i++)
			aspectParams[i + 1] = PTypes[i];

		return weaveForObject(
				new Method(aspectMethod, true, Class.forName(aspectClass), MethodType.methodType(klass, aspectParams)),
				new Method("init", true, klass, MethodType.methodType(klass, PTypes)),
				new DynamicExceptionHandlerGenerator(exceptionType), objs);

	}

	public static Pointcut weaveExceptionHandlerForConstructor(String componentClass, String aspectClass,
			String aspectMethod, String exceptionClass, Class<?>... PTypes) throws ClassNotFoundException, Throwable {

		Class<?> klass = Class.forName(componentClass);
		Class<?> exceptionType = Class.forName(exceptionClass);
		Class<?>[] aspectParams = new Class<?>[PTypes.length + 1];
		aspectParams[0] = exceptionType;
		for (int i = 0; i < PTypes.length; i++)
			aspectParams[i + 1] = PTypes[i];

		return weave(
				new Method(aspectMethod, true, Class.forName(aspectClass), MethodType.methodType(klass, aspectParams)),
				new Method("init", true, klass, MethodType.methodType(klass, PTypes)),
				new DynamicExceptionHandlerGenerator(exceptionType));

	}

	public static Pointcut weaveExceptionHandlerForMethod(String componentClass, String componentMethod, Object aspect,
			String aspectMethod, String exceptionClass, Class<?> RType, Class<?>... PTypes)
					throws ClassNotFoundException, Throwable {
		Class<?>[] aspectParams = new Class<?>[PTypes.length + 2];
		aspectParams[0] = Class.forName(exceptionClass);
		aspectParams[1] = Class.forName(componentClass);
		for (int i = 0; i < PTypes.length; i++)
			aspectParams[i + 2] = PTypes[i];
		return weaveForFullState(
				new Method(aspectMethod, false, aspect.getClass(), MethodType.methodType(RType, aspectParams)),
				new Method(componentMethod, false, Class.forName(componentClass), MethodType.methodType(RType, PTypes)),
				new DynamicExceptionHandlerGenerator(Class.forName(exceptionClass)), aspect);

	}

	public static Pointcut weaveExceptionHandlerForMethod(String componentClass, String componentMethod,
			String aspectClass, String aspectMethod, String exceptionClass, Class<?> RType, Class<?>... PTypes)
					throws ClassNotFoundException, Throwable {
		Class<?>[] aspectParams = new Class<?>[PTypes.length + 2];
		aspectParams[0] = Class.forName(exceptionClass);
		aspectParams[1] = Class.forName(componentClass);
		for (int i = 0; i < PTypes.length; i++)
			aspectParams[i + 2] = PTypes[i];
		return weave(
				new Method(aspectMethod, true, Class.forName(aspectClass), MethodType.methodType(RType, aspectParams)),
				new Method(componentMethod, false, Class.forName(componentClass), MethodType.methodType(RType, PTypes)),
				new DynamicExceptionHandlerGenerator(Class.forName(exceptionClass)));

	}

	public static PointcutForObjects weaveExceptionHandlerForMethod(String componentClass, String componentMethod,
			String aspectClass, String aspectMethod, String exceptionClass, Object[] objs, Class<?> RType,
			Class<?>... PTypes) throws ClassNotFoundException, Throwable {

		Class<?>[] aspectParams = new Class<?>[PTypes.length + 2];

		aspectParams[0] = Class.forName(exceptionClass);
		aspectParams[1] = Class.forName(componentClass);
		for (int i = 0; i < PTypes.length; i++)
			aspectParams[i + 2] = PTypes[i];
		return weaveForObject(
				new Method(aspectMethod, true, Class.forName(aspectClass), MethodType.methodType(RType, aspectParams)),
				new Method(componentMethod, false, Class.forName(componentClass), MethodType.methodType(RType, PTypes)),
				new DynamicExceptionHandlerGenerator(Class.forName(exceptionClass)), objs);

	}

	private static Pointcut weaveForFullState(Method aspect, Method componentMethod, MethodHandle componentHandle,
			Wrapper gen, Object aspectInstance) throws Throwable {
		PointcutImpl p = new PointcutImpl(componentHandle, componentMethod);
		gen = checkForNameField(aspect, componentMethod, gen);
		changeCallSite(componentMethod, DynamicGeneratorManager.getDynamicGenerateMethod(aspect, componentHandle, p,
				new DynamicStatefulGenerator(gen, aspectInstance)));
		return p;
	}

	private static Pointcut weaveForFullState(Method aspect, Method componentMethod, Wrapper gen, Object aspectInstance)
			throws Throwable {
		return weaveForFullState(aspect, componentMethod, getHandle(componentMethod), gen, aspectInstance);
	}

	private static PointcutForObjects weaveForObject(Method aspect, Method componentMethod,
			MethodHandle componentHandle, Wrapper gen, Object... objs) throws Throwable {
		PointcutForObjects p = new PointcutForObjects(componentHandle, componentMethod);
		gen = checkForNameField(aspect, componentMethod, gen);
		changeCallSite(componentMethod, DynamicGeneratorManager.getDynamicGenerateMethod(aspect, componentHandle, p,
				new DynamicGeneratorForSingleObjectsGeneric(gen, objs)));
		return p;
	}

	private static PointcutForObjects weaveForObject(Method aspect, Method componentMethod, Wrapper gen, Object... objs)
			throws Throwable {
		return weaveForObject(aspect, componentMethod, getHandle(componentMethod), gen, objs);
	}
}
