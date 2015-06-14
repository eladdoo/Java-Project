package view;

import presenter.Presenter.Command;
import algorithms.mazeGenerators.Maze;
import algorithms.search.Solution;

/**
 * The Interface View.
 */
public interface View 
{
	
	/**
	 * Start.
	 */
	public void start();
	
	/**
	 * Sets the commands.
	 *
	 * @param coName the co name
	 * @param c the c
	 */
	public void setCommands(String coName,Command c);
	
	/**
	 * Gets the user command.
	 *
	 * @return the user command
	 */
	public Command getUserCommand();
	
	/**
	 * Display maze.
	 *
	 * @param m the m
	 * @param pMaze the maze
	 */
	public void displayMaze(Maze m,String pMaze);
	
	/**
	 * Display solution.
	 *
	 * @param s the s
	 */
	public void displaySolution(Solution s);
	
	/**
	 * Gets the the name.
	 *
	 * @return the the name
	 */
	public String getTheName();
	
	/**
	 * Exit prog.
	 */
	public void exitProg();
	
	/**
	 * Update user.
	 *
	 * @param update the update
	 */
	public void UpdateUser(String update);
	
	/**
	 * Gets the ting file name.
	 *
	 * @return the ting file name
	 */
	public String gettingFileName();
	
	/**
	 * Gets the ting new maze.
	 *
	 * @return the ting new maze
	 */
	public Maze gettingNewMaze();
	
	/**
	 * Update user in game.
	 *
	 * @param update the update
	 */
	public void UpdateUserInGame(String update);
	
	/**
	 * Sets the connect.
	 *
	 * @param flag the flag
	 */
	public void SetConnect(boolean flag);
	
	/**
	 * Gets the user name.
	 *
	 * @return the user name
	 */
	public String getUserName();
	
	/**
	 * Setu prob.
	 *
	 * @param flag the flag
	 */
	public void SetuProb(boolean flag);
	
	/**
	 * Gets the connect.
	 *
	 * @return true, if successful
	 */
	public boolean GetConnect();
}
