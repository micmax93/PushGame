package ai.push.logic.ai;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ai.push.logic.Board;
import ai.push.logic.Logic;
import ai.push.logic.Settings;
import ai.push.logic.Transition;
import ai.push.logic.ai.tt.Transposition;
import ai.push.logic.ai.tt.TranspositionHashStorage;
import ai.push.logic.ai.tt.TranspositionTable;
import ai.push.logic.oracle.DelphiOracle;
import ai.push.logic.oracle.Oracle;
import ai.push.logic.oracle.TransitionComparator;

public class FSAlphaBetaTTAI extends AbstractAI implements ThreadEndEvent {

	private int[] threadReturn;
	private FSAlphaBetaTTThread[] threads;
	private boolean forceOneThread = false;

	public FSAlphaBetaTTAI(Logic logic, Oracle.PLAYER player) {
		super(logic, player);
		// oracle = new RankOracle(1, 2);
		// oracle = new LogarithmicOracle(1, 2);
		oracle = new DelphiOracle(1, 2);
		// oracle = new ExponentialOracle(1, 2); // tu ustawiasz wyroczniê
		threadReturn = new int[2];
		threads = new FSAlphaBetaTTThread[2];
		//magicTable = new TranspositionTable();
	}

	public static boolean isGameOver(Board board) {
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

	@Override
	protected void algorithm() {
		// maxDepth = 4; // g³êbokoœæ przeszukiwania
		Transition decision = null;
		TranspositionHashStorage magicTable = new TranspositionTable();
		System.out.println("CHAR = " + Character.SIZE);

		int plr;
		if (player == Oracle.PLAYER.PLAYER1)
			plr = 1;
		else
			plr = 2;

		int decisionVal = Integer.MIN_VALUE;

		int alpha = Integer.MIN_VALUE + 2;
		int beta = Integer.MAX_VALUE - 1;
		System.out.println("A=" + alpha + " B =" + beta);

		// System.out.println(new Date() + " BEG");

		if (player == Oracle.PLAYER.PLAYER1) {
			Collections.sort(list, new TransitionComparator(oracle,
					Oracle.PLAYER.PLAYER1, TransitionComparator.ORDER.DESC));
		} else {
			Collections.sort(list, new TransitionComparator(oracle,
					Oracle.PLAYER.PLAYER2, TransitionComparator.ORDER.DESC));
		}

		// decision = list.get(0);

		int iterStep = 2;
		if (forceOneThread) {
			iterStep = 1;
		}

		boolean debug = false;

		for (int i = 0; i < list.size(); i += iterStep) {
			System.out.println("->" + i);
			
			if (debug)
				System.out.println("->" + i);

			threads[0] = new FSAlphaBetaTTThread(0, this, new DelphiOracle(1, 2),
					new Transition(list.get(i)), maxDepth - 1, -beta, -alpha,
					3 - plr, magicTable);
			threads[0].run();
			if (!forceOneThread && (i + 1 < list.size())) {
				threads[1] = new FSAlphaBetaTTThread(1, this, new DelphiOracle(1,
						2), new Transition(list.get(i + 1)), maxDepth - 1,
						-beta, -alpha, 3 - plr, magicTable);
				threads[1].run();
			}
			try {
				threads[0].join();
				if (!forceOneThread && (i + 1 < list.size())) {
					threads[1].join();
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			if (threadReturn[0] > alpha) {
				alpha = threadReturn[0];
				if (debug)
					System.out.println("i: " + i + " NOWA ALFA: " + alpha);
			}
			if (alpha >= beta) {
				break;
			}
			if (threadReturn[0] > decisionVal) {
				decisionVal = threadReturn[0];
				decision = list.get(i);
			}
			if (!forceOneThread && (i + 1 < list.size())) {
				if (threadReturn[1] > alpha) {
					alpha = threadReturn[1];
					if (debug)
						System.out.println("i: " + i + " NOWA ALFA: " + alpha);
				}
				if (alpha >= beta) {
					break;
				}
				if (threadReturn[1] > decisionVal) {
					decisionVal = threadReturn[1];
					decision = list.get(i + 1);
				}
			}
			
			//System.out.println("MAGIC_TABLE_SIZE = " + magicTable.size());
		}

		if (debug)
			System.out.println("DECISION = " + decisionVal);

		if (decision == null) {
			System.err.println("DECISION ERROR!");
			System.exit(-1);
		}
		System.out.println("DEC=" + decisionVal);
		result = decision.mainMove;
		if (debug)
			System.out.println("RESULT = " + result.destination.row);
		
		//System.out.println("MAGIC_TABLE_SIZE = " + magicTable.size());
	}

	@Override
	public void threadEnd(int threadId, int value) {
		threadReturn[threadId] = value;
	}
}

class FSAlphaBetaTTThread extends Thread {
	private int threadId;
	private Transition transition;
	private ThreadEndEvent event;
	private Oracle oracle;
	private int depth;
	private int alpha;
	private int beta;
	private int player;
	private TranspositionHashStorage magicTable;

	public FSAlphaBetaTTThread() {
	}

	public FSAlphaBetaTTThread(int threadId, ThreadEndEvent event, Oracle oracle,
			Transition transition, int depth, int alpha, int beta, int player, TranspositionHashStorage magicTable) {
		super();
		this.threadId = threadId;
		this.event = event;
		this.oracle = oracle;
		this.transition = transition;
		this.depth = depth;
		this.alpha = alpha;
		this.beta = beta;
		this.player = player;
		this.magicTable = magicTable;
	}

	public int alphaBeta(Transition transition, int depth, int alpha, int beta,
			int player) {
		if ((depth == 0) || FSAlphaBetaTTAI.isGameOver(transition.in)) {
			return oracle.getProphecy(transition, player);
		}
		
		//System.out.println("MAGIC_TABLE_SIZE = " + magicTable.size());
		
		int prevAlpha = alpha;
		
		Transposition t = magicTable.get(transition.out); // TODO
		if (t != null) { // jeœli znaleziono coœ w tablicy transpozycji
			if (t.getDepth() >= depth) { // i wynik mo¿e mieæ znaczenie na tym poziomie
				if (t.getType() == Transposition.VALUE_TYPE.LOWER)
					alpha = Math.max(alpha, t.getValue());
				else if (t.getType() == Transposition.VALUE_TYPE.UPPER)
					beta = Math.min(beta, t.getValue());
				else {
					alpha = t.getValue();
					beta = t.getValue();
				}
			}
			if (alpha >= beta) // odciêcie
				return t.getValue();
		}
		
		List<Transition> moves = new ArrayList<Transition>();
		if (t != null && t.getNext() != null) {
			moves.add(t.getNext());
		}
		moves.addAll(transition.getNextGeneration(player));
		
		int best = Integer.MIN_VALUE;
		Transition nextBest = null;

		int value;
		for (Transition m : moves) {
			value = -alphaBeta(m, depth - 1, -beta, -alpha, 3 - player);
			if (value > best) {
				best = value;
				nextBest = m;
			}
			if (best >= beta) {
				// System.out.println("ODCIÊCIE! @ LVL: " + depth);
				break;
			}
			if (best > alpha)
				alpha = best;
		}
		moves.clear();
		
		if (nextBest != null) { //  && depth > 1
			magicTable.put(transition.out, new Transposition(best, depth, prevAlpha, beta, nextBest));
		}

		return best;
	}

	public void run() {
		int value = -alphaBeta(transition, depth, alpha, beta, player);
		event.threadEnd(threadId, value);
	}
}
