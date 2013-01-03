package ai.push.logic.oracle;

import java.util.Comparator;

import ai.push.logic.Transition;

/**
 * Klasa pozwalaj�ca na por�wnywanie tranzycji za pomoc� wyroczni. Umo�liwia
 * sortowanie w obu kierunkach.
 * 
 * @author blacksmithy
 * 
 */
public class TransitionComparator implements Comparator<Transition> {

	/**
	 * Okre�la porz�dek sortowania. ASC - rosn�cy (domy�lny), DESC - malej�cy.
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
	 * Konstruktor tworz�cy komparator w porz�dku rosnacym (domyslnym).
	 * 
	 * @param oracle
	 *            Wyrocznia, za pomoc� kt�rej b�d� por�wnywane tranzycje.
	 * @param player
	 *            Gracz, dla kt�rego maj� by� por�wnywane tranzycje.
	 */
	public TransitionComparator(Oracle oracle, Oracle.PLAYER player) {
		this.oracle = oracle;
		this.player = player;
		this.comparisionOrder = ORDER.ASC;
	}

	/**
	 * Konstruktor tworz�cy komparator w wybranym porz�dku.
	 * 
	 * @param oracle
	 *            Wyrocznia, za pomoc� kt�rej b�d� por�wnywane tranzycje.
	 * @param player
	 *            Gracz, dla kt�rego maj� by� por�wnywane tranzycje.
	 * @param order
	 *            Porz�dek sortowania: ASC (rosn�cy), DESC (malej�cy).
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
