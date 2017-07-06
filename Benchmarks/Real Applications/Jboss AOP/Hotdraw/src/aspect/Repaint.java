package aspect;

import java.awt.Graphics;
import java.util.Iterator;
import java.util.List;

import paintCanvas.PaintCanvas;
import shapesPackage.CircleA;
import shapesPackage.RectangleA;
import shapesPackage.Shape;
import shapesPackage.Shapes;
import shapesPackage.TriangleA;
import utilsClass.Operation;

public class Repaint {
	static Graphics g;
	static int n;
	static Shape shape;
	static int valuesX[] = new int[3];
	static int valuesY[] = new int[3];

	public static void afterPaintIDMS(PaintCanvas p) {

		// int op = Shapes.getInstance().getPaintConsoleOrCanvas();
		// if(op!=2){ //si op=2 estamos dibujando en la consola.
		if (n != -1) {
			switch (n) {
			case 2: {// delete
				repaintShapes(g, shape);
				break;
			}
			default: {// insert,move, scale
				repaintShapes(g, shape);
				// pinto la nueva shape
				paintShape(g, shape);
				break;
			}
			}// del switch
		}
		// }//if(op!=2)
	}

	public static void beforePaintIDMS(PaintCanvas p, Graphics g, int n, Shape s, Operation o) {

		Repaint.g = g;
		Repaint.n = n;
		Repaint.shape = s;

	}

	static void loadDatasPolygon(Shape s) {
		int x = s.getPoint().getX();
		int y = s.getPoint().getY();

		TriangleA t = (TriangleA) s;
		int side = t.getSide();

		valuesX[0] = x;
		int x2 = x + side;
		valuesX[1] = x2;
		valuesX[2] = x + (x2 - x) / 2;
		valuesY[0] = y;
		valuesY[1] = y;
		valuesY[2] = y - side;

	}

	static void paintShape(Graphics g, Shape shape) {
		// pintamos la figura
		if (shape != null && (shape.getPaintedIn() == 1 || shape.getPaintedIn() == 3)) {
			if (shape.getClass().getName().equals("shapesPackage.RectangleA")) {
				RectangleA r = (RectangleA) shape;

				g.setColor(r.getColor());
				g.fillRect(r.getPoint().getX(), r.getPoint().getY(), r.getWidth(), r.getHeight());
			} else if (shape.getClass().getName().equals("shapesPackage.CircleA")) {
				CircleA c = (CircleA) shape;

				g.setColor(c.getColor());
				g.fillOval(c.getPoint().getX() - c.getRadius(), c.getPoint().getY() - c.getRadius(), 2 * c.getRadius(),
						2 * c.getRadius());
			} else if (shape.getClass().getName().equals("shapesPackage.TriangleA")) {
				TriangleA t = (TriangleA) shape;

				loadDatasPolygon(shape);

				g.setColor(t.getColor());
				g.fillPolygon(valuesX, valuesY, 3);
			}
		} // if(shape!=null)
	}

	static void repaintShapes(Graphics g, Shape shape) {
		// repintamos el resto de figuras de la lista
		List list = Shapes.getInstance().getListShapes();

		Shape s;
		Iterator iter = list.iterator();
		while (iter.hasNext()) {
			s = (Shape) iter.next();
			if (s.getId() != shape.getId() && (s.getPaintedIn() == 1 || s.getPaintedIn() == 3)) { // pinto
																									// todas
																									// las
																									// shapes
																									// menos
																									// la
																									// que
																									// estoy
																									// moviendo,copiando,etc....
				if (s.getClass().getName().equals("shapesPackage.RectangleA")) {
					RectangleA r = (RectangleA) s;

					g.setColor(r.getColor());
					g.fillRect(r.getPoint().getX(), r.getPoint().getY(), r.getWidth(), r.getHeight());
				} else if (s.getClass().getName().equals("shapesPackage.CircleA")) {
					CircleA c = (CircleA) s;

					g.setColor(c.getColor());
					g.fillOval(c.getPoint().getX() - c.getRadius(), c.getPoint().getY() - c.getRadius(),
							2 * c.getRadius(), 2 * c.getRadius());
				} else if (s.getClass().getName().equals("shapesPackage.TriangleA")) {
					TriangleA t = (TriangleA) s;

					loadDatasPolygon(s);

					g.setColor(t.getColor());
					g.fillPolygon(valuesX, valuesY, 3);
				}
			}
		}
	}
}