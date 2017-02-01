import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Square 
{
	public final int DISP_LENGTH = 156;
	public final int DISP_HEIGHT = 100;
	public final int GRID_LENGTH = 100;
	public final int GRID_HEIGHT = 64;
	private int value, row, col, year;
	private boolean enabled, moused;
	private Image scores = loadImage("scores");
	private Song song;
	
	public Square (int row, int col, Song song)
	{
		this.row = row;
		this.col = col;
		value = row*100;
		year = col + 2009;
		this.song = song;
		enabled = true;
		moused = false;
	}
	
	public Image loadImage (String url)
	{
		Image temp = null;
		try
	    {
			temp = ImageIO.read (new File ("Images/" + url + ".png")); // load file into Image object     
	    }
	    catch (IOException e) // Catch exception if image does not exist
	    {
	    }
		return temp;
	}
	
	public int getRow ()
	{
		return row;
	}
	
	public int getCol ()
	{
		return col;
	}
	
	public int getVal ()
	{
		return value;
	}
	
	public boolean getEnabled ()
	{
		return enabled;
	}
	
	public int getYear ()
	{
		return year;
	}
	
	public Song getSong()
	{
		return song;
	}
	
	public void disable ()
	{
		enabled = false;
	}
	
	public void mouseOver ()
	{
		if (enabled)
			moused = true;
	}
	
	public void unmouseOver ()
	{
		moused = false;
	}
	
	public boolean equals (Square other)
	{
		return (row == other.getRow() && col == other.getCol());
	}
	
	public void show (Graphics g)
	{
		g.drawImage(scores, (col-1)*DISP_LENGTH, (row-1)*DISP_HEIGHT, col*DISP_LENGTH, row*DISP_HEIGHT,
				0, (row-1)*GRID_HEIGHT, GRID_LENGTH, row*GRID_HEIGHT, null);
		if (moused)
			g.drawRect((col-1)*DISP_LENGTH, (row-1)*DISP_HEIGHT, DISP_LENGTH-1, DISP_HEIGHT-1);
	}
	
}
