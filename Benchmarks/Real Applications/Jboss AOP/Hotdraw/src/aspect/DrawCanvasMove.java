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

public class DrawCanvasMove implements Interceptor {

	static Operation o = new Operation();

	public Object invoke(Invocation invocation) throws Throwable {
		Shape currentShape = (Shape) ((MethodCalledByMethodInvocation) invocation).getArguments()[0];
		Shape ins = (Shape) invocation.getTargetObject();
		o.addOperation(new Integer(3));
		Shape sh = utilsClass.Utils.copyShape(currentShape);
		o.addShapeDelete(sh);

		Object ret = invocation.invokeNext();

		PanelPrincipal canvas = PanelPrincipal.getInstance();
		PaintCanvas lienzo = canvas.getPaintCanvas();
		lienzo.tomaShape(currentShape, o, null);
		lienzo.repaint();

		return ret;
	}

	public String getName() {
		return "DRAWCANVAS_MOVE";
	}
}
