package ai.push.logic.oracle;

import ai.push.logic.Board;
import ai.push.logic.Field;
import ai.push.logic.Transition;

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
	 */
	public DistancesOracle(int player1, int player2) {
		this.player1 = player1;
		this.player2 = player2;
	}

	/**
	 * Returns distances-based prophecy. Oracle computes for each player mark
	 * value: sum of distances of player's checkers. Prophecy is a difference
	 * between marks (player's mark - opponent's mark).
	 */
	@Override
	public int getProphecy(Board board, PLAYER player) {
		int markPlayer1 = 0;
		int markPlayer2 = 0;

		for (int r = 0; r < board.getWidth(); ++r) {
			for (int c = 0; c < board.getWidth(); ++c) {
				if (board.getValue(new Field(r, c)) == player1) {
					markPlayer1 += r;
				} else if (board.getValue(new Field(r, c)) == player2) {
					markPlayer2 += board.getWidth() - r - 1;
				}
			}
		}

		if (player == PLAYER.PLAYER1)
			return markPlayer1 - markPlayer2;
		else
			return markPlayer2 - markPlayer1;
	}

	@Override
	public int getProphecy(Transition transition, PLAYER player) {
		return getProphecy(transition.out, player);
	}
}
