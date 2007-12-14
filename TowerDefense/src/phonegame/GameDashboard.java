/*
 * File: 	GameDashboard.java
 * Created: Jan 27, 2005
 */
package phonegame;

import java.util.Vector;
import java.io.IOException;

import javax.microedition.lcdui.*;

/**
 * This class visually represents the "dashboard" in you game. You can use this
 * dashboard to display all sorts of status information like, for example: the
 * amount of health, power, etc. The items on the dashboard may be text or
 * an image 
 * 
 * @author Tim Prijn, Richard Kettelerij, Paul Bergervoet, Richard Holleman
 * @version 2.0, October 11, 2005
 * @version 2.1, April 24, 2006
 * @version 3.0, November 17, 2006
 * @version 3.1, December 19, 2006
 */

public class GameDashboard
{

	private int backRed = 0, backGreen = 0, backBlue = 0;

	private int lineRed = 255, lineGreen = 255, lineBlue = 255;

	private int foreRed = 255, foreGreen = 255, foreBlue = 255;

	private int dashX = 0, dashY = 0;

	private int dashW = 0, dashH = 10;

	private int fontStyle;

	private boolean dotted = false;

	private Vector dashboardItems;

	/**
	 * Constructs an empty GameDashboard
	 */
	public GameDashboard()
	{
		dashboardItems = new Vector();
	}

	/**
	 * Adds a new text item to the dashboard that displays the specified textvalue. 
	 * An item consists of a label (the name of the item) and a value. Both are Strings. 
	 * The position of the item are the x,y coördinates. <br/> For example a text String: to
	 * show points, make the label "Points" and the value at start "Pts: 0". 
	 * Later on, you can change the image or text of an item.
	 * <br/>
	 * NOTE: The label will NOT be displayed, it serves as a key for lookup only!
	 * 
	 * @param label
	 *            the label of this item.
	 * @param value
	 *            the text value of this item 
	 * @param x
	 *            the horizontal position XXX coordinaten relatief???? Wat is ypos bij text???
	 * @param y
	 *            the vertical position
	 */
	public void addTextItem(String label, String value, int x, int y)
	{
		dashboardItems.addElement(new DashboardItem(false, label, value, x, y));
	}

	/**
	 * Adds a new text item to the dashboard that displays the specified textvalue. 
	 * An item consists of a label (the name of the item) and the path name of the image file. 
	 * Both are Strings. The position of the item are the x,y coördinates. 
	 * <br/>For example an image : to show the selected weapon, type is
	 * true, the label is "weapon" and the value is "/res/image.weapon1.png" for
	 * both the examples x and y are the position of the item. Later on, you can
	 * change the image or text of an item.
	 * <br/>
	 * NOTE: The label will NOT be displayed, it serves as a key for lookup only!
	 * 
	 * @param label
	 *            the label of this item.
	 * @param name
	 *            the name of the image 
	 * @param x
	 *            the horizontal position 
	 * @param y
	 *            the vertical position
	 */
	public void addImageItem(String label, String name, int x, int y)
	{
		dashboardItems.addElement(new DashboardItem(true, label, name, x, y));
	}

	/**
	 * Sets the new text value of a display-item on the dashboard. 
	 * It doesn't matter is the item was an image before, the contents of 
	 * the item will be set to text, so you can use a mix of texts and images.
	 * 
	 * If there is no display-item with the given label, nothing is changed
	 * 
	 * @param label
	 *            the label name of the item (serves as a 'key')
	 * @param newvalue
	 *            The new value of the item
	 */
	public void setItemText(String label, String newvalue)
	{
		int index = 0;
		while (index < dashboardItems.size())
		{
			if (((DashboardItem) (dashboardItems.elementAt(index))).getLabel()
					.equals(label))
			{
				((DashboardItem) (dashboardItems.elementAt(index))).setText(newvalue);
			}
			index++;
		}
	}

	/**
	 * Sets the new image of a display-item on the dashboard. 
	 * It doesn't matter is the item was a text before, the contents of 
	 * the item will be set to the image, so you can use a mix of texts and images.
	 * 
	 * If there is no display-item with the given label, nothing is changed
	 * 
	 * @param label
	 *            the label name of the item (serves as a 'key')
	 * @param newname
	 *            The pathname of the image
	 */
	public void setItemImage(String label, String newname)
	{
		int index = 0;
		while (index < dashboardItems.size())
		{
			if (((DashboardItem) (dashboardItems.elementAt(index))).getLabel()
					.equals(label))
			{
				((DashboardItem) (dashboardItems.elementAt(index))).setImage(newname);
			}
			index++;
		}
	}

	/**
	 * Sets the new position of a display-item on the dashboard. 
	 * If there is no
	 * display-item with the given label, nothing is changed
	 * 
	 * @param label
	 *            the label name of the item (serves as a 'key')
	 * @param x
	 *            the horizontal position
	 * @param y
	 *            the vertical position
	 */
	public void setDashboardItemposition(String label, int x, int y)
	{
		int index = 0;
		while (index < dashboardItems.size())
		{
			if (((DashboardItem) (dashboardItems.elementAt(index))).getLabel()
					.equals(label))
			{
				((DashboardItem) (dashboardItems.elementAt(index))).setPostion(
						x, y);
			}
			index++;
		}
	}

	/**
	 * Remove the item with the specified label If there is no display-item with
	 * the given label, nothing is changed
	 * 
	 * @param label
	 *            the label name of the item (serves as a 'key')
	 */
	public void deleteDashboardItem(String label)
	{

		int index = 0;
		boolean found = false;
		while (index < dashboardItems.size() && found == false)
		{
			if (((DashboardItem) (dashboardItems.elementAt(index))).getLabel()
					.equals(label))
			{
				found = true;
				dashboardItems.removeElementAt(index);
			}
			index++;
		}

	}

	/**
	 * Sets the background color of the dashboard. The default color is black.
	 * If you don't want to use a background color set the RGB values to -1
	 * (transparent)
	 * 
	 * @param red
	 *            the red RGB component
	 * @param blue
	 *            the blue RGB component
	 * @param green
	 *            the green RGB component
	 */
	public void setBackgroundColor(int red, int green, int blue)
	{
		backRed = red;
		backGreen = green;
		backBlue = blue;
	}

	/**
	 * Sets the color of the text that is displayed on the dashboard.
	 * 
	 * @param red
	 *            the red RGB component
	 * @param blue
	 *            the blue RGB component
	 * @param green
	 *            the green RGB component
	 */
	public void setForegroundColor(int red, int green, int blue)
	{
		foreRed = red;
		foreGreen = green;
		foreBlue = blue;
	}

	/**
	 * Sets the line color of the dashboard. The default color is black. If you
	 * don't want to use a line color set the RGB values to -1 (transparent)
	 * 
	 * @param red
	 *            the red RGB component
	 * @param blue
	 *            the blue RGB component
	 * @param green
	 *            the green RGB component
	 * @param dotted
	 *            true if the border should be drawn as a dotted line
	 */
	public void setLineColor(int red, int green, int blue, boolean dotted)
	{
		lineRed = red;
		lineGreen = green;
		lineBlue = blue;
		this.dotted = dotted;
	}

	/**
	 * Sets the font-properties of the text on the dashboard. You can switch
	 * on/off: Bold, italic and underline.
	 * 
	 * @param bold
	 *            true, if text should appear <b>bold </b>
	 * @param italic
	 *            true, if text should appear <i>italic </i>
	 * @param underline
	 *            true, if text should appear <u>underlined </u>
	 */
	public void setFont(boolean bold, boolean italic, boolean underline)
	{
		if (bold)
			fontStyle = Font.STYLE_BOLD;
		if (italic)
			fontStyle |= Font.STYLE_ITALIC;
		if (underline)
			fontStyle |= Font.STYLE_UNDERLINED;
	}

	/**
	 * Sets the position of the dashboard on the screen
	 * 
	 * @param x
	 *            the horizontal position
	 * @param y
	 *            the vertical position
	 */
	public void setPosition(int x, int y)
	{
		dashX = x;
		dashY = y;

	}

	/**
	 * Sets the dimensions of the dashboard
	 * 
	 * @param w
	 *            the width of the dashboard
	 * @param h
	 *            the height of the dashboard
	 */
	public void setSize(int w, int h)
	{
		dashW = w;
		dashH = h;
	}

	/**
	 * Draws the dashboard on the screen.
	 * <p>
	 * Note: If you want to create a more fancy dashboard you'll need to
	 * override this method with your own implementation.
	 * 
	 * @param g
	 *            the Graphics object of the GameWindow
	 */
	public void paint(Graphics g)
	{
		g.setClip(dashX, dashY, dashW, dashH);
		// draw background (if color are not equal to -1)
		if (backRed != -1 && backGreen != -1 && backGreen != -1)
		{
			g.setColor(backRed, backGreen, backBlue);
			g.fillRect(dashX, dashY, dashW, dashH);
		}

		if (dashboardItems.size() > 0)
		{
			int index = 0;
			while (index < dashboardItems.size())
			{
				((DashboardItem) (dashboardItems.elementAt(index))).paint(g);
				index++;
			}
		}

		// draw border (if color are not equal to -1)
		if (lineRed != -1 && lineGreen != -1 && lineBlue != -1)
		{
			if (dotted)
			{
				g.setStrokeStyle(Graphics.DOTTED);
			}
			g.setColor(lineRed, lineGreen, lineBlue);
			g.drawRect(dashX, dashY, dashW - 1, dashH - 1);
		}

		// reset styles
		g.setFont(Font.getDefaultFont());
		g.setStrokeStyle(Graphics.SOLID);
	}
	
	/**
	 * The class DashboardItem is only to store iteminformation the input is the type of the
	 * item, label, value and position. if typeofitem is false than the type is
	 * text, if true than the type is an image.
	 * 
	 * @author Richard Holleman
     * @version 1.0
	 */

	class DashboardItem
	{
		private boolean isImage;

		private String label;

		private String textvalue;
		
		private Image iconImage;

		private int itemX;

		private int itemY;

		/**
		 * Construct a DashboardItem.
		 * This item can be either an image or text. Items can change from text to image
		 * and back/
		 * 
		 * @param isImage boolean, true if an image
		 * @param label the identifying label of this item
		 * @param value the text value or pathname of the image
		 * @param x x-position of the item relative to the dashboard (0,0) is the upper left
		 * corner of the dashboard
		 * @param y y-position
		 */
		public DashboardItem(boolean isImage, String label, String value, int x, int y)
		{
			this.isImage = isImage;
			this.label = label;
			this.itemX = x;
			this.itemY = y;
			if ( isImage )
			{
			    setImage(value);
			} else
			{  
			    this.textvalue = value;
			}
		}

		/**
		 * Get the label of the item
		 * 
		 * @return label
		 */
		public String getLabel()
		{
			return label;
		}

		/**
		 * Set the new value of the item. 
		 * The type of the item will be set, so you can change a text item into an image item.
		 * 
		 * @param name
		 *            the (path) name of the image file
		 */
		public void setImage(String name)
		{
			try
			{
				iconImage = Image.createImage(name);
			} catch (IOException e)
			{
			    throw new GameException("The image " + name + " could not be found.");
			}
			isImage = true;
		}

		/**
		 * Set the new textvalue of the item.
		 * The type of the item will be set, so you can change an image item into an text item.
		 * 
		 * @param value
		 *            if type is text the value will be printed in the dashboard
		 */
		public void setText(String value)
		{
			this.textvalue = value;
			isImage = false;
		}

		/**
		 * Set the new position of the item
		 * 
		 * @param x
		 *            the horizontal position
		 * @param y
		 *            the vertical position
		 */
		public void setPostion(int x, int y)
		{
			this.itemX = x;
			this.itemY = y;
		}

		/**
		 * Paint this DashboardItem
		 * 
		 * @param g
		 *            the graphics toolkit
		 */
		public void paint(Graphics g)
		{
			if ( isImage )
			{
				g.drawImage(iconImage, itemX + dashX, itemY + dashY, Graphics.TOP | Graphics.LEFT);
			} else
			{
				g.setColor(foreRed, foreGreen, foreBlue);
				g.setFont(Font.getFont(Font.FACE_SYSTEM, fontStyle,
						Font.SIZE_SMALL));
				g.drawString(textvalue, itemX + dashX, itemY + dashY, Graphics.TOP
						| Graphics.LEFT);
	
			}

		}
	}	
}