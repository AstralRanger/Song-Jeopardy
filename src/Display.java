import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Display extends JFrame
{
	JPanel screen, draw, topics, score;
	Image top;
	public Display ()
	{
		screen = new JPanel(new BorderLayout());
		draw = new DrawArea (900, 500);
		topics = new TopBar (900, 100);
		score = new JPanel (); 
		
		screen.add(draw, "Center");
		screen.add(topics, "North");
		screen.add(score, "East");
		top = loadImage ("categories");
		 
		setContentPane(screen);
		setTitle ("Song Jeopardy");
		setSize (1000, 600);
		setResizable (false);
		setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo (null);  
		
		repaint();
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
	
	class TopBar extends JPanel
    {
		public TopBar (int width, int height)
		{
		    this.setPreferredSize (new Dimension (width, height)); // size
		}
	
	
		public void paintComponent (Graphics g)
		{
			g.drawImage(top, 0, 0, 900, 100, null);
		}
    }
	
	class DrawArea extends JPanel
    {
		public DrawArea (int width, int height)
		{
		    this.setPreferredSize (new Dimension (width, height)); // size
		}
	
	
		public void paintComponent (Graphics g)
		{
		}
    }
}
