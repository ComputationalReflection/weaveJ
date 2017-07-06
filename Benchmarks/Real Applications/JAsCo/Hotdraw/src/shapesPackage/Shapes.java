package shapesPackage;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;




/**Clase que utiliza el patrón Singleton. Implementa la coleción de figuras que están 
 * añadidas al lienzo y sobre las cuales se puede operar.*/
public class Shapes {
	private List listShapes=new ArrayList();
	private int numberShapes = 0; //para generar identificadores únicos.
	private int paintConsoleOrCanvas = 0;
	

	
	private static Shapes _instance=null;
	
	public static Shapes getInstance(){
		if (_instance==null){
			_instance=new Shapes();
		}
		return _instance;
	}
	
	
	
	//return shapes`s list insert
	public List getListShapes(){
		return listShapes;
	}
	
	public int getNumberShapes(){
		return numberShapes;
	}
	
	public void setNumberShapes(int numberShapes){
		this.numberShapes = numberShapes;
	}
	
	
	public int getPaintConsoleOrCanvas(){
		return this.paintConsoleOrCanvas;
	}
	
	public void setPaintConsoleOrCanvas(int paintConsoleOrCanvas){
		this.paintConsoleOrCanvas = paintConsoleOrCanvas;
	}
	
	
	public void insert(Shape s,String message){	
		//s.setPaintedIn(this.getPaintConsoleOrCanvas());
		listShapes.add(s);
	}
	
	
	public Shape find(int id){
		Iterator it = listShapes.iterator();
		while (it.hasNext()){
		  Shape s=(Shape)it.next();
		  if(s.getId()==id)
			  return s;
		}
		
		return null;
	}
	
	
	
	public void delete(Shape s, String message){
		listShapes.remove(s);
	}
	
			
}
	
