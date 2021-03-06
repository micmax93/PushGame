package ai.push.logic.ai;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import ai.push.logic.Logic;
import ai.push.logic.Transition;
import ai.push.logic.oracle.DelphiOracle;
import ai.push.logic.oracle.DistancesEgoisticOracle;
import ai.push.logic.oracle.GreedyTransitionComparator;
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
		oracle = new DistancesEgoisticOracle(1, 2);
		//oracle = new DelphiOracle(1, 2);
		usingDelay=true;
	}
	
	@Override
	/**
	 * Algorym wybiera zach�annie najbardziej korzystny ruch.
	 */
	protected void algorithm() {
		System.out.println("G " + new Date());

		List<Transition> listSorted = list;

		Collections.sort(listSorted, new GreedyTransitionComparator(oracle,
				player, GreedyTransitionComparator.ORDER.DESC));
		Transition decision = listSorted.get(0);

		boolean found = false;
		List<Transition> next;

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
		}
		result = decision.mainMove;
	}
}
