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

public class DemoTimer implements Demo{
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		//int n = Shapes.getInstance().getPaintConsoleOrCanvas();
    
    	
		Shape s = null;
		Controller controller = new Controller();
		for(long i=0;i<90;i++){
			  
			
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
		
		
				    			
		Map hash = new HashMap();
		//insertamos el rectangulo
		hash.put("shape", new Integer(0));
		hash.put("x", new Integer(100));
		hash.put("y", new Integer(100));
		hash.put("width", new Integer(50));
		hash.put("height", new Integer(50));
		hash.put("color", Color.red);
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
		hash.put("color", Color.green);
		controller.createShape(hash);
		int idTriangle1 = Shapes.getInstance().getNumberShapes();
		hash.clear();
		//logical.sleeping(n);
		//insertamos el triangulo
		hash.put("shape", new Integer(2));
		hash.put("x", new Integer(300));
		hash.put("y", new Integer(100));
		hash.put("side", new Integer(50));
		hash.put("color", Color.yellow);
		controller.createShape(hash);
		int idTriangle2 = Shapes.getInstance().getNumberShapes();
		hash.clear();
		//logical.sleeping(n);
		
		
		//realizamos operaciones con las figuras
		list=controller.getListShapes(); //shapes`s list insert
	
		
		//movemos el círculo
		s = Shapes.getInstance().find(idCircle);
		PointA p = new PointA();
		p.setX(300);
		p.setY(300);		
		controller.move(s,p);
				
		//logical.sleeping(n);
		
		
		
		list=controller.getListShapes(); 
		
		
		//movemos el rectángulo
		s = Shapes.getInstance().find(idRectangle);
		p = new PointA();
		p.setX(100);
		p.setY(300);		
		controller.move(s,p);
		
		//logical.sleeping(n);
		
		
		
		list=controller.getListShapes();
			
		//escalamos el círculo
		s = Shapes.getInstance().find(idCircle);			
		hash.clear();
		hash.put("radius", new Integer(100));
		controller.scale(s,hash);
	  
		//logical.sleeping(n);
		
		
		
		list=controller.getListShapes();
		s = Shapes.getInstance().find(idRectangle);
		p = new PointA();
		p.setX(300);
		p.setY(300);
		controller.move(s,p);
		
		
		//logical.sleeping(n);
		
		
		
		list=controller.getListShapes();
		s = Shapes.getInstance().find(idTriangle2);
		p = new PointA();
		p.setX(300);
		p.setY(300);		
		controller.move(s,p);
		
		
		//logical.sleeping(n);
		
	
		
		list=controller.getListShapes();
		s = Shapes.getInstance().find(idRectangle);
		p = new PointA();
		p.setX(100);
		p.setY(100);
				
		
		//logical.sleeping(n);
				
		
		list=controller.getListShapes();
		s = Shapes.getInstance().find(idTriangle2);
		hash.clear();
		hash.put("side", new Integer(100));
		controller.scale(s,hash);
	 
		
		//logical.sleeping(n);
		
		
		
		list=controller.getListShapes();
		s = Shapes.getInstance().find(idRectangle);
		controller.delete(s);
		
		
		//logical.sleeping(n);
		
	
		
		list=controller.getListShapes();
		s = Shapes.getInstance().find(idCircle);
		hash.clear();
		hash.put("radius", new Integer(50));
		controller.scale(s,hash);
		
		//escalamos
		list=controller.getListShapes();
		s = Shapes.getInstance().find(idTriangle2);
		hash.clear();
		hash.put("side", new Integer(50));
		controller.scale(s,hash);
		
		//movemos
		list=controller.getListShapes();
		s = Shapes.getInstance().find(idTriangle2);
		//PointA p = new PointA();
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
