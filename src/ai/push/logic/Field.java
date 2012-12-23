package ai.push.logic;

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
