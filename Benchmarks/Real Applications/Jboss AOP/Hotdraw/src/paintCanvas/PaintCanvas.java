package paintCanvas;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.util.List;

import controller.Logical;
import shapesPackage.Shape;
import utilsClass.Operation;

public class PaintCanvas extends Canvas {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Logical logical = null;
	private int n = -1;
	private Operation operation = new Operation();
	private Shape shape = null;
	private Shape shapeDelete = null;

	/**
	 * Constructor. Hace que el tamaño del canvas sea 100x100 pixels.
	 */
	public PaintCanvas() { // toda la pantalla
							// this.setSize (600,
							// this.getToolkit().getScreenSize().height);
		this.setSize(600, 750);
		this.setBackground(Color.white);
		this.operation.addOperation(new Integer(-1));
		this.logical = new Logical();
	}

	@Override
	public void paint(Graphics g) {
		// List listShapes = Shapes.getInstance().getListShapes();
		Object ope[];
		ope = this.operation.getOperation();
		n = ((Integer) ope[0]).intValue();
		if (n != -1) {
			paintIDMS(g, n, this.shape, this.operation);
		} // del if
	}

	public void paintIDMS(Graphics g, int n, Shape shape, Operation o) {
		if (n != 1) { // delete,move,scale
			int a[] = logical.calculateRect(shapeDelete);
			// borro el rectángulo ocupado por la figura
			g.clearRect(a[0], a[1], a[2], a[3]);
			if (n == 3 || n == 4) // move,scale
				logical.changePositionShape(shape);
		}

	}

	public void tomaShape(Shape s, Operation o, List listI) {
		this.shape = s;
		this.operation = o;
		Object ope[] = o.getOperation();
		this.shapeDelete = (Shape) ope[1];
	}

	/*
	 * se sobreescribe para que no borre todo el lienzo. Se controla así, la
	 * parte del lienzo que queremos borrar, para volver a dibujar.
	 * 
	 */
	@Override
	public void update(Graphics g) {
		paint(g);
	}

}
