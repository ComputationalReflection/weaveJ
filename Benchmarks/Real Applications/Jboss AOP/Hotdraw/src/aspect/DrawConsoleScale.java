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

public class DrawConsoleScale implements Interceptor{




	public  Object invoke(Invocation invocation) throws Throwable {
		Object ret=invocation.invokeNext();
		Shape currentShape= (Shape) ((MethodCalledByMethodInvocation) invocation).getArguments()[0];
		Shape ins= (Shape)invocation.getTargetObject();
			System.out.println(
					"SCALING shape : " + currentShape.getClass().getName() + " con id " + currentShape.getId());
			System.out.println(utilsClass.Utils.datasShow(currentShape));
		
		return ret;
	}


	public String getName() {
		return "DRAWCONSOLE_SCALE";
	}



}
