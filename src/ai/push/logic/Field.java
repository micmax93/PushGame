package ai.push.logic;

/**
 * Przechwuje wspó³rzêdne na szachownicy.
 * @author micmax93
 *
 */
public class Field
{
	public byte row;
	public byte column;

	//public Field()
	//{
	//}

	public Field(byte row, byte column)
	{
		this.row = row;
		this.column = column;
	}
	

	public Field(int row, int column)
	{
		this.row = (byte) row;
		this.column = (byte) column;
	}
	
	/*
	 * Konstruktor kopiuj¹cy
	 * @param field
	 */
	/*
	public Field(Field field) {
		this.row = field.row;
		this.column = field.row;
	}
	*/

	/**
	 * Sprawdza czy wspó³rzêdne s¹ poprawne, czyli czy znajduj¹ siê na szachownicy.
	 */
	/*
	public boolean isValid()
	{
		if ((row >= 0) && (column >= 0)  && (row < Settings.size) && (column < Settings.size)) {
			return true;
		}
//		if ((row >= 0) && (row < Settings.size))
//		{
//			if ((column >= 0) && (column < Settings.size))
//			{
//				return true;
//			}
//		}
		return false;
	}
	*/
}
