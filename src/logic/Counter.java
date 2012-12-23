package logic;

import gui.GameWindow;

public class Counter extends Thread
{
	int time[] = new int[3];
	int turn = 1;
	int count = 0;
	boolean active;
	GameWindow home;

	public int getTime(int i)
	{
		return time[i];
	}

	public String getTimeString(int i)
	{
		return ((new Integer(time[i] / 60).toString()) + ":" + (new Integer(
				(time[i] / 10) % 6).toString()))
				+ (new Integer(time[i] % 10).toString());
	}

	synchronized public void setTurn(int i)
	{
		turn = i;
	}

	public void run()
	{
		while (active)
		{
			try
			{
				sleep(1000);
			}
			catch (InterruptedException e)
			{
			}
			time[turn] += count;
			home.time1.setValue(getTime(1));
			home.time2.setValue(getTime(2));
			home.timer[1].setText(getTimeString(1));
			home.timer[3].setText(getTimeString(2));
		}
	}

	public void reset()
	{
		count = 0;
		time[0] = 1;
		time[1] = 1;
		time[2] = 1;
		count = 1;
	}

	public Counter(GameWindow root)
	{
		super();
		home = root;
		active = true;
		reset();
	}

	synchronized public void finish()
	{
		active = false;
	}
}
