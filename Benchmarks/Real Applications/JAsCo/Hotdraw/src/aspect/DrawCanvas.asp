package aspect;


import utilsClass.*;
import shapesPackage.*;
import paintCanvas.*;
import java.util.Map;



class DrawCanvas {
	
	Operation o = new Operation();
			
    hook InsertCanvas {
		
		InsertCanvas(method(..args)) {
			execution(method);
		}
		    
		after() {			
			Shape s = (Shape)args[0];
			PanelPrincipal canvas = PanelPrincipal.getInstance();
			PaintCanvas lienzo = canvas.getPaintCanvas();
			global.o.addOperation(new Integer(1));
			lienzo.tomaShape(s,global.o, null);
			lienzo.repaint();
		}
	}
	
      
    
    hook DeleteCanvas{
		DeleteCanvas(method(..args)){
			execution(method);
		}
		
		after(){
			 Shape s = (Shape)args[0];
			 int x = Shapes.getInstance().getPaintConsoleOrCanvas();
			 PanelPrincipal canvas = PanelPrincipal.getInstance();
			 //if (x==2)//estamos pintando en la consola
				//canvas.setVisible(false);
			 PaintCanvas lienzo = canvas.getPaintCanvas();
			 global.o.addOperation(new Integer(2));
			 global.o.addShapeDelete(s);
			 lienzo.tomaShape(s, global.o, null);
			 lienzo.repaint();
		}
		
	}
    
    
    
	
	
    hook MoveCanvas{
		MoveCanvas(method(..args)){
			execution(method);
		}
		
		
		before(){
				global.o.addOperation(new Integer(3));
				Shape s = (Shape)args[0];
				Shape sh = utilsClass.Utils.copyShape(s);
			 	global.o.addShapeDelete(sh);
		}
			
		after(){
				Shape s = (Shape)args[0];
				PanelPrincipal canvas = PanelPrincipal.getInstance();
			 	PaintCanvas lienzo = canvas.getPaintCanvas();
			 	lienzo.tomaShape(s, global.o, null);
				lienzo.repaint();
		}
		
	}//hook Move
	
	
	
    
    
    hook ScaleCanvas{
		ScaleCanvas(method(..args)){
			execution(method);
		}

		
		before(){
			global.o.addOperation(new Integer(4));
			Shape s = (Shape)args[0];
			Shape sh = utilsClass.Utils.copyShape(s);
			global.o.addShapeDelete(sh);
		}
		
		after(){
			Shape s = (Shape)args[0];
			PanelPrincipal canvas = PanelPrincipal.getInstance();
		 	PaintCanvas lienzo = canvas.getPaintCanvas();
		 	lienzo.tomaShape(s,global.o, null);
			lienzo.repaint();
		}
		
	}//hook Scale
	
	

  	
}//class
