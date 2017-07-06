package factoryPackage;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import shapesPackage.CircleA;
import shapesPackage.PointA;
import shapesPackage.RectangleA;
import shapesPackage.Shapes;
import shapesPackage.TriangleA;
import utilsClass.Utils;


/** abstract class that implements abstract factory pattern.*/
public abstract class ShapeFactory {	
	/** this method allows you to create a shape.*/
	public static void createShape(Map hash){	
		Shapes list = Shapes.getInstance(); //get shapes collection , singleton
		if(!hash.isEmpty()){
			Integer shapeType = (Integer)hash.get("shape");
			switch (shapeType){
			case 0:{ //is a rectangle
				//get us shapes´s datas
				Integer x = (Integer)hash.get("x");
				Integer y = (Integer)hash.get("y");
				Integer width = (Integer)hash.get("width");
				Integer height = (Integer)hash.get("height");
				Color color = (Color)hash.get("color");
				RectangleA r = new RectangleA();
				PointA p = new PointA();
				p.setX(x);
				p.setY(y);
				r.setPoint(p);
				r.setWidth(width);
				r.setHeight(height);
				r.setColor(color);
				r.setPaintedIn(list.getPaintConsoleOrCanvas());
				int id = list.getNumberShapes()+1;
				String cadena = "creating rectangle " + Utils.getWhereIsPainted(r) + " with id: " + id + " in (" + x + "," + y + ") with width:" + width + ", height:" + height + " and color:" + color.toString();
				list.setNumberShapes(id);
				r.setId(id); 
				list.insert(r,cadena);
				break;
			}
			case 1:{ //is a circle
				//get us shapes´s datas
				Integer x = (Integer)hash.get("x");
				Integer y = (Integer)hash.get("y");
				Integer radius = (Integer)hash.get("radius");
				Color color = (Color)hash.get("color");
				CircleA c = new CircleA();
				PointA p = new PointA();
				p.setX(x);
				p.setY(y);
				c.setPoint(p);
				c.setRadius(radius);
				c.setColor(color);
				c.setPaintedIn(list.getPaintConsoleOrCanvas());
				int id = list.getNumberShapes()+1;
				String cadena = "creating circle " + Utils.getWhereIsPainted(c) + " with id: " + id + " in (" + x + "," + y + ") with radius:" + radius +  " and color:" + color.toString();
				list.setNumberShapes(id);
				c.setId(id);
				list.insert(c,cadena);
				break;
			}
			case 2:{//is a triangle
				//get us shapes`s datas
				Integer x = (Integer)hash.get("x");
				Integer y = (Integer)hash.get("y");
				Integer side = (Integer)hash.get("side");
				Color color = (Color)hash.get("color");
				TriangleA t = new TriangleA();
				PointA p = new PointA();
				p.setX(x);
				p.setY(y);
				t.setPoint(p);
				t.setSide(side);
				t.setColor(color);
				t.setPaintedIn(list.getPaintConsoleOrCanvas());
				int id = list.getNumberShapes()+1;
				String cadena = "creating triangle " + Utils.getWhereIsPainted(t) + " with id: " + id + " in (" + x + "," + y + ") with side:" + side +  " and color:" + color.toString();
				list.setNumberShapes(id);
				t.setId(id);
				list.insert(t,cadena);
				break;
			}
		}
			
		}
	}
	
	/** this method returns a list with all shapes.*/
	public static List getShapeTypes() {
		List listShapes = new ArrayList();
		listShapes.add("rectangle");
		listShapes.add("circle");
		listShapes.add("triangle");
		return listShapes;
	}

}
