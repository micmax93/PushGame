package ai.push.logic;

import java.util.Vector;


/**
 * Klasa abstrakcyjna implementuj�ca podstawow� integracje AI z gr�.
 * Uruchamiana jest jako osobny w�tek.
 * @author micmax93
 */
abstract public class AbstractAI extends Thread
{
	protected Logic game;
	protected Vector<Transition> list;
	protected Movement result;
	int delay;
	/**
	 * Konstruktor incjalizuj�cy AI.
	 * @param logic
	 * Referencja na logik� gry.
	 */
	AbstractAI(Logic logic)
	{
		super();
		game=logic;
		delay=1000;
	}
	
	/**
	 * Ustawienie op�nienia w mili sekundach.
	 * @param d
	 */
	public void setDelay(int d)
	{
		if(d>=0)
		{
			delay=d;
		}
	}
	
	/**
	 * Algorytm kt�ry powinien wybra� optymalny ruch i umie�ci� go w result.
	 */
	abstract protected void algorithm();
	
	/**
	 * G��wna funkcja w�tku.
	 * Generuje mo�liwe ruchy oraz uruchamia algorytm.
	 * Nast�pnie zleca wykonanie ruchu oraz ko�czy swoj� tur�.
	 * Nie ma konieczno�ci przeci��aniatej cz�ci klasy.  
	 */
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
