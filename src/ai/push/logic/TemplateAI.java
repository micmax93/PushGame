package ai.push.logic;

import java.util.Random;
import java.util.Vector;



public class TemplateAI
{
	public Movement choseBest(Vector<Transition> list)
	{
//		try
//		{
//			Thread.sleep(1000);
//		}
//		catch (InterruptedException e)
//		{
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		return list.get(new Random().nextInt(list.size())).mainMove;
	}
}
