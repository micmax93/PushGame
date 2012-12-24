package ai.push.logic;

import ai.push.gui.GameWindow;

/**
 * Licznik do odmierzania sumarycznego czasu wykorzystanego przez przez gracza. 
 * @author micmax93
 */
public class Counter extends Thread
{
	int time[] = new int[3];
	int turn = 1;
	int count = 0;
	boolean active;
	GameWindow home;

	/**
	 * Zwraca czas dla danego gracza.
	 * @param i
	 * Identyfikator gracza.
	 * @return
	 * Czas w sekundach.
	 */
	public int getTime(int i)
	{
		return time[i];
	}

	/**
	 * Zwraca czas dla danego gracza.
	 * @param i
	 * Identyfikator gracza.
	 * @return
	 * Czas w postaci sformatowanej.
	 */
	public String getTimeString(int i)
	{
		return ((new Integer(time[i] / 60).toString()) + ":" + (new Integer(
				(time[i] / 10) % 6).toString()))
				+ (new Integer(time[i] % 10).toString());
	}
	
	/**
	 * Ustawienie aktualnego gracza.
	 * @param i
	 * Identyfikator gracza.
	 */
	synchronized public void setTurn(int i)
	{
		turn = i;
	}

	/**
	 * G³ówna pêtla w¹tku.
	 */
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
	
	/**
	 * Wyzerowanie licznika.
	 */
	public void reset()
	{
		count = 0;
		time[0] = 1;
		time[1] = 1;
		time[2] = 1;
		count = 1;
	}

	/**
	 * Utworzenie licznika dla danego okna gry.
	 * @param root
	 */
	public Counter(GameWindow root)
	{
		super();
		home = root;
		active = true;
		reset();
	}

	/**
	 * Zakoñczenie pracy w¹tku.
	 */
	synchronized public void finish()
	{
		active = false;
	}
}
