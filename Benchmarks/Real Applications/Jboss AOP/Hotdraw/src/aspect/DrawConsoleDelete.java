package aspect;

import java.util.Map;

import org.jboss.aop.advice.Interceptor;
import org.jboss.aop.advice.annotation.Arg;
import org.jboss.aop.advice.annotation.Target;
import org.jboss.aop.joinpoint.Invocation;
import org.jboss.aop.joinpoint.MethodCalledByMethodInvocation;

import shapesPackage.PointA;
import shapesPackage.Shape;
import shapesPackage.Shapes;
import utilsClass.Operation;

public class DrawConsoleDelete implements Interceptor{




	public  Object invoke(Invocation invocation) throws Throwable {
		Object ret=invocation.invokeNext();
		Shape currentShape= (Shape) ((MethodCalledByMethodInvocation) invocation).getArguments()[0];
		Shapes ins= (Shapes)invocation.getTargetObject();
			System.out.println(
					"DELETING shape : " + currentShape.getClass().getName() + " con id " + currentShape.getId());
			System.out.println(utilsClass.Utils.datasShow(currentShape));
		
		return ret;
	}


	public String getName() {
		return "DRAWCONSOLE_DELETE";
	}



}
