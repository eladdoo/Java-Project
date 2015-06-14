package model;

import algorithms.mazeGenerators.Maze;

/**
 * The Interface Model.
 */
public interface Model 
{
	/**
	 * Connect client.
	 *
	 * @param userName the user name
	 */
	public void ConnectClient(String userName);
	
	/**
	 * Ask generate m.
	 *
	 * @param name the name
	 */
	public void AskGenerateM(String name);
	
	/**
	 * Ask for sol.
	 *
	 * @param m the m
	 * @param name the name
	 */
	public void AskForSol(Maze m,String name);
	
	/**
	 * Ask to display.
	 *
	 * @param name the name
	 */
	public void AskToDisplay(String name);
	
	/**
	 * Ask to load.
	 *
	 * @param path the path
	 */
	public void AskToLoad(String path);
	
	/**
	 * Gets the solv.
	 *
	 * @return the solv
	 */
	public SolveProblem getSolv(); 

	/**
	 * Gets the command.
	 *
	 * @return the command
	 */
	public String getCommand();
	
	/**
	 * Sets the command.
	 *
	 * @param command the new command
	 */
	public void setCommand(String command); 

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName(); 

	/**
	 * Sets the name.
	 *
	 * @param name the new name
	 */
	public void setName(String name); 

	/**
	 * Gets the maze.
	 *
	 * @return the maze
	 */
	public Maze getMaze(); 

	/**
	 * Sets the maze.
	 *
	 * @param maze the new maze
	 */
	public void setMaze(Maze maze);
	
	/**
	 * Stop model.
	 */
	public void StopModel();
	
	/**
	 * Load cli prop.
	 *
	 * @param path the path
	 * @return true, if successful
	 */
	public boolean loadCliProp(String path);
	
	/**
	 * Gets the connection.
	 *
	 * @return the connection
	 */
	public boolean getConnection();
	
	/**
	 * Sets the u name.
	 *
	 * @param name the name
	 */
	public void SetUName(String name);
	
	/**
	 * Stop model un.
	 */
	public void StopModelUN();
}
