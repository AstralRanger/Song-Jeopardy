import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Display extends JFrame implements MouseListener, MouseMotionListener
{
	private JPanel screen, draw, topics, score, prompt;
	private Image top;
	private ArrayList<Square> questions;
	private Square selected;
	public Display ()
	{
		screen = new JPanel(new BorderLayout());
		prompt = new JPanel();
		draw = new DrawArea (780, 500);
		draw.addMouseListener(this);
		draw.addMouseMotionListener(this);
		topics = new TopBar (780, 100);
		score = new JPanel (); 
		selected = new Square (-1, -1);
	
		screen.add(draw, "Center");
		screen.add(topics, "North");
		screen.add(score, "South");
		top = loadImage ("categories");
		
		// Add questions
		questions = new ArrayList<Square>();
		for (int c = 1; c <= 5; c++)
		{
			for (int d = 1; d <= 5; d++)
			{
				questions.add(new Square (c, d));
			}
		}
		 
		setContentPane(screen);
		setTitle ("Song Jeopardy");
		setSize (780, 700);
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
			g.drawImage(top, 0, 0, 780, 100, null);
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
			for (int c = 0; c < questions.size(); c++)
				questions.get(c).show(g);
		}
    }
	
	public Square findSquare (int x, int y)
	{
		int row, col;
		if (inBounds(x, y))
		{
			row = y/100 + 1;
			col = x/158 + 1;
		}
		else
		{
			row = 1;
			col = 1;
		}
		return questions.get((row-1)*5 + (col-1));
	}
	
	public boolean inBounds (int x, int y)
	{
		return ((x >= 0 && x <= 780) && (y >= 0 && y <= 500));
	}

	@Override
	public void mouseClicked(MouseEvent e) 
	{
		int x = e.getX();
		int y = e.getY();
		if (inBounds (x, y))
		{
			@SuppressWarnings("unused")
			String s = JOptionPane.showInputDialog(null, prompt, "Enter the name of the song:");
		}
	}

	@Override
	public void mouseEntered(MouseEvent e)
	{
		
	}

	@Override
	public void mouseExited(MouseEvent e) 
	{

	}

	@Override
	public void mousePressed(MouseEvent e) 
	{
		
	}

	@Override
	public void mouseReleased(MouseEvent e) 
	{
		
	}

	@Override
	public void mouseDragged(MouseEvent e) 
	{
		
	}

	@Override
	public void mouseMoved(MouseEvent e) 
	{
		int x = e.getX();
		int y = e.getY();
		if (!(findSquare(x,y).equals(selected)))
		{
			if (selected.getRow() != -1)
				questions.get((selected.getRow()-1)*5 + (selected.getCol()-1)).unmouseOver();;
			
			if (inBounds(x,y))
			{
				selected = findSquare(x,y);
				findSquare(x, y).mouseOver();
			}
		}
		repaint();
	}
}
