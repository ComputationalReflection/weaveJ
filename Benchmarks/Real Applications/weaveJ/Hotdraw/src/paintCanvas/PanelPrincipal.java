package paintCanvas;



import java.awt.Toolkit;
import java.awt.event.WindowAdapter;

import javax.swing.JFrame;

import paintCanvas.PaintCanvas;




/*
 * class that implemets the canvas. It uses Singleton pattern
 */
public class PanelPrincipal extends JFrame{
    
    /** El canvas de dibujo */
    private PaintCanvas canvas = new PaintCanvas();
    
    
    private static PanelPrincipal _instance=null;
	
	public static PanelPrincipal getInstance(){
		if (_instance==null){
			_instance=new PanelPrincipal();
		}
		return _instance;
	}
    
	
   
	/**
     * Constructor por defecto. Pone el canvas y el botón en el panel y
     * hace que se dibuje la primera línea.
     */
    public PanelPrincipal()
    {
    	this.setTitle("Canvas");
   	 	//this.setSize(600, this.getToolkit().getScreenSize().height);
    	this.setSize(600, 750);
   	 	this.setResizable(false);
   	 	this.setIconImage(Toolkit.getDefaultToolkit().getImage("icons/bunny2b.gif"));
   	 	this.setVisible(false);
        
        setComponents();
    }
    
    
    
    /**
     * Pone el canvas y el botón en el panel.
     */
    private void setComponents()
    {
        this.add (canvas);
    }
    

    public PaintCanvas getPaintCanvas(){
    	return canvas;
    }
    

}
