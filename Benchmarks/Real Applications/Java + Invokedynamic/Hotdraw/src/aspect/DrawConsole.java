package aspect;

import java.util.Map;

import shapesPackage.PointA;
import shapesPackage.Shape;
import shapesPackage.Shapes;
import utilsClass.Operation;

public class DrawConsole {

	static Shape currentShape;

	static Operation o = new Operation();

	public static void afterDeleteShape(Shapes ins) {

		if (ins.getPaintConsoleOrCanvas() == 1 || ins.getPaintConsoleOrCanvas() == 3) {
			System.out.println(
					"DELETING shape : " + currentShape.getClass().getName() + " con id " + currentShape.getId());
			System.out.println(utilsClass.Utils.datasShow(currentShape));
		}
	}

	public static void afterInsertShape(Shapes ins) {

		if (ins.getPaintConsoleOrCanvas() == 1 || ins.getPaintConsoleOrCanvas() == 3) {
			if (currentShape.getClass().getName().equals("shapesPackage.RectangleA")) {
				System.out.println("INSERT RECTANGLE with datas " + utilsClass.Utils.datasShow(currentShape));
			} else if (currentShape.getClass().getName().equals("shapesPackage.CircleA")) {
				System.out.println("INSERT CIRCLE with datas " + utilsClass.Utils.datasShow(currentShape));
			} else if (currentShape.getClass().getName().equals("shapesPackage.TriangleA")) {
				System.out.println("INSERT TRIANGLE with datas " + utilsClass.Utils.datasShow(currentShape));
			}
		}
	}

	public static void afterMoveShape(Shape ins) {

		if (ins.getPaintedIn() == 1 || ins.getPaintedIn() == 3) {
			System.out
					.println("MOVING shape : " + currentShape.getClass().getName() + " con id " + currentShape.getId());
			System.out.println(utilsClass.Utils.datasShow(currentShape));
		}
	}

	public static void afterScaleShape(Shape ins) {

		if (ins.getPaintedIn() == 1 || ins.getPaintedIn() == 3) {
			System.out.println(
					"SCALING shape : " + currentShape.getClass().getName() + " con id " + currentShape.getId());
			System.out.println(utilsClass.Utils.datasShow(currentShape));
		}
	}


	public static void beforeMoveShape(Shape ins, Shape s, String message, PointA p) {
		currentShape = s;
	}

	public static void beforeOperationOnShapes(Shapes ins, Shape s, String message) {
		currentShape = s;
	}

	public static void beforeScaleShape(Shape ins, Shape s, String message, Map hash) {
		currentShape = s;
	}
}
