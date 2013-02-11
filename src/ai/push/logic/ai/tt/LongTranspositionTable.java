package ai.push.logic.ai.tt;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import ai.push.logic.Board;

public class LongTranspositionTable implements TranspositionHashStorage {
	private Map<Long, Transposition> transpositions;
	
	
	public LongTranspositionTable() {
		this.transpositions = new ConcurrentHashMap<Long, Transposition>();
	}
	
	private String hash(Board b) {
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
		return transpositions.get(b.hash64);
	}
	
	@Override
	public Transposition get(long key) {
		return transpositions.get(key);
	}
	
	public void put(Board b, Transposition t) {
		if (transpositions.containsKey(b.hash64)) {
			transpositions.remove(b.hash64);
		}
		transpositions.put(b.hash64, t);
	}
	
	@Override
	public void put(long key, Transposition t) {
		if (transpositions.containsKey(key)) {
			transpositions.remove(key);
		}
		transpositions.put(key, t);
	}
	
	public void remove(Board b) {
		if (transpositions.containsKey(b.hash64)) {
			transpositions.remove(b.hash64);
		}
	}
	
	@Override
	public void remove(long key) {
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
	public Transposition get(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void put(String key, Transposition t) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void remove(String key) {
		// TODO Auto-generated method stub
		
	}
}
