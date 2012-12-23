package ai.push.oracle;
/**
 * Distance-based Oracle.
 * 
 * @author blacksmithy
 * @since 23-12-2012
 * @version 1.0
 */
public class DistancesOracle extends Oracle {

	/**
	 * Creates distance-based Oracle.
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
	public DistancesOracle(int player1, int player2,
			BOARD_ORIENTATION orientation, PLAYER firstMove) {
		this.player1 = player1;
		this.player2 = player2;
		this.orientation = orientation;
		this.setSides(firstMove);
	}

	/**
	 * Returns distances-based prophecy. Oracle computes for each player mark
	 * value: sum of distances of player's checkers. Prophecy is a difference
	 * between marks (player's mark - opponent's mark).
	 */
	@Override
	public int getProphecy(int[][] board, PLAYER player) {
		int markPlayer1 = 0;
		int markPlayer2 = 0;
		
		if (orientation == BOARD_ORIENTATION.HORIZONTAL) {
			for (int r = 0; r < board.length; ++r) {
				for (int c = 0; c < board[r].length; ++c) {
					if (board[r][c] == player1) {
						markPlayer1 += r;
					} else if (board[r][c] == player2) {
						markPlayer2 += board.length - r - 1;
					}
				}
			}
		} else {
			for (int r = 0; r < board.length; ++r) {
				for (int c = 0; c < board[r].length; ++c) {
					if (board[r][c] == player1) {
						markPlayer1 += c;
					} else if (board[r][c] == player2) {
						markPlayer2 += board[r].length - c - 1;
					}
				}
			}
		}
		System.out.println("P1: " + markPlayer1);
		System.out.println("P2: " + markPlayer2);

		if ((player == PLAYER.PLAYER1 && (!playersExchange))
				|| (player == PLAYER.PLAYER2 && playersExchange))
			return markPlayer1 - markPlayer2;
		else
			return markPlayer2 - markPlayer1;
	}
}
