package ai.push.logic.ai;

import java.util.Collections;
import java.util.List;

import ai.push.logic.Board;
import ai.push.logic.Logic;
import ai.push.logic.Settings;
import ai.push.logic.Transition;
import ai.push.logic.oracle.DelphiOracle;
import ai.push.logic.oracle.LogarithmicOracle;
import ai.push.logic.oracle.Oracle;
import ai.push.logic.oracle.TransitionComparator;

public class AlphaBetaAI extends AbstractAI  implements ThreadEndEvent {
	
	private int[] threadReturn;
	private AlphaBetaThread[] threads;
	private boolean forceOneThread = false;
	private Oracle oracle2;
	
	public AlphaBetaAI(Logic logic, Oracle.PLAYER player) {
		super(logic, player);
//		oracle = new RankOracle(1, 2);
//		oracle = new LogarithmicOracle(1, 2);
		//oracle = new DelphiOracle(1, 2);
		//oracle = new LogarithmicOracle(1, 2);
		//oracle2 = new LogarithmicOracle(1, 2);
		oracle = new DelphiOracle(1, 2);
		oracle2 = new DelphiOracle(1, 2);
		
		
//		oracle = new ExponentialOracle(1, 2); // tu ustawiasz wyroczniê
		threadReturn = new int[2];
		threads = new AlphaBetaThread[2];
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
		//maxDepth = 4; // g³êbokoœæ przeszukiwania
		Transition decision = null;

		int plr;
		if (player == Oracle.PLAYER.PLAYER1)
			plr = 1;
		else
			plr = 2;

		int decisionVal = Integer.MIN_VALUE;

		//int alpha = Integer.MIN_VALUE + 1;
		//int beta = Integer.MAX_VALUE - 1;
		
		int alpha = Integer.MIN_VALUE + 2;
		int beta = Integer.MAX_VALUE - 1;
		
		
		//System.out.println(new Date() + " BEG");
		
		if (player == Oracle.PLAYER.PLAYER1) {
			Collections.sort(list, new TransitionComparator(
					Oracle.PLAYER.PLAYER1, TransitionComparator.ORDER.DESC));
		} else {
			Collections.sort(list, new TransitionComparator(
					Oracle.PLAYER.PLAYER2, TransitionComparator.ORDER.DESC));
		}

		//decision = list.get(0);	
		
		int iterStep = 2;
		if (forceOneThread) {
			iterStep = 1;
		}
		
		boolean debug = false;
		
		for (int i = 0; i < list.size(); i += iterStep) {
			//if (debug)
				System.out.println("->" + i);

				threads[0] = new AlphaBetaThread(0, this, new DelphiOracle(1, 2), new Transition(list.get(i)), maxDepth - 1, -beta, -alpha, 3 - plr);
				threads[0].run();
				if (! forceOneThread && (i + 1 < list.size())) {
				threads[1] = new AlphaBetaThread(1, this, new DelphiOracle(1, 2), new Transition(list.get(i+1)), maxDepth - 1, -beta, -alpha, 3 - plr);
				threads[1].run();
				}
				try {
					threads[0].join();
					if (! forceOneThread && (i + 1 < list.size())) {
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
					System.out.println("ODCIÊCIE!!!!!!!!!");
					break;
				}
				if (threadReturn[0] > decisionVal)
				{
					decisionVal = threadReturn[0];
					decision = list.get(i);
				}
				if (! forceOneThread && (i + 1 < list.size())) {
					if (threadReturn[1] > alpha) {
						alpha = threadReturn[1];
						if (debug)
							System.out.println("i: " + i + " NOWA ALFA: " + alpha);
					}
					if (alpha >= beta) {
						System.out.println("ODCIÊCIE!!!!!!!!!");
						break;
					}
					if (threadReturn[1] > decisionVal)
					{
						decisionVal = threadReturn[1];
						decision = list.get(i+1);
					}
				}
		}
		
		if (debug)
			System.out.println("DECISION = " + decisionVal);

		if (decision == null) {
			System.err.println("DECISION ERROR!");
			System.exit(-1);
		}

		result = decision.mainMove;
		if (debug)
			System.out.println("RESULT = " + result.destination.row);
	}

	@Override
	public void threadEnd(int threadId, int value) {
		threadReturn[threadId] = value;		
	}
}

class AlphaBetaThread extends Thread {
	private int threadId;
	private Transition transition;
	private ThreadEndEvent event;
	private Oracle oracle;
	private int depth;
	private int alpha;
	private int beta;
	private int player;
	
	private int cutOffs = 0;
	
	public AlphaBetaThread() {}

	public AlphaBetaThread(int threadId, ThreadEndEvent event, Oracle oracle, Transition transition, int depth, int alpha,
			int beta, int player) {
		super();
		this.threadId = threadId;
		this.event = event;
		this.oracle = oracle;
		this.transition = transition;
		this.depth = depth;
		this.alpha = alpha;
		this.beta = beta;
		this.player = player;
	}
	
	private boolean checkUnique(List<Transition> transitions) {
		boolean unique = true;
		for (int i=0; i<transitions.size(); ++i) {
			for (int j = i+1; j <transitions.size(); ++j) {
				if (transitions.get(i).out.hash64 == transitions.get(j).out.hash64) {
					unique = false;
					return unique;
				}
			}
		}
		return unique;
	}
	
	public int alphaBeta(Transition transition, int depth, int alpha, int beta,
			int player) {
		if ((depth == 0) || AlphaBetaAI.isGameOver(transition.in)) {
			//System.out.println("TERMINATE PLAYER# " + player + " WITH: " + oracle.getProphecy(transition, player));
			//System.out.println(new Date() + " END");
			return oracle.getProphecy(transition, player); // player
		}
		long startTime, elapsedTime;
		//startTime = System.nanoTime();
		List<Transition> moves = transition.getNextGeneration(player);
		//elapsedTime = System.nanoTime() - startTime;
		//System.out.println("GENEROWANIE NASTÊPNIKÓW:\t" + elapsedTime + "ns");
		//System.out.println("SIZE =  " + moves.size());
		
		//startTime = System.nanoTime();
		if (player == 1) {
			Collections.sort(moves, new TransitionComparator(
					Oracle.PLAYER.PLAYER1, TransitionComparator.ORDER.DESC));
		} else {
			Collections.sort(moves, new TransitionComparator(
					Oracle.PLAYER.PLAYER2, TransitionComparator.ORDER.DESC));
		}
		//elapsedTime = System.nanoTime() - startTime;
		//System.out.println("SORTOWANIE DZIECI:\t\t" + elapsedTime + "ns");
		
		int value;
		for (Transition m : moves) {
			value = -alphaBeta(m, depth - 1, -beta, -alpha, 3 - player);
			if (value > alpha)
				alpha = value;
			if (alpha >= beta) {
				/*if (depth > 3) {
					++cutOffs;
					System.out.println("ODCIÊCIE! @ LVL: " + depth + " # " + cutOffs);
				}*/
				return beta;
			}
		}
		//moves.clear();
		
		return alpha;
	}

	public void run() {
		int value = -alphaBeta(transition, depth, alpha, beta, player);
		event.threadEnd(threadId, value);
	}
}


interface ThreadEndEvent {
	public void threadEnd(int threadId, int value);
}

