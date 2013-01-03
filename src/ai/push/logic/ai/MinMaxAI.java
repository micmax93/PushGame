package ai.push.logic.ai;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Vector;

import ai.push.logic.Logic;
import ai.push.logic.Transition;
import ai.push.logic.oracle.DistancesEgoisticOracle;
import ai.push.logic.oracle.Oracle;

/**
 * Przyk³adowa implementacja AI dla modelu opartego na algorytmie MinMax z
 * odciêciami alfa-beta.
 * 
 * @author blacksmithy
 * 
 */
public class MinMaxAI extends AbstractAI {
	public MinMaxAI(Logic logic, Oracle.PLAYER player) {
		super(logic, player);
		oracle = new DistancesEgoisticOracle(1, 2);
	}

	@Override
	/**
	 * Algorym wybiera najlepszy ruch, analizuj¹c do przodu mozliwe ruchy, 
	 * do okreœlonej g³êbokoœci (maxDepth). Jesli odleg³oœæ nie zosta³a ustalona, 
	 * zostanie przyjêta wartoœæ domyslna.
	 */
	protected void algorithm() {
		maxDepth = 3;
		Transition decision = null;

		result = decision.mainMove;
	}
}
