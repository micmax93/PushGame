package logic;

import java.util.Vector;

public class Board
{
	public int tab[][];

	public int getValue(Field p)
	{
		return tab[p.row][p.column];
	}

	public void setValue(Field p, int val)
	{
		tab[p.row][p.column] = val;
	}

	public Board()
	{
		int size = Settings.size;
		tab = new int[size][size];
		for (int w = 0; w < size; w++)
		{
			int beg = Settings.rowCount;
			int end = size - Settings.rowCount;
			for (int k = 0; k < size; k++)
			{
				if (w < beg)
				{
					tab[w][k] = 1;
				}
				else if (w >= end)
				{
					tab[w][k] = 2;
				}
				else
				{
					tab[w][k] = 0;
				}
			}
		}
	}

	Vector<Field> getChain(Field origin, int angle)
	{
		Vector<Field> chain = new Vector<Field>();
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
			mov = mov.next();
			if (mov == null)
			{
				break;
			}
		}
		return chain;
	}

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

		Field last = getChain(mov.origin, mov.angle).lastElement();
		for (int d = 1; d <= mov.distance; d++)
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
		return true;
	}

	public Vector<Movement> possibleMoves(Field src)
	{
		Vector<Movement> movs = new Vector<Movement>();
		for (int ang = 0; ang < 8; ang++)
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

	public boolean execute(Movement mov)
	{
		if (!isExecutable(mov))
		{
			return false;
		}
		int id = getValue(mov.origin);
		Vector<Field> chain = getChain(mov.origin, mov.angle);
		for (int i = chain.size() - 1; i >= 0; i--)
		{
			Movement curr = new Movement(chain.get(i), mov.angle, mov.distance);
			setValue(curr.destination, id);
			setValue(curr.origin, 0);
		}
		return true;
	}
}