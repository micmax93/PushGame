package ai.push.logic.ai.tt;

import ai.push.logic.Board;

public interface TranspositionHashStorage {
		
	public Transposition get(Board b);
	
	public Transposition get(String key);
	
	public void put(Board b, Transposition t);
	
	public void put(String key, Transposition t);
	
	public void remove(Board b);
	
	public void remove(String key);
	
	public void clearSoft();
	
	public void clearHard();
	
	public void clearAll();
	
	public int size();
}
