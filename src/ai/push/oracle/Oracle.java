package ai.push.oracle;
/**
 * Interface for Oracles.
 * 
 * @author blacksmithy
 * @since 23-12-2012
 * @version 1.0
 */
public abstract class Oracle {

	public static enum BOARD_ORIENTATION {
		HORIZONTAL, VERTICAL
	}

	public static enum PLAYER {
		PLAYER1, PLAYER2
	}

	protected int player1;
	protected int player2;
	protected BOARD_ORIENTATION orientation;
	/**
	 * True if firstMove was Player 2's (it was a swap in setSides()).
	 */
	protected boolean playersExchange = false;

	public abstract int getProphecy(int[][] board, PLAYER player);

	public int getPlayer1Symbol() {
		return player1;
	}

	public int getPlayer2Symbol() {
		return player1;
	}

	protected void setSides(PLAYER firstMove) {
		if (firstMove == PLAYER.PLAYER2) {
			int swap = player1;
			player1 = player2;
			player2 = swap;
			playersExchange = true;
		}
	}
}
