package shapesPackage;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import aspect.AspectLogger;
import aspect.AspectProfiler;
import aspect.DrawCanvas;
import aspect.DrawConsole;
import loggerPackage.Log;
import main.MainWindow;
import profilerPackage.Profiler;
import utilsClass.Operation;

/**
 * Clase que utiliza el patrón Singleton. Implementa la coleción de figuras que
 * están añadidas al lienzo y sobre las cuales se puede operar.
 */
public class Shapes {
	private static Shapes _instance = null;

	public static Shapes getInstance() {
		if (_instance == null) {
			_instance = new Shapes();
		}

		return _instance;
	}

	Log l = new Log();

	private List listShapes = new ArrayList();
	private int numberShapes = 0; // para generar identificadores únicos.
	Operation o = new Operation();

	Profiler p = new Profiler();

	private int paintConsoleOrCanvas = 0;

	public void delete(Shape s, String message) {

		if (MainWindow.log)
			AspectLogger.ShapesOperationLog(this, s, message);

		if (MainWindow.profile)
			AspectProfiler.profileShapesOperation(this, s, message);

		if (MainWindow.drawCanvas)
			DrawCanvas.beforeOperationOnShapes(this, s, message);
		if (MainWindow.drawConsole)
			DrawConsole.beforeOperationOnShapes(this, s, message);
		listShapes.remove(s);
		if (MainWindow.drawConsole)
			DrawConsole.afterDeleteShape(this);
		if (MainWindow.drawCanvas)
			DrawCanvas.afterDeleteShape(this);
		if (MainWindow.profile)
			AspectProfiler.profileOperationEnd(this);
	}

	public Shape find(int id) {
		Iterator it = listShapes.iterator();
		while (it.hasNext()) {
			Shape s = (Shape) it.next();
			if (s.getId() == id)
				return s;
		}

		return null;
	}

	// return shapes`s list insert
	public List getListShapes() {
		return listShapes;
	}

	public int getNumberShapes() {
		return numberShapes;
	}

	public int getPaintConsoleOrCanvas() {
		return this.paintConsoleOrCanvas;
	}

	public void insert(Shape s, String message) {
		// s.setPaintedIn(this.getPaintConsoleOrCanvas())
		if (MainWindow.log)
			AspectLogger.ShapesOperationLog(this, s, message);
		if (MainWindow.profile)
			AspectProfiler.profileShapesOperation(this, s, message);
		if (MainWindow.drawCanvas)
			DrawCanvas.beforeOperationOnShapes(this, s, message);
		if (MainWindow.drawConsole)
			DrawConsole.beforeOperationOnShapes(this, s, message);
		listShapes.add(s);
		if (MainWindow.drawConsole)
			DrawConsole.afterInsertShape(this);
		if (MainWindow.drawCanvas)
			DrawCanvas.afterInsertShape(this);
		if (MainWindow.profile)
			AspectProfiler.profileOperationEnd(this);
	}

	public void setNumberShapes(int numberShapes) {
		this.numberShapes = numberShapes;
	}

	public void setPaintConsoleOrCanvas(int paintConsoleOrCanvas) {
		this.paintConsoleOrCanvas = paintConsoleOrCanvas;
	}

}
