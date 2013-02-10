package ai.push.logic.ai;

import java.util.Random;

import ai.push.logic.Logic;
import ai.push.logic.oracle.Oracle;

/**
 * Przyk³adowa implementacja AI dla modelu losowego.
 * 
 * @author micmax93
 * 
 */
public class RandomAI extends AbstractAI {
	public RandomAI(Logic logic, Oracle.PLAYER player) {
		super(logic, player);
		usingDelay=true;
	}

	@Override
	/**
	 * Algorym wybiera losowy ruch.
	 */
	protected void algorithm() {
		result = list.get(new Random().nextInt(list.size())).mainMove;
	}

}
