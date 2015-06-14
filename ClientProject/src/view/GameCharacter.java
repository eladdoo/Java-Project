package view;

import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.graphics.Image;


/**
 * The Class GameCharacter.
 * the class to create and give properties to the game character
 */
public class GameCharacter 
{
	
	/** The height. */
	private int row,coll,width,height;
	
	/**
	 * Instantiates a new game character.
	 *
	 * @param x the x
	 * @param y the y
	 */
	public GameCharacter(int x,int y) 
	{
		this.row=x;
		this.coll=y;
	}
	
	/**
	 * Paint.
	 *
	 * @param e the e
	 * @param w the w
	 * @param h the h
	 */
	public void paint(PaintEvent e,int w,int h)
	{
		final Image gameChar = new Image(e.display,"images/MazeCharacter.png");
		e.gc.drawImage(gameChar,gameChar.getBounds().x,gameChar.getBounds().y,gameChar.getBounds().width,gameChar.getBounds().height, coll*w,row*h,w,h);
		gameChar.dispose();
	}

	/**
	 * Gets the row.
	 *
	 * @return the row
	 */
	public int getRow() 
	{
		return row;
	}

	/**
	 * Sets the row.
	 *
	 * @param row the new row
	 */
	public void setRow(int row) 
	{
		this.row = row;
	}

	/**
	 * Gets the coll.
	 *
	 * @return the coll
	 */
	public int getColl() 
	{
		return coll;
	}

	/**
	 * Sets the coll.
	 *
	 * @param coll the new coll
	 */
	public void setColl(int coll) 
	{
		this.coll = coll;
	}

	/**
	 * Gets the width.
	 *
	 * @return the width
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * Sets the width.
	 *
	 * @param width the new width
	 */
	public void setWidth(int width) {
		this.width = width;
	}

	/**
	 * Gets the height.
	 *
	 * @return the height
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * Sets the height.
	 *
	 * @param height the new height
	 */
	public void setHeight(int height) 
	{
		this.height = height;
	}
	
}
