package ai.push.logic.oracle;

import java.util.List;

import ai.push.logic.Board;
import ai.push.logic.Settings;
import ai.push.logic.Transition;

// PORZUCONA

public class RankOracle extends Oracle {

	// BONUSES
	private static final int CHECKER_FINISH_BONUS_1 = 10;
	private static final int CHECKER_FINISH_BONUS_2 = 15;
	private static final int ONE_MOVE_TO_WIN_BONUS = 100;

	// PENALTIES
	//private static final int NO_MOVE_FORWARD_PENALTY = -3;
	private static final int MOVE_BACK_PENALTY = -30;
	
	public RankOracle(int player1, int player2) {
		this.player1 = player1;
		this.player2 = player2;
	}
	
	/**
	 * UWAGA! ZWRACA EGOISTIC
	 */
	@Override
	public int getProphecy(Board board, PLAYER player) {
		return new DistancesEgoisticOracle(player1, player2).getProphecy(board, player);
	}

	@Override
	public int getProphecy(Transition transition, PLAYER player) {
		int plr = 100;
		if (player == PLAYER.PLAYER1) {
			plr = player1;
		} else {
			plr = player2;
		}

		int rank = 30;
		int size = transition.out.getWidth();

		for (int r = 0; r < size; ++r) {
			for (int c = 0; c < size; ++c) {
				if (transition.out.tab[r][c] == plr) {
					if (plr == player1) { // player at the top of the board
						rank += r;
						if (r == size - 2) {
							rank += CHECKER_FINISH_BONUS_1;
						}
						else if (r == size - 1) {
							rank += CHECKER_FINISH_BONUS_2;
						}
						
						/*
						rank += 2 * ((r > 0) ? r - 1 : 0);
						if (r == size - 1) {
							rank += CHECKER_FINISH_BONUS;
						}
						*/
					} else { // player at the bottom of the board
						rank += size- 1 - r;
						if (r == 1) {
							rank += CHECKER_FINISH_BONUS_1;
						}
						else if (r == 0) {
							rank += CHECKER_FINISH_BONUS_2;
						}
						
						/*rank += 2 * ((r < size - 2) ? size - r - 2 : 0);
						if (r == 0) {
							rank += CHECKER_FINISH_BONUS;
						}*/
					}
				}
			}
		}

		int angle = transition.mainMove.angle;

		if ((plr == player1 && (angle == 7 || angle == 0 || angle == 1))
				|| (plr == player2 && (angle == 5 || angle == 4 || angle == 3))) {
			// move back
			rank += MOVE_BACK_PENALTY;
		}
		else {
			if (angle == 6 || angle == 2) {
				//rank += NO_MOVE_FORWARD_PENALTY;
				// sprawdzenie cz nastêpny ruch da zwyciêstwo
				List<Transition> nextMoves = transition.getNextGeneration(plr);
				for (Transition t : nextMoves) {
					if (hasFinished(t.out) == plr) {
						rank += ONE_MOVE_TO_WIN_BONUS;
					}
				}
				
			}
		}
		
		//System.out.println(rank);

		//rank += transition.mainMove.distance;
		return rank;
		//return new Random().nextInt(10);
	}

	private int hasFinished(Board board) {
		boolean won1 = true, won2 = true;
		for (int row = 0; row < board.getWidth(); row++) {
			int beg = Settings.rowCount;
			int end = board.getWidth() - Settings.rowCount;
			for (int column = 0; column < board.getWidth(); column++) {
				if (row < beg) {
					if (board.tab[row][column] != 2) {
						won2 = false;
					}
				} else if (row >= end) {
					if (board.tab[row][column] != 1) {
						won1 = false;
					}
				}
			}
		}
		if (won1) {
			return 1;
		} else if (won2) {
			return 2;
		} else {
			return 0;
		}
	}
}
