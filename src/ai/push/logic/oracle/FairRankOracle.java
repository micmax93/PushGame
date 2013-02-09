package ai.push.logic.oracle;

import ai.push.logic.Board;
import ai.push.logic.Transition;

public class FairRankOracle extends Oracle {

	public FairRankOracle(int player1, int player2) {
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
		int rank = 0;
		rank += transition.mainMove.distance;
		
		return rank;
	}

}
