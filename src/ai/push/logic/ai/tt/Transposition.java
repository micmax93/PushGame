package ai.push.logic.ai.tt;

import ai.push.logic.Transition;

public class Transposition {
	
	public static enum VALUE_TYPE {
		UPPER, LOWER, ACCURATE
	};
	
	private int value;
	private VALUE_TYPE type;
	private int depth;
	private Transition next;
	
	public Transposition() {}
	
	public Transposition(int value, VALUE_TYPE type, int depth, Transition next) {
		this.value = value;
		this.type = type;
		this.depth = depth;
		this.next = next;
	}
	
	public Transposition(int value, int depth, int alpha, int beta, Transition next) {
		this(value, VALUE_TYPE.ACCURATE, depth, next);
		if (value <= alpha)
			type = VALUE_TYPE.UPPER;
		else if (value >= beta)
			type = VALUE_TYPE.LOWER;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public VALUE_TYPE getType() {
		return type;
	}

	public void setType(VALUE_TYPE type) {
		this.type = type;
	}

	public int getDepth() {
		return depth;
	}

	public void setDepth(int depth) {
		this.depth = depth;
	}

	public Transition getNext() {
		return next;
	}

	public void setNext(Transition next) {
		this.next = next;
	}
	
	
}
