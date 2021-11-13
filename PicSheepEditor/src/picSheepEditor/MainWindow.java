package picSheepEditor;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class MainWindow {
 public void displayIt(){
	  final Display display = new Display();
	    final Shell shell = new Shell(display);
	    shell.setText("Canvas Example");
	    shell.setLayout(new FillLayout());

	    Canvas canvas = new Canvas(shell, SWT.NONE);

	    canvas.addPaintListener((PaintListener) new PaintListener() {
	      public void paintControl(PaintEvent e) {
	        Image image = new Image(display, "BILDDATEINAME");

	        e.gc.drawImage(image, 10, 10);

	        image.dispose();
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
