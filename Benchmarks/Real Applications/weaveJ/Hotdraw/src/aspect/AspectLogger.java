package aspect;

import java.util.Map;

import loggerPackage.Log;
import shapesPackage.PointA;
import shapesPackage.Shape;
import shapesPackage.Shapes;

public class AspectLogger {

	static Log l = new Log();

	public static void activationMessage(main.MainWindow p) {

		l.writeLog("ACTIVATING LOG....");
	}

	public static void deactivationMessage(main.MainWindow p) {

		l.writeLog("DISACTIVATING LOG....");
	}

	public static void moveShapeLog(Shape ins,Shape shape,String message,PointA p){
		l.writeLog(message);
	}

	public static void scaleShapeLog(Shape ins, Shape shape, String message, Map hash) {
		l.writeLog(message);
	}

	public static void ShapesOperationLog(Shapes ins, Shape shape, String message) {
		l.writeLog(message);
	}
}
