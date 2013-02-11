package ai.push.logic;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import ai.push.logic.ai.AIFactory;
import ai.push.logic.ai.AbstractAI;
import ai.push.logic.oracle.Oracle;

public class Logic {
	AbstractAI ai1, ai2;
	final byte size;
	/**
	 * Identyfikarot u¿ytkownika który ma tera ruch.
	 */
	public byte turn;
	protected Board board;
	/**
	 * Przedstawia czy zasz³y jakieœ zmiany na planszy.
	 */
	public Boolean edited;
	/**
	 * Blokada u¿ytkownika kiedy jest tura AI.
	 */
	public Boolean locked;
	/**
	 * Identyfikator zwyciêzcy, w trakcie gry 0.
	 */
	public byte winner;

	/**
	 * Konstruktor inicjalizuj¹cy podstawowe pola.
	 */
	public Logic() {
		this.size = (byte) Settings.size;
		board = new Board();
		turn = 2;
		edited = new Boolean(true);
		locked = new Boolean(true);
	}

	/**
	 * Pozwala na pobranie tablicy pionków.
	 * 
	 * @return tablica 2D intów zawieraj¹ca odpowiednik aktualnego stanu
	 *         planszy.
	 */
	public byte[][] getTab() {
		return board.tab;
	}

	/**
	 * Zakoñczenie tury.
	 */
	public void endTurn() {
		if(winner<0) {return;}
		locked = true;
		if(pause>=0)
		{
			pause++;
			return;
		}
		edited = true;
		turn = enemyID();
		winner = hasFinished();
		
		if (winner == 0) {
			if ((turn == 1) && (Settings.AI1)) {
				ai1 = AIFactory.getAI(this, Oracle.PLAYER.PLAYER1);
				System.out.println("P1:" + ai1.getClass().getSimpleName() + " @ " + new Date() );
				ai1.start();
			}
			else if ((turn == 2) && (Settings.AI2)) {
				ai2 = AIFactory.getAI(this, Oracle.PLAYER.PLAYER2);
				System.out.println("P2:" + ai2.getClass().getSimpleName() + " @ " + new Date() );
				ai2.start();
			} else {
				locked = false;
			}
		}
	}

	/**
	 * Zwraca identyfikator przeciwnika.
	 */
	public byte enemyID() {
		if (turn == 1) {
			return 2;
		} else {
			return 1;
		}
	}

	/**
	 * Sprawdza czy ktoœ wygra³.
	 * 
	 * @return Identyfikator zwyciêzcy, je¿eli brak to 0.
	 */
	public byte hasFinished() {
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
	 * Sprawdza czy z danego pola mozna wykonaæ ruch.
	 */
	public boolean isSelectable(int row, int column) {
		return (turn == board.tab[row][column]);
	}

	/**
	 * Wyszukuje listê pól do których mo¿na przejœæ z danego pola.
	 * 
	 * @param origin
	 *            Pole Ÿród³³owe.
	 * @return Lista mo¿liwych pól docelowych.
	 */
	public List<Field> possibleDestinations(Field origin) {
		List<Movement> movs = board.possibleMoves(origin);
		List<Field> destinations = new ArrayList<Field>();
		for (int i = 0; i < movs.size(); i++) {
			destinations.add(movs.get(i).destination);
		}
		return destinations;
	}

	/**
	 * Realizuje ruch. Weryfikuje poprawnoœæ identyfikatora w³aœciciela.
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
	 * Generuje wszystkie wykonywalne ruchy dla aktualnie graj¹cego gracza.
	 * 
	 * @return Lista tranzycji przedstawiaj¹cy wszystkie wykonywalny ruchy.
	 */
	public List<Transition> generateTransitions() {
		return board.generateTransitions(turn);
	}
	
	int pause=-1;
	public void holdGame()
	{
		pause++;
		locked=true;
	}
	public void resumeGame()
	{
		if(pause>0)
		{
			pause=-1;
			endTurn();
		}
		else
		{
			locked=false;
			pause=-1;
		}
	}
	
	public void saveToFile(String fname) throws IOException
	{
		ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fname,false));
	    out.writeObject(turn);
	    out.writeObject(board);
	    out.close();
	}
	
	public void loadFromFile(String fname) throws FileNotFoundException, IOException, ClassNotFoundException
	{
		ObjectInputStream in = new ObjectInputStream(new FileInputStream(fname));
	    turn = (byte) in.readObject();
	    board = (Board) in.readObject();
	    in.close();
	    Settings.size=board.size;
	    Settings.rowCount=board.rowcount;
	}
}