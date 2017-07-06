package shapesPackage;

import java.awt.Color;
import java.util.Map;

/** clase abstracta de la que heredan el resto de figuras.*/
public abstract class Shape {

	public int id;
	public Color color;
	public PointA p;
	public int paintedIn = 0;
	
	
	public int getPaintedIn() {
		return paintedIn;
	}

	public void setPaintedIn(int paintedIn) {
		this.paintedIn = paintedIn;
	}
	
	
	public PointA getPoint() {
		return p;
	}

	public void setPoint(PointA p) {
		this.p = p;
	}
	
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	
	public Color getColor() {
		// TODO Auto-generated method stub
		return color;
	}
	
	
	
	public void setColor(Color color) {
		// TODO Auto-generated method stub
		this.color=color;
	}
	

	
	public void moveShape(Shape s,String message,PointA p){
		s.setPoint(p);
	}
	
	
	public void scaleShape(Shape s,String message,Map hash){
			if(s.getClass().getName().equals("shapesPackage.RectangleA")){
				RectangleA r=(RectangleA)s;
				
				Integer width=(Integer)hash.get("width");
				Integer height=(Integer)hash.get("height");
				r.setWidth(width);
				r.setHeight(height);
			}
			else if(s.getClass().getName().equals("shapesPackage.CircleA")){
					CircleA c=(CircleA)s;
							
					Integer radius=(Integer)hash.get("radius");
					c.setRadius(radius);
			}
			else if(s.getClass().getName().equals("shapesPackage.TriangleA")){
				TriangleA t=(TriangleA)s;
				
				Integer side=(Integer)hash.get("side");
				t.setSide(side);
			}
	}

	
	
}