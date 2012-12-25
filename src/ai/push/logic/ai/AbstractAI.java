package ai.push.logic.ai;

import java.util.Vector;

import ai.push.logic.Logic;
import ai.push.logic.Movement;
import ai.push.logic.Settings;
import ai.push.logic.Transition;
import ai.push.logic.oracle.Oracle;

/**
 * Klasa abstrakcyjna implementuj�ca podstawow� integracje AI z gr�. Uruchamiana
 * jest jako osobny w�tek.
 * 
 * @author micmax93
 */
public abstract class AbstractAI extends Thread {
	protected Logic game;
	protected Vector<Transition> list;
	protected Movement result;
	protected int delay;
	protected Oracle.PLAYER player;
	protected Oracle oracle;

	/*
	 * Konstruktor incjalizuj�cy AI.
	 * 
	 * @param logic
	 *            Referencja na logik� gry.
	 */
	AbstractAI(Logic logic, Oracle.PLAYER player) {
		super();
		game = logic;
		delay = Settings.delay;
		this.player = player;
		oracle = null;
	}

	/**
	 * Ustawienie op�nienia w milisekundach.
	 * 
	 * @param d
	 */
	public void setDelay(int d) {
		if (d >= 0) {
			delay = d;
		}
	}

	/**
	 * Algorytm kt�ry powinien wybra� optymalny ruch i umie�ci� go w result.
	 */
	protected abstract void algorithm();

	/**
	 * G��wna funkcja w�tku. Generuje mo�liwe ruchy oraz uruchamia algorytm.
	 * Nast�pnie zleca wykonanie ruchu oraz ko�czy swoj� tur�. Nie ma
	 * konieczno�ci przeci��aniatej cz�ci klasy.
	 */
	public void run() {
		list = game.generateTransitions();
		algorithm();

		try {
			Thread.sleep(delay);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		game.executeMove(result);
		game.endTurn();
	}
}
