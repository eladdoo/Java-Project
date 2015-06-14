package model;

import java.io.Serializable;
import algorithms.demo.MazeDomain;
import algorithms.search.Solution;

/**
 * The Class SolveProblem.
 */
public class SolveProblem implements Serializable
{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	
	/** The m. */
	private MazeDomain m;
	
	/** The solution. */
	private Solution sol;
	
	/** The message. */
	private String message;
	
	/**
	 * Instantiates a new solve problem.
	 */
	public SolveProblem()
	{
		super();
	}
	
	/**
	 * Gets the sol.
	 *
	 * @return the sol
	 */
	public Solution getSol()
	{
		return sol;
	}
	
	/**
	 * Sets the sol.
	 *
	 * @param sol the new sol
	 */
	public void setSol(Solution sol)
	{
		this.sol = sol;
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
	 * Gets the m.
	 *
	 * @return the m
	 */
	public MazeDomain getM() 
	{
		return m;
	}

	/**
	 * Sets the m.
	 *
	 * @param m the new m
	 */
	public void setM(MazeDomain m) 
	{
		this.m = m;
	}

	/**
	 * transforming the object into string
	 */
	@Override
	public String toString()
	{
		String str = "";
		if (this.m==null && this.sol==null)
		{
			str = str + this.message;
		}
		else
		{
			str = str + this.message + "!";
		}
		if (this.m!=null)
		{
			str = str + "M" + "!";
			if (m.isDiagonal()==true)
			{
				str = str + "Y" + "!";
			}
			else
			{
				str = str + "N" + "!";
			}
			str = str + this.m.getM().toString();
		}
		if (this.sol!=null)
		{
			str = str + "S" + "!" + this.sol.toString();
		}
		return str;
	}
	
}
