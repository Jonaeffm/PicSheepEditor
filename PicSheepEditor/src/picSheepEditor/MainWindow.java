package picSheepEditor;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageLoader;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.ColorDialog;
import org.eclipse.swt.widgets.Control;
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
	Boolean kreis = false;
PictureSettings ps;
	  
Canvas canvas ;
	  Display display;
	   Group outerGroup,outerGroup2;
	  Shell shell;
	 
	  public void save()
	  {
		  Image drawable = new Image(display, canvas.getBounds());
			GC gc = new GC(drawable);
			canvas.print(gc);
			ImageLoader loader = new ImageLoader();
			loader.data = new org.eclipse.swt.graphics.ImageData[] {drawable.getImageData()};
			loader.save("/home/jon/Bilder/swt.png", SWT.IMAGE_PNG);
			drawable.dispose();
			gc.dispose();

	  }
	  
	  public void drawImage(Image i)
	  {
		  canvas.addPaintListener(new PaintListener() { 
			    public void paintControl(PaintEvent e) { 
			    	
			    	e.gc.drawImage(i, 0, 0);
			    	
			    	
			    }
			});
		 canvas.redraw();
	  }
public void drawLineImage(Event event) {
	 canvas.addPaintListener(new PaintListener() { 
		    public void paintControl(PaintEvent e) { 
		    	
		    
		    	e.gc.setForeground( new Color( ps.getColor().getRGB() ) );
		    	 
		    	e.gc.drawLine(X, Y, event.x,event.y);
		    	
		    }
		});
	 canvas.redraw();
}

public void drawRectangleImage(Event event,int xg, int xk,int yg , int yk) {
	 canvas.addPaintListener(new PaintListener() { 
		    public void paintControl(PaintEvent e) { 
		    		Color c =  new Color( ps.getColor().getRGB() );
    			    	e.gc.setForeground( c );
    	        		   e.gc.drawRectangle(xk, yk, xg-xk,yg-yk);
    	     
		    }
		});

canvas.redraw();
}

public void drawCircleImage(Event event,int xg, int xk,int yg , int yk) {
	 canvas.addPaintListener(new PaintListener() { 
		    public void paintControl(PaintEvent e) { 
		    	
   			    	e.gc.setForeground( new Color( ps.getColor().getRGB() ) );
   	        		   e.gc.drawOval(xk, yk, xg-xk,yg-yk);
   			
		    }
		});

canvas.redraw();
}


 public void displayIt(){
	  display = new Display();
	    shell = new Shell(display);
	    
	    
	    ps = new PictureSettings();
	    ps.setX(400);
	    ps.setY(200);
	    ps.setColor(new Color(0,0,0));
	    shell.setText("PicSheepEditor");
	    shell.setLayout(new FillLayout());

	    GridLayout gridLayout = new GridLayout();
        gridLayout.numColumns = 1;
        shell.setLayout(gridLayout);
	    
        outerGroup = new Group(shell, SWT.NONE);

        // Tell the group to stretch in all directions
        outerGroup.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
        outerGroup.setLayout(new GridLayout(1, true));
        outerGroup.setText("Image");

        outerGroup2 = new Group(shell, SWT.NONE);

        // Tell the group to stretch in all directions
        outerGroup2.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
        outerGroup2.setLayout(new GridLayout(10, true));
        outerGroup2.setText("Tools");

        
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
	            
	            if(ps.getX()>event.x&&ps.getY()>event.y) {
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
        		   drawRectangleImage(event,xg,xk,yg,yk);
        		  // GC gc = new GC(canvas);
        		   
        		   //gc.dispose(); 
	            	
	            		
		            
		            
	            }
	            if(!mouse&&kreis)
	            {
	            	X = event.x;
		            Y = event.y;
	            
	            }
	            if(mouse&&kreis)
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
        		   drawCircleImage(event,xg,xk,yg,yk);
        		  // GC gc = new GC(canvas);
        		   
        		   //gc.dispose(); 
	            	
	            		
		            
		            
	            }
	            
	            }}
	        
	      };
	    
	      Listener listenerMove = new Listener() {
	     
	          public void handleEvent(Event event) {
	            
	              /*if ((event.stateMask & SWT.BUTTON1) == 0)
	                break;*/
	        	   System.out.println("Move");
	        	   if(ps.getX()>event.x&&ps.getY()>event.y) {
	        	   if(!mouse&&bleistift)
	        	   {
	        		   X = event.x;
			            Y = event.y;
	        	   }
	        	   if(mouse&&bleistift)
	        	   	{
	        		
	        		   drawLineImage(event);
	        		   
	   		    	//gc.drawImage(image, 0, 0);
	        		  /* GC gc = new GC(canvas);
	        		   gc.setForeground( new Color( ps.getColor().getRGB() ) );
	        		   gc.drawLine(X, Y, event.x,event.y);
	        		   gc.dispose(); */
	        		   X = event.x;
			            Y = event.y;
	        	   }
	        	   }
	          }
	        };
	    canvas.addListener(SWT.MouseDown, listenerDown);
	    canvas.addListener(SWT.MouseMove, listenerMove);
	    canvas.pack();
	    final Button button = new Button(outerGroup2, SWT.PUSH);
	    button.setText("Pencil");
	    
	    button.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
			
				bleistift = !bleistift;
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				
			}

	       
	      });
	    
	    final Button buttonViereck = new Button(outerGroup2, SWT.PUSH);
	    buttonViereck.setText("Square");
	    
	    buttonViereck.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				System.out.println("square");
				viereck = !viereck;
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				
			}

	       
	      });
	    
	    final Button buttonKreis = new Button(outerGroup2, SWT.PUSH);
	    buttonKreis.setText("Circle");
	    
	    buttonKreis.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				System.out.println("Circle");
				kreis = !kreis;
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				
			}

	       
	      });
	    
	    
	    final Button buttonColor = new Button(outerGroup2, SWT.PUSH);
	    buttonColor.setText("Color");
	    
	    buttonColor.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				
				save();
   		    	
				
				ColorDialog dlg = new ColorDialog(shell);
			    dlg.setRGB(new RGB(0, 0, 255));
			    RGB rgb = dlg.open();
			    if (rgb != null) {
			      Color color = new Color(shell.getDisplay(), rgb);
			      System.out.println(color.getRGB());
			      //--------------------------------------
			      ps.setColor(color);
			      //color.dispose();
			    }
			    
			    Image image = new Image(display,"/home/jon/Bilder/swt.png");
	   		    
   		    	drawImage(image);
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
	    saveItem.addSelectionListener(new MenuItemListener());
	   
	    
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
	  
	  GC gc = new GC(outerGroup);
	  gc.setBackground(display.getSystemColor(SWT.COLOR_WHITE));
	   gc.fillRectangle(0, 0, ps.getX(),ps.getY());
	   gc.dispose(); 	  
	  }
	  else if (((MenuItem) event.widget).getText().equals("Save")) {
		  System.out.println("save");
		  /*Image drawable = new Image(display, canvas.getBounds());
			GC gc = new GC(drawable);
			canvas.print(gc);
			ImageLoader loader = new ImageLoader();
			loader.data = new org.eclipse.swt.graphics.ImageData[] {drawable.getImageData()};
			loader.save("/home/jon/Bilder/swt.png", SWT.IMAGE_PNG);
			drawable.dispose();
			gc.dispose();*/
		  save();



		  
	  }
	 }
	  }
}
