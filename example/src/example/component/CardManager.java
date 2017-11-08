package example.component;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.invoke.MethodType;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import es.uniovi.reflection.weavej.weaver.Pointcut;
import es.uniovi.reflection.weavej.weaver.Weaver;


public class CardManager {
	static int ID_COUNTER = 1;
	private static CardManager instance = new CardManager();
	private static String weavingFile = "src/example/weaving.txt";

	public static CardManager getInstance() {
		return instance;
	}

	private CreditCard[] creditCards;

	private Date lastModifiedDate;

	private List<Pointcut> pointcuts = new ArrayList<Pointcut>();

	private String[][] getAspects(String fileName) {
		List<String[]> list = new ArrayList<String[]>();
		try {
			BufferedReader input = new BufferedReader(new FileReader(fileName));
			while (input.ready()) {
				String line = input.readLine();
				if (line != null && !line.equals("")) {
					String[] words = line.split(" ");
					if (words.length >= 3)
						list.add(words);
				}
			}
			input.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String[][] array = new String[list.size()][];
		list.toArray(array);
		return array;
	}

	private Class<?> getClassFromName(String className) throws ClassNotFoundException {
		switch (className) {
		case "boolean":
			return boolean.class;
		case "int":
			return int.class;
		case "long":
			return long.class;
		case "char":
			return char.class;
		case "byte":
			return byte.class;
		case "short":
			return short.class;
		case "float":
			return float.class;
		case "double":
			return double.class;
		}
		return Class.forName(className);
	}

	private MethodType getMethodType(String[] aspect) throws ClassNotFoundException {
		Class<?> rType = getClassFromName(aspect[6]);
		List<Class<?>> paramTypes = new ArrayList<Class<?>>();
		for (int i = 7; i < aspect.length; i++)
			paramTypes.add(getClassFromName(aspect[i]));
		return MethodType.methodType(rType, paramTypes);
	}

	private Object getStatefulInstance(String aspectClass, String arg)
			throws NoSuchMethodException, SecurityException, ClassNotFoundException, InstantiationException,
			IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Class<?> klass = Class.forName(aspectClass);

		if (arg == null)
			return klass.newInstance();
		@SuppressWarnings("rawtypes")
		Constructor ctor = klass.getDeclaredConstructor(String.class);
		ctor.setAccessible(true);
		return ctor.newInstance(arg);
	}

	private Date lastModifedDate(String fileName) {
		File f = new File(fileName);
		long datetime = f.lastModified();
		return new Date(datetime);
	}

	public void run() throws Throwable {
		while (true) {
			Thread.sleep(500);
			weaveFromFile(weavingFile);
		}
	}

	public void startTargets(int n) {
		creditCards = new CreditCard[n];
		for (int i = 0; i < n; i++) {
			creditCards[i] = new CreditCard(50 + new Random().nextInt(50));
			new Thread(creditCards[i]).start();
		}
	}

	public List<Pointcut> weave(String[][] aspects) throws Throwable {
		for (Pointcut p : pointcuts)
			p.unweave();
		pointcuts.clear();
		for (String[] aspect : aspects) {
			boolean stateFul = aspect[1].contains("FUL");
			String arg = stateFul && aspect[1].contains("-") ? aspect[1].split("-")[1] : null;
			boolean instance = aspect[1].contains("[");
			CreditCard[] cards = null;
			String[] stringIds;

			if (instance) {
				stringIds = aspect[1].substring(1).split(",");
				cards = new CreditCard[stringIds.length];
				for (int i = 0; i < stringIds.length; i++)
					cards[i] = creditCards[Integer.parseInt(stringIds[i])];
			}
			String aspectClass = aspect[2];
			String aspectMethod = aspect[3];
			String componentClass = aspect[4];
			String methodName = aspect[5];
			MethodType methodType = getMethodType(aspect);
			switch (aspect[0]) {
			case "BEFORE":
				pointcuts.add(stateFul
						? Weaver.weaveAspectForMethodBefore(componentClass, methodName,
								getStatefulInstance(aspectClass, arg), aspectMethod, methodType.returnType(),
								methodType.parameterArray())
						: instance
								? Weaver.weaveAspectForMethodBefore(componentClass, methodName, aspectClass,
										aspectMethod, cards, methodType.returnType(), methodType.parameterArray())
								: Weaver.weaveAspectForMethodBefore(componentClass, methodName, aspectClass,
										aspectMethod, methodType.returnType(), methodType.parameterArray()));

				break;
			case "AFTER":
				pointcuts.add(stateFul
						? Weaver.weaveAspectForMethodAfter(componentClass, methodName,
								getStatefulInstance(aspectClass, arg), aspectMethod, methodType.returnType(),
								methodType.parameterArray())
						: instance
								? Weaver.weaveAspectForMethodAfter(componentClass, methodName, aspectClass,
										aspectMethod, cards, methodType.returnType(), methodType.parameterArray())
								: Weaver.weaveAspectForMethodAfter(componentClass, methodName, aspectClass,
										aspectMethod, methodType.returnType(), methodType.parameterArray()));
				break;
			case "AROUND":
				pointcuts.add(stateFul
						? Weaver.weaveAspectForMethodAround(componentClass, methodName,
								getStatefulInstance(aspectClass, arg), aspectMethod, methodType.returnType(),
								methodType.parameterArray())
						: instance
								? Weaver.weaveAspectForMethodAround(componentClass, methodName, aspectClass,
										aspectMethod, cards, methodType.returnType(), methodType.parameterArray())
								: Weaver.weaveAspectForMethodAround(componentClass, methodName, aspectClass,
										aspectMethod, methodType.returnType(), methodType.parameterArray()));
				break;
			}
		}
		return pointcuts;
	}

	private void weaveFromFile(String fileName) throws Throwable {
		/*Date date = lastModifedDate(fileName);
		if (lastModifiedDate == null || date.compareTo(lastModifiedDate) > 0) {
			this.lastModifiedDate = date;
			String[][] aspects = getAspects(weavingFile);
			weave(aspects);
		}
		*/
		weave(getAspects(weavingFile));
	}
}
