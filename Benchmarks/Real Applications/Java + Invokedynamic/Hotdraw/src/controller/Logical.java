package controller;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;

import main.MainWindow;
import shapesPackage.CircleA;
import shapesPackage.PointA;
import shapesPackage.RectangleA;
import shapesPackage.Shape;
import shapesPackage.Shapes;
import shapesPackage.TriangleA;
import utilsClass.Demo;
import utilsClass.Utils;



public class Logical {
	
	private int selection = -1;
	private int selectionDemo = -1;

	public int[] calculateRect(Shape shapeDelete){
	    	int a[] = new int[4];
	    	
	    	if(shapeDelete!=null){
	    		if(shapeDelete.getClass().getName().equals("shapesPackage.RectangleA")){
	        		RectangleA r = (RectangleA)shapeDelete;
	        		a[0] = r.getPoint().getX();
	        		a[1] = r.getPoint().getY();
	        		a[2] = r.getWidth()+3;
	        		a[3] = r.getHeight()+3;	
	    		}
	    		else if(shapeDelete.getClass().getName().equals("shapesPackage.CircleA")){
	    				CircleA c = (CircleA)shapeDelete;
	    				a[0] = c.getPoint().getX()-c.getRadius();
	            		a[1] = c.getPoint().getY()-c.getRadius();
	            		a[2] = 2*c.getRadius()+3;
	            		a[3] = 2*c.getRadius()+3;
	    			}
	    			else if(shapeDelete.getClass().getName().equals("shapesPackage.TriangleA")){
	    				TriangleA t = (TriangleA)shapeDelete;
	    				a[0] = t.getPoint().getX();
	    				a[1] = t.getPoint().getY()-t.getSide();
	    				a[2] = t.getSide()+3;	
	    				a[3] = t.getSide()+3;
	    			}
	    	}//if(shape!=null)
	    
	    	return a;
	    }
	
	
	public void changePositionShape(Shape shape){
    	List listShapes = Shapes.getInstance().getListShapes();
    	listShapes.remove(shape);
    	listShapes.add(shape);
    }
	
	
	
	
	public void clearCanvas(){
		Shape s = null;
		String cadena = null;
		Controller controller = new Controller();
		List list = this.shapesDrawIn(controller.getListShapes());
    	Iterator iter = list.iterator();
    	while (iter.hasNext()){
    		s = (Shape)iter.next();
    		controller.delete(s);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
	}
	
	
	
	
	public int[] comprobationDatas(int x, int y, int param1, int param2,Shape s){
		int a[] = new int[2];
		

		if (x <= 0){
			if (s.getClass().getName().equals("shapesPackage.CircleA")){
				x = param1;
			}
			else { //rectángulo y triángulo
				x = 0;
			}
		}
		else if (x > 593) 
		    	x = 593 - param1;
		
		
		
		if (y <= 0){
			if (s.getClass().getName().equals("shapesPackage.RectangleA")){
				 y = 0;
			}
			else{ //círculo y triángulo
				y = param1;
			}
		}
		else if (y > 720){
			if (s.getClass().getName().equals("shapesPackage.RectangleA")){
				 y = 720 - param2;
			}
			else if(s.getClass().getName().equals("shapesPackage.CircleA")){
				y = 720 - param1;
			}
			else { //triángulo
				y = 720;
			}
		}
		
		a[0] = x;
		a[1] = y;
	
		return a;
	}
	
	
	public int[] comprobationDatasScaling(int x, int y, int param1, int param2, Shape s){
		int a[] = new int[2];
		
		if(s.getClass().getName().equals("shapesPackage.RectangleA")){
			if (x + param1 > 593){
				param1 = 593 - x;
			}	
			
			if(y + param2 > 720){
				param2 = 720 - y;
			}
		}
		else if (s.getClass().getName().equals("shapesPackage.CircleA")){
			if (x - param1 < 0)
				param1 = x;
			else if(x + param1 > 593)
				param1 = 593 - x;
			
			if (y - param1 < 0)
				param1 = y;
			else if(y + param1 > 720)
				param1 = 720 - y;
		}
		else{ //triángulo
			if (x + param1 > 593)
				param1 = 593 - x;
			
			if (y - param1 < 0)
				param1 = y;
		}
		
		a[0] = param1;
		a[1] = param2;
		
		return a;
	}
	
	
	public boolean ComprobationFieldsMove(MainWindow p) {
		boolean FieldsRight = true;
		
		if ( this.isEmpty(p.get_new_y()) || this.isEmpty(p.get_new_x()) )
			FieldsRight = false;
		
		return FieldsRight;
	}
	
	
	public boolean comprobationFieldsScale(Shape s, MainWindow p) {
		boolean b = true;
		if(s.getClass().getName().equals("shapesPackage.RectangleA")){
			if (this.isEmpty(p.getField_NewWidth()) || this.isEmpty(p.getField_NewHeight())    )
				b = false;
		}
		else if(s.getClass().getName().equals("shapesPackage.CircleA")){
			if ( this.isEmpty(p.getField_NewRadius())) 
				b = false;
		}
		else if(s.getClass().getName().equals("shapesPackage.TriangleA")){
			if ( this.isEmpty(p.getField_NewSide()) )
				b = false;
		}
		return b;
	}
	
	
	public String[] getDemos(){
		File f = new File("bin/demos");
		FilenameFilter filter = new FilenameFilter() {
		    
			public boolean accept(File dir, String name) {		    	
		        return !name.contains("$");
		    }
		};
		String [] n = f.list(filter);
		int d = n.length;
		String [] s = new String[d];
		int j = 0;
		for(int i=0;i<n.length;i++){
			String a[] = n[i].split(".class");
			s[i] = a[0];
		}
		return s; 
	}
	
	
	public int getSelection(){
		return selection;
	}
	
	
	
	
	
	public int getSelectionDemo(){
		return selectionDemo;
	}
	
	
	
	
	public Shape getShapeSelecction(){
		Controller controller = new Controller();
		List list=controller.getListShapes();
		//Shape s = (Shape)list.get(selection);
		Shape s = Shapes.getInstance().find(selection);
		return s;
	}
	
	
	
	
	public Map introducDatasCir(int typeShape, MainWindow pr) {
		Map hash = null;
		if( !isEmpty(pr.get_field_x()) && !isEmpty(pr.get_field_y()) && !isEmpty(pr.get_field_radius()) ){	
			int x = Integer.parseInt(pr.get_field_x());
			int y = Integer.parseInt(pr.get_field_y());
			int radius = Integer.parseInt(pr.get_field_radius());
			
			CircleA c = new CircleA();
			//int a[] = comprobationDatas(x,y,radius,0,typeShape);
			int a[] = comprobationDatas(x,y,radius,0,c);
			
			hash = new HashMap();
			hash.put("shape", new Integer(typeShape));
			hash.put("x", new Integer(a[0]));
			hash.put("y", new Integer(a[1]));
			hash.put("radius", new Integer(radius));
			hash.put("color", pr.getColor());
		}
		
		return hash;
	}
	
	
	
	public Map introducDatasRec(int typeShape, MainWindow pr) {
		Map hash = null;
		if( !isEmpty(pr.get_field_x()) && !isEmpty(pr.get_field_y()) && !isEmpty(pr.get_field_width()) && !isEmpty(pr.get_field_height())    ){	
			int x = Integer.parseInt(pr.get_field_x());
			int y = Integer.parseInt(pr.get_field_y());
			int width = Integer.parseInt(pr.get_field_width());
			int height = Integer.parseInt(pr.get_field_height());
			
			RectangleA r = new RectangleA();
			//int a[] = comprobationDatas(x,y,width,height,typeShape);
			int a[] = comprobationDatas(x,y,width,height,r);
			
			hash = new HashMap();
			hash.put("shape", new Integer(typeShape));
			hash.put("x", new Integer(a[0]));
		 	hash.put("y", new Integer(a[1]));
			hash.put("width", new Integer(width));
			hash.put("height", new Integer(height));
			hash.put("color", pr.getColor());
		}
		
		return hash;
	}


	
	
	
	
	public Map introducDatasTri(int typeShape, MainWindow pr) {
		Map hash = null;
		if( !isEmpty(pr.get_field_x()) && !isEmpty(pr.get_field_y()) && !isEmpty(pr.get_field_side()) ){
			int x = Integer.parseInt(pr.get_field_x());
			int y = Integer.parseInt(pr.get_field_y());
			int side = Integer.parseInt(pr.get_field_side());
			
			TriangleA t = new TriangleA();
			//int a[] = comprobationDatas(x,y,side,0,typeShape);
			int a[] = comprobationDatas(x,y,side,0,t);
			
			hash = new HashMap();
			hash.put("shape", new Integer(typeShape));
			hash.put("x", new Integer(a[0]));
			hash.put("y", new Integer(a[1]));
			hash.put("side", new Integer(side));
			hash.put("color", pr.getColor());
		}	
		return hash;
	}
	
	
	public boolean isEmpty(String cad){
		if (cad.equals("")){
			return true;
		}
		return false;
	}
	
	
	public void listenerMoveShape(MainWindow pr) {
		Controller controller = new Controller();
		List list=controller.getListShapes();
		//Shape s = (Shape)list.get(selection);
		Shape s = Shapes.getInstance().find(selection);
		if(s != null){
			boolean b = ComprobationFieldsMove(pr);
			if (b){
			String message = "Are you sure that you want move the shape " + s.getClass().getName();
			int res = JOptionPane.showConfirmDialog(pr,message,"",JOptionPane.YES_NO_OPTION);
			if (res == JOptionPane.YES_OPTION){
				PointA p = new PointA();
				Integer nx = new Integer(pr.get_new_x());
				Integer ny = new Integer(pr.get_new_y());
				int x = nx.intValue();
				int y = ny.intValue();
				
				int a[] = new int[2];
				if(s.getClass().getName().equals("shapesPackage.RectangleA")){
					RectangleA r = (RectangleA)s;
					//a = comprobationDatas(x,y,r.getWidth(),r.getHeight(),0);
					a = this.comprobationDatas(x,y,r.getWidth(),r.getHeight(),r);
				}
				else if(s.getClass().getName().equals("shapesPackage.CircleA")){
					CircleA c = (CircleA)s;
					//a = comprobationDatas(x,y,c.getRadius(),0,1);
					a = this.comprobationDatas(x,y,c.getRadius(),0,c);
				}
				else if(s.getClass().getName().equals("shapesPackage.TriangleA")){
					TriangleA t = (TriangleA)s;
					//a = comprobationDatas(x,y,t.getSide(),0,2);
					a = this.comprobationDatas(x,y,t.getSide(),0,t);
				}
				
				p.setX(a[0]);
				p.setY(a[1]);
			
				controller.move(s,p);
			} //if (res == JOptionPane.YES_OPTION)
			} //if (b)
			else {
				JOptionPane.showMessageDialog(pr, "All fields should be full ","", JOptionPane.INFORMATION_MESSAGE);
			}
		} //if(s != null) 
	}
	
	
	
	
	
	
	public void ListenerScaleShape(MainWindow pr){
		int typeShape;
		Map hash=new HashMap();
		Controller controller = new Controller();
		List list=controller.getListShapes();
		//Shape s = (Shape)list.get(selection);
		Shape s = Shapes.getInstance().find(selection);
		if(s!=null){
			boolean b = comprobationFieldsScale(s,pr);
			if (b){
			String message = "Are you sure that you want scale the shape " + s.getClass().getName();
			int res = JOptionPane.showConfirmDialog(pr,message,"",JOptionPane.YES_NO_OPTION);
			if (res == JOptionPane.YES_OPTION){
				if(s.getClass().getName().equals("shapesPackage.RectangleA")){
					Integer width = new Integer(pr.getField_NewWidth());
					Integer height = new Integer(pr.getField_NewHeight());
					int newWidth = width.intValue();
					int newHeight = height.intValue();
					
					RectangleA r = (RectangleA)s;
					int a[] = comprobationDatasScaling(r.getPoint().getX(),r.getPoint().getY(),newWidth,newHeight,r);
					
					
					hash.put("width", new Integer(a[0]));
					hash.put("height", new Integer(a[1]));
				
					controller.scale(r,hash);
				}
				else if(s.getClass().getName().equals("shapesPackage.CircleA")){
					Integer radius = new Integer(pr.getField_NewRadius());
					int newRadius = radius.intValue();
					
					CircleA c = (CircleA)s;
					int a[] = comprobationDatasScaling(c.getPoint().getX(),c.getPoint().getY(),newRadius,0,c);
					
					hash.put("radius", new Integer(a[0]));
				
					controller.scale(c,hash);
				}
				else if(s.getClass().getName().equals("shapesPackage.TriangleA")){
					Integer side = new Integer(pr.getField_NewSide());
					int newSide = side.intValue();
					
					TriangleA t = (TriangleA)s;
					int a[] = comprobationDatasScaling(t.getPoint().getX(),t.getPoint().getY(),newSide,0,t);
					
					
					hash.put("side", new Integer(a[0]));
				
					controller.scale(t,hash);
				}
			} //if (res == JOptionPane.YES_OPTION)
			} //if (b)
			else {
				JOptionPane.showMessageDialog(pr, "All fields should be full ","", JOptionPane.INFORMATION_MESSAGE);
			}
		} //if(s!=null)
	}
	
	
	
	
	public void loadDemo(String name){
		String f = "demos." + name;
		try {
			Class c = Class.forName(f);
			try {
				Demo demo = (Demo)c.newInstance();
				demo.run();	
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	public String[] loadShapes(){
		Controller controller = new Controller();
		List list = this.shapesDrawIn(controller.getListShapes());

		String[] sps = null;
		if (list.size()>0){
			sps = new String[list.size()];
			String cad = "";
			
			for(int i=0;i<list.size();i++){
				Shape s = (Shape)list.get(i);
				cad = "\t " + s.getClass().getName() + " con id:" + s.getId();
				cad = cad + Utils.datasShow(s);
				sps[i] = cad;
				cad = "";
			}
		}
		
		return sps;
	}
	
	
	
	public void setSelection(int selection){
		this.selection = selection;
	}
	
	
	public void setSelectionDemo(int selection){
		this.selectionDemo = selection;
	}
	
	
	
	public List shapesDrawIn(List list){
		List l = new ArrayList();

		Shapes sh = Shapes.getInstance();
		Iterator it = list.iterator();
		while (it.hasNext()){
		  Shape s=(Shape)it.next();
		  if (s.getPaintedIn() == sh.getPaintConsoleOrCanvas()){
			l.add(s);
		  }
		}
		return l;
	}
	
	
	
	 public void sleeping(int n){
		int t = 0;
		switch (n){
		case 3:{
			t = 1500;
		}
		default:{
			t = 1000;
		}
		}
		try {

			Thread.sleep(t);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	
	
}
