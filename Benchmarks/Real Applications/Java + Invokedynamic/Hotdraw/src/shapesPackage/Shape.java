package shapesPackage;

import java.awt.Color;
import java.util.Map;

import aspect.AspectLogger;
import aspect.AspectProfiler;
import aspect.DrawCanvas;
import aspect.DrawConsole;
import loggerPackage.Log;
import main.MainWindow;
import profilerPackage.Profiler;
import utilsClass.Operation;

/** clase abstracta de la que heredan el resto de figuras. */
public abstract class Shape {

	public Color color;
	public int id;
	Log l = new Log();
	Operation o = new Operation();
	public PointA p;
	public int paintedIn = 0;
	Profiler prof = new Profiler();

	public Color getColor() {
		// TODO Auto-generated method stub
		return color;
	}

	public int getId() {
		return id;
	}

	public int getPaintedIn() {
		return paintedIn;
	}

	public PointA getPoint() {
		return p;
	}

	public void moveShape(Shape s, String message, PointA p) {
		if (MainWindow.log)
			AspectLogger.moveShapeLog(this, s, message, p);
		if (MainWindow.profile)
			AspectProfiler.profileMoveShape(this, s, message, p);
		if (MainWindow.drawCanvas)
			DrawCanvas.beforeMoveShape(this, s, message, p);
		if (MainWindow.drawConsole)
			DrawConsole.beforeMoveShape(this, s, message, p);
		s.setPoint(p);

		if (MainWindow.drawConsole)
			DrawConsole.afterMoveShape(this);
		if (MainWindow.drawCanvas)
			DrawCanvas.afterOperationOnShape(this);
		if (MainWindow.profile)
			AspectProfiler.profileOperationEnd(this);
	}

	public void scaleShape(Shape s, String message, Map hash) {
		if (MainWindow.log)
			AspectLogger.scaleShapeLog(this, s, message, hash);
		if (MainWindow.profile)
			AspectProfiler.profileScaleShape(this, s, message, hash);
		if (MainWindow.drawCanvas)
			DrawCanvas.beforeScaleShape(this, s, message, hash);
		if (MainWindow.drawConsole)
			DrawConsole.beforeScaleShape(this, s, message, hash);
		if (s.getClass().getName().equals("shapesPackage.RectangleA")) {
			RectangleA r = (RectangleA) s;

			Integer width = (Integer) hash.get("width");
			Integer height = (Integer) hash.get("height");
			r.setWidth(width);
			r.setHeight(height);
		} else if (s.getClass().getName().equals("shapesPackage.CircleA")) {
			CircleA c = (CircleA) s;

			Integer radius = (Integer) hash.get("radius");
			c.setRadius(radius);
		} else if (s.getClass().getName().equals("shapesPackage.TriangleA")) {
			TriangleA t = (TriangleA) s;

			Integer side = (Integer) hash.get("side");
			t.setSide(side);
		}
		if (MainWindow.drawConsole)
			DrawConsole.afterScaleShape(this);
		if (MainWindow.drawCanvas)
			DrawCanvas.afterOperationOnShape(this);
		if (MainWindow.profile)
			AspectProfiler.profileOperationEnd(this);
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setPaintedIn(int paintedIn) {
		this.paintedIn = paintedIn;
	}

	public void setPoint(PointA p) {
		this.p = p;
	}

}
