package picSheepEditor;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
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
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageLoader;
import org.eclipse.swt.graphics.PaletteData;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.ColorDialog;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;

public class MainWindow {
	
	//position of click
	int X,Y,newX,newY;
	
	//load image with this
	Image image;
	
	//is mouse button?
	Boolean mouse=false;
	
	//boolean for draw
	Boolean bleistift = false;
	Boolean viereck = false;
	Boolean kreis = false;
	Boolean line = false;
	Boolean fviereck=false;
	Boolean fkreis=false;
	
	//settings of actual picture
	PictureSettings ps;
	
	//filename for save automatically
	String fileName;
	String fileNameSource;
	
	//canvas to draw
	Canvas canvas ;
	
	//display object of SWT
	Display display;
	
	//group and shell objects (SWT)
	Group outerGroup,outerGroup2;
	Shell shell;
	ScrolledComposite scrolledComposite;
	
	//counter for saving automatically
	int zaehler = 0;
	
//invert colors
public Image inverse(Image i)
{
	
	 GC gc = new GC(i);
	 ImageData imageData= i.getImageData();;
	 int pixelValue ;
	 PaletteData palette = imageData.palette;
	 RGB rgb ;
	 Color c ;
	 for (int x = 0;x<i.getBounds().width;x++)
	 {	for (int y=0;y<i.getBounds().height;y++)
		{
		 	pixelValue = imageData.getPixel(x, y);
      	 	rgb = palette.getRGB(pixelValue);
            int pixel = palette.getPixel(new RGB( 255-rgb.red ,255-rgb.green,255-rgb.blue));
            imageData.setPixel(x, y, pixel);
        }
	}
	Image j =new Image( i.getDevice(), imageData );
	drawImage(j);
	return j; 
}
	
//grayscale
public Image grayscale(Image i)
{
	Image j = new Image(display, i, SWT.IMAGE_GRAY);
	drawImage(j);
	return j; 
}

//save as
public void saveAs(String pfad)
{
	Image drawable = new Image(display, canvas.getBounds());
	GC gc = new GC(drawable);
	canvas.print(gc);
	ImageLoader loader = new ImageLoader();
	loader.data = new org.eclipse.swt.graphics.ImageData[] {drawable.getImageData()};
	loader.save(pfad, SWT.IMAGE_PNG);
	drawable.dispose();
	gc.dispose();
 }
 
//save automatically
public void save()
{

	zaehler++;
	if(fileName==null) {
	FileDialog fd = new FileDialog(shell, SWT.SAVE);
	fd.setText("Save as");
	fd.setFilterPath("/");
	String[] filterExt = { "*.png"};
	fd.setFilterExtensions(filterExt);
	fileNameSource = fd.open();
	fileNameSource = fileNameSource.substring(0, fileNameSource.length()-4);
	}
	String zahl = Integer.toString(zaehler);
	fileName = fileNameSource+zahl;
	fileName = fileName + ".png";
	
	
	Image drawable = new Image(display, canvas.getBounds());
	GC gc = new GC(drawable);
	canvas.print(gc);
	ImageLoader loader = new ImageLoader();
	loader.data = new org.eclipse.swt.graphics.ImageData[] {drawable.getImageData()};
	loader.save(fileName, SWT.IMAGE_PNG);
	drawable.dispose();
	gc.dispose();
	mouse= true;
}

//new image
public void drawNewImage()
{
	canvas.addPaintListener(new PaintListener() { 
	public void paintControl(PaintEvent e) { 
	   	e.gc.fillRectangle(0, 0, ps.getX(),ps.getY());
	    }
	});
}

//load image
public void drawImage(Image i)
{
	canvas.addPaintListener(new PaintListener() { 
	public void paintControl(PaintEvent e) { 
	e.gc.drawImage(i, 0, 0);
	}
});
canvas.redraw();
}


//draw line
public void drawLineImage(Event event,int xg, int xk,int yg , int yk) {
	 canvas.addPaintListener(new PaintListener() { 
		    public void paintControl(PaintEvent e) { 
		    e.gc.setForeground( new Color( ps.getColor().getRGB() ) );
		   	e.gc.drawLine(xk, yk, xg,yg);
		    }
	 });
	 
	 canvas.redraw();
	 
}

//draw rectangle
public void drawRectangleImage(Event event,int xg, int xk,int yg , int yk) {
	 canvas.addPaintListener(new PaintListener() { 
		    public void paintControl(PaintEvent e) { 
		    	Color c =  new Color( ps.getColor().getRGB() );
    		   	e.gc.setForeground( c );
    	       e.gc.drawRectangle(xk, yk, xg-xk,yg-yk);
    	    }
		});
	 save();
	 canvas.redraw();
	 
}

//draw filled rectangle
public void drawFRectangleImage(Event event,int xg, int xk,int yg , int yk) {
	 canvas.addPaintListener(new PaintListener() { 
		    public void paintControl(PaintEvent e) { 
		    	Color c =  new Color( ps.getColor().getRGB() );
   		   	e.gc.setBackground( c );
   	       e.gc.fillRectangle(xk, yk, xg-xk,yg-yk);
   	    }
		});
	 save();
	 canvas.redraw();

}

//draw circle
public void drawCircleImage(Event event,int xg, int xk,int yg , int yk) {
	 canvas.addPaintListener(new PaintListener() { 
		    public void paintControl(PaintEvent e) { 
		    e.gc.setForeground( new Color( ps.getColor().getRGB() ) );
   	        e.gc.drawOval(xk, yk, xg-xk,yg-yk);
   			}
		});
	 save();
	 canvas.redraw();

}

//draw filled circle
public void drawFCircleImage(Event event,int xg, int xk,int yg , int yk) {
	 canvas.addPaintListener(new PaintListener() { 
		    public void paintControl(PaintEvent e) { 
		    e.gc.setBackground( new Color( ps.getColor().getRGB() ) );
  	        e.gc.fillOval(xk, yk, xg-xk,yg-yk);
  			}
		});
	 save();
	 canvas.redraw();

}

//draw point
public void drawPointImage(Event event) {
	 canvas.addPaintListener(new PaintListener() { 
		    public void paintControl(PaintEvent e) { 
		       	e.gc.setForeground( new Color( ps.getColor().getRGB() ) );
		       	e.gc.drawPoint(event.x,event.y);
  			}
		});
	 save();
	 canvas.redraw();

}

//main window
public void displayIt(){
		
		//SWT objects
	  	display = new Display();
	    shell = new Shell(display);
	    
	    //settings of picture
	    ps = new PictureSettings();
	    ps.setX(320);
	    ps.setY(240);
	    ps.setColor(new Color(0,0,0));
	    
	    //shell text
	    shell.setText("PicSheepEditor");
	    
	    //SWT objects
	    GridLayout gridLayout = new GridLayout(1,false);
        gridLayout.numColumns = 1;
        shell.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
        shell.setLayout(gridLayout);
	    scrolledComposite = new ScrolledComposite( shell,SWT.SINGLE | SWT.BORDER| SWT.H_SCROLL | SWT.V_SCROLL );
        GridData gridData;
        gridData = new GridData();
        gridData.horizontalAlignment = GridData.FILL;
        gridData.verticalAlignment=GridData.FILL;
        gridData.grabExcessHorizontalSpace = true;
        gridData.grabExcessVerticalSpace=true;
        scrolledComposite.setLayoutData(gridData);
        outerGroup2 = new Group(shell, SWT.NONE);
        outerGroup2.setLayout(new GridLayout(10, true));
        outerGroup2.setText("Tools");
        GridData data1 = new GridData(SWT.FILL, SWT.BOTTOM, true, false);
        data1.heightHint = 50;
        data1.verticalSpan = 1;
        outerGroup2.setLayoutData(data1);
        
        //canvas to paint
	    canvas = new Canvas(scrolledComposite,SWT.NONE);
	    canvas.setSize(ps.getX(), ps.getY());
	    
	    //SWT objects
	    scrolledComposite.setContent(canvas);
	    shell.addListener( SWT.Resize, event -> {
	    	System.out.println("resize");
	    	int width = shell.getClientArea().width;
	    	int height=canvas.getClientArea().height;
	    	scrolledComposite.setMinSize( scrolledComposite.computeSize(width,height));
	    });
	    canvas.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
	    canvas.addPaintListener((PaintListener) new PaintListener() {
	      public void paintControl(PaintEvent e) {
	      }
	    });
	    canvas.addPaintListener((PaintListener) new PaintListener() {
		public void paintControl(PaintEvent e) {
		    	  e.gc.setBackground(e.display.getSystemColor(SWT.COLOR_WHITE));
		          e.gc.fillRectangle(0, 0, 400, 200);	         
		      }
		    });	
	    
	    //listener for mouse button
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
	           
	           if(!mouse&&fviereck)
	           {
	            	X = event.x;
		            Y = event.y;
	           }
	           
	           if(!mouse&&line)
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
        		}
	           
	           if(mouse&&fviereck)
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
        		   drawFRectangleImage(event,xg,xk,yg,yk);
        		}
	           
	            if(mouse&&line)
	            {
	         	   xk=X;
	         	   xg=event.x;
        		   yk=Y;
        		   yg=event.y;
        		   drawLineImage(event,xg,xk,yg,yk);
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
        		}
	            
	            if(!mouse&&fkreis)
	            {
	            	X = event.x;
		            Y = event.y;
	            }
	            if(mouse&&fkreis)
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
        		   drawFCircleImage(event,xg,xk,yg,yk);
        		}
	            
	            }}
	      };
	      
	      //listener mouse movement
	      Listener listenerMove = new Listener() {
	          public void handleEvent(Event event) {
	        	  int xk,xg,yk,yg;
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
	        		   xk=X;
	        		   xg=event.x;
	        		   yk=Y;
	        		   yg=event.y;
	        		   drawLineImage(event,xg,xk,yg,yk);
	        		   X = event.x;
				       Y = event.y;
	   		    	 	}
	        	   }
	          }
	    };
	    canvas.addListener(SWT.MouseDown, listenerDown);
	    canvas.addListener(SWT.MouseMove, listenerMove);
	    canvas.pack();
	    
	    //buttons for drawing
	    
	    
	    
	    final Button button = new Button(outerGroup2, SWT.PUSH);
	    button.setText("pencil");
	    button.setBackground(new Color(255,0,0));
	    button.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				mouse = true;
				bleistift = !bleistift;
				if(bleistift)
					button.setBackground(new Color(0,255,0));
				else
					{save();
					canvas.redraw();
					button.setBackground(new Color(255,0,0));
					}
					}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				bleistift = !bleistift;
			}
});
	    
	    final Button buttonViereck = new Button(outerGroup2, SWT.PUSH);
	    buttonViereck.setText("square");
	    buttonViereck.setBackground(new Color(255,0,0));
	    buttonViereck.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				mouse =  true;
				System.out.println("square");
				viereck = !viereck;
				if(viereck)
					buttonViereck.setBackground(new Color(0,255,0));
				else
					buttonViereck.setBackground(new Color(255,0,0));
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
			}
	    });
	    
	    final Button btnFViereck = new Button(outerGroup2, SWT.PUSH);
	    btnFViereck.setText("fill rectangle");
	    btnFViereck.setBackground(new Color(255,0,0));
	    btnFViereck.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				mouse =  true;
				// TODO Auto-generated method stub
				fviereck = !fviereck;
				if(fviereck)
					btnFViereck.setBackground(new Color(0,255,0));
				else
					{save();
					canvas.redraw();
					btnFViereck.setBackground(new Color(255,0,0));
					}
					}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				fviereck = !fviereck;
			}
});
	    
	    final Button buttonKreis = new Button(outerGroup2, SWT.PUSH);
	    buttonKreis.setText("circle");
	    buttonKreis.setBackground(new Color(255,0,0));
	    buttonKreis.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				System.out.println("Circle");
				mouse =  true;
				kreis = !kreis;
				if(kreis)
				{
					  buttonKreis.setBackground(new Color(0,255,0));
				}
				else
					 buttonKreis.setBackground(new Color(255,0,0));
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				
			}
	      });
	    
	    final Button buttonFKreis = new Button(outerGroup2, SWT.PUSH);
	    buttonFKreis.setText("fill circle");
	    buttonFKreis.setBackground(new Color(255,0,0));
	    buttonFKreis.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				System.out.println("FCircle");
				mouse =  true;
				fkreis = !fkreis;
				if(fkreis)
				{
					  buttonFKreis.setBackground(new Color(0,255,0));
				}
				else
					 buttonFKreis.setBackground(new Color(255,0,0));
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				
			}
	      });
	    
	    final Button buttonLine = new Button(outerGroup2, SWT.PUSH);
	    buttonLine.setText("line");
	    buttonLine.setBackground(new Color(255,0,0));
	    buttonLine.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				mouse =  true;
				// TODO Auto-generated method stub
				System.out.println("Circle");
				line = !line;
				if(line)
					buttonLine.setBackground(new Color(0,255,0));
				else
					buttonLine.setBackground(new Color(255,0,0));
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				
			}
	    });
	 
	    final Button buttonColor = new Button(outerGroup2, SWT.PUSH);
	    buttonColor.setText("color");
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
			    Image image = new Image(display,fileName);
   		    	drawImage(image);
   		    	mouse =  true;
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				
			}
	      });
	    
	    //menu
	    Menu menu, fileMenu, effectMenu, editMenu;
	    menu = new Menu(shell, SWT.BAR);
	    MenuItem fileItem = new MenuItem(menu, SWT.CASCADE);
	    fileItem.setText("File");
	    MenuItem effectItem = new MenuItem(menu, SWT.CASCADE);
	    effectItem.setText("Effects");
	    MenuItem editItem = new MenuItem(menu, SWT.CASCADE);
	    editItem.setText("Edit");
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
	    new MenuItem(fileMenu, SWT.SEPARATOR);
	    MenuItem pageSetupItem = new MenuItem(fileMenu, SWT.NONE);
	    pageSetupItem.setText("Page Setup...");
	    MenuItem printItem = new MenuItem(fileMenu, SWT.NONE);
	    printItem.setText("Print...");
	    new MenuItem(fileMenu, SWT.SEPARATOR);
	    MenuItem exitItem = new MenuItem(fileMenu, SWT.NONE);
	    exitItem.setText("Exit");
	    effectMenu = new Menu(menu);
	    effectItem.setMenu(effectMenu);
	    MenuItem negativItem = new MenuItem(effectMenu, SWT.NONE);
	    negativItem.setText("Negativ");
	    MenuItem grayscaleItem = new MenuItem(effectMenu, SWT.NONE);
	    grayscaleItem.setText("Grayscale");
	    editMenu = new Menu(menu);
	    editItem.setMenu(editMenu);
	    MenuItem backItem = new MenuItem(editMenu, SWT.NONE);
	    backItem.setText("Back");
	    MenuItem fontItem = new MenuItem(editMenu, SWT.NONE);
	    fontItem.setText("Font");
	    shell.setMenuBar(menu);
	    
	    newItem.addSelectionListener(new MenuItemListener());
	    saveItem.addSelectionListener(new MenuItemListener());
	    openItem.addSelectionListener(new MenuItemListener());
	    exitItem.addSelectionListener(new MenuItemListener());
	    negativItem.addSelectionListener(new MenuItemListener());
	    grayscaleItem.addSelectionListener(new MenuItemListener());
	    backItem.addSelectionListener(new MenuItemListener());
	    scrolledComposite.pack();
	  
	    shell.pack();
	    shell.open();
	    while (!shell.isDisposed()) {
	      if (!display.readAndDispatch()) {
	        display.sleep();
	      }
	    }
	    display.dispose();
}
 

//menu 
class MenuItemListener extends SelectionAdapter {
	  public void widgetSelected(SelectionEvent event) {
	  if (((MenuItem) event.widget).getText().equals("New")) {
	  NewDialog nd = new NewDialog();
	  ps = nd.show(display);
	  canvas.setSize(ps.getX(), ps.getY());
	  canvas.setBounds(0, 0, ps.getX(), ps.getY());
	  drawNewImage();
	  canvas.redraw();
	  }
	  else if (((MenuItem) event.widget).getText().equals("Save")) {
		  System.out.println("save");
		  FileDialog fd = new FileDialog(shell, SWT.SAVE);
	      fd.setText("Save as");
	      fd.setFilterPath("/");
	      String[] filterExt = { "*.png"};
	      fd.setFilterExtensions(filterExt);
	      String selected = fd.open();
	      System.out.println(selected);
		  saveAs(selected);
		  }
	  else if (((MenuItem) event.widget).getText().equals("Open...")) {
		  System.out.println("OpenDialog");
		  FileDialog fd = new FileDialog(shell, SWT.OPEN);
	      fd.setText("Open");
	      fd.setFilterPath("/");
	      String[] filterExt = { "*.png"};
	      fd.setFilterExtensions(filterExt);
	      String selected = fd.open();
	      System.out.println(selected);
	      Image image = new Image(display,selected);
   		    
		  drawImage(image);
	  }
	  
	  else if (((MenuItem) event.widget).getText().equals("Exit")) {
		  shell.dispose();
	  }
	  else if (((MenuItem) event.widget).getText().equals("Negativ")) {
		  System.out.println("negativ");
		  save();
		  Image drawable = new Image(display,fileName);
		  
		  Image j = inverse(drawable);
	  }
	  else if (((MenuItem) event.widget).getText().equals("Grayscale")) {
		  System.out.println("grayscale");
		  save();
		  Image drawable = new Image(display,"/home/jon/Bilder/swt.png");
		  Image j = grayscale(drawable);
	  }
	  else if (((MenuItem) event.widget).getText().equals("Back")) {
		  System.out.println("back");
		  if(zaehler>0)
		  {
			  zaehler--;
			  String selected = fileNameSource+Integer.toString(zaehler)+".png";
			  Image image = new Image(display,selected);
	   		    
			  drawImage(image);
		  }
	  }
	 }
	  }
}