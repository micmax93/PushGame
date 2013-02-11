package ai.push.logic.ai;

import java.util.List;

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
	protected List<Transition> list;
	protected Movement result;
	protected int delay;
	protected boolean usingDelay=false;
	protected Oracle.PLAYER player;
	protected Oracle.PLAYER opponent;
	protected Oracle oracle;
	protected int maxDepth;

	/**
	 * Konstruktor incjalizuj�cy AI.
	 * 
	 * @param logic Referencja na logik� gry.
	 */
	AbstractAI(Logic logic, Oracle.PLAYER player) {
		super();
		game = logic;
		delay = Settings.delay;
		this.player = player;
		if (player == Oracle.PLAYER.PLAYER1)
			opponent = Oracle.PLAYER.PLAYER2;
		else
			opponent = Oracle.PLAYER.PLAYER1;
		oracle = null;
	}

	/**
	 * Ustawienie op�nienia w milisekundach.
	 * 
	 */
	public void setDelay(int d) {
		if (d >= 0) {
			delay = d;
		}
	}

	/**
	 * Ustawienie maksymalnej g��boko�ci dla algorytmu przeszukiwania. Mo�liwe
	 * tylko dla algorytm�w obs�uguj�cych takie ograniczenia.
	 * 
	 * @param depth
	 *            Maksymalna g��boko�� przeszukiwania (0 - przeszukiwanie a� do
	 *            wyczerpania zasob�w; > 0 - nowa g��boko��).
	 */
	public void setMaxDepth(int depth) {
		if (depth >= 0)
			this.maxDepth = depth;
	}

	/**
	 * Algorytm kt�ry powinien wybra� optymalny ruch i umie�ci� go w result.
	 */
	protected abstract void algorithm();

	/**
	 * G��wna funkcja w�tku. Generuje mo�liwe ruchy oraz uruchamia algorytm.
	 * Nast�pnie zleca wykonanie ruchu oraz ko�czy swoj� tur�. Nie ma
	 * konieczno�ci przeci��ania tej cz�ci klasy.
	 */
	public void run() {
		list = game.generateTransitions();
		algorithm();

		if ((usingDelay)&&(delay != 0)) {
			try {
				Thread.sleep(delay);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		game.executeMove(result);
		Runtime.getRuntime().gc();
		game.endTurn();
	}
}
