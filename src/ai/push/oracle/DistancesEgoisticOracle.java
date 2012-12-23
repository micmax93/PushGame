package ai.push.oracle;
/**
 * Distance-based egoistic Oracle.
 * 
 * @author blacksmithy
 * @since 24-12-2012
 * @version 1.0
 */
public class DistancesEgoisticOracle extends Oracle {

	/**
	 * Creates distance-based egoistic Oracle.
	 * 
	 * @param player1
	 *            Number representing Player1
	 * @param player2
	 *            Number representing Player2
	 * @param orientation
	 *            Playing board orientation
	 * @param firstMove
	 *            Player who starts: HORIZONTAL - top, VERTICAL - left
	 */
	public DistancesEgoisticOracle(int player1, int player2,
			BOARD_ORIENTATION orientation, PLAYER firstMove) {
		this.player1 = player1;
		this.player2 = player2;
		this.orientation = orientation;
		this.setSides(firstMove);
	}

	/**
	 * Returns distance-based egoistic prophecy. Oracle computes for player the
	 * mark value: sum of distances of player's checkers. Prophecy is a mark
	 * value - it doesn't consider opponent.
	 */
	@Override
	public int getProphecy(int[][] board, PLAYER player) {
		int markPlayer = 0;

		if (orientation == BOARD_ORIENTATION.HORIZONTAL) {
			for (int r = 0; r < board.length; ++r) {
				for (int c = 0; c < board[r].length; ++c) {
					if ((player == PLAYER.PLAYER1 && (!playersExchange))
							|| (player == PLAYER.PLAYER2 && playersExchange)) {
						if (board[r][c] == player1) {
							markPlayer += r;
						}
					} else {
						if (board[r][c] == player2) {
							markPlayer += board.length - r - 1;
						}
					}
				}
			}
		} else {
			for (int r = 0; r < board.length; ++r) {
				for (int c = 0; c < board[r].length; ++c) {
					if ((player == PLAYER.PLAYER1 && (!playersExchange))
							|| (player == PLAYER.PLAYER2 && playersExchange)) {
						if (board[r][c] == player1) {
							markPlayer += c;
						}
					} else {
						if (board[r][c] == player2) {
							markPlayer += board[r].length - c - 1;
						}
					}
				}
			}
		}
		return markPlayer;
	}
}
