package picSheepEditor;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;

public class MainWindow {
int X,Y;
	Image image;
	Boolean mouse=false;
	Boolean bleistift = false;
	Boolean viereck = false;
 public void displayIt(){
	  final Display display = new Display();
	    final Shell shell = new Shell(display);
	    
	   
	    shell.setText("PicSheepEditor");
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
	        	   int xk,xg,yk,yg;
	            mouse = !mouse;
	          
	            if(!mouse&&viereck)
	            {
	            	X = event.x;
		            Y = event.y;
	            
	            }
	            if(mouse&&viereck)
	            {
	         	   if(X<event.x)
        		   {
        			   xk=X;
        			   xg=event.x;
        		   }
        		   else
        		   {
        			   xk=event.x;
        			   xg=X;
        		   }
        		   if(Y<event.y)
        		   {
        			   yk=Y;
        			   yg=event.y;
        		   }
        		   else
        		   {
        			   yk=event.y;
        			   yg=Y;
        		   }	
        		   GC gc = new GC(shell);
        		   gc.drawRectangle(xk, yk, xg-xk,yg-yk);
        		   gc.dispose(); 
	            	
	            		
		            
		            
	            }
	            
	        }
	      };
	    
	      Listener listenerMove = new Listener() {
	     
	          public void handleEvent(Event event) {
	            
	              /*if ((event.stateMask & SWT.BUTTON1) == 0)
	                break;*/
	        	   System.out.println("Move");
	        	  
	        	   if(!mouse&&bleistift)
	        	   {
	        		   X = event.x;
			            Y = event.y;
	        	   }
	        	   if(mouse&&bleistift)
	        	   	{
	        		  
	        		   GC gc = new GC(shell);
	        		   gc.drawLine(X, Y, event.x,event.y);
	        		   gc.dispose(); 
	               X = event.x;
		            Y = event.y;
	        	   }
	        	   
	          }
	        };
	    canvas.addListener(SWT.MouseDown, listenerDown);
	    canvas.addListener(SWT.MouseMove, listenerMove);
	    
	    final Button button = new Button(shell, SWT.PUSH);
	    button.setText("Bleistift");
	    
	    button.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				System.out.println("Bleistift");
				bleistift = !bleistift;
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				
			}

	       
	      });
	    
	    final Button buttonViereck = new Button(shell, SWT.PUSH);
	    buttonViereck.setText("Viereck");
	    
	    buttonViereck.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				System.out.println("Bleistift");
				viereck = !viereck;
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				
			}

	       
	      });
	    
	    shell.open();
	    while (!shell.isDisposed()) {
	      if (!display.readAndDispatch()) {
	        display.sleep();
	      }
	    }
	    display.dispose();
}
}
