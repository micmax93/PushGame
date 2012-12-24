package ai.push.logic;

import java.util.Vector;



abstract public class AbstractAI extends Thread
{
	protected Logic game;
	protected Vector<Transition> list;
	protected Movement result;
	int delay;
	AbstractAI(Logic logic)
	{
		super();
		game=logic;
		delay=1000;
	}
	public void setDelay(int d)
	{
		if(d>=0)
		{
			delay=d;
		}
	}
	abstract protected void algorithm();
	
	public void run()
	{
		list=game.generateTransitions();
		algorithm();
		
		try
		{
			Thread.sleep(delay);
		}
		catch (InterruptedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		game.executeMove(result);
		game.endTurn();
	}
}
