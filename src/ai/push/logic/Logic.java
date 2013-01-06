package ai.push.logic;

import java.util.Vector;

import ai.push.logic.ai.AbstractAI;
import ai.push.logic.ai.AlphaBetaAI;
import ai.push.logic.ai.GreedyAI;
import ai.push.logic.oracle.Oracle;

public class Logic {
	AbstractAI ai1, ai2;
	final int size;
	/**
	 * Identyfikarot u�ytkownika kt�ry ma tera ruch.
	 */
	public int turn;
	Board board;
	/**
	 * Przedstawia czy zasz�y jakie� zmiany na planszy.
	 */
	public Boolean edited;
	/**
	 * Blokada u�ytkownika kiedy jest tura AI.
	 */
	public Boolean locked;
	/**
	 * Identyfikator zwyci�zcy, w trakcie gry 0.
	 */
	public int winner;

	/**
	 * Konstruktor inicjalizuj�cy podstawowe pola.
	 */
	public Logic() {
		this.size = Settings.size;
		board = new Board();
		turn = 2;
		edited = new Boolean(true);
		locked = new Boolean(false);
	}

	/**
	 * Pozwala na pobranie tablicy pionk�w.
	 * 
	 * @return tablica 2D int�w zawieraj�ca odpowiednik aktualnego stanu
	 *         planszy.
	 */
	public int[][] getTab() {
		return board.tab;
	}

	/**
	 * Zako�czenie tury.
	 */
	public void endTurn() {
		if(winner<0) {return;}
		locked = true;
		edited = true;
		turn = enemyID();
		winner = hasFinished();
		
		if (winner == 0) {
			if ((turn == 1) && (Settings.AI1)) {
//				ai1 = new RandomAI(this, Oracle.PLAYER.PLAYER1);
				ai1 = new GreedyAI(this, Oracle.PLAYER.PLAYER1);
//				ai1 = new AlphaBetaAI(this, Oracle.PLAYER.PLAYER1);
				ai1.start();
			}
			if ((turn == 2) && (Settings.AI2)) {
//				ai2 = new RandomAI(this, Oracle.PLAYER.PLAYER2);
				ai2 = new GreedyAI(this, Oracle.PLAYER.PLAYER2);
//				ai2 = new AlphaBetaAI(this, Oracle.PLAYER.PLAYER2);
				ai2.start();
			} else {
				locked = false;
			}
		}
	}

	/**
	 * Zwraca identyfikator przeciwnika.
	 */
	int enemyID() {
		if (turn == 1) {
			return 2;
		} else {
			return 1;
		}
	}

	/**
	 * Sprawdza czy kto� wygra�.
	 * 
	 * @return Identyfikator zwyci�zcy, je�eli brak to 0.
	 */
	int hasFinished() {
		boolean won1 = true, won2 = true;
		for (int row = 0; row < size; row++) {
			int beg = Settings.rowCount;
			int end = size - Settings.rowCount;
			for (int column = 0; column < size; column++) {
				if (row < beg) {
					if (board.tab[row][column] != 2) {
						won2 = false;
					}
				} else if (row >= end) {
					if (board.tab[row][column] != 1) {
						won1 = false;
					}
				}
			}
		}
		if (won1) {
			return 1;
		} else if (won2) {
			return 2;
		} else {
			return 0;
		}
	}

	/**
	 * Sprawdza czy z danego pola mozna wykona� ruch.
	 */
	public boolean isSelectable(int row, int column) {
		return (turn == board.tab[row][column]);
	}

	/**
	 * Wyszukuje list� p�l do kt�rych mo�na przej�� z danego pola.
	 * 
	 * @param origin
	 *            Pole �r�d��owe.
	 * @return Vector mo�liwych p�l docelowych.
	 */
	public Vector<Field> possibleDestinations(Field origin) {
		Vector<Movement> movs = board.possibleMoves(origin);
		Vector<Field> destinations = new Vector<Field>();
		for (int i = 0; i < movs.size(); i++) {
			destinations.add(movs.get(i).destination);
		}
		return destinations;
	}

	/**
	 * Realizuje ruch. Weryfikuje poprawno�� identyfikatora w�a�ciciela.
	 * 
	 * @param move
	 *            Ruch do wykonania.
	 */
	public boolean executeMove(Movement move) {
		if (board.getValue(move.origin) == turn) {
			return board.execute(move);
		}
		return false;
	}

	/**
	 * Generuje wszystkie wykonywalne ruchy dla aktualnie graj�cego gracza.
	 * 
	 * @return Vector tranzycji przedstawiaj�cy wszystkie wykonywalny ruchy.
	 */
	public Vector<Transition> generateTransitions() {
		return board.generateTransitions(turn);
	}
}