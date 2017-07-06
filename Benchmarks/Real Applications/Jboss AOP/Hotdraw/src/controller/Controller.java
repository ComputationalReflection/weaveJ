package controller;

import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;

import factoryPackage.ShapeFactory;
import loggerPackage.Log;
import shapesPackage.CircleA;
import shapesPackage.PointA;
import shapesPackage.RectangleA;
import shapesPackage.Shape;
import shapesPackage.Shapes;
import shapesPackage.TriangleA;
import utilsClass.Utils;


public class Controller {
	private Shapes s = Shapes.getInstance();
	
	//access Shapes´s methods
	public List getListShapes(){ 
		return s.getListShapes();
	}
	

	
	public void delete(Shape sh){
		Shape saux=s.find(sh.getId());
		if(saux!=null){ //la figura existe en la collección
			String cadena = "deleting shape " + Utils.getWhereIsPainted(sh) + " with id:" + sh.getId() + " and type: " + sh.getClass().getName();
			cadena = cadena + Utils.datasShow(sh);
			s.delete(sh,cadena);
		}
	}
	
	
	
	public void move(Shape sh,PointA p){
		Shape saux=s.find(sh.getId());
		if(saux!=null){//la figura existe en la colección
			String cadena = "moving shape " + Utils.getWhereIsPainted(sh) + " with id:" + sh.getId() + " and type: " + sh.getClass().getName();
			cadena = cadena + Utils.datasShow(sh);			
			sh.moveShape(sh,cadena,p);
		}
	}
	
	
	
	public void scale(Shape sh,Map hash){
		Shape saux=s.find(sh.getId());
		if(saux!=null){ //la figura existe en la colección
			String cadena = "scaling shape " + Utils.getWhereIsPainted(sh) + " with id:" + sh.getId() + " and type: " + sh.getClass().getName();
			cadena = cadena + Utils.datasShow(sh);
			sh.scaleShape(sh,cadena,hash);
		}
	}
	
	
	//access ShapeFactory´s methods
	public List getShapeTypes(){
		return ShapeFactory.getShapeTypes();
	}
	
	public static void createShape(Map hash){
		ShapeFactory.createShape(hash);
	}
	
	
}
