package ai.push.gui;

import javax.swing.JOptionPane;

public class Refresher extends Thread
{
	GameWindow window;
	boolean active;
	public Refresher(GameWindow win)
	{
		super();
		window=win;
		active=true;
	}
	public void run()
	{
		while(active)
		{
			if(window.checker.game.edited)
			{
				
				window.repaint();
			}
			int win=window.checker.game.winner;
			if (win > 0)
			{
				JOptionPane.showMessageDialog(null,
						"Gracz " + Integer.toString(win)
								+ " wygra³!!!");
				active=false;
			}
			try
			{
				sleep(200);
			}
			catch (InterruptedException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public void finish()
	{
		active=false;
	}

}
