package ai.push.logic.ai.tt;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import ai.push.logic.Board;

public class TranspositionTable implements TranspositionHashStorage {
	private Map<String, Transposition> transpositions;
	
	public TranspositionTable() {
		this.transpositions = new ConcurrentHashMap<String, Transposition>(50000000, 1.0f);
	}
	
	private String hash(Board b) {
		StringBuilder hash = new StringBuilder();
		int size = b.getWidth();
		for (int r = 0; r < size; ++r) {
			for (int c = 0; c < size; ++c) {
				hash.append(String.valueOf(b.tab[r][c]));
			}
		}
		return hash.toString();
	}
	
	public Transposition get(Board b) {
		String key = hash(b);
		return transpositions.get(key);
	}
	
	public void put(Board b, Transposition t) {
		String key = hash(b);
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
	
}
