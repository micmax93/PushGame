package ai.push.logic.ai;

import java.util.List;

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
	protected List<Transition> list;
	protected Movement result;
	protected int delay;
	protected boolean usingDelay=false;
	protected Oracle.PLAYER player;
	protected Oracle.PLAYER opponent;
	protected Oracle oracle;
	protected int maxDepth;

	/**
	 * Konstruktor incjalizuj¹cy AI.
	 * 
	 * @param logic Referencja na logikê gry.
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
	 * Ustawienie opóŸnienia w milisekundach.
	 * 
	 */
	public void setDelay(int d) {
		if (d >= 0) {
			delay = d;
		}
	}

	/**
	 * Ustawienie maksymalnej g³êbokoœci dla algorytmu przeszukiwania. Mo¿liwe
	 * tylko dla algorytmów obs³uguj¹cych takie ograniczenia.
	 * 
	 * @param depth
	 *            Maksymalna g³êbokoœæ przeszukiwania (0 - przeszukiwanie a¿ do
	 *            wyczerpania zasobów; > 0 - nowa g³êbokoœæ).
	 */
	public void setMaxDepth(int depth) {
		if (depth >= 0)
			this.maxDepth = depth;
	}

	/**
	 * Algorytm który powinien wybraæ optymalny ruch i umieœciæ go w result.
	 */
	protected abstract void algorithm();

	/**
	 * G³ówna funkcja w¹tku. Generuje mo¿liwe ruchy oraz uruchamia algorytm.
	 * Nastêpnie zleca wykonanie ruchu oraz koñczy swoj¹ turê. Nie ma
	 * koniecznoœci przeci¹¿ania tej czêœci klasy.
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
