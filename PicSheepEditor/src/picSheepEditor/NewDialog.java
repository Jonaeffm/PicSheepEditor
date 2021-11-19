package picSheepEditor;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class NewDialog {
	public static void show(Display display1)
	{	//Display display1 = new Display();
    Shell shell = new Shell(display1);
    shell.setText("My First SWT GUI");
    shell.setSize(200, 100);

    GridLayout gridLayout = new GridLayout();
    gridLayout.numColumns = 2;
    shell.setLayout(gridLayout);
    
    Group outerGroup = new Group(shell, SWT.NONE);

    // Tell the group to stretch in all directions
    outerGroup.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
    outerGroup.setLayout(new GridLayout(3, true));
    outerGroup.setText("image size");
    
    Label helloText = new Label(outerGroup, SWT.CENTER);
    helloText.setText("Please insert picture size.");
    helloText.setBounds(47, 20, 100, 20);

    Text hightText = new Text(outerGroup, SWT.BORDER);

    //hightText.setLayoutData(layoutData);
    hightText.setText("hight");

    Text widthText = new Text(outerGroup, SWT.BORDER);

    //hightText.setLayoutData(layoutData);
    widthText.setText("width");
    
    shell.open();
    while (!shell.isDisposed()) {
      if (!display1.readAndDispatch())
        display1.sleep();
    }
    display1.dispose();
	}
	}
