package ai.push;
import java.awt.EventQueue;

import ai.push.gui.MainMenu;
import ai.push.logic.HashUtils;


public class Push
{

	public static void main(String[] args)
	{
		EventQueue.invokeLater(new Runnable()
		{

			@Override
			public void run()
			{
				HashUtils hu = new HashUtils(8);
				System.out.println(hu.checkUnique());
				new MainMenu();
			}
		});

	}

}