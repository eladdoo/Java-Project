package config;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;


/**
 * The Class HandelProperties.
 */
public class HandelProperties 
{
	
	/** The path. */
	private String path;
	
	/**
	 * Instantiates a new handel properties.
	 */
	public HandelProperties()
	{
		super();
		path = "ClinPro.xml";
	}

	/**
	 * Instantiates a new handel properties.
	 *
	 * @param path the path
	 */
	public HandelProperties(String path) 
	{
		super();
		this.path = path;
	}
	
	/**
	 * Read properties.
	 *
	 * @return the properties
	 */
	public Properties ReadProperties()
	{
		File f = new File(path);
		if (f.length()==0)
		{
			return null;
		}
		else
		{
			XMLDecoder decoder = null;
			try 
			{
				decoder = new XMLDecoder(new FileInputStream(path));
				Properties p = (Properties) decoder.readObject();
				return p;
			} 
			catch (FileNotFoundException e) 
			{
				e.printStackTrace();
				Properties p = new Properties();
				return p;
			}
			finally
			{
				decoder.close();
			}
		}
	}
	
	/**
	 * Save properties.
	 *
	 * @param p the p
	 */
	public void SaveProperties(Properties p)
	{
		XMLEncoder encoder = null;
		try 
		{
			encoder = new XMLEncoder(new FileOutputStream(path));
			encoder.writeObject(p);
		} 
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		}
		finally
		{
			encoder.close();
		}
	}
	
}
