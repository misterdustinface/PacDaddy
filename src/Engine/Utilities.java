package Engine;


final public class Utilities {
	
	public static int[][] StringToIntArray(String str) {
		int numTiles = str.length();
		final int COLS = str.indexOf(System.lineSeparator());
		final int ROWS = numTiles / COLS;
		int[][] ar = new int[ROWS][COLS];
		
		char tilechar;
		int strindex = 0;
		
		for (int row = 0; row < ROWS; row++) {
			for (int col = 0; col < COLS; col++) {
				
				if (strindex < str.length()) {
					do {
						tilechar = str.charAt(strindex);
						strindex++;
					} while(!Character.isDigit(tilechar) && strindex < str.length());
					
					ar[row][col] = Character.getNumericValue(tilechar);
				}
				
			}
		}
		
		return ar;
	}
	
}