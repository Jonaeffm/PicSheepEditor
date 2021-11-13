package picSheepEditor;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class MainWindow {
	static void display()
	{
		Display display = new Display();
		  // Ein einfaches neues (Standard-)Fenster erzeugen.
		  Shell shell = new Shell(display); 
		 
		  shell.setText("PicSheepEditor");
		  shell.open(); 
		  // Nach dem Anzeigen die Main-Event-Loop abfangen
		  while (!shell.isDisposed()) {
	            if (!display.readAndDispatch()) {
	                display.sleep();
	            }
	        }
	        display.dispose(); 
		
	}
}
