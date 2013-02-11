package ai.push.logic;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.io.Serializable;

public class Board implements Serializable
{
	private static final long serialVersionUID = 888670286751254258L;
	public byte tab[][];
	public final byte size,rowcount;
	public long hash64;

	/**
	 * Zwraca wartoœæ pola na szachownicy.
	 * @param p
	 * Pole.
	 * @return
	 * Wartoœæ podanego Pola.
	 */
	public byte getValue(Field p)
	{
		return tab[p.row][p.column];
	}
	
	/**
	 * Zwraca iloœæ wierszy/kolumn (d³ugoœæ boku) szachownicy.
	 * @return Iloœæ pól boku szachownicy.
	 */
	public int getWidth() {
		return tab.length;
	}

	/**
	 * Ustawia wartoœæ pola.
	 * @param p
	 * Poke.
	 * @param val
	 * Wartoœæ nadawana polu.
	 */
	public void setValue(Field p, byte val)
	{
		tab[p.row][p.column] = val;
	}

	/**
	 * Konstruktor inicjalizuj¹cy wartoœci pól na szachownicy.
	 */
	public Board()
	{
		hash64 = 0;
		
		size = (byte) Settings.size;
		rowcount=(byte) Settings.rowCount;
		tab = new byte[size][size];
		for (int w = 0; w < size; w++)
		{
			int beg = Settings.rowCount;
			int end = size - Settings.rowCount;
			for (int k = 0; k < size; k++)
			{
				if (w < beg)
				{
					tab[w][k] = 1;
					hash64 ^= HashUtils.ZOBRIST_KEYS[0][w*size+k];
				}
				else if (w >= end)
				{
					tab[w][k] = 2;
					hash64 ^= HashUtils.ZOBRIST_KEYS[1][w*size+k];
				}
				else
				{
					tab[w][k] = 0;
				}
			}
		}
	}
	
	/**
	 * Konstruktor kopiuj¹cy.
	 * @param copy
	 * Plansza do skopiowania.
	 */
	public Board(Board copy)
	{
		hash64 = copy.hash64;
		size=copy.size;
		rowcount=copy.rowcount;
		tab=new byte[size][size];
		for(int row=0;row<size;row++)
		{
			for(int column=0;column<size;column++)
			{
				tab[row][column]=copy.tab[row][column];
			}
		}
	}
	
	/**
	 * Wyszukuje ³añcuch(szereg) pionów pod danym k¹tem.
	 * @param origin
	 * Pole pocz¹tkowe.
	 * @param angle
	 * K¹t pod którym znajduje siê szereg wzglêdem pola pocz¹tkowego.
	 * @return
	 * Lista pól nale¿¹cych do szeregu.
	 */
	List<Field> getChain(Field origin, byte angle)
	{
		List<Field> chain = new ArrayList<Field>();
		chain.add(origin);

		int id = getValue(origin);
		Movement mov = new Movement(origin, angle);

		while (mov.isValid())
		{
			if (id != getValue(mov.destination))
			{
				break;
			}
			chain.add(mov.destination);
			mov = new Movement(mov.destination,angle);
		}
		return chain;
	}

	/**
	 * Sprawdza czy dany ruch jest wykonywalny.
	 * @param mov
	 * Ruch do sprawdzenia.
	 */
	public boolean isExecutable(Movement mov)
	{
		if (mov == null)
		{
			return false;
		}
		if (!mov.isValid())
		{
			return false;
		}
		List<Field> lst = getChain(mov.origin, mov.angle);
		Field last = lst.get(lst.size() - 1);
		for (byte d = 1; d <= mov.distance; d++)
		{
			Movement lastMove = new Movement(last, mov.angle, d);
			if (!lastMove.isValid())
			{
				return false;
			}
			if (getValue(lastMove.destination) != 0)
			{
				return false;
			}
		}
		mov.chainSize=(byte) lst.size();
		return true;
	}
	
	/**
	 * Generuje listê wszstkich wykonywalnych ruchów z danego pola.
	 * @param src
	 * Pole Ÿród³owe.
	 * @return
	 * Lista mo¿liwych ruchów.
	 */
	public List<Movement> possibleMoves(Field src)
	{
		List<Movement> movs = new ArrayList<Movement>();
		for (byte ang = 0; ang < 8; ang++)
		{
			Movement curr = new Movement(src, ang);
			while (isExecutable(curr))
			{
				movs.add(curr);
				curr = curr.next();
			}
		}
		return movs;
	}

	/**
	 * Realizuje dany ruch.
	 * @param mov
	 * Ruch do wykonania.
	 */
	public boolean execute(Movement mov)
	{
		if (!isExecutable(mov))
		{
			return false;
		}
		byte id = getValue(mov.origin);
		List<Field> chain = getChain(mov.origin, mov.angle);
		for (int i = chain.size() - 1; i >= 0; i--)
		{
			Movement curr = new Movement(chain.get(i), mov.angle, mov.distance);
			
			hash64 ^= HashUtils.ZOBRIST_KEYS[id-1][curr.origin.row*size + curr.origin.column];
			hash64 ^= HashUtils.ZOBRIST_KEYS[id-1][curr.destination.row*size + curr.destination.column];
			
			setValue(curr.destination, id);
			setValue(curr.origin, (byte)0);
		}
		return true;
	}	
	
	/**
	 * Generuje wszystkie wykonywalne ruchy dla danego u¿ytkownika.
	 * Wersja ta nie odwo³uje siê do zadnej innej zewnêtrznej funkcji.
	 * @param id
	 * Identyfikator u¿ytkownika.
	 * @return
	 * Lista mo¿liwych ruchów.
	 */
	public List<Transition> generateTransitions(int id) {
		List<Transition> transitions = new ArrayList<Transition>(250);
		Movement curr = null;
		Field src = null;
		for(int row=0;row<size;row++) {
			for(int column=0;column<size;column++) {
				if(tab[row][column] == id) {
					src = new Field(row, column);
					for (byte ang = 0; ang < 8; ang++) {
						curr = new Movement(src, ang);
						while (isExecutable(curr)) {
							transitions.add(new Transition(this, curr));
							curr = curr.next();
						}
					}
				}
			}
		}
		Collections.shuffle(transitions);
		return transitions;
	}
	
	
	FieldColection[] srcFields=new FieldColection[3];
	Movement[] currMovement=new Movement[3];
	boolean[] srchFinished=new boolean[3];
	public Transition getNextTransition(int id) {
		if(srchFinished[id]) {return null;}
		if(srcFields[id]==null)
		{
			srcFields[id]=new FieldColection();
			for(int i=0;i<size;i++)
			{
				for(int j=0;j<size;j++)
				{
					if(tab[i][j]==id)
					{
						srcFields[id].addField(new Field(i, j));
					}
				}
			}	
			srcFields[id].randomize();
			currMovement[id]=srcFields[id].getNextInitialMovement();
		}
		else
		{
			currMovement[id]=currMovement[id].next();
		}
		while(!isExecutable(currMovement[id]))
		{
			currMovement[id]=srcFields[id].getNextInitialMovement();
			if(currMovement[id]==null)
			{
				srchFinished[id]=true;
				return null;
			}
		}
		return new Transition(this, currMovement[id]);
	}
	
	/**
	 * Generuje wszystkie wykonywalne ruchy dla danego u¿ytkownika.
	 * @param id
	 * Identyfikator u¿ytkownika.
	 * @return
	 * Vector mo¿liwych ruchów.
	 */
	@Deprecated
	public List<Transition> generateTransitionsOld(int id)
	{
		List<Transition> result=new ArrayList<Transition>(200);
		List<Movement> movs;
		
		for(int row=0;row<size;row++)
		{
			for(int column=0;column<size;column++)
			{
				if(tab[row][column]==id)
				{
					movs = possibleMoves(new Field(row,column));
					for (Movement m : movs) {
						result.add(new Transition(this, m));
					}
				}
				
			}
		}
		Collections.shuffle(result);
		return result;
	}
}