package ai.push.logic.oracle;

import ai.push.logic.Board;
import ai.push.logic.Field;

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
	 */
	public DistancesEgoisticOracle(int player1, int player2) {
		this.player1 = player1;
		this.player2 = player2;
	}

	/**
	 * Returns distance-based egoistic prophecy. Oracle computes for player the
	 * mark value: sum of distances of player's checkers. Prophecy is a mark
	 * value - it doesn't consider opponent.
	 */
	@Override
	public int getProphecy(Board board, PLAYER player) {
		int markPlayer1 = 0;
		int markPlayer2 = 0;

		for (int r = 0; r < board.getWidth(); ++r) {
			for (int c = 0; c < board.getWidth(); ++c) {
				if (board.getValue(new Field(r, c)) == player1
						&& player == PLAYER.PLAYER1) {
					markPlayer1 += r;
				} else if (board.getValue(new Field(r, c)) == player2
						&& player == PLAYER.PLAYER2) {
					markPlayer2 += board.getWidth() - r -1;
				}
			}
		}

		if (player == PLAYER.PLAYER1)
			return markPlayer1;
		else
			return markPlayer2;
	}
}
