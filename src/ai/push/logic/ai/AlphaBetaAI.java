package ai.push.logic.ai;

import java.util.Vector;

import ai.push.logic.Board;
import ai.push.logic.Logic;
import ai.push.logic.Settings;
import ai.push.logic.Transition;
import ai.push.logic.oracle.DistancesOracle;
import ai.push.logic.oracle.Oracle;

/**
 * Przyk³adowa implementacja AI dla modelu z odciêciami alfa-beta.
 * 
 * @author blacksmithy
 * 
 */
public class AlphaBetaAI extends AbstractAI {
	public AlphaBetaAI(Logic logic, Oracle.PLAYER player) {
		super(logic, player);
		oracle = new DistancesOracle(1, 2);
	}

	private boolean isGameOver(Board board) {
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
		if (won1 || won2)
			return true;
		else
			return false;
	}

	public int alphaBeta(Transition transition, int depth, int alpha, int beta,
			int player, int nextGen) {
		if ((depth == 0) || isGameOver(transition.in))
			return 100+oracle.getProphecy(transition.in, player);

		Vector<Transition> moves = transition.getNextGeneration(player);
		int value;
		for (Transition m : moves) {
			value = -alphaBeta(m, depth - 1, -beta, -alpha, player, 3 - nextGen);
			if (value > alpha)
				alpha = value;
			if (alpha >= beta) {
				//System.out.println("ODCIÊCIE!");
				return beta;
			}
		}
		return alpha;
	}

	@Override
	/**
	 * Algorytm wybiera najlepszy ruch, analizuj¹c do przodu mozliwe ruchy, 
	 * do okreœlonej g³êbokoœci (maxDepth). Jesli odleg³oœæ nie zosta³a ustalona, 
	 * zostanie przyjêta wartoœæ domyslna.
	 */
	protected void algorithm() {
		maxDepth = 4;
		Transition decision = null;
		
		int plr;
		if (player == Oracle.PLAYER.PLAYER1)
			plr = 1;
		else
			plr = 2;

		int decisionVal = Integer.MIN_VALUE;
		int temp;
		
		int alpha = Integer.MIN_VALUE+1;
		int beta = Integer.MAX_VALUE-1;
		
		System.out.println("SIZE=" + list.size());
		
		for (Transition t : list) {
			temp = -alphaBeta(t, maxDepth-1, -beta, -alpha, plr, 3 - plr);
			if (temp > alpha) {
				alpha = temp;
				System.out.println("NOWA ALFA");
			}
			if (alpha >= beta) {
				System.out.println("ODCIÊCIE!");
				break;
			}
			if (temp > decisionVal) {
				decisionVal = temp;
				decision = t;
			}
		}
		
		System.out.println(decisionVal);

		if (decision == null) {
			System.out.println("DECISION ERROR!");
		}

		result = decision.mainMove;
	}
}