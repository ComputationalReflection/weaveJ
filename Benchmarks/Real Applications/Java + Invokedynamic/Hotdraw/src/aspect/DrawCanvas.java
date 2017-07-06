package aspect;

import java.util.Map;

import paintCanvas.PaintCanvas;
import paintCanvas.PanelPrincipal;
import shapesPackage.PointA;
import shapesPackage.Shape;
import shapesPackage.Shapes;
import utilsClass.Operation;

public class DrawCanvas {

	static Shape currentShape;

	static Operation o = new Operation();

	public static void afterDeleteShape(Shapes ins) {

		if (ins.getPaintConsoleOrCanvas() == 2 || ins.getPaintConsoleOrCanvas() == 3)
		{
			 
			 int x = Shapes.getInstance().getPaintConsoleOrCanvas();
			 PanelPrincipal canvas = PanelPrincipal.getInstance();
			 //if (x==2)//estamos pintando en la consola
				//canvas.setVisible(false);
			 PaintCanvas lienzo = canvas.getPaintCanvas();
			 o.addOperation(new Integer(2));
			o.addShapeDelete(currentShape);
			lienzo.tomaShape(currentShape, o, null);
			 lienzo.repaint();
		}
	}

	public static void afterInsertShape(Shapes ins) {
		if (ins.getPaintConsoleOrCanvas() == 2 || ins.getPaintConsoleOrCanvas() == 3) {
			PanelPrincipal canvas = PanelPrincipal.getInstance();
			PaintCanvas lienzo = canvas.getPaintCanvas();
			o.addOperation(new Integer(1));
			lienzo.tomaShape(currentShape, o, null);
			lienzo.repaint();
		}
	}

	public static void afterOperationOnShape(Shape ins) {
		if (ins.getPaintedIn() == 2 || ins.getPaintedIn() == 3) {
			PanelPrincipal canvas = PanelPrincipal.getInstance();
			PaintCanvas lienzo = canvas.getPaintCanvas();
			lienzo.tomaShape(currentShape, o, null);
			lienzo.repaint();
		}
	}


	public static void beforeMoveShape(Shape ins, Shape s, String message, PointA p) {
		currentShape = s;
		if (ins.getPaintedIn() == 2 || ins.getPaintedIn() == 3) {
			o.addOperation(new Integer(3));
			Shape sh = utilsClass.Utils.copyShape(s);
			o.addShapeDelete(sh);
		}
	}

	public static void beforeOperationOnShapes(Shapes ins, Shape s, String message) {
		currentShape = s;
	}

	public static void beforeScaleShape(Shape ins, Shape s, String message, Map hash) {
		currentShape = s;

		if (ins.getPaintedIn() == 2 || ins.getPaintedIn() == 3) {
			o.addOperation(new Integer(4));
			Shape sh = utilsClass.Utils.copyShape(s);
			o.addShapeDelete(sh);
		}
	}
}
