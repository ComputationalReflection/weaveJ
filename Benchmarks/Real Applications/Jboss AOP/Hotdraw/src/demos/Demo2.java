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

public class Demo2 implements Demo {

	@Override
	public void run() {
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
		hash.put("color", Color.blue);
		controller.createShape(hash);
		int idRectangle = Shapes.getInstance().getNumberShapes();
		hash.clear();
		
		//insertamos el triangulo
		hash.put("shape", new Integer(2));
		hash.put("x", new Integer(100));
		hash.put("y", new Integer(100));
		hash.put("side", new Integer(50));
		hash.put("color", Color.green);
		controller.createShape(hash);
		int idTriangle = Shapes.getInstance().getNumberShapes();
		hash.clear();
		
		
		//realizamos operaciones con las figuras
		for(int i=0;i<500;i++){
			//movemos el rectángulo
			list=controller.getListShapes();
			s = Shapes.getInstance().find(idRectangle);
			PointA p = new PointA();
			p.setX(100);
			p.setY(100);		
			controller.move(s,p);
			
			//movemos el triángulo
			list=controller.getListShapes();
			s = Shapes.getInstance().find(idTriangle);
			p = new PointA();
			p.setX(300);
			p.setY(300);		
			controller.move(s,p);
			
			//escalamos
			list=controller.getListShapes();
			s = Shapes.getInstance().find(idTriangle);
			hash.clear();
			hash.put("side", new Integer(50));
			controller.scale(s,hash);
			
			//movemos el rectángulo
			list=controller.getListShapes(); 
			s = Shapes.getInstance().find(idRectangle);
			p = new PointA();
			p.setX(400);
			p.setY(500);		
			controller.move(s,p);
			
			//movemos el triángulo
			list=controller.getListShapes();
			s = Shapes.getInstance().find(idTriangle);
			p = new PointA();
			p.setX(100);
			p.setY(500);		
			controller.move(s,p);
		}
	
	}

}
