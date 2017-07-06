package aspect;

import java.util.Map;

import org.jboss.aop.advice.Interceptor;
import org.jboss.aop.advice.annotation.Arg;
import org.jboss.aop.advice.annotation.Target;
import org.jboss.aop.joinpoint.Invocation;
import org.jboss.aop.joinpoint.MethodCalledByMethodInvocation;

import paintCanvas.PaintCanvas;
import paintCanvas.PanelPrincipal;
import shapesPackage.PointA;
import shapesPackage.Shape;
import shapesPackage.Shapes;
import utilsClass.Operation;

public class DrawCanvasInsert implements Interceptor {


	static Operation o = new Operation();


	public Object invoke(Invocation invocation) throws Throwable {
		Object ret=invocation.invokeNext();
		Shape currentShape= (Shape) ((MethodCalledByMethodInvocation) invocation).getArguments()[0];
		Shapes ins= (Shapes)invocation.getTargetObject();
		
			PanelPrincipal canvas = PanelPrincipal.getInstance();
			PaintCanvas lienzo = canvas.getPaintCanvas();
			o.addOperation(new Integer(1));
			lienzo.tomaShape(currentShape, o, null);
			lienzo.repaint();
		
		return ret;
	}


	public String getName() {
		// TODO Auto-generated method stub
		return "DRAWCANVAS_INSERT";
	}
}
