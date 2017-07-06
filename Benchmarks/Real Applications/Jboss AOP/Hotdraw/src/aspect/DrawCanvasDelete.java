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

public class DrawCanvasDelete implements Interceptor {

	static Operation o = new Operation();



	public Object invoke(Invocation invocation) throws Throwable {
		Object ret=invocation.invokeNext();
		Shape currentShape= (Shape) ((MethodCalledByMethodInvocation) invocation).getArguments()[0];
		Shapes ins= (Shapes)invocation.getTargetObject();
		
			 
			 int x = Shapes.getInstance().getPaintConsoleOrCanvas();
			 PanelPrincipal canvas = PanelPrincipal.getInstance();
			 //if (x==2)//estamos pintando en la consola
				//canvas.setVisible(false);
			 PaintCanvas lienzo = canvas.getPaintCanvas();
			o.addOperation(new Integer(2));
			o.addShapeDelete(currentShape);
			lienzo.tomaShape(currentShape, o, null);
			 lienzo.repaint();
		
		return ret;
	}


	public String getName() {
		return "DRAWCANVAS_DELETE";
	}
}
