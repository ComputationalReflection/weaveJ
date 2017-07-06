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

public class Demo3 implements Demo {

	@Override
	public void run() {
		// TODO Auto-generated method stub
		//int n = Shapes.getInstance().getPaintConsoleOrCanvas();
	 	
    	Shape s = null;
		Controller controller = new Controller();
		List list=controller.getListShapes();
		
		for(int i=0;i<7;i++)
		{
			Map hash = new HashMap();
			//insertamos el triangulo
			hash.put("shape", new Integer(2));
			hash.put("x", new Integer(100));
			hash.put("y", new Integer(600));
			hash.put("side", new Integer(100));
			hash.put("color", Color.orange);
			controller.createShape(hash);
			int idTriangle = Shapes.getInstance().getNumberShapes();
			hash.clear();
		
			//insertamos el círculo
			hash.put("shape", new Integer(1));
			hash.put("x", new Integer(100));
			hash.put("y", new Integer(100));
			hash.put("radius", new Integer(50));
			hash.put("color", Color.pink);
			controller.createShape(hash);
			int idCircle = Shapes.getInstance().getNumberShapes();
			hash.clear();
		

			//realizamos operaciones con las figuras
			for(int j=0;j<100;j++){
				
				//escalamos
				list=controller.getListShapes();
				s = Shapes.getInstance().find(idTriangle);
				hash.clear();
				hash.put("side", new Integer(50));
				controller.scale(s,hash);
			
				//movemos
				list=controller.getListShapes();
				s = Shapes.getInstance().find(idTriangle);
				PointA p = new PointA();
				p.setX(300);
				p.setY(300);		
				controller.move(s,p);
			
				//escalamos
				list=controller.getListShapes();
				s = Shapes.getInstance().find(idTriangle);
				hash.clear();
				hash.put("side", new Integer(150));
				controller.scale(s,hash);
			
			}
			
			s = Shapes.getInstance().find(idTriangle);
			controller.delete(s);
			s = Shapes.getInstance().find(idCircle);
			controller.delete(s);
			
		}
	}

}
