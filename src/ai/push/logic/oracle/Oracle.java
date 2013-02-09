package ai.push.logic.oracle;

import ai.push.logic.Board;
import ai.push.logic.Transition;

/**
 * Interface for Oracles.
 * 
 * @author blacksmithy
 * @since 23-12-2012
 * @version 1.0
 */
public abstract class Oracle {

	public static enum PLAYER {
		PLAYER1, PLAYER2
	}

	protected int player1;
	protected int player2;

	public abstract int getProphecy(Board board, PLAYER player);
	
	public abstract int getProphecy(Transition transition, PLAYER player);
	
	public int getProphecy(Board board, int player) {
		if (player == player1)
			return getProphecy(board, Oracle.PLAYER.PLAYER1);
		else
			return getProphecy(board, Oracle.PLAYER.PLAYER2);
	}
	
	public int getProphecy(Transition transition, int player) {
		if (player == player1)
			return getProphecy(transition, Oracle.PLAYER.PLAYER1);
		else
			return getProphecy(transition, Oracle.PLAYER.PLAYER2);
	}
	public int getPlayer1Symbol() {
		return player1;
	}

	public int getPlayer2Symbol() {
		return player1;
	}
}
