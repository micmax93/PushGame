package ai.push.logic.ai;

import java.util.Collections;
import java.util.List;
import java.util.Vector;

import ai.push.logic.Logic;
import ai.push.logic.Transition;
import ai.push.logic.oracle.DistancesOracle;
import ai.push.logic.oracle.Oracle;
import ai.push.logic.oracle.TransitionComparator;
import ai.push.logic.oracle.TransitionComparator.ORDER;

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
		List<Transition> listSorted = list;
		Collections.sort(listSorted, new TransitionComparator(oracle, player, ORDER.DESC));
		Transition decision = listSorted.get(0);
		boolean found = false;
		Vector<Transition> next;

		int opponentId = 0;
		Oracle.PLAYER opponent;
		if (player == Oracle.PLAYER.PLAYER1) {
			opponentId = 2;
			opponent = Oracle.PLAYER.PLAYER2;
		} else {
			opponentId = 1;
			opponent = Oracle.PLAYER.PLAYER1;
		}
		if (oracle.getProphecy(list.get(0).in, player) <= oracle.getProphecy(
				listSorted.get(0).out, player)) {
			// brak poprawy
			for (Transition t : listSorted) {
				next = t.getNextGeneration(opponentId);
				for (Transition n : next) {
					if (oracle.getProphecy(n.in, opponent) < oracle
							.getProphecy(n.out, opponent)) {
						// przeciwnik posiada jakikolwiek ruch poprawiaj¹cy
						decision = t;
						found = true;
						break;
					}
				}
				if (found) {
					break;
				}
			}
		}
		result = decision.mainMove;
	}
}
