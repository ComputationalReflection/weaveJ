package example.component;

public class Main {
	public static void main(String a[]) throws Throwable {
		CardManager cm = CardManager.getInstance();
		cm.startTargets(10);
		cm.run();
	}

}
