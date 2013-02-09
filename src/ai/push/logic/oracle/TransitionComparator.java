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
		int a1, a2, d1, d2;
		if (player == Oracle.PLAYER.PLAYER1) { // na górze
			if (t1.mainMove.angle == 4 || t1.mainMove.angle == 5 || t1.mainMove.angle == 3)
				a1 = 1; // do przodu
			else if (t1.mainMove.angle == 6 || t1.mainMove.angle == 2)
				a1 = 0; // ruch w linii
			else
				a1 = -1;
			
			if (t2.mainMove.angle == 4 || t2.mainMove.angle == 5 || t2.mainMove.angle == 3)
				a2 = 1; // do przodu
			else if (t2.mainMove.angle == 6 || t2.mainMove.angle == 2)
				a2 = 0; // ruch w linii
			else
				a2 = -1;
		}
		else { // dla gracza 2 - na dole planszy
			if (t1.mainMove.angle == 7 || t1.mainMove.angle == 0 || t1.mainMove.angle == 1)
				a1 = 1;
			else if (t1.mainMove.angle == 6 || t1.mainMove.angle == 2)
				a1 = 0;
			else
				a1 = -1;
			
			if (t2.mainMove.angle == 7 || t2.mainMove.angle == 0 || t2.mainMove.angle == 1)
				a2 = 1;
			else if (t2.mainMove.angle == 6 || t2.mainMove.angle == 2)
				a2 = 0;
			else
				a2 = -1;
		}
		
		if (a1 > a2) {
			if (comparisionOrder == ORDER.DESC)
				return -1;
			else
				return 1;
		}
		else if (a1 < a2) {
			if (comparisionOrder == ORDER.DESC)
				return 1;
			else
				return -1;
		}
		else {
			d1 = t1.mainMove.distance;
			d2 = t2.mainMove.distance;
			if (d1 > d2) {
				if (comparisionOrder == ORDER.DESC)
					return -1;
				else
					return 1;
			}
			else if (d1 < d2) {
				if (comparisionOrder == ORDER.DESC)
					return 1;
				else
					return -1;
			}
			else
				return 0;
		}
	}
	
	/*// stary komparator
	public int compare(Transition t1, Transition t2) {
		int val1 = oracle.getProphecy(t1, player);
		int val2 = oracle.getProphecy(t2, player);
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
	*/

}
