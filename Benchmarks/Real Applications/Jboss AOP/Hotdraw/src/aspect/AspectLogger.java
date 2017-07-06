package aspect;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Map;

import org.jboss.aop.Advisor;
import org.jboss.aop.advice.annotation.Arg;
import org.jboss.aop.joinpoint.MethodExecution;

import loggerPackage.Log;
import shapesPackage.PointA;
import shapesPackage.Shape;
import shapesPackage.Shapes;

public class AspectLogger {

	static Log l = new Log();

	public static void activationMessage() {
		l.writeLog("ACTIVATING LOG....");
	}

	public static void deactivationMessage() {
		l.writeLog("DISACTIVATING LOG....");
	}

	public static void ShapesOperationLog(@Arg String message) {
		l.writeLog(message);
	}
}
