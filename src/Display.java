import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Display extends JFrame implements MouseListener, MouseMotionListener, ActionListener
{
	private JPanel screen, draw, topics, score, prompt;
	private JLabel scoreboard;
	private Image top;
	private ArrayList<Square> questions;
	private Square selected;
	private SongList songlist;
	private JButton play;
	static int currentx, currenty, my_score;
	public Display ()
	{
		screen = new JPanel(new BorderLayout());
		prompt = new JPanel();
		draw = new DrawArea (780, 500);
		draw.addMouseListener(this);
		draw.addMouseMotionListener(this);
		topics = new TopBar (780, 100);
		score = new JPanel (); 
		selected = new Square (-1, -1, null);
	
		
		top = loadImage ("categories");
		
		scoreboard = new JLabel("Score: " + score);
		// Add questions
		songlist = new SongList();
		questions = new ArrayList<Square>();
		for (int c = 1; c <= 5; c++)
		{
			for (int d = 1; d <= 5; d++)
			{
				questions.add(new Square (c, d, songlist.getSong(2009 + d, c)));
			}
		}
		currentx = 0;
		currenty = 0;
		my_score = 0;
		scoreboard = new JLabel("Score: 0");
		score.add(scoreboard);
		
		screen.add(draw, "Center");
		screen.add(topics, "North");
		screen.add(score, "South");
		play = new JButton("Play");
		play.addActionListener(this);
		prompt.add(play);
		 
		setContentPane(screen);
		setTitle ("Song Jeopardy");
		setSize (780, 700);
		setResizable (false);
		setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo (null);  
		
		playSongs();
		
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
			Square active = findSquare (x, y);
			if (active.getEnabled())
			{
				currentx = x;
				currenty = y;
				String s = JOptionPane.showInputDialog(null, prompt, "Enter the name of the song:");
				if (s != null)
				{
					boolean correct = checkCorrect(active.getSong().getUrl(), s);
					if (correct)
					{
						JOptionPane.showMessageDialog(null, "You are correct!");
						my_score += active.getSong().getDifficulty()*100;
						scoreboard.setText("Score: " + my_score);
					}
					else
					{
						JOptionPane.showMessageDialog(null, "The Correct Song Name Is: " + active.getSong().getName());
					}
				}
			    play.setEnabled(true);
			    findSquare(x,y).disable(); 
			    
			    if (gameEnded())
			    	JOptionPane.showMessageDialog(null, "Game has ended. Your score is " + my_score + ". Please close the window now.");
			    	
			 }
		}
	}
	
	public boolean checkCorrect (String actual, String tried)
	{
		String temp = "";
		for (int c = 0; c < tried.length(); c++)
		{
			if (((tried.charAt(c) >= 'A' && tried.charAt(c) <= 'Z') || (tried.charAt(c) >= 'a' && tried.charAt(c) <= 'z'))
					|| (tried.charAt(c) >= '0' && tried.charAt(c) <= '9'))
				temp += Character.toString(tried.charAt(c));
		}
		return temp.equalsIgnoreCase(actual);
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
		try
		{
			if (!inBounds(x, y) || !(findSquare(x,y).equals(selected)))
			{
				if (selected.getRow() != -1)
					questions.get((selected.getRow()-1)*5 + (selected.getCol()-1)).unmouseOver();;
				
				if (inBounds(x,y))
				{
					selected = findSquare(x,y);
					findSquare(x, y).mouseOver();
				}
			}
		}
		catch (Exception except)
		{
		}
		repaint();
	}
	
	public void playSongs()
	{
		for (int c = 0; c < songlist.size(); c++)
		{
			String url = songlist.get(c).getUrl();
			try
		    {
		        Clip clip = AudioSystem.getClip();
		        clip.open(AudioSystem.getAudioInputStream(new File("res//" + url + ".wav")));
		        clip.start();
		    }
		    catch (Exception exc)
		    {
		        exc.printStackTrace(System.out);
		    }
		}
	}
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		play.setEnabled(false);
		Square active = findSquare (currentx, currenty);
		{
			String url = active.getSong().getUrl();
			try
		    {
		        Clip clip = AudioSystem.getClip();
		        clip.open(AudioSystem.getAudioInputStream(new File("res//" + url + ".wav")));
		        clip.start();
		    }
		    catch (Exception exc)
		    {
		        exc.printStackTrace(System.out);
		    }
		}
	}
	
	public boolean gameEnded()
	{
		boolean done = true;
		for (int c = 0; c < questions.size(); c++)
		{
			if (questions.get(c).getEnabled())
				done = false;
		}
		return done;
	}
	
}

	
