package view;

import java.util.Observable;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;


/**
 * The Class BasicWindow.
 * class to extends from that run the main loop 
 */
public abstract class BasicWindow extends Observable implements Runnable
{
	
	/** The display. */
	protected Display display;
	
	/** The shell. */
	protected Shell shell;
	
	/**
	 * Instantiates a new basic window.
	 *
	 * @param title the title
	 * @param width the width
	 * @param height the height
	 */
	public BasicWindow(String title, int width, int height) 
	{
		display=new Display();
		shell=new Shell(display);
		shell.setText(title);
		shell.setSize(width,height);
	}
	
	/**
	 * Inits the widgets.
	 */
	abstract void initWidgets();
	
	/* 
	 * the run method which runs the main loop
	 */
	@Override
	public void run() 
	{
		initWidgets();
		shell.open();
		// main event loop
		 while(!shell.isDisposed()){ // while window isn't closed

		    // 1. read events, put then in a queue.
		    // 2. dispatch the assigned listener
		    if(!display.readAndDispatch()){ 	// if the queue is empty
		       display.sleep(); 			// sleep until an event occurs 
		    }

		 } // shell is disposed

		 display.dispose(); // dispose OS components
	}
}
