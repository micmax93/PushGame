package ai.push.logic.ai;

import java.util.Vector;

import ai.push.logic.Logic;
import ai.push.logic.Movement;
import ai.push.logic.Settings;
import ai.push.logic.Transition;
import ai.push.logic.oracle.Oracle;

/**
 * Klasa abstrakcyjna implementuj¹ca podstawow¹ integracje AI z gr¹. Uruchamiana
 * jest jako osobny w¹tek.
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
	 * Konstruktor incjalizuj¹cy AI.
	 * 
	 * @param logic
	 *            Referencja na logikê gry.
	 */
	AbstractAI(Logic logic, Oracle.PLAYER player) {
		super();
		game = logic;
		delay = Settings.delay;
		this.player = player;
		oracle = null;
	}

	/**
	 * Ustawienie opóŸnienia w milisekundach.
	 * 
	 * @param d
	 */
	public void setDelay(int d) {
		if (d >= 0) {
			delay = d;
		}
	}

	/**
	 * Algorytm który powinien wybraæ optymalny ruch i umieœciæ go w result.
	 */
	protected abstract void algorithm();

	/**
	 * G³ówna funkcja w¹tku. Generuje mo¿liwe ruchy oraz uruchamia algorytm.
	 * Nastêpnie zleca wykonanie ruchu oraz koñczy swoj¹ turê. Nie ma
	 * koniecznoœci przeci¹¿aniatej czêœci klasy.
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
