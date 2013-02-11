package ai.push.logic.oracle;

import ai.push.logic.Board;
import ai.push.logic.Transition;

public class DelphiOracle extends Oracle {

	private final int[] DISTANCES_RANKS = new int[] { 1, 16, 36, 56, 80, 110,
			144, 176, 216, 266, 326, 396, 466 };

	public DelphiOracle(int player1, int player2) {
		this.player1 = player1;
		this.player2 = player2;
	}

	/**
	 * UWAGA! ZWRACA EGOISTIC
	 */
	@Override
	public int getProphecy(Board board, PLAYER player) {
		return new DistancesEgoisticOracle(player1, player2).getProphecy(board,
				player);
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
	
	int countBoardDist(Board b, PLAYER p)
	{
		int n=0,val=0,id=0;
		if(p==PLAYER.PLAYER2)
			{id=2;}
		else if(p==PLAYER.PLAYER1)
			{id=1;}
		
		for(int i=2;i<b.size-2;i++)
		{
			n=0;
			for(int j=0;j<b.size;j++)
			{
				if(b.tab[i][j]==id)
				{
					n++;
				}
			}
			
			if(p==PLAYER.PLAYER2)
				{val+=n*(b.size-i)/2;}
			else if(p==PLAYER.PLAYER1)
				{val+=n*(i)/2;}
			
		}
		return val;
	}

	@Override
	public int getProphecy(Transition transition, PLAYER player) {
		int plr = 100;
		if (player == PLAYER.PLAYER1) {
			plr = player1;
		} else {
			plr = player2;
		}
		PLAYER enemy;
		if (player == PLAYER.PLAYER1)
			enemy = PLAYER.PLAYER2;
		else
			enemy = PLAYER.PLAYER1;
		
		int prophecy = 0;
		int q=countBoardFill(transition.out, player)-countBoardFill(transition.in, player);
		int qq = countBoardFill(transition.out, enemy)-countBoardFill(transition.in, enemy);
		int dd=countBoardDist(transition.out, player)-countBoardDist(transition.in, player);
		int ddd=countBoardDist(transition.out, enemy)-countBoardDist(transition.in, enemy);
		prophecy=q*100+dd - (qq*100+ddd);
		
		//System.out.println("PR = " + prophecy);
		
		return prophecy; //prophecy;
	}

	/*
	 * // chwilowo nieu¿ywane private int hasFinished(Board board) { boolean
	 * won1 = true, won2 = true; for (int row = 0; row < board.getWidth();
	 * row++) { int beg = Settings.rowCount; int end = board.getWidth() -
	 * Settings.rowCount; for (int column = 0; column < board.getWidth();
	 * column++) { if (row < beg) { if (board.tab[row][column] != 2) { won2 =
	 * false; } } else if (row >= end) { if (board.tab[row][column] != 1) { won1
	 * = false; } } } } if (won1) { return 1; } else if (won2) { return 2; }
	 * else { return 0; } }
	 */
}
