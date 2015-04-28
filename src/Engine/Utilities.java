package Engine;

final public class Utilities {
	
	final public static String WINDOWS_NEWLINE = "\r\n";
	
	public static int[][] StringToIntArray(String str) {
		if (str == null) {
			return new int[][] {};
		}
		
		String[] data;
		
		if (str.contains(WINDOWS_NEWLINE)) {
			data = str.split(WINDOWS_NEWLINE);
		} else {
			data = str.split(System.lineSeparator());
		}
        
		final int ROWS = data.length;
		final int COLS = data[0].length();
		
		int[][] ar = new int[ROWS][COLS];
		
		char tilechar;
		for (int row = 0; row < ROWS; row++) {
			for (int col = 0; col < COLS; col++) {
				tilechar = data[row].charAt(col);
				if (Character.isDigit(tilechar)) {
					ar[row][col] = Character.getNumericValue(tilechar);
				}
			}
		}
		
		return ar;
	}
	
}