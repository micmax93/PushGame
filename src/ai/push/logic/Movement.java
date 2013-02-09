package ai.push.logic;

/**
 * Opisuje ruch pionka z jednego pola na drugie.
 * @author micmax93
 */
public class Movement
{
	public Field origin, destination;
	public int distance;
	/**
	 * K¹t który jest zdefiniowany jako liczba ca³kowita z przedzia³u <0;7>.
	 * K¹t jest zdefiniowany zgodnie z ruchem wskazówek zegara od 0.
	 * 7 | 0 | 1
	 * 6 | X | 2
	 * 5 | 4 | 3
	 */
	public int angle;

	/**
	 * Konstruktor wyliczaj¹cy pole docelowe.
	 * @param src
	 * Pole Ÿród³owe.
	 * @param ang
	 * K¹t ruchu.
	 * @param dist
	 * Odleg³oœæ jak¹ przebêdzie ruch.
	 */
	public Movement(Field src, int ang, int dist) {
		origin = src;
		distance = dist;
		angle = ang;

		int vert = 0, horiz = 0;

		if ((angle > 0) && (angle < 4)) {
			horiz = 1;
		}
		if ((angle > 4) && (angle < 8)) {
			horiz = -1;
		}

		if ((angle < 2) || (angle > 6)) {
			vert = -1;
		}
		if ((angle > 2) && (angle < 6)) {
			vert = 1;
		}

		destination = new Field();
		destination.row = origin.row + distance * vert;
		destination.column = origin.column + distance * horiz;
	}
	
	/**
	 * Konstruktor wyliczaj¹cy pole docelowe.
	 * Za dystans przyjmujemy 1. 
	 * @param src
	 * Pole Ÿród³owe.
	 * @param ang
	 * K¹t ruchu.
	 */
	public Movement(Field src, int ang) {
		this(src, ang, 1);
	}

	/**
	 * Konstruktor wyliczaj¹cy k¹t i odleg³oœæ.
	 * @param src
	 * Pole Ÿód³owe.
	 * @param dest
	 * Pole docelowe.
	 */
	public Movement(Field src, Field dest) {
		origin = src;
		destination = dest;

		int vert = destination.row - origin.row;
		int horiz = destination.column - origin.column;

		if (vert == 0) {
			if (horiz > 0) {
				angle = 2;
				distance = horiz;
			} else if (horiz < 0) {
				angle = 6;
				distance = -horiz;
			} else {
				angle = -1;
				distance = 0;
			}
		} else if (horiz == 0) {
			if (vert > 0) {
				angle = 4;
				distance = vert;
			} else if (vert < 0) {
				angle = 0;
				distance = -vert;
			}
		} else {
			if (Math.abs(vert) == Math.abs(horiz)) {
				distance = Math.abs(vert);
				if (vert < 0) {
					if (horiz > 0) {
						angle = 1;
					} else {
						angle = 7;
					}
				} else {
					if (horiz > 0) {
						angle = 3;
					} else {
						angle = 5;
					}
				}
			} else {
				angle = -1;
				distance = 0;
			}
		}
	}
	
	public Movement(Movement movement) {
		this.origin = new Field(movement.origin);
		this.destination = new Field(movement.destination);
		this.distance = movement.distance;
		this.angle = movement.angle;
	}
	
	
	/**
	 * Sprawdza czy ruch jest poprawny, ale bez uwzglêdnienia innych pionków.
	 */
	public boolean isValid() {
		if ((angle >= 0) && (angle < 8)) {
			if ((distance > 0) && (distance <= 3)) {
				if (origin.isValid() && destination.isValid()) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Generuje ruch w którym pole docelowe jest przesuniête o pole dalej.
	 * @return
	 */
	public Movement next() {
		Movement nxt = new Movement(origin, angle, distance + 1);
		if (nxt.isValid()) {
			return nxt;
		} else {
			return null;
		}
	}
}
