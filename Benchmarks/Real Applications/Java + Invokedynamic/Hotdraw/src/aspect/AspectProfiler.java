package aspect;

import java.util.Map;

import demos.Demo1;
import demos.Demo2;
import demos.Demo3;
import demos.Demo4;
import demos.DemoTimer;
import profilerPackage.Profiler;
import shapesPackage.PointA;
import shapesPackage.Shape;
import shapesPackage.Shapes;

public class AspectProfiler {

	static String message = null;
	static Profiler p = new Profiler();

	public static void activateProfiler(main.MainWindow prueba) {
		p.writeProfiler("ACTIVATING PROFILER....");
	}

	public static void deactivateProfiler(main.MainWindow prueba) {
		p.writeProfiler("DISACTIVATING PROFILER....");
	}

	public static void endDemoProfile(Demo1 ins) {
		long endTime = p.endTimeDemo();
		p.writeProfiler("Time necesary to execute demo: " + endTime + "ms");
	}

	public static void endDemoProfile(Demo2 ins) {
		long endTime = p.endTimeDemo();
		p.writeProfiler("Time necesary to execute demo: " + endTime + "ms");
	}

	public static void endDemoProfile(Demo3 ins) {
		long endTime = p.endTimeDemo();
		p.writeProfiler("Time necesary to execute demo: " + endTime + "ms");
	}

	public static void endDemoProfile(Demo4 ins) {
		long endTime = p.endTimeDemo();
		p.writeProfiler("Time necesary to execute demo: " + endTime + "ms");
	}

	public static void endDemoProfile(DemoTimer ins) {
		long endTime = p.endTimeDemo();
		p.writeProfiler("Time necesary to execute demo: " + endTime + "ms");
	}

	public static void initDemoProfile(Demo1 ins) {

		p.initTimeDemo();
		p.writeProfiler("starting demo....");
	}

	public static void initDemoProfile(Demo2 ins) {

		p.initTimeDemo();
		p.writeProfiler("starting demo....");
	}

	public static void initDemoProfile(Demo3 ins) {

		p.initTimeDemo();
		p.writeProfiler("starting demo....");
	}

	public static void initDemoProfile(Demo4 ins) {

		p.initTimeDemo();
		p.writeProfiler("starting demo....");
	}

	public static void initDemoProfile(DemoTimer ins) {

		p.initTimeDemo();
		p.writeProfiler("starting demo....");
	}

	public static  void profileMoveShape(Shape ins, Shape shape, String message, PointA point) {
		AspectProfiler.message = message;
		p.initTime();
	}

	public static void profileOperationEnd(Shape ins) {
		String message = AspectProfiler.message + ", Time necesary: " + p.endTime() + "ms";
		p.writeProfiler(message);
	}

	public static void profileOperationEnd(Shapes ins) {
		String message = AspectProfiler.message + ", Time necesary: " + p.endTime() + "ms";
		p.writeProfiler(message);
	}

	public static void profileScaleShape(Shape ins, Shape shape, String message, Map hash) {
		AspectProfiler.message = message;
		p.initTime();
	}

	public static void profileShapesOperation(Shapes ins, Shape shape, String message) {
	AspectProfiler.message = message;
	p.initTime();
}
}
