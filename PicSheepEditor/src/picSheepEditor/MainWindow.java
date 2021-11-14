package picSheepEditor;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;

public class MainWindow {
int X,Y;
	Image image;
	Boolean mouse=false;
 public void displayIt(){
	  final Display display = new Display();
	    final Shell shell = new Shell(display);
	    
	   
	    shell.setText("Canvas Example");
	    shell.setLayout(new FillLayout());

	    Canvas canvas = new Canvas(shell, SWT.NONE);

	    canvas.addPaintListener((PaintListener) new PaintListener() {
	      public void paintControl(PaintEvent e) {
	        image = new Image(display, "DATEINAME");

	        e.gc.drawImage(image, 10, 10);

	       
	  
	      }
	    });
	    
	    canvas.addPaintListener((PaintListener) new PaintListener() {
		      public void paintControl(PaintEvent e) {
		     

		        Rectangle bounds = image.getBounds();
		        e.gc.drawLine(0,0,bounds.width,bounds.height);
		        e.gc.drawLine(0,bounds.height,bounds.width,0);
		  
		      }
		    });
	    
	    Listener listenerDown = new Listener() {


	        public void handleEvent(Event event) {
	         
	        	   System.out.println("Down");
	            X = event.x;
	            Y = event.y;
	            mouse = !mouse;
	          
	        }
	      };
	    
	      Listener listenerMove = new Listener() {
	     
	          public void handleEvent(Event event) {
	            
	              /*if ((event.stateMask & SWT.BUTTON1) == 0)
	                break;*/
	        	   System.out.println("Move");
	        	   if(mouse)
	        	   {
	        	   GC gc = new GC(shell);
	               gc.drawLine(X, Y, event.x, event.y);
	               gc.dispose();  	    
	        	   }
	        	   X = event.x;
		            Y = event.y;
	          }
	        };
	    canvas.addListener(SWT.MouseDown, listenerDown);
	    canvas.addListener(SWT.MouseMove, listenerMove);
	    
	    shell.open();
	    while (!shell.isDisposed()) {
	      if (!display.readAndDispatch()) {
	        display.sleep();
	      }
	    }
	    display.dispose();
}
}
