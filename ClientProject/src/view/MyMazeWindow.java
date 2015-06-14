package view;

import java.util.HashMap;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.MessageBox;

import presenter.Presenter.Command;
import Messages.MessageType;
import algorithms.mazeGenerators.Maze;
import algorithms.search.Solution;

/**
 * The Class MyMazeWindow.
 * the main window of the game 
 */
public class MyMazeWindow extends BasicWindow implements View
{
	
	/** The message box. */
	private MessageBox messageBox = new MessageBox(shell,SWT.ICON_INFORMATION | SWT.OK);
	
	/** The Maze name. */
	private String MazeName;
	
	/** The file name. */
	private String fileName;
	
	/** The The command. */
	private String TheCommand;
	
	/** The update maze. */
	private Maze updateMaze;
	
	/** The hint counter. */
	private int hintCounter=0;
	
	/** The pl. */
	private PlayTheMaze pl;
	
	/** The can p. */
	private boolean canP = true;
	/** The h. */
	private HashMap<String,Command> h = new HashMap<String,Command>();
	
	/** The connected. */
	private boolean connected=false;
	
	/** The User name. */
	private String UserName;
	
	/** The u prob. */
	private boolean uProb=false;
	
	/**
	 * Instantiates a new my maze window.
	 *
	 * @param title the title
	 * @param width the width
	 * @param height the height
	 */
	public MyMazeWindow(String title, int width, int height)
	{
		super(title, width, height);
	}

	/*
	 * starts the main loop
	 */
	/* (non-Javadoc)
	 * @see view.View#start()
	 */
	@Override
	public void start() 
	{
		this.run();
	}

	/* 
	 * setting user commands
	 */
	/* (non-Javadoc)
	 * @see view.View#setCommands(java.lang.String, presenter.Presenter.Command)
	 */
	@Override
	public void setCommands(String coName, Command c)
	{
		h.put(coName,c);
	}

	/* 
	 * getting user commands
	 */
	/* (non-Javadoc)
	 * @see view.View#getUserCommand()
	 */
	@Override
	public Command getUserCommand() 
	{
		return h.get(TheCommand);
	}

	/* 
	 * displaying the maze in the new window
	 */
	/* (non-Javadoc)
	 * @see view.View#displayMaze(algorithms.mazeGenerators.Maze, java.lang.String)
	 */
	@Override
	public void displayMaze(Maze m,String pMaze) 
	{
		pl = new PlayTheMaze(this);
		this.MazeName = pMaze;
		pl.setM(m);
		this.canP = false;
		pl.Open();
	}

	/* 
	 * displaying the solution for the user and solve the maze
	 */
	/* (non-Javadoc)
	 * @see view.View#displaySolution(algorithms.search.Solution)
	 */
	@Override
	public void displaySolution(Solution s)
	{
		pl.setNewSol(s);
	}

	/* 
	 * get the name of the maze
	 */
	/* (non-Javadoc)
	 * @see view.View#getTheName()
	 */
	@Override
	public String getTheName() 
	{
		return this.MazeName;
	}

	/* 
	 * exit the game
	 */
	/* (non-Javadoc)
	 * @see view.View#exitProg()
	 */
	@Override
	public void exitProg() 
	{
		this.UpdateUser("Thank You For Playing");
		shell.close();
	}

	/* 
	 * update user with messages
	 */
	/* (non-Javadoc)
	 * @see view.View#UpdateUser(java.lang.String)
	 */
	@Override
	public void UpdateUser(String update) 
	{
		messageBox.setText("Information");
	    messageBox.setMessage(update);
	    messageBox.open();
	}

	/* 
	 * the building of the shell
	 */
	/* (non-Javadoc)
	 * @see view.BasicWindow#initWidgets()
	 */
	@Override
	void initWidgets()
	{
		/**
		 * creating the shell properties and background 
		 */
		shell.setLayout(new GridLayout(2,false));
		final Image menu = new Image(display,"images/menu1.jpeg");
		shell.setBackgroundImage(menu);
		shell.setBounds(menu.getBounds().x,menu.getBounds().y, menu.getBounds().width,menu.getBounds().height);
		Button con = new Button(shell,SWT.PUSH);
		con.setText("Connect To Server");
		con.setLayoutData(new GridData(SWT.FILL,SWT.NONE,true,false,2,1));
		con.addSelectionListener(new SelectionListener()
		{

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {}

			@Override
			public void widgetSelected(SelectionEvent arg0) 
			{
				InputDialog dlg = new InputDialog(shell,SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL,"UserName Choose","Please Enter A UserName");
				UserName = dlg.open();
				TheCommand = "connect";
				setChanged();
				notifyObservers(MessageType.CONNECT);
				if (uProb==true)
				{
					con.setEnabled(false);
					connected = true;
				}
			}
			
		});
		/**
		 * creating the new maze button 
		 */
		Button nm = new Button(shell,SWT.PUSH);
		nm.setText("New Maze");
		nm.setLayoutData(new GridData(SWT.FILL,SWT.FILL,true,false,2,1));
		nm.addSelectionListener(new SelectionListener()
		{

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {}

			@Override
			public void widgetSelected(SelectionEvent arg0) 
			{
				if (connected)
				{
					InputDialog dlg = new InputDialog(shell,SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL,"Maze Defenition","Enter Name(Space)num rows(Space)num colls(Space)diagonal Y/N");
					MazeName = dlg.open();
					if (MazeName==null)
					{
						UpdateUser("You Didnt Entered The Properties Please Try Again");
					}
					else
					{
						TheCommand = "generateMaze";
						setChanged();
						notifyObservers(MessageType.GotCommand);
					}
					if (uProb==false)
					{
						con.setEnabled(true);
					}
				}
				else
				{
					UpdateUser("Please Connect To The Server First");
				}
			}
			
		});
		/**
		 * creating the play the maze button which allow the user to play the maze he created
		 */
		Button pm = new Button(shell,SWT.PUSH);
		pm.setText("Play Maze");
		pm.setLayoutData(new GridData(SWT.FILL,SWT.FILL,true,false,2,1));
		pm.addSelectionListener(new SelectionListener()
		{

			@Override
			public void widgetDefaultSelected(SelectionEvent paramSelectionEvent) {}

			@Override
			public void widgetSelected(SelectionEvent paramSelectionEvent) 
			{
				if (connected)
				{
					InputDialog dlg = new InputDialog(shell,SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL,"Choose Maze","Enter The Name Of The Maze");
					MazeName = dlg.open();
					if (MazeName==null)
					{
						UpdateUser("You Didnt Entered The Name Of The Maze You Want To Play");
					}
					else if (canP==true)
					{
						TheCommand = "displayMaze";
						setChanged();
						notifyObservers(MessageType.GotCommand);
						if (uProb==false)
						{
							con.setEnabled(true);
						}
					}
					else
					{
						UpdateUser("You Already Playing In A Maze Please Close It"); //user can play only in one maze
					}
				}
				else
				{
					UpdateUser("Please Connect To The Server First");
				}
			}
			
		});
		/**
		 * open and load user new properties
		 */
		Button prs = new Button(shell,SWT.PUSH);
		prs.setText("Open Properties Server");
		prs.setLayoutData(new GridData(SWT.FILL,SWT.NONE,true,false,2,1));
		prs.addSelectionListener(new SelectionListener()
		{
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {}

			@Override
			public void widgetSelected(SelectionEvent arg0) 
			{
				if (connected)
				{
					FileDialog fd = new FileDialog(shell,SWT.OPEN);
					fd.setText("open");
					fd.setFilterPath("");
					String[] filterExt = {"*.xml"};
					fd.setFilterExtensions(filterExt);
					fileName = fd.open();
					if (fileName!=null)
					{
						setChanged();
						notifyObservers(MessageType.OpenFileS);
						if (uProb==false)
						{
							con.setEnabled(true);
						}
					}
				}
				else
				{
					UpdateUser("Please Connect To The Server First");
				}
			}
		});
		Button prc = new Button(shell,SWT.PUSH);
		prc.setText("Open Properties Client");
		prc.setLayoutData(new GridData(SWT.FILL,SWT.NONE,true,false,2,1));
		prc.addSelectionListener(new SelectionListener()
		{
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {}

			@Override
			public void widgetSelected(SelectionEvent arg0) 
			{
				FileDialog fd = new FileDialog(shell,SWT.OPEN);
				fd.setText("open");
				fd.setFilterPath("");
				String[] filterExt = {"*.xml"};
				fd.setFilterExtensions(filterExt);
				fileName = fd.open();
				if (fileName!=null)
				{
					setChanged();
					notifyObservers(MessageType.OpenFileC);
				}
			}
		});
		/**
		 * exit the game button
		 */
		Button ex = new Button(shell,SWT.PUSH);
		ex.setText("EXIT");
		ex.setLayoutData(new GridData(SWT.FILL,SWT.FILL,true,false,2,1));
		ex.addSelectionListener(new SelectionListener()
		{
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {}

			@Override
			public void widgetSelected(SelectionEvent arg0) 
			{
				menu.dispose();
				setChanged();
				notifyObservers(MessageType.EXIT);
			}
		});
	}
	
	/* 
	 * getting the file name
	 */
	/* (non-Javadoc)
	 * @see view.View#gettingFileName()
	 */
	@Override
	public String gettingFileName()
	{
		return this.fileName;
	}
	
	/**
	 * Sets the change.
	 */
	public void SetChange()
	{
		this.setChanged();
	}
	
	/**
	 * Sets the the command.
	 *
	 * @param theCommand the new the command
	 */
	public void setTheCommand(String theCommand) 
	{
		TheCommand = theCommand;
	}

	/**
	 * Gets the hint counter.
	 *
	 * @return the hint counter
	 */
	public int getHintCounter() 
	{
		return hintCounter;
	}

	/**
	 * Sets the hint counter.
	 *
	 * @param hintCounter the new hint counter
	 */
	public void setHintCounter(int hintCounter) 
	{
		this.hintCounter = hintCounter;
	}

	/**
	 * Sets the can p.
	 *
	 * @param canP the new can p
	 */
	public void setCanP(boolean canP) 
	{
		this.canP = canP;
	}

	/**
	 * Sets the update maze.
	 *
	 * @param updateMaze the new update maze
	 */
	public void setUpdateMaze(Maze updateMaze) 
	{
		this.updateMaze = updateMaze;
	}

	/* 
	 * getting the new maze after the game character moved
	 */
	@Override
	public Maze gettingNewMaze()
	{
		return this.updateMaze;
	}
	
	/* 
	 * update the user inside the maze shell
	 */
	@Override
	public void UpdateUserInGame(String update)
	{
		pl.UpdateUser(update);
	}
	
	/**
	 * set connection.
	 *
	 * @param flag the flag
	 */
	@Override
	public void SetConnect(boolean flag)
	{
		this.connected = flag;
	}
	
	/**
	 * get the user name.
	 *
	 * @return the user name
	 */
	@Override
	public String getUserName()
	{
		return this.UserName;
	}
	
	/**
	 * set flag for connection button.
	 *
	 * @param flag the flag
	 */
	@Override
	public void SetuProb(boolean flag)
	{
		this.uProb = flag;
	}
	
	/**
	 * get connection status
	 */
	@Override
	public boolean GetConnect()
	{
		return this.connected;
	}

}
