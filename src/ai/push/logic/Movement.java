package ai.push.logic;

/**
 * Opisuje ruch pionka z jednego pola na drugie.
 * @author micmax93
 */
public class Movement
{
	public Field origin, destination;
	public byte distance, chainSize;
	/**
	 * K�t kt�ry jest zdefiniowany jako liczba ca�kowita z przedzia�u <0;7>.
	 * K�t jest zdefiniowany zgodnie z ruchem wskaz�wek zegara od 0.
	 * 7 | 0 | 1
	 * 6 | X | 2
	 * 5 | 4 | 3
	 */
	public byte angle = 0;

	/**
	 * Konstruktor wyliczaj�cy pole docelowe.
	 * @param src
	 * Pole �r�d�owe.
	 * @param ang
	 * K�t ruchu.
	 * @param dist
	 * Odleg�o�� jak� przeb�dzie ruch.
	 */
	public Movement(Field src, byte ang, byte dist) {
		origin = src;
		distance = dist;
		angle = ang;

		int vert = 0, horiz = 0;

		if ((angle > 0) && (angle < 4)) {
			horiz = 1;
		}
		else if ((angle > 4) && (angle < 8)) {
			horiz = -1;
		}

		if ((angle < 2) || (angle > 6)) {
			vert = -1;
		}
		else if ((angle > 2) && (angle < 6)) {
			vert = 1;
		}

		// TODO check correctness!
//		destination = new Field();
//		destination.row = (byte) (origin.row + distance * vert);
//		destination.column = (byte) (origin.column + distance * horiz);
		destination = FieldsStaticStorage.getField(origin.row + distance * vert, origin.column + distance * horiz);
	}
	
	/**
	 * Konstruktor wyliczaj�cy pole docelowe.
	 * Za dystans przyjmujemy 1. 
	 * @param src
	 * Pole �r�d�owe.
	 * @param ang
	 * K�t ruchu.
	 */
	public Movement(Field src, byte ang) {
		this(src, ang, (byte)1);
	}

	/**
	 * Konstruktor wyliczaj�cy k�t i odleg�o��.
	 * @param src
	 * Pole ��d�owe.
	 * @param dest
	 * Pole docelowe.
	 */
	public Movement(Field src, Field dest) {
		origin = src;
		destination = dest;

		byte vert = (byte) (destination.row - origin.row);
		byte horiz = (byte) (destination.column - origin.column);

		if (vert == 0) {
			if (horiz > 0) {
				angle = 2;
				distance = horiz;
			} else if (horiz < 0) {
				angle = 6;
				distance = (byte) -horiz;
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
				distance = (byte) -vert;
			}
		} else {
			if (Math.abs(vert) == Math.abs(horiz)) {
				distance = (byte) Math.abs(vert);
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
//		this.origin = new Field(movement.origin);
		this.origin = movement.origin;
//		this.destination = new Field(movement.destination);
		this.destination = movement.destination;
		this.distance = movement.distance;
		this.angle = movement.angle;
	}
	
	
	/**
	 * Sprawdza czy ruch jest poprawny, ale bez uwzgl�dnienia innych pionk�w.
	 */
	
	public boolean isValid() {
		// wersja 3
		if (distance < 4 && destination != null)
			return true;
		return false;
		
		/*
		// wersja 2
		if (destination == null)
			return false;
		if ((distance > 0) && (distance <= 3))
			return true;
		return false;
		*/
		
		/*
		// wersja 1
		if ((angle >= 0) && (angle < 8)) {
			if ((distance > 0) && (distance <= 3)) {
				if (origin.isValid() && destination.isValid()) {
					return true;
				}
			}
		}
		return false;
		*/
	}
	

	/**
	 * Generuje ruch w kt�rym pole docelowe jest przesuni�te o pole dalej.
	 * @return
	 */
	public Movement next() {
		Movement nxt = new Movement(origin, angle, (byte) (distance + 1));
		if (nxt.isValid()) {
			return nxt;
		} else {
			return null;
		}
	}
}
