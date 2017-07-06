package main;

import java.util.ArrayList;
import java.util.List;

import org.jboss.aop.AspectManager;
import org.jboss.aop.advice.AdviceBinding;
import org.jboss.aop.advice.AdviceFactory;
import org.jboss.aop.advice.AdviceType;
import org.jboss.aop.advice.AspectDefinition;
import org.jboss.aop.advice.AspectFactory;
import org.jboss.aop.advice.GenericAspectFactory;
import org.jboss.aop.advice.Scope;
import org.jboss.aop.pointcut.ast.ParseException;

public class BindingUtil {
	private static final boolean DEBUG = false;
	public static final int LOG = 0, PROFILE = 1, DRAW_CANVAS = 2, DRAW_CONSOLE = 3;

	private static List<List<String>> bindingNames = initList();

	public static AdviceBinding addBindingInterceptor(Class<?> interceptor, String pointcutExp, int groupIndex) {
		AdviceBinding binding = null;
		try {
			binding = new AdviceBinding(pointcutExp, null);
			if (DEBUG)
				System.out.println("Created adviceBinding...");
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		binding.addInterceptor(interceptor);
		if (DEBUG)
			System.out.println("Added interceptor...");
		AspectManager.instance().addBinding(binding);
		if (DEBUG)
			System.out.println("Added binding...");
		bindingNames.get(groupIndex).add(binding.getName());
		return binding;
	}

	public static AdviceBinding addBinding(String aspectClass, String aspectName, String aspectMethod,
			AdviceType adviceType, String pointcutExp, int groupIndex) {
		if (DEBUG)
			System.out.println("INIT BINDING ");
		AspectFactory aspectFactory = new GenericAspectFactory(aspectClass, null);
		if (DEBUG)
			System.out.println("Aspect factory done....");
		AspectDefinition aspectDef = new AspectDefinition(aspectName, Scope.PER_CLASS, aspectFactory);
		if (DEBUG)
			System.out.println("Aspect definition done...");
		AspectManager manager = AspectManager.instance();
		manager.addAspectDefinition(aspectDef);
		if (DEBUG)
			System.out.println("Added definition...");
		AdviceFactory adviceFactory = new AdviceFactory(aspectDef, aspectMethod, adviceType);
		AdviceBinding binding = null;
		try {
			binding = new AdviceBinding(pointcutExp, null);
			if (DEBUG)
				System.out.println("Created adviceBinding...");
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		binding.addInterceptorFactory(adviceFactory);
		if (DEBUG)
			System.out.println("Added factory...");
		manager.addBinding(binding);
		if (DEBUG)
			System.out.println("Added binding...");
		bindingNames.get(groupIndex).add(binding.getName());
		return binding;
	}

	private static List<List<String>> initList() {
		List<List<String>> list = new ArrayList<List<String>>();
		for (int i = 0; i < DRAW_CONSOLE + 1; i++)
			list.add(new ArrayList<String>());
		return list;
	}

	public static void removeBindings(int bindingGrup) {
		if (DEBUG)
			System.out.println("Trying to remove....");
		for (String s : bindingNames.get(bindingGrup))
			AspectManager.instance().removeBinding(s);
		if (DEBUG)
			System.out.println("Removed");
	}
}
