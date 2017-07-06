package utilsClass; 
 
import java.awt.Color;
import java.io.File;
import javax.swing.JList;

import shapesPackage.CircleA;
import shapesPackage.RectangleA;
import shapesPackage.Shape;
import shapesPackage.TriangleA;

import java.util.Map;

public class Utils {

	
	
	public static String datasShow(Shape s){
		String cad = "";
		
		if(s.getClass().getName().equals("shapesPackage.RectangleA")){   
    		RectangleA r = (RectangleA)s;
    		cad = ", (x,y): (" + r.getPoint().getX() + "," + r.getPoint().getY() + "), width: " + r.getWidth() + ", height: " + r.getHeight();
		}
		else if(s.getClass().getName().equals("shapesPackage.CircleA")){
				CircleA c = (CircleA)s;
				cad = ", (x,y): (" + c.getPoint().getX() + "," + c.getPoint().getY() + "), radius: " + c.getRadius();
			}
			else if(s.getClass().getName().equals("shapesPackage.TriangleA")){
				TriangleA t = (TriangleA)s;
				cad = ", (x,y): (" + t.getPoint().getX() + "," + t.getPoint().getY() + "), side: " + t.getSide();
			}	   
		
		return cad;
	}
	
	
	
	
	
	public static String getDatas(Map hash){
		String cadena = "";
		
		if(!hash.isEmpty()){
			Integer shapeType = (Integer)hash.get("shape");
			switch (shapeType){
			case 0:{ 
				Integer x = (Integer)hash.get("x");
				Integer y = (Integer)hash.get("y");
				Integer width = (Integer)hash.get("width");
				Integer height = (Integer)hash.get("height");
				Color color = (Color)hash.get("color");
				cadena = "creating rectangle in (" + x + "," + y + ") with width:" + width + ", height:" + height + " and color:" + color.toString();
				break;
			}
			case 1:{ //is a circle
				//get us shapes´s datas
				Integer x = (Integer)hash.get("x");
				Integer y = (Integer)hash.get("y");
				Integer radius = (Integer)hash.get("radius");
				Color color = (Color)hash.get("color");
				cadena = "creating circle with in (" + x + "," + y + ") with radius:" + radius +  " and color:" + color.toString();
				break;
			}
			case 2:{//is a triangle
				//get us shapes`s datas
				Integer x = (Integer)hash.get("x");
				Integer y = (Integer)hash.get("y");
				Integer side = (Integer)hash.get("side");
				Color color = (Color)hash.get("color");
				cadena = "creating triangle with in (" + x + "," + y + ") with side:" + side +  " and color:" + color.toString();
				break;
			}
			}
		}
			
		return cadena;
	}
	
	
	
	
	
	
	public static Shape copyShape(Shape s){
		Shape a = null;

		if(s.getClass().getName().equals("shapesPackage.RectangleA")){   
    		RectangleA r = (RectangleA)s;
    		RectangleA aux = new RectangleA();

    		aux.setPoint(r.getPoint());
    		aux.setWidth(r.getWidth());
    		aux.setHeight(r.getHeight());
    		
    		a = aux;
		}
		else if(s.getClass().getName().equals("shapesPackage.CircleA")){
				CircleA c = (CircleA)s;
				CircleA aux = new CircleA();
				
				aux.setPoint(c.getPoint());
				aux.setRadius(c.getRadius());
				a = aux;
			}
			else if(s.getClass().getName().equals("shapesPackage.TriangleA")){
				TriangleA t = (TriangleA)s;
				TriangleA aux = new TriangleA();
				
				aux.setPoint(t.getPoint());
				aux.setSide(t.getSide());
				
				a = aux;
			}
		
		return a;
	}
	
	
	
	
	
	public static int getIdSelection(JList jList_Shapes,int selection){	
		//obtenemos el id de la shape seleccionada.
		String cadena = (String)jList_Shapes.getSelectedValue();
		String[] a = cadena.split(":");
		String[] b = a[1].split(",");
		
		return (Integer.valueOf(b[0])).intValue();
	}

	
	
	
	
	public static String getWhereIsPainted(Shape s){
		String cadena = "";
		switch (s.getPaintedIn()){
		case 1:{
			cadena = "in canvas";
			break;
		}
		case 2:{
			cadena = "in console";
			break;
		}
		case 3:{
			cadena = "in both";
			break;
		}
		}
		return cadena;
	}
	
	
	

}
