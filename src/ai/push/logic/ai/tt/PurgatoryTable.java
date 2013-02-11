package ai.push.logic.ai.tt;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import ai.push.logic.Board;

public class PurgatoryTable implements TranspositionHashStorage {
	private Map<String, Transposition> transpositions;
	private Map<String, TranspositionSoul> purgatory;
	private final int stPeterKey = 10;
	
	
	public PurgatoryTable() {
		this.transpositions = new ConcurrentHashMap<String, Transposition>(100000);
		this.purgatory = new ConcurrentHashMap<String, TranspositionSoul> (100000);
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

class TranspositionSoul {
	private Transposition transposition;
	private int stats;
	
	public TranspositionSoul(Transposition t) {
		transposition = t;
		stats = 0;
	}
	
	public void incrementStats() {
		++stats;
	}
	
	public Transposition getTransposition() {
		return transposition;
	}
	
	public int getStats() {
		return stats;
	}
}
