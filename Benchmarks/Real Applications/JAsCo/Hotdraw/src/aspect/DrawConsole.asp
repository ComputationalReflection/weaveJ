package aspect;


import utilsClass.*;
import shapesPackage.*;
import paintCanvas.*;
import java.util.Map;



class DrawConsole {

	
    hook InsertConsole {
		
		InsertConsole(method(..args)) {
			execution(method);
		}
		    
		after() {
			Shape s = (Shape)args[0];
			if(s.getClass().getName().equals("shapesPackage.RectangleA")){   
				System.out.println("INSERT RECTANGLE with datas " + utilsClass.Utils.datasShow(s));
			}
			else if(s.getClass().getName().equals("shapesPackage.CircleA")){
				System.out.println("INSERT CIRCLE with datas " + utilsClass.Utils.datasShow(s));
				}
				else if(s.getClass().getName().equals("shapesPackage.TriangleA")){
					System.out.println("INSERT TRIANGLE with datas " + utilsClass.Utils.datasShow(s));
				}	   
		}
	}
	
    
    
    
    
    hook DeleteConsole{
		DeleteConsole(method(..args)){
			execution(method);
		}
		
		after(){
			 Shape s = (Shape)args[0];
			 //global.deleteCanvas(s); //borro el canvas por si hay shapes dibujadas debido a la ejecución de la demo.
			 System.out.println("DELETING shape : " + s.getClass().getName() + " con id " + s.getId());
			 System.out.println(utilsClass.Utils.datasShow(s));
		}
		
	}
    
    
    
	
	
    hook MoveConsole{
		MoveConsole(method(..args)){
			execution(method);
		}
		
		after(){
			Shape s = (Shape)args[0];
			System.out.println("MOVING shape : " + s.getClass().getName() + " con id " + s.getId());
			System.out.println(utilsClass.Utils.datasShow(s));
		}
		
	}//hook Move
	
	
	
    
    
    hook ScaleConsole{
		ScaleConsole(method(..args)){
			execution(method);
		}
		
		after(){
			Shape s = (Shape)args[0];
			System.out.println("SCALING shape : " + s.getClass().getName() + " con id " + s.getId());
			System.out.println(utilsClass.Utils.datasShow(s));
		}
		
	}//hook Scale
	
	

  	
}//class