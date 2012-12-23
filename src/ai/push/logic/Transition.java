package ai.push.logic;

import java.util.Vector;

public class Transition
{
	public Movement mainMove;
	public Vector<Movement> allMoves;
	public Board in,out;
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
}
