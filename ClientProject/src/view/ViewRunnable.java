package view;

/**
 * The Class ViewRunnable.
 * making CLI a thread
 */
public class ViewRunnable implements Runnable
{
	
	/** The v. */
	View v;
	
	/**
	 * Instantiates a new view runnable.
	 *
	 * @param v the v
	 */
	public ViewRunnable(View v) 
	{
		super();
		this.v = v;
	}

	/* 
	 * override run method from runnable
	 */
	@Override
	public void run()
	{
		v.start();
	}
}
