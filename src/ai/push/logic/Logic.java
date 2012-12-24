package ai.push.logic;

import java.util.Vector;



public class Logic
{
	AbstractAI ai1, ai2;
	final int size;
	public int turn;
	Board board;
	public Boolean edited;
	public Boolean locked;
	public int winner;

	public Logic()
	{
		this.size = Settings.size;
		board = new Board();
		turn = 2;
		edited=new Boolean(true);
		locked=new Boolean(false);
	}

	public int[][] getTab()
	{
		return board.tab;
	}
	

	public void endTurn()
	{
		locked=true;
		edited=true;
		turn = enemyID();
		int winner=hasFinished();
		if(winner==0)
		{
			if((turn==1)&&(Settings.AI1))
			{
				ai1=new RandomAI(this);
				ai1.start();
			}
			if((turn==2)&&(Settings.AI2))
			{
				ai2=new RandomAI(this);
				ai2.start();
			}
			else
			{
				locked=false;
			}
			
		}
	}

	int enemyID()
	{
		if (turn == 1)
		{
			return 2;
		}
		else
		{
			return 1;
		}
	}

	int hasFinished()
	{
		boolean won1 = true, won2 = true;
		for (int row = 0; row < size; row++)
		{
			int beg = Settings.rowCount;
			int end = size - Settings.rowCount;
			for (int column = 0; column < size; column++)
			{
				if (row < beg)
				{
					if (board.tab[row][column] != 2)
					{
						won2 = false;
					}
				}
				else if (row >= end)
				{
					if (board.tab[row][column] != 1)
					{
						won1 = false;
					}
				}
			}
		}
		if (won1)
		{
			return 1;
		}
		else if (won2)
		{
			return 2;
		}
		else
		{
			return 0;
		}
	}

	public boolean isSelectable(int row, int column)
	{
		return (turn == board.tab[row][column]);
	}

	public Vector<Field> possibleDestinations(Field origin)
	{
		Vector<Movement> movs = board.possibleMoves(origin);
		Vector<Field> destinations = new Vector<Field>();
		for (int i = 0; i < movs.size(); i++)
		{
			destinations.add(movs.get(i).destination);
		}
		return destinations;
	}

	public boolean executeMove(Movement move)
	{
		if (board.getValue(move.origin) == turn)
		{
			return board.execute(move);
		}
		return false;
	}
	public Vector<Transition> generateTransitions()
	{
		return board.generateTransitions(turn);
	}
}