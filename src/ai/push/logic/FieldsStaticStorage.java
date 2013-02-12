package ai.push.logic;

public class FieldsStaticStorage {

	private static Field[][] fields = null;
	private static int size = 8;

	private FieldsStaticStorage() {	}

	public static Field getField(byte row, byte column) {
		if (fields == null) {
			fields = new Field[size][size];
			for (byte i = 0; i < size; ++i) {
				for (byte j = 0; j < size; ++j) {
					fields[i][j] = new Field(i, j);
				}
			}
		}
		Field f = null;
		try {
			f = fields[row][column];
		} catch (ArrayIndexOutOfBoundsException e) {
		}
		return f;
	}
	
	public static Field getField(int row, int column) {
		if (fields == null) {
			fields = new Field[size][size];
			for (byte i = 0; i < size; ++i) {
				for (byte j = 0; j < size; ++j) {
					fields[i][j] = new Field(i, j);
				}
			}
		}
		Field f = null;
		try {
			f = fields[row][column];
		} catch (ArrayIndexOutOfBoundsException e) {
		}
		return f;
	}

}
