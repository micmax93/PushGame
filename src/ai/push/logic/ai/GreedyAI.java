package ai.push.logic.ai;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Vector;

import ai.push.logic.Logic;
import ai.push.logic.Transition;
import ai.push.logic.oracle.DistancesOracle;
import ai.push.logic.oracle.Oracle;

/**
 * Przyk�adowa implementacja AI dla modelu zach�annego.
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
	 * Algorym wybiera zach�annie najbardziej korzystny ruch.
	 */
	protected void algorithm() {
		List<Transition> listSorted = list;
		Collections.sort(listSorted, new Comparator<Transition>() {

			@Override
			public int compare(Transition t1, Transition t2) {
				int val1 = oracle.getProphecy(t1.out, player);
				int val2 = oracle.getProphecy(t2.out, player);
				if (val1 > val2)
					return -1;
				if (val1 < val2)
					return 1;
				else
					return 0;
			}
		});
		Transition decision = listSorted.get(0);
		boolean found = false;
		Vector<Transition> next;
		
		int opponentId = 0;
		Oracle.PLAYER opponent;
		if(player == Oracle.PLAYER.PLAYER1) {
			opponentId = 2;
			opponent = Oracle.PLAYER.PLAYER2;
		}
		else {
			opponentId = 1;
			opponent = Oracle.PLAYER.PLAYER1;
		}
		if (oracle.getProphecy(list.get(0).in, player) <= oracle.getProphecy(
				listSorted.get(0).out, player)) {
			// brak poprawy
			//System.out.println("Mo�liwa kolizja! " + new Date());
			for (Transition t : listSorted) {
				next = t.getNextGeneration(opponentId);
				for (Transition n : next) {
					if (oracle.getProphecy(n.in, opponent) < oracle.getProphecy(n.out, opponent)) {
						// przeciwnik posiada jakikolwiek ruch poprawiaj�cy
						decision = t;
						found = true;
						break;
					}
				}
				if (found) {
					break;
				}
			}
			//if (! found)
				//System.out.println("\tnie znaleziono ruchu...");
		}
		result = decision.mainMove;
	}
}
