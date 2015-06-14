package presenter;

import java.util.Observable;
import java.util.Observer;
import Messages.MessageType;
import view.View;
import model.Model;

/**
 * The Class Presenter.
 */
public class Presenter implements Observer
{
	
	/** The model. */
	private Model m;
	
	/** The view. */
	private View v;
	
	/**
	 * Instantiates a new presenter.
	 *
	 * @param m the m
	 * @param v the v
	 */
	public Presenter(Model m, View v) 
	{
		super();
		this.m = m;
		this.v = v;
		v.setCommands("generateMaze",new GenerateMaze());
		v.setCommands("displayMaze",new DisplayMaze());
		v.setCommands("solveMaze",new SolveMaze());
		v.setCommands("exit",new Exit());
	}

	/**
	 * update for observer
	 */
	@SuppressWarnings("incomplete-switch")
	@Override
	public void update(Observable o, Object arg) 
	{
		if (o==v) //if its view
		{
			MessageType n = (MessageType)arg;
			switch (n)
			{
			case GotCommand:
				Command c = v.getUserCommand();
				String Name = v.getTheName();
				c.doCommand(Name);
				break;
			case OpenFileS:
				String FnameS = v.gettingFileName();
				m.AskToLoad(FnameS);
				break;
			case OpenFileC:
				String FnameC = v.gettingFileName();
				if (m.loadCliProp(FnameC))
				{
					v.UpdateUser("The File Load Successfuly");	
				}
				else
				{
					v.UpdateUser("Error Loading The File");
				}
				break;
			case CONNECT:
				m.ConnectClient(v.getUserName());
				break;
			case EXIT:
				m.StopModel();
				v.exitProg();
				break;
			case chooseAg:
				m.SetUName(v.getUserName());
				break;
			}
		}
		else //its model
		{
			MessageType t = (MessageType)arg;
			switch (t)
			{
			case GenreatedMaze:
				String NewName = m.getSolv().getMessage();
				v.UpdateUser("The Maze"+ " " + NewName + " " + "is ready");
				break;
			case MazeSolved:
				String solo = m.getSolv().getMessage();
				v.UpdateUserInGame("The Solution for:" + " " + solo + " " + "is ready" );
				break;
			case FOpened:
				String er = m.getSolv().getMessage();
				if (er.equals("loaded"))
				{
					v.UpdateUser("The File Load Successfuly");	
				}
				else
				{
					v.UpdateUser("Error Loading The File");
				}
				break;
			case CONNECT:
				v.UpdateUser("Could Not Connect To The Server");
				break;
			case ConErr:
				v.UpdateUser("The UserName Is Already Exist Please Try Again");
				m.StopModelUN();
				break;
			case Success:
				v.SetuProb(true);
				v.UpdateUser("Connection Succeed");
				break;
			case servDisc:
				v.UpdateUser("You Have Been Disconected From The Server");
				v.SetuProb(false);
				v.SetConnect(false);
				break;
			case servDiscGA:
				v.UpdateUserInGame("You Have Been Disconected From The Server But You Can Still Play Offline In This Maze");
				v.SetuProb(false);
				v.SetConnect(false);
				break;
			}
		}
	}
	
	/**
	 * The Interface Command.
	 */
	public interface Command 
	{
		
		/**
		 * Do command.
		 *
		 * @param name the name
		 */
		void doCommand(String name);
	}
	
	/**
	 * The Class GenerateMaze.
	 */
	public class GenerateMaze implements Command
	{
		/**
		 * doing the generate maze
		 * 
		 * @param name of the maze
		 */
		@Override
		public void doCommand(String name) 
		{
			String[] res = name.split(" ");
			if (res.length<4 || (res[3].equals("Y")==false && res[3].equals("N")==false))
			{
				v.UpdateUser("Wrong Input Try Again");
			}
			else
			{
				m.AskGenerateM(name); //activate the method from the model
			}
		}
	}
	
	/**
	 * The Class DisplayMaze.
	 */
	public class DisplayMaze implements Command
	{

		/**
		 * Displaying maze do command
		 * 
		 * @param maze name
		 */
		@Override
		public void doCommand(String name) 
		{
			m.AskToDisplay(name); //activate the method from the model
			if (v.GetConnect())
			{
				if (m.getSolv().getMessage().equals("Error")==true)
				{
					v.UpdateUser("You Didnt Created Any Maze With That Name");
				}
				else
				{
					v.displayMaze(m.getSolv().getM().getM(),name);
				}
			}
		}
	}
	
	/**
	 * The Class SolveMaze.
	 */
	public class SolveMaze implements Command
	{

		/**
		 * do the solve maze command
		 * 
		 * @param maze name
		 */
		@Override
		public void doCommand(String name) 
		{
			m.AskForSol(v.gettingNewMaze(),name); //activate the method from the model
			if (v.GetConnect())
			{
				v.displaySolution(m.getSolv().getSol());
			}
		}
	}
	
	
	/**
	 * The Class Exit.
	 */
	public class Exit implements Command
	{
		/**
		 * exit program
		 */
		@Override
		public void doCommand(String name) 
		{
			m.setCommand("exit");
		}
	}
}
