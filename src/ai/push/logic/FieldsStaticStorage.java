package ai.push.logic;

public class FieldsStaticStorage {

	private static Field[][] fields = null;

	private FieldsStaticStorage() {	}

	public static Field getField(byte row, byte column) {
		if (fields == null) {
			fields = new Field[12][12];
			for (byte i = 0; i < 12; ++i) {
				for (byte j = 0; j < 12; ++j) {
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
			fields = new Field[12][12];
			for (byte i = 0; i < 12; ++i) {
				for (byte j = 0; j < 12; ++j) {
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
