package picSheepEditor;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;

public class MainWindow {
int X,Y;
	Image image;
	Boolean mouse=false;
	Boolean bleistift = false;
	Boolean viereck = false;
	
PictureSettings ps;
	  
Canvas canvas ;
	  Display display;
	  
	  Shell shell;
	  
 public void displayIt(){
	  display = new Display();
	    shell = new Shell(display);
	    
	   
	    shell.setText("PicSheepEditor");
	    shell.setLayout(new FillLayout());

	    GridLayout gridLayout = new GridLayout();
        gridLayout.numColumns = 1;
        shell.setLayout(gridLayout);
	    
        Group outerGroup = new Group(shell, SWT.NONE);

        // Tell the group to stretch in all directions
        outerGroup.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
        outerGroup.setLayout(new GridLayout(1, true));
        outerGroup.setText("Image");

        
	    canvas = new Canvas(outerGroup, SWT.NONE);
	    canvas.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
	    canvas.addPaintListener((PaintListener) new PaintListener() {
	      public void paintControl(PaintEvent e) {
	       // image = new Image(display, "DATEINAME");

	        //e.gc.drawImage(image, 10, 10);

	       
	  
	      }
	    });
	    
	    canvas.addPaintListener((PaintListener) new PaintListener() {
		      public void paintControl(PaintEvent e) {
		     

		        //Rectangle bounds = image.getBounds();
		        //e.gc.drawLine(0,0,bounds.width,bounds.height);
		        //e.gc.drawLine(0,bounds.height,bounds.width,0);
		        
		    	  e.gc.setBackground(e.display.getSystemColor(SWT.COLOR_WHITE));
		          e.gc.fillRectangle(0, 0, 400, 200);
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
	    canvas.pack();
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
	    
	    Menu menu, fileMenu, editMenu, viewMenu;

	    

	    menu = new Menu(shell, SWT.BAR);
	    MenuItem fileItem = new MenuItem(menu, SWT.CASCADE);
	    fileItem.setText("File");
	    MenuItem editItem = new MenuItem(menu, SWT.CASCADE);
	    editItem.setText("Edit");
	    MenuItem viewItem = new MenuItem(menu, SWT.CASCADE);
	    viewItem.setText("View");
	    MenuItem helpItem = new MenuItem(menu, SWT.CASCADE);
	    helpItem.setText("Help");

	    fileMenu = new Menu(menu);
	    fileItem.setMenu(fileMenu);
	    MenuItem newItem = new MenuItem(fileMenu, SWT.NONE);
	    newItem.setText("New");
	    MenuItem openItem = new MenuItem(fileMenu, SWT.NONE);
	    openItem.setText("Open...");
	    MenuItem saveItem = new MenuItem(fileMenu, SWT.NONE);
	    saveItem.setText("Save");
	    MenuItem saveAsItem = new MenuItem(fileMenu, SWT.NONE);
	    saveAsItem.setText("Save As...");
	    new MenuItem(fileMenu, SWT.SEPARATOR);
	    MenuItem pageSetupItem = new MenuItem(fileMenu, SWT.NONE);
	    pageSetupItem.setText("Page Setup...");
	    MenuItem printItem = new MenuItem(fileMenu, SWT.NONE);
	    printItem.setText("Print...");
	    new MenuItem(fileMenu, SWT.SEPARATOR);
	    MenuItem exitItem = new MenuItem(fileMenu, SWT.NONE);
	    exitItem.setText("Exit");

	    editMenu = new Menu(menu);
	    editItem.setMenu(editMenu);
	    MenuItem cutItem = new MenuItem(editMenu, SWT.NONE);
	    cutItem.setText("Cut");
	    MenuItem pasteItem = new MenuItem(editMenu, SWT.NONE);
	    pasteItem.setText("Paste");

	    viewMenu = new Menu(menu);
	    viewItem.setMenu(viewMenu);
	    MenuItem toolItem = new MenuItem(viewMenu, SWT.NONE);
	    toolItem.setText("ToolBars");
	    MenuItem fontItem = new MenuItem(viewMenu, SWT.NONE);
	    fontItem.setText("Font");

	    //exitItem.addSelectionListener(new MenuItemListener());
	    
	    shell.setMenuBar(menu);
	    
	    newItem.addSelectionListener(new MenuItemListener());
	    
	   
	    
	    shell.pack();
	    shell.open();
	    while (!shell.isDisposed()) {
	      if (!display.readAndDispatch()) {
	        display.sleep();
	      }
	    }
	    display.dispose();
}
 

class MenuItemListener extends SelectionAdapter {
	  public void widgetSelected(SelectionEvent event) {
	  if (((MenuItem) event.widget).getText().equals("New")) {
	  NewDialog nd = new NewDialog();
	  ps = nd.show(display);
	  
	  canvas.addPaintListener((PaintListener) new PaintListener() {
	      public void paintControl(PaintEvent e) {
	     

	        //Rectangle bounds = image.getBounds();
	        //e.gc.drawLine(0,0,bounds.width,bounds.height);
	        //e.gc.drawLine(0,bounds.height,bounds.width,0);
	        
	    	  e.gc.setBackground(e.display.getSystemColor(SWT.COLOR_WHITE));
	          e.gc.fillRectangle(0, 0, ps.getX(), ps.getY());
	      }
	    });
	  
	  }
	 }
	  }
}
