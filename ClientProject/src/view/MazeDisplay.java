package view;

import java.util.Timer;
import java.util.TimerTask;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import algorithms.mazeGenerators.Maze;
import algorithms.search.Action;
import algorithms.search.ActuallState;
import algorithms.search.Solution;


/**
 * The Class MazeDisplay.
 * this class displaying the maze for the user to play
 */
public class MazeDisplay extends Canvas
{
	
	/** The m. */
	private Maze m;
	
	/** The ch. */
	private GameCharacter ch;
	
	/** The pt. */
	private PlayTheMaze pt;
	
	/** The timer. */
	private Timer timer;
	
	/** The my task. */
	private TimerTask myTask;
	
	/** The count action. */
	private int countAction;
	
	/** The Solving. */
	private boolean Solving=false;
	/** The width of cell. */
	private int w;
	/** The height of cell. */
	private int h;

	/**
	 * Instantiates a new maze display.
	 *
	 * @param parent the parent
	 * @param style the style
	 * @param me the me
	 * @param pt the pt
	 */
	public MazeDisplay(Composite parent, int style,Maze me,PlayTheMaze pt) 
	{
		super(parent, style | SWT.DOUBLE_BUFFERED);
		this.m = me;
		this.pt = pt;
		setBackground(new Color(null, 255, 255, 255));
		ch = new GameCharacter(0,0);
		
		/**
		 * paint listener to draw the maze,game character and goal images
		 */
		this.addPaintListener(new PaintListener()
		{

			@Override
			public void paintControl(PaintEvent e) 
			{
				e.gc.setForeground(new Color(null,0,0,0));
				e.gc.setBackground(new Color(null,0,0,0));
				int width=getSize().x;
				int height=getSize().y;
				final Image BackGrond = new Image(e.display,"images/gameBackground.jpg");
				e.gc.drawImage(BackGrond,0,0,BackGrond.getBounds().width,BackGrond.getBounds().height,0,0,width,height);
				w = width/m.getMaze()[0].length;
				h = height/m.getMaze().length;
				ch.setWidth(w);
				ch.setHeight(h);
				e.gc.setLineWidth(5);
				for(int i=0;i<m.getMaze().length;i++)
				{
					for(int j=0;j<m.getMaze()[i].length;j++)
					{
						int x=(j)*w;
				        int y=(i)*h;
				        int x2 = (j +1)*w;
				        int y2 = (i + 1) * h;
				        if (m.getCell(i,j).isHasDownWall()==true)
				        {
				        	e.gc.drawLine(x,y2,x2,y2);		        
				        }
				        if (m.getCell(i,j).isHasRightWall()==true)
				        {
				        	e.gc.drawLine(x2,y,x2, y2);
				        }
					}
				}
				for (int i=0;i<m.getMaze().length;i++)
				{
					int y=(i)*h;
					int y2 = (i + 1) * h;
					e.gc.drawLine(0,y,0, y2);		
					int x = width-3;
					e.gc.drawLine(x,y,x, y2);
				}
				for (int j=0;j<m.getMaze()[0].length;j++)
				{
					int x=(j + 1)*w;
					int x2 = (j + 2)*w;
					e.gc.drawLine(x,0,x2,0);
					int y = height-3;
					e.gc.drawLine(x,y,x2,y);
				}
				ch.paint(e, w, h);
				final Image TresPic = new Image(e.display,"images/TrsPic.png");
				e.gc.drawImage(TresPic,TresPic.getBounds().x,TresPic.getBounds().y,TresPic.getBounds().width,TresPic.getBounds().height,m.getGoal().getColl()*w,m.getGoal().getRow()*h,w,h);
				TresPic.dispose();
				BackGrond.dispose();
			}
			
		});
		
		/**
		 * key listener for moving the game character in the maze
		 */
		this.addKeyListener(new KeyListener()
		{
			@Override
			public void keyPressed(KeyEvent e) 
			{
				if (e.keyCode == SWT.ARROW_DOWN)
				{
					if (ch.getRow()!=m.getRows()-1 && m.getCell(ch.getRow(),ch.getColl()).isHasDownWall()==false)
					{
						moveChar("down");
					}
				}
				else if (e.keyCode == SWT.ARROW_LEFT)
				{
					if (ch.getColl()!=0 && m.getCell(ch.getRow(),ch.getColl()-1).isHasRightWall()==false)
					{
						moveChar("left");
					}
				}
				else if (e.keyCode == SWT.ARROW_RIGHT)
				{
					if (ch.getColl()!=m.getColls()-1 && m.getCell(ch.getRow(),ch.getColl()).isHasRightWall()==false)
					{
						moveChar("right");
					}
				}
				else
				{
					if (ch.getRow()!=0 && m.getCell(ch.getRow()-1,ch.getColl()).isHasDownWall()==false)
					{
						moveChar("up");
					}
				}
				if (Solving==true)
				{
					pt.getMy().setCanP(true);
					pt.getShell().close();
				}
				else if (WinGame()==true)
				{
					pt.getMy().setCanP(true);
					pt.getShell().close();
				}
			}
			@Override
			public void keyReleased(KeyEvent paramKeyEvent) {}
		});
		
		/**
		 * mouse listener for clickes and move the game character
		 */
		
		Listener mouseListener = new Listener(){

			boolean flag;
			Point p;
			
			@Override
			public void handleEvent(Event e) {
				switch (e.type) 
				{
				case SWT.MouseDown:
					int coll = e.x/w;
					int row = e.y/h;
					if (row==ch.getRow() && coll==ch.getColl())
					{
						p = new Point(e.x,e.y);
						flag = true;
					}
					break;
				case SWT.MouseMove:
					if(flag)
					{
						if(e.x < p.x - w)//left
						{	
							p.x = e.x;
							p.y = e.y;
							if (ch.getColl()!=0 && m.getCell(ch.getRow(),ch.getColl()-1).isHasRightWall()==false)
							{
								moveChar("left");
							}
						}
						else if (e.x > p.x + w)//right
						{
							p.x = e.x;
							p.y = e.y;
							if (ch.getColl()!=m.getColls()-1 && m.getCell(ch.getRow(),ch.getColl()).isHasRightWall()==false)
							{
								moveChar("right");
							}
						}
						else if (e.y < p.y - h)//up
						{
							p.x = e.x;
							p.y = e.y;
							if (ch.getRow()!=0 && m.getCell(ch.getRow()-1,ch.getColl()).isHasDownWall()==false)
							{
								moveChar("up");
							}
						}
						else if (e.y > p.y + h)//down
						{
							p.x = e.x;
							p.y = e.y;
							if (ch.getRow()!=m.getRows()-1 && m.getCell(ch.getRow(),ch.getColl()).isHasDownWall()==false)
							{
								moveChar("down");
							}
						}	
					}
					break;
				case SWT.MouseUp:
					flag = false;
					break;
				}
				
			}
			
		};
		
		addListener( SWT.MouseDown, mouseListener);
		addListener( SWT.MouseMove, mouseListener);
		addListener( SWT.MouseUp, mouseListener);		
	}

	/**
	 * Gets the ch.
	 *
	 * @return the ch
	 */
	public GameCharacter getCh() 
	{
		return ch;
	}
	
	/**
	 * Win game.
	 *
	 * @return true, if successful
	 */
	public boolean WinGame()
	{
		ActuallState character = new ActuallState(ch.getRow(),ch.getColl());
		if (character.getRow()==m.getGoal().getRow() && character.getColl()==m.getGoal().getColl())
		{
			pt.UpdateUser("You Win!!!");
			return true;
		}
		return false;
	}
	
	/**
	 * Solve the maze.
	 * solving the maze and moving it to the goal place
	 *
	 * @param s the s
	 */
	public void SolveTheMaze(Solution s)
	{
		countAction = 0;
		this.myTask = new TimerTask()
		{
			@Override
			public void run()
			{
				getDisplay().syncExec(new Runnable()
				{

					@Override
					public void run() 
					{
						Action a = s.getMoves().get(countAction);
						if (a.getMove().equals("go right"))
						{
							int coll = ch.getColl()+1;
							ch.setColl(coll);
							MazeDisplay.this.redraw();
						}
						else if (a.getMove().equals("go down"))
						{
							int row = ch.getRow()+1;
							ch.setRow(row);
							MazeDisplay.this.redraw();
						}
						else if (a.getMove().equals("go left"))
						{
							int coll = ch.getColl()-1;
							ch.setColl(coll);
							MazeDisplay.this.redraw();
						}
						else if (a.getMove().equals("go up"))
						{
							int row = ch.getRow()-1;
							ch.setRow(row);
							MazeDisplay.this.redraw();
						}
						else if (a.getMove().equals("go down and right"))
						{
							int row = ch.getRow()+1;
							int coll = ch.getColl()+1;
							ch.setRow(row);
							ch.setColl(coll);
							MazeDisplay.this.redraw();
						}
						else if (a.getMove().equals("go down and left"))
						{
							int row = ch.getRow()+1;
							int coll = ch.getColl()-1;
							ch.setRow(row);
							ch.setColl(coll);
							MazeDisplay.this.redraw();
						}
						else if (a.getMove().equals("go up and right"))
						{
							int row = ch.getRow()-1;
							int coll = ch.getColl()+1;
							ch.setRow(row);
							ch.setColl(coll);
							MazeDisplay.this.redraw();
						}
						else if (a.getMove().equals("go up and left"))
						{
							int row = ch.getRow()-1;
							int coll = ch.getColl()-1;
							ch.setRow(row);
							ch.setColl(coll);
							MazeDisplay.this.redraw();
						}
						countAction++;
						if (countAction==s.getMoves().size())
						{
							StopTimers();
							WinGame();
							Solving = true;
						}
					}
					
				});
			}
		};
		timer = new Timer();
		timer.scheduleAtFixedRate(myTask,0,500);
	}
	
	/**
	 * Stop timers.
	 */
	public void StopTimers()
	{
		if (this.myTask!=null)
		{
			this.myTask.cancel();
		}
		if (this.timer!=null)
		{
			this.timer.cancel();
		}
	}
	
	/**
	 * move char in the maze method (update the cell for the charecter)
	 */
	public void moveChar(String side)
	{
		if (side.equals("left"))
		{
			int coll = ch.getColl()-1;
			ch.setColl(coll);
			MazeDisplay.this.redraw();
		}
		else if (side.equals("right"))
		{
			int coll = ch.getColl()+1;
			ch.setColl(coll);
			MazeDisplay.this.redraw();
		}
		else if (side.equals("up"))
		{
			int row = ch.getRow()-1;
			ch.setRow(row);
			MazeDisplay.this.redraw();
		}
		else if (side.equals("down"))
		{
			int row = ch.getRow()+1;
			ch.setRow(row);
			MazeDisplay.this.redraw();
		}
	}
}
