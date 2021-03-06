package ai.push.logic;

import java.util.List;

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
	
	/*
	 * Ruchy powsta�e przez przepchni�cie pionk�w przez g��wny ruch.
	 * Pierwszy element - ruch g��wny.
	 * Ostatni element - ruch ostatniego pionka w szeregu.
	 * allMoves.size() - liczba przesuni�tych pionk�w.
	 
	public Vector<Movement> allMoves;
	*/
	
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
	
	protected Transition() {}
	
	/**
	 * Konstruktor.
	 * @param beg
	 * Stan wej�ciowy planszy.
	 * @param mov
	 * Ruch do opisania.
	 */
	public Transition(Board beg,Movement mov)
	{
		in=beg;
		out=new Board(beg);
		out.execute(mov);
		
		mainMove=mov;
		/*allMoves=new Vector<Movement>();
		
		Vector<Field> chain = in.getChain(mov.origin, mov.angle);
		for (int i = 0; i < chain.size(); i++)
		{
			allMoves.add(new Movement(chain.get(i), mov.angle, mov.distance));
		}*/
	}
	
	public Transition(Transition transition) {
		this.in = new Board(transition.in);
		this.out = new Board(transition.in);
		Movement mov = new Movement(transition.mainMove);
		out.execute(mov);
		/*allMoves = new Vector<Movement>();

		Vector<Field> chain = in.getChain(mov.origin, mov.angle);
		for (int i = 0; i < chain.size(); i++) {
			allMoves.add(new Movement(chain.get(i), mov.angle, mov.distance));
		}
		*/
	}
	
	public Transition getCopyWithCustomIn(Board in) {
		Transition t = new Transition(this);
		t.in = in;
		return t;
	}
	
	/**
	 * Generuje mo�liwe ruchy dla sytuacji po wykanoniu aktualnie opisywanego.
	 * Mo�liwe jest wygenerowanie ruch�w dla dowolnego z graczy.
	 * @param id
	 * Identyfikator gracza dla kt�rego wyszukiwane s� dost�pne ruchy.
	 * @return
	 * Vector tranzycji opisuj�cych mo�liwe ruchy.
	 */
	public List<Transition> getNextGeneration(int id)
	{
		return out.generateTransitions(id);
	}
}
