package ai.push.logic.oracle;

import ai.push.logic.Board;

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

	public int getPlayer1Symbol() {
		return player1;
	}

	public int getPlayer2Symbol() {
		return player1;
	}
}
