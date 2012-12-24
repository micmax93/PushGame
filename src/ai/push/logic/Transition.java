package ai.push.logic;

import java.util.Vector;

/**
 * Zawiera wszelkie mo�liwe informacje o ruchu.
 * @author micmax93
 *
 */
public class Transition
{
	/**
	 * G��wny ruch, kt�ry zostaje wykonany.
	 * Kierunek i dystans ruchu mo�na otrzyma� z tego ruchu.
	 */
	public Movement mainMove;
	
	/**
	 * Ruchy powsta�e przez przepchni�cie pionk�w przez g��wny ruch.
	 * Pierwszy element - ruch g��wny.
	 * Ostatni element - ruch ostatniego pionka w szeregu.
	 * allMoves.size() - liczba przesuni�tych pionk�w.
	 */
	public Vector<Movement> allMoves;
	
	/**
	 * Stan planszy przed wykonaniem ruchu.
	 * Referencja na aktualny stan planszy.
	 */
	public Board in;
	
	/**
	 * Stan planszy po wykonaniu ruchu.
	 * Kopia stanu wej�ciowego z wykonanym ruchem.
	 */
	public Board out;
	
	/**
	 * Konstruktor.
	 * @param beg
	 * Stan wej�ciowy planszy.
	 * @param mov
	 * Ruch do opisania.
	 */
	Transition(Board beg,Movement mov)
	{
		in=beg;
		out=new Board(beg);
		out.execute(mov);
		
		mainMove=mov;
		allMoves=new Vector<Movement>();
		
		Vector<Field> chain = in.getChain(mov.origin, mov.angle);
		for (int i = 0; i < chain.size(); i++)
		{
			allMoves.add(new Movement(chain.get(i), mov.angle, mov.distance));
		}
	}
	
	/**
	 * Generuje mo�liwe ruchy dla sytuacji po wykanoniu aktualnie opisywanego.
	 * Mo�liwe jest wygenerowanie ruch�w dla dowolnego z graczy.
	 * @param id
	 * Identyfikator gracza dla kt�rego wyszukiwane s� dost�pne ruchy.
	 * @return
	 * Vector tranzycji opisuj�cych mo�liwe ruchy.
	 */
	public Vector<Transition> getNextGeneration(int id)
	{
		return out.generateTransitions(id);
	}
}
