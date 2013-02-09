package ai.push.logic;

import java.awt.Color;

/**
 * Zawiera podstawowe ustawienia gry.
 * @author micmax93
 *
 */
public class Settings
{
	static public Color C1 = Color.BLUE, C2 = Color.RED;
	static public boolean AI1 = true, AI2 = true;
	static public int size = 8, rowCount = 2;
	static public int delay=250;
	
	private Color _C1, _C2;
	private boolean _AI1, _AI2;
	private int _size, _rowCount;
	private int _delay;
	
	private Settings() {}
	
	public Settings getSettings()
	{
		Settings sets=new Settings();
		sets._C1=C1;
		sets._C2=C2;
		sets._AI1=AI1;
		sets._AI2=AI2;
		sets._size=size;
		sets._rowCount=rowCount;
		sets._delay=delay;
		return sets;
	}
	
	public void loadSettings()
	{
		C1=_C1;
		C2=_C2;
		AI1=_AI1;
		AI2=_AI2;
		size=_size;
		rowCount=_rowCount;
		delay=_delay;
	}
}