package ai.push.logic;

/**
 * Przechwuje wsp�rz�dne na szachownicy.
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
	 * Konstruktor kopiuj�cy
	 * @param field
	 */
	/*
	public Field(Field field) {
		this.row = field.row;
		this.column = field.row;
	}
	*/

	/**
	 * Sprawdza czy wsp�rz�dne s� poprawne, czyli czy znajduj� si� na szachownicy.
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
