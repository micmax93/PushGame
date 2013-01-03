package ai.push.logic.oracle;

import java.util.Comparator;

import ai.push.logic.Transition;

/**
 * Klasa pozwalaj¹ca na porównywanie tranzycji za pomoc¹ wyroczni. Umo¿liwia
 * sortowanie w obu kierunkach.
 * 
 * @author blacksmithy
 * 
 */
public class TransitionComparator implements Comparator<Transition> {

	/**
	 * Okreœla porz¹dek sortowania. ASC - rosn¹cy (domyœlny), DESC - malej¹cy.
	 * 
	 * @author blacksmithy
	 * 
	 */
	public static enum ORDER {
		ASC, DESC
	}

	protected ORDER comparisionOrder;
	protected Oracle.PLAYER player;
	protected Oracle oracle;

	/**
	 * Konstruktor tworz¹cy komparator w porz¹dku rosnacym (domyslnym).
	 * 
	 * @param oracle
	 *            Wyrocznia, za pomoc¹ której bêd¹ porównywane tranzycje.
	 * @param player
	 *            Gracz, dla którego maj¹ byæ porównywane tranzycje.
	 */
	public TransitionComparator(Oracle oracle, Oracle.PLAYER player) {
		this.oracle = oracle;
		this.player = player;
		this.comparisionOrder = ORDER.ASC;
	}

	/**
	 * Konstruktor tworz¹cy komparator w wybranym porz¹dku.
	 * 
	 * @param oracle
	 *            Wyrocznia, za pomoc¹ której bêd¹ porównywane tranzycje.
	 * @param player
	 *            Gracz, dla którego maj¹ byæ porównywane tranzycje.
	 * @param order
	 *            Porz¹dek sortowania: ASC (rosn¹cy), DESC (malej¹cy).
	 */
	public TransitionComparator(Oracle oracle, Oracle.PLAYER player, ORDER order) {
		this.oracle = oracle;
		this.player = player;
		this.comparisionOrder = order;
	}

	@Override
	public int compare(Transition t1, Transition t2) {
		int val1 = oracle.getProphecy(t1.out, player);
		int val2 = oracle.getProphecy(t2.out, player);
		if (val1 > val2) {
			if (comparisionOrder == ORDER.DESC)
				return -1;
			else
				return 1;
		}
		if (val1 < val2) {
			if (comparisionOrder == ORDER.DESC)
				return 1;
			else
				return -1;
		} else
			return 0;
	}

}
