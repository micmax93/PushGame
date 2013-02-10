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

	@Override
	public int getProphecy(Transition transition, PLAYER player) {
		int plr = 100;
		if (player == PLAYER.PLAYER1) {
			plr = player1;
		} else {
			plr = player2;
		}
		// int rank = 0;
		int prophecy = 0;
		// double improvement = 0.0f;
		int size = transition.out.getWidth();

		for (int r = 0; r < size; ++r) {
			for (int c = 0; c < size; ++c) {
				if (transition.out.tab[r][c] == plr) {
					if (plr == player1) { // player at the top of the board
						prophecy += DISTANCES_RANKS[r];
						// value=r+1)
					} else {
						prophecy += DISTANCES_RANKS[size - 1 - r];
					}
				}
			}
		}
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
