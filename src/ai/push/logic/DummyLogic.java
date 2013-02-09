package ai.push.logic;

import java.util.Vector;

public class DummyLogic extends Logic
{
	public DummyLogic()
	{
		super();
	}
	

	public boolean isSelectable(int row, int column) {
		return (board.tab[row][column]>0);
	}
	
	public Vector<Field> possibleDestinations(Field origin)
	{
		Vector<Field> destinations = new Vector<Field>();
		
		for (int i = 0; i < board.tab.length; i++)
		{
			for (int j = 0; j < board.tab[i].length; j++)
			{
				if (board.tab[i][j] == 0)
				{
					destinations.add(new Field(i, j));
				}
			}
		}
		
		return destinations;
	}
	
	public boolean executeMove(Movement move)
	{
		board.setValue(move.destination, board.getValue(move.origin));
		board.setValue(move.origin, 0);
		return true;
	}
	
	public void endTurn()
	{
		edited = true;
	}
}
