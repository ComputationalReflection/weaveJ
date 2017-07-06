package utilsClass;

import shapesPackage.Shape;

public class Operation {
	private Object ope[];
	
	public Operation(){
		ope = new Object[2];
	}
	
	public Object[] getOperation(){
		return ope;
	}
	
	public void addOperation(Integer x){
		ope[0] = x;
	}
	
	public void addShapeDelete(Shape s){
		ope[1] = s;
	}
	
}
