package Engine;

import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.JsePlatform;

final public class Utilities {
	
	public static int[][] StringToIntArray(String str) {
		int numTiles = str.length();
		final int COLS = str.indexOf('\n');
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
	
	public static void runLuaScript(String script) {
		Globals globals = JsePlatform.standardGlobals();
		LuaValue chunk = globals.loadfile(script);
		chunk.call( LuaValue.valueOf(script) );
	}
	
}
