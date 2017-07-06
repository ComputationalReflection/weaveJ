package demos;

import java.awt.Color;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import controller.Controller;
import shapesPackage.PointA;
import shapesPackage.Shape;
import shapesPackage.Shapes;
import utilsClass.Demo;

public class Demo1 implements Demo {

	@Override
	public void run() {
		String message;
		// TODO Auto-generated method stub
		int n = Shapes.getInstance().getPaintConsoleOrCanvas();
 	
    	Shape s = null;
		Controller controller = new Controller();
		List list=controller.getListShapes();
		
		
				    			
		Map hash = new HashMap();
		//insertamos el rectangulo
		hash.put("shape", new Integer(0));
		hash.put("x", new Integer(300));
		hash.put("y", new Integer(300));
		hash.put("width", new Integer(100));
		hash.put("height", new Integer(100));
		hash.put("color", Color.pink);
		controller.createShape(hash);
		int idRectangle = Shapes.getInstance().getNumberShapes();
		hash.clear();
		
		//insertamos el círculo
		hash.put("shape", new Integer(1));
		hash.put("x", new Integer(100));
		hash.put("y", new Integer(300));
		hash.put("radius", new Integer(50));
		hash.put("color", Color.blue);
		controller.createShape(hash);
		int idCircle = Shapes.getInstance().getNumberShapes();
		hash.clear();
		
		
		hash = new HashMap();
		//insertamos el rectangulo
		hash.put("shape", new Integer(0));
		hash.put("x", new Integer(400));
		hash.put("y", new Integer(500));
		hash.put("width", new Integer(50));
		hash.put("height", new Integer(50));
		hash.put("color", Color.orange);
		controller.createShape(hash);
		int idRectangleR = Shapes.getInstance().getNumberShapes();
		hash.clear();
		
		//insertamos el triangulo
		hash.put("shape", new Integer(2));
		hash.put("x", new Integer(100));
		hash.put("y", new Integer(600));
		hash.put("side", new Integer(100));
		hash.put("color", Color.black);
		controller.createShape(hash);
		int idTriangle1 = Shapes.getInstance().getNumberShapes();
		hash.clear();
		
		//insertamos el triangulo
		hash.put("shape", new Integer(2));
		hash.put("x", new Integer(300));
		hash.put("y", new Integer(100));
		hash.put("side", new Integer(50));
		hash.put("color", Color.gray);
		controller.createShape(hash);
		int idTriangle2 = Shapes.getInstance().getNumberShapes();
		hash.clear();
		PointA p = new PointA();
		
		for(int i=0;i<200;i++){
		//realizamos operaciones con las figuras
		list=controller.getListShapes(); //shapes`s list insert
	
		
		//movemos el círculo
		s = Shapes.getInstance().find(idCircle);
		
		p.setX(100);
		p.setY(100);		
		controller.move(s,p);
				
		
		list=controller.getListShapes(); 
		
		
		//movemos el rectángulo
		s = Shapes.getInstance().find(idRectangleR);
		p = new PointA();
		p.setX(100);
		p.setY(300);		
		controller.move(s,p);
			
		
		list=controller.getListShapes();
			
		//escalamos el círculo
		s = Shapes.getInstance().find(idCircle);			
		hash.clear();
		hash.put("radius", new Integer(100));
		controller.scale(s,hash);
	  	
		
		list=controller.getListShapes();
		s = Shapes.getInstance().find(idRectangle);
		p = new PointA();
		p.setX(300);
		p.setY(100);
		controller.move(s,p);
			
		
		list=controller.getListShapes();
		s = Shapes.getInstance().find(idTriangle2);
		p = new PointA();
		p.setX(300);
		p.setY(300);		
		controller.move(s,p);
		
		
		list=controller.getListShapes();
		s = Shapes.getInstance().find(idRectangle);
		p = new PointA();
		p.setX(100);
		p.setY(100);
		controller.move(s,p);	
		
		list=controller.getListShapes();
		s = Shapes.getInstance().find(idTriangle2);
		hash.clear();
		hash.put("side", new Integer(100));
		controller.scale(s,hash);
	
		
		
		
		
		list=controller.getListShapes();
		s = Shapes.getInstance().find(idCircle);
		hash.clear();
		hash.put("radius", new Integer(50));
		controller.scale(s,hash);
		}
	}

}
