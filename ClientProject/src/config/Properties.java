package config;

import java.io.Serializable;


/**
 * The Class Properties.
 */
public class Properties implements Serializable
{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The port. */
	private int port;
	
	/** The ip. */
	private String ip;
	
	
	/**
	 * Instantiates a new properties.
	 */
	public Properties() 
	{
		super();
		this.port = 1234;
		this.ip = "127.0.0.1";
	}


	/**
	 * Gets the port.
	 *
	 * @return the port
	 */
	public int getPort() 
	{
		return port;
	}


	/**
	 * Sets the port.
	 *
	 * @param port the new port
	 */
	public void setPort(int port) 
	{
		this.port = port;
	}


	/**
	 * Gets the ip.
	 *
	 * @return the ip
	 */
	public String getIp() 
	{
		return ip;
	}


	/**
	 * Sets the ip.
	 *
	 * @param ip the new ip
	 */
	public void setIp(String ip) 
	{
		this.ip = ip;
	}

}
