package ai.push.logic.ai;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import ai.push.logic.Logic;
import ai.push.logic.Transition;
import ai.push.logic.oracle.DistancesOracle;
import ai.push.logic.oracle.Oracle;

/**
 * Przyk³adowa implementacja AI dla modelu zach³annego.
 * 
 * @author blacksmithy
 * 
 */
public class GreedyAI extends AbstractAI {
	public GreedyAI(Logic logic, Oracle.PLAYER player) {
		super(logic, player);
		oracle = new DistancesOracle(1, 2);
	}

	@Override
	/**
	 * Algorym wybiera zach³annie najbardziej korzystny ruch.
	 */
	protected void algorithm() {
		//System.out.println("Greedy beg for player: " + player + " at: " + new Date());
		Transition decision = null;
		List<Transition> planB = new ArrayList<Transition>();
		int max = Integer.MIN_VALUE;
		int val = 0;
		int previousMark = Integer.MIN_VALUE;
		if (list.size() > 0) {
			previousMark = oracle.getProphecy(list.firstElement().in, player);
		}
		else {
			System.out.println("\tempty list!!");
		}
		
		for(Transition t : list) {
			val = oracle.getProphecy(t.out, player);
			if (val < previousMark) {
				planB.add(t);
			}
			if (val > max) {
				max = val;
				decision = t;
			}
		}
		if (max == Integer.MIN_VALUE || decision == null)
			result = list.get(new Random().nextInt(list.size())).mainMove;
		else {
			if (max == previousMark && (! planB.isEmpty()))
				result = planB.get(new Random().nextInt(planB.size())).mainMove;
			else
				result = decision.mainMove;	
		}
		//System.out.println("Greedy end for player: " + player + " at: " + new Date());
	}

}
