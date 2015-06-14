package view;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

/**
 * The Class InputDialog.
 * this is to comunicating with the user and getting the command and etc.
 */
public class InputDialog extends Dialog
{
	
	/** The message. */
	private String message;
	
	/** The input. */
	private String input;

	  /**
  	 * Instantiates a new input dialog.
  	 *
  	 * @param parent the parent
  	 */
  	public InputDialog(Shell parent) 
	  {
	    this(parent, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
	  }
	  
	  /**
  	 * Instantiates a new input dialog.
  	 *
  	 * @param parent the parent
  	 * @param style the style
  	 */
  	public InputDialog(Shell parent, int style)
	  {
		  super(parent, style);  
	  }

	  /**
  	 * Instantiates a new input dialog.
  	 *
  	 * @param parent the parent
  	 * @param style the style
  	 * @param Text the text
  	 * @param UserProp the user prop
  	 */
  	public InputDialog(Shell parent, int style, String Text, String UserProp) 
	  {
	    super(parent, style);
	    setText(Text);
	    setMessage(UserProp);
	  }

	  /**
  	 * Gets the message.
  	 *
  	 * @return the message
  	 */
  	public String getMessage()
	  {
	    return message;
	  }

	  /**
  	 * Sets the message.
  	 *
  	 * @param message the new message
  	 */
  	public void setMessage(String message) 
	  {
	    this.message = message;
	  }

	  /**
  	 * Gets the input.
  	 *
  	 * @return the input
  	 */
  	public String getInput() 
	  {
	    return input;
	  }

	  /**
  	 * Sets the input.
  	 *
  	 * @param input the new input
  	 */
  	public void setInput(String input) 
	  {
	    this.input = input;
	  }

	  /**
  	 * Open.
  	 *
  	 * @return the string
  	 */
  	public String open() 
	  {
	    Shell shell = new Shell(getParent(), getStyle());
	    shell.setText(getText());
	    createContents(shell);
	    shell.pack();
	    shell.open();
	    Display display = getParent().getDisplay();
	    while (!shell.isDisposed()) 
	    {
	      if (!display.readAndDispatch()) 
	      {
	        display.sleep();
	      }
	    }
	    return input;
	  }

	  /**
  	 * Creates the contents.
  	 *
  	 * @param shell the shell
  	 */
	  private void createContents(final Shell shell) 
	  {
	    shell.setLayout(new GridLayout(2, true));

	    Label label = new Label(shell, SWT.NONE);
	    label.setText(message);
	    GridData data = new GridData();
	    data.horizontalSpan = 2;
	    label.setLayoutData(data);

	    final Text text = new Text(shell, SWT.BORDER);
	    data = new GridData(GridData.FILL_HORIZONTAL);
	    data.horizontalSpan = 2;
	    text.setLayoutData(data);

	    Button ok = new Button(shell, SWT.PUSH);
	    ok.setText("OK");
	    data = new GridData(GridData.FILL_HORIZONTAL);
	    ok.setLayoutData(data);
	    ok.addSelectionListener(new SelectionAdapter() 
	    {
	      public void widgetSelected(SelectionEvent event) 
	      {
	        input = text.getText();
	        shell.close();
	      }
	    });

	    Button cancel = new Button(shell, SWT.PUSH);
	    cancel.setText("Cancel");
	    data = new GridData(GridData.FILL_HORIZONTAL);
	    cancel.setLayoutData(data);
	    cancel.addSelectionListener(new SelectionAdapter() 
	    {
	      public void widgetSelected(SelectionEvent event) 
	      {
	        input = null;
	        shell.close();
	      }
	    });

	    shell.setDefaultButton(ok);
	  }
}
