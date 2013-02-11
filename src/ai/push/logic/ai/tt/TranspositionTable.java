package ai.push.logic.ai.tt;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import ai.push.logic.Board;

public class TranspositionTable implements TranspositionHashStorage {
	private Map<String, Transposition> transpositions;
	
	public static final byte[][] CHAR_ID_8X8 = new byte[][] {
		{ 0,  0,  0,  0,  0,  1,  1,  1},
		{ 1,  1,  2,  2,  2,  2,  2,  3},
		{ 3,  3,  3,  3,  4,  4,  4,  4},
		{ 4,  5,  5,  5,  5,  5,  6,  6},
		{ 6,  6,  6,  7,  7,  7,  7,  7},
		{ 8,  8,  8,  8,  8,  9,  9,  9},
		{ 9,  9, 10, 10, 10, 10, 10, 11},
		{11, 11, 11, 11, 12, 12, 12, 12}		
	};
	
	public static final byte[][] BIT_MASK_8X8 = new byte[][] {
		{ 0,  3,  6,  9, 12,  0,  3,  6},
		{ 9, 12,  0,  3,  6,  9, 12,  0},
		{ 3,  6,  9, 12,  0,  3,  6,  9},
		{12,  0,  3,  6,  9, 12,  0,  3},
		{ 6,  9, 12,  0,  3,  6,  9, 12},
		{ 0,  3,  6,  9, 12,  0,  3,  6},
		{ 9, 12,  0,  3,  6,  9, 12,  0},
		{ 3,  6,  9, 12,  0,  3,  6,  9}		
	};
	
	//public static final char[] hashPattern = new char[] {0, 0, 0, 0, 0, 0, 0, 0};
	
	public TranspositionTable() {
		this.transpositions = new ConcurrentHashMap<String, Transposition>(200000000, 0.9f);
	}
	
	private String hash(Board b) {
		/*
		char[] hash = hashPattern.clone();
		int size = b.getWidth() - 1;
		for (int r = size; r >= 0; --r) {
			for (int c = size; c >= 0; --c) {
				if (b.tab[r][c] == 1) {
					hash[r] <<= 2;
					hash[r] |= 0b01;
				}
				else if (b.tab[r][c] == 2) {
					hash[r] <<= 2;
					hash[r] |= 0b10;
				}
				else {
					hash[r] <<= 2;
				}
			}
		}
		*/
		
//		StringBuilder hash = new StringBuilder();
//		int size = b.getWidth();
//		for (int r = 0; r < size; ++r) {
//			for (int c = 0; c < size; ++c) {
//				hash.append(String.valueOf(b.tab[r][c]));
//			}
//		}
//		return hash.toString();
		
		StringBuilder hash = new StringBuilder();
		int size = b.getWidth() - 1;
		for (int r = size; r >= 0; --r) {
			for (int c = size; c >= 0; --c) {
				hash.append(String.valueOf(b.tab[r][c]));
			}
		}
		return hash.toString();
		
	}
	
	public Transposition get(Board b) {
		String key = hash(b);
		return transpositions.get(key);
	}
	
	@Override
	public Transposition get(String key) {
		return transpositions.get(key);
	}
	
	public void put(Board b, Transposition t) {
		String key = hash(b);
		if (transpositions.containsKey(key)) {
			transpositions.remove(key);
		}
		transpositions.put(key, t);
	}
	
	@Override
	public void put(String key, Transposition t) {
		if (transpositions.containsKey(key)) {
			transpositions.remove(key);
		}
		transpositions.put(key, t);
	}
	
	public void remove(Board b) {
		String key = hash(b);
		if (transpositions.containsKey(key)) {
			transpositions.remove(key);
		}
	}
	
	@Override
	public void remove(String key) {
		if (transpositions.containsKey(key)) {
			transpositions.remove(key);
		}
	}
	
	public void clear() {
		transpositions.clear();
	}
	
	public int size() {
		return transpositions.size();
	}

	@Override
	public void clearSoft() {
		transpositions.clear();
		
	}

	@Override
	public void clearHard() {
		transpositions.clear();
		
	}

	@Override
	public void clearAll() {
		transpositions.clear();		
	}

	@Override
	public Transposition get(long key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void put(long key, Transposition t) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void remove(long key) {
		// TODO Auto-generated method stub
		
	}
}
