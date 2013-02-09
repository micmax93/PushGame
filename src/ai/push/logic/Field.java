package ai.push.logic;

/**
 * Przechwuje wsp�rz�dne na szachownicy.
 * @author micmax93
 *
 */
public class Field
{
	public int row, column;

	public Field()
	{
	}

	public Field(int row, int column)
	{
		this.row = row;
		this.column = column;
	}
	
	/**
	 * Konstruktor kopiuj�cy
	 * @param field
	 */
	public Field(Field field) {
		this.row = field.row;
		this.column = field.row;
	}

	/**
	 * Sprawdza czy wsp�rz�dne s� poprawne, czyli czy znajduj� si� na szachownicy.
	 */
	public boolean isValid()
	{
		if ((row >= 0) && (row < Settings.size))
		{
			if ((column >= 0) && (column < Settings.size))
			{
				return true;
			}
		}
		return false;
	}
}
