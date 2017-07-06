package utilsClass;

import shapesPackage.Shape;

public class Operation {
	private Object[] op;

	public Operation() {
		op = new Object[2];
	}

	public Object[] getOperation() {
		return op;
	}

	public void addOperation(Integer x) {
		op[0] = x;
	}

	public void addShapeDelete(Shape s) {
		op[1] = s;
	}

}
