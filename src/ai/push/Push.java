package ai.push;
import java.awt.EventQueue;

import ai.push.gui.MainMenu;


public class Push
{

	public static void main(String[] args)
	{
		EventQueue.invokeLater(new Runnable()
		{

			@Override
			public void run()
			{
				new MainMenu();
			}
		});

	}

}