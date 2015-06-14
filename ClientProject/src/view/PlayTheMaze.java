package view;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import Messages.MessageType;
import algorithms.mazeGenerators.Maze;
import algorithms.search.ActuallState;
import algorithms.search.Solution;


/**
 * The Class PlayTheMaze.
 * play the maze the user created
 */
public class PlayTheMaze
{
	
	/** The shell. */
	private Shell shell;
	
	/** The new sol. */
	private Solution newSol;
	
	/** The Last sol. */
	private Solution LastSol;
	
	/** The m. */
	private Maze m;
	
	/** The my. */
	private MyMazeWindow my;
	
	/** The message box. */
	private MessageBox messageBox;
	
	/** The last c. */
	private int lastR,lastC;

	/**
	 * Instantiates a new play the maze.
	 *
	 * @param mz the mz
	 */
	public PlayTheMaze(MyMazeWindow mz)
	{
		shell = new Shell();
		my = mz;
		this.messageBox = new MessageBox(shell,SWT.ICON_INFORMATION | SWT.OK);
	}

	/**
	 * Open.
	 * open the new shell for the user to play the maze
	 */
	void Open() 
	{
		shell.setText("Play Game");
		shell.setLayout(new GridLayout(2,false));
		/**
		 * creating the maze display canvas
		 */
		final MazeDisplay maze=new MazeDisplay(shell, SWT.BORDER,m,this);
		maze.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true,true,1,3));
		/**
		 * the solve maze button which solving the maze for the user
		 */
		Button sm = new Button(shell,SWT.PUSH);
		sm.setText("Solve Maze");
		sm.setLayoutData(new GridData(SWT.FILL,SWT.FILL,false,false,1,1));
		sm.addSelectionListener(new SelectionListener()
		{
			@Override
			public void widgetSelected(SelectionEvent paramSelectionEvent) 
			{
				Maze temp = newStat(maze.getCh().getRow(),maze.getCh().getColl());
				my.setUpdateMaze(temp);
				my.setTheCommand("solveMaze");
				my.SetChange();
				my.notifyObservers(MessageType.GotCommand);
				if (my.GetConnect())
				{
					maze.SolveTheMaze(newSol);
					my.setHintCounter(0);
					sm.setEnabled(false);
				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent paramSelectionEvent) {}	
		});
		/**
		 * hint button which give a hint to the user
		 */
		Button hint = new Button(shell,SWT.PUSH);
		hint.setText("Get Hint");
		hint.setLayoutData(new GridData(SWT.FILL,SWT.NONE,false,false,1,1));
		hint.addSelectionListener(new SelectionListener()
		{
			@Override
			public void widgetSelected(SelectionEvent paramSelectionEvent) 
			{
				int count = my.getHintCounter();
				Maze temp = newStat(maze.getCh().getRow(),maze.getCh().getColl());
				if (newSol!=null)
				{
					LastSol = newSol;
				}
				my.setUpdateMaze(temp);
				my.setTheCommand("solveMaze");
				my.SetChange();
				my.notifyObservers(MessageType.GotCommand);
				if (my.GetConnect())
				{
					if (LastSol!=null)
					{
						if (newSol.equals(LastSol)==false)
						{
							count = 0;
							String move = newSol.getMoves().get(count).getMove();
							UpdateUser("You Need To:" + move);
						}
						else if (lastR==maze.getCh().getRow() && lastC==maze.getCh().getColl())
						{
							count = 0;
							String move = newSol.getMoves().get(count).getMove();
							UpdateUser("You Need To:" + move);
						}
						else
						{
							String move = newSol.getMoves().get(count).getMove();
							UpdateUser("You Need To:" + move);
						}
					}
					else
					{
						String move = newSol.getMoves().get(count).getMove();
						UpdateUser("You Need To:" + move);
					}
					count++;
					my.setHintCounter(count);
					lastR = maze.getCh().getRow();
					lastC = maze.getCh().getColl();
				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent paramSelectionEvent) {}
		});
		/**
		 * exit the maze game
		 */
		Button exit = new Button(shell,SWT.PUSH);
		exit.setText("Exit");
		exit.setLayoutData(new GridData(SWT.FILL,SWT.NONE,false,false,1,1));
		exit.addSelectionListener(new SelectionListener()
		{

			@Override
			public void widgetSelected(SelectionEvent paramSelectionEvent) 
			{
				my.setHintCounter(0);
				my.setCanP(true);
				shell.close();
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent paramSelectionEvent) {}
			
		});
		shell.open();
	}

	/**
	 * Gets the new sol.
	 *
	 * @return the new sol
	 */
	public Solution getNewSol() 
	{
		return newSol;
	}

	/**
	 * Sets the new sol.
	 *
	 * @param newSol the new new sol
	 */
	public void setNewSol(Solution newSol) 
	{
		this.newSol = newSol;
	}

	/**
	 * Gets the m.
	 *
	 * @return the m
	 */
	public Maze getM() 
	{
		return m;
	}

	/**
	 * Sets the m.
	 *
	 * @param m the new m
	 */
	public void setM(Maze m) 
	{
		this.m = m;
	}

	/**
	 * Gets the my.
	 *
	 * @return the my
	 */
	public MyMazeWindow getMy() 
	{
		return my;
	}
	
	/**
	 * Update user.
	 *
	 * @param update the update
	 */
	public void UpdateUser(String update) 
	{
		messageBox.setText("Information");
	    messageBox.setMessage(update);
	    messageBox.open();
	}

	/**
	 * Gets the shell.
	 *
	 * @return the shell
	 */
	public Shell getShell() {
		return shell;
	}

	/**
	 * Sets the shell.
	 *
	 * @param shell the new shell
	 */
	public void setShell(Shell shell) {
		this.shell = shell;
	}
	
	/**
	 * New stat.
	 *
	 * @param row the row
	 * @param coll the coll
	 * @return the maze
	 */
	public Maze newStat(int row,int coll)
	{
		Maze temp = new Maze();
		temp.setMaze(m.getMaze());
		temp.setStart(new ActuallState(row,coll));
		temp.setGoal(m.getGoal());
		temp.setColls(m.getColls());
		temp.setRows(m.getRows());
		return temp;
	}
	

}
