package model;

import java.io.Serializable;

import algorithms.mazeGenerators.Maze;

/**
 * The Class Problem.
 */
public class Problem implements Serializable
{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	
	/** The command. */
	private String command;
	
	/** The name. */
	private String name;
	
	/** The m. */
	private Maze m;
	
	/** The Fname. */
	private String Fname;
	
	/** The User. */
	private String User;
	
	/**
	 * Instantiates a new problem.
	 */
	public Problem()
	{
		super();
	}
	
	/**
	 * Instantiates a new problem.
	 *
	 * @param command the command
	 * @param name the name
	 * @param m the m
	 * @param Fname the fname
	 * @param user the user
	 */
	public Problem(String command, String name, Maze m,String Fname,String user) 
	{
		super();
		this.command = command;
		this.name = name;
		this.m = m;
		this.Fname = Fname;
		this.User = user;
	}
	
	/**
	 * Gets the command.
	 *
	 * @return the command
	 */
	public String getCommand() 
	{
		return command;
	}
	
	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName()
	{
		return name;
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
	 * Gets the fname.
	 *
	 * @return the fname
	 */
	public String getFname()
	{
		return Fname;
	}
	
	/**
	 * transforming the object into string
	 */
	@Override
	public String toString()
	{
		String str = "";
		str = str + this.command + "]";
		str = str + this.name + "]";
		str = str + this.User + "]";
		if (this.Fname!=null)
		{
			str = str + "F" + "]" + this.Fname;
		}
		if (this.m!=null)
		{
			str = str + "M" + "]" + this.m.toString();
		}
		return str;
	}

	/**
	 * Sets the command.
	 *
	 * @param command the new command
	 */
	public void setCommand(String command)
	{
		this.command = command;
	}

	/**
	 * Sets the name.
	 *
	 * @param name the new name
	 */
	public void setName(String name)
	{
		this.name = name;
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
	 * Sets the fname.
	 *
	 * @param fname the new fname
	 */
	public void setFname(String fname)
	{
		Fname = fname;
	}

	/**
	 * Gets the user.
	 *
	 * @return the user
	 */
	public String getUser() 
	{
		return User;
	}

	/**
	 * Sets the user.
	 *
	 * @param user the new user
	 */
	public void setUser(String user) 
	{
		User = user;
	}
	
}
