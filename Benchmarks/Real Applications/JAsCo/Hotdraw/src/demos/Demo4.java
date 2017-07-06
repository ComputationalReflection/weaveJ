package demos;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import controller.Controller;
import shapesPackage.PointA;
import shapesPackage.Shape;
import shapesPackage.Shapes;
import utilsClass.Demo;

public class Demo4 implements Demo {

	public void run() {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		//int n = Shapes.getInstance().getPaintConsoleOrCanvas();
    
    	
    	Shape s = null;
		Controller controller = new Controller();
		for(int i=0;i<60;i++){
			  
			
			List list = new ArrayList();
            Iterator iter = controller.getListShapes().iterator();
            while (iter.hasNext())
            {
            	s = (Shape)iter.next();
                list.add(s);
            }
            iter = list.iterator();
            while (iter.hasNext())
            {
            	s = (Shape)iter.next();
                controller.delete(s);
             }
		
		//List list=controller.getListShapes();
		
		Map hash = new HashMap();
		//insertamos el rectangulo
		hash.put("shape", new Integer(0));
		hash.put("x", new Integer(100));
		hash.put("y", new Integer(100));
		hash.put("width", new Integer(50));
		hash.put("height", new Integer(50));
		hash.put("color", Color.green);
		controller.createShape(hash);
		int idRectangle = Shapes.getInstance().getNumberShapes();
		hash.clear();
		//logical.sleeping(n);
		//insertamos el círculo
		hash.put("shape", new Integer(1));
		hash.put("x", new Integer(100));
		hash.put("y", new Integer(300));
		hash.put("radius", new Integer(50));
		hash.put("color", Color.blue);
		controller.createShape(hash);
		int idCircle = Shapes.getInstance().getNumberShapes();
		hash.clear();
		//logical.sleeping(n);
		//insertamos el triangulo
		hash.put("shape", new Integer(2));
		hash.put("x", new Integer(100));
		hash.put("y", new Integer(600));
		hash.put("side", new Integer(50));
		hash.put("color", Color.yellow);
		controller.createShape(hash);
		int idTriangle1 = Shapes.getInstance().getNumberShapes();
		hash.clear();
		//logical.sleeping(n);
		//insertamos el triangulo
		hash.put("shape", new Integer(2));
		hash.put("x", new Integer(300));
		hash.put("y", new Integer(100));
		hash.put("side", new Integer(50));
		hash.put("color", Color.red);
		controller.createShape(hash);
		int idTriangle2 = Shapes.getInstance().getNumberShapes();
		hash.clear();
		//logical.sleeping(n);
		//insertamos el círculo
		hash.put("shape", new Integer(1));
		hash.put("x", new Integer(400));
		hash.put("y", new Integer(500));
		hash.put("radius", new Integer(50));
		hash.put("color", Color.pink);
		controller.createShape(hash);
		int idCircle2 = Shapes.getInstance().getNumberShapes();
		hash.clear();
		//logical.sleeping(n);
		//insertamos el círculo
		hash.put("shape", new Integer(1));
		hash.put("x", new Integer(400));
		hash.put("y", new Integer(200));
		hash.put("radius", new Integer(50));
		hash.put("color", Color.gray);
		controller.createShape(hash);
		int idCircle3 = Shapes.getInstance().getNumberShapes();
		hash.clear();
		//logical.sleeping(n);
		//insertamos el triangulo
		hash.put("shape", new Integer(2));
		hash.put("x", new Integer(200));
		hash.put("y", new Integer(200));
		hash.put("side", new Integer(50));
		hash.put("color", Color.magenta);
		controller.createShape(hash);
		int idTriangle3 = Shapes.getInstance().getNumberShapes();
		hash.clear();
		//logical.sleeping(n);
		//insertamos el rectangulo
		hash.put("shape", new Integer(0));
		hash.put("x", new Integer(500));
		hash.put("y", new Integer(500));
		hash.put("width", new Integer(50));
		hash.put("height", new Integer(50));
		hash.put("color", Color.orange);
		controller.createShape(hash);
		int idRectangle2 = Shapes.getInstance().getNumberShapes();
		hash.clear();
		//logical.sleeping(n);
		//insertamos el rectangulo
		hash.put("shape", new Integer(0));
		hash.put("x", new Integer(300));
		hash.put("y", new Integer(400));
		hash.put("width", new Integer(50));
		hash.put("height", new Integer(50));
		hash.put("color", Color.lightGray);
		controller.createShape(hash);
		int idRectangle3 = Shapes.getInstance().getNumberShapes();
		hash.clear();
		//logical.sleeping(n);
		//insertamos el triangulo
		hash.put("shape", new Integer(2));
		hash.put("x", new Integer(200));
		hash.put("y", new Integer(500));
		hash.put("side", new Integer(50));
		hash.put("color", Color.blue);
		controller.createShape(hash);
		int idTriangle4 = Shapes.getInstance().getNumberShapes();
		hash.clear();
		//logical.sleeping(n);
		//escalamos
		list=controller.getListShapes();
		s = Shapes.getInstance().find(idTriangle2);
		hash.clear();
		hash.put("side", new Integer(50));
		controller.scale(s,hash);
		
		//movemos
		list=controller.getListShapes();
		s = Shapes.getInstance().find(idTriangle2);
		PointA p = new PointA();
		p.setX(300);
		p.setY(300);		
		controller.move(s,p);
		
		//escalamos
		list=controller.getListShapes();
		s = Shapes.getInstance().find(idTriangle2);
		hash.clear();
		hash.put("side", new Integer(150));
		controller.scale(s,hash);	
		}
	}
	
	
	

}
