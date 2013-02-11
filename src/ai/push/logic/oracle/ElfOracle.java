package ai.push.logic.oracle;

import ai.push.logic.Board;
import ai.push.logic.Transition;

public class ElfOracle extends Oracle
{

	@Override
	public int getProphecy(Board board, PLAYER player)
	{
		// TODO Auto-generated method stub
		return 0;
	}
	
	int countBoardFill(Board b, PLAYER p)
	{
		int n=0;
		if(p==PLAYER.PLAYER2)
		{
			for(int i=0;i<2;i++)
			{
				for(int j=0;j<b.size;j++)
				{
					if(b.tab[i][j]==2)
					{
						n++;
					}
				}
			}
		}
		else if(p==PLAYER.PLAYER1)
		{
			for(int i=b.size-2;i<b.size;i++)
			{
				for(int j=0;j<b.size;j++)
				{
					if(b.tab[i][j]==1)
					{
						n++;
					}
				}
			}
		}
		return n;
	}

	@Override
	public int getProphecy(Transition transition, PLAYER player)
	{
		int val=0;
		int dist=transition.mainMove.destination.row-transition.mainMove.origin.row;
		if(player==PLAYER.PLAYER2) {dist=-dist;}
		int diff=countBoardFill(transition.out, player)-countBoardFill(transition.in, player);
		val=dist*transition.mainMove.chainSize+3*diff;
		return val;
	}

}
