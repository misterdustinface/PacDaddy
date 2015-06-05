package Engine;

import java.util.Arrays;

class TileWorld {

	volatile private int[][] tileWorld;
	volatile private String[] tilenamesarray;
	
	TileWorld() {
		tileWorld = new int[][]{};
		tilenamesarray = new String[]{};
	}
	
	public void loadFromString(String worldstring) {
		tileWorld = Utilities.StringToIntArray(worldstring);
	}
	
	public void addTileType(String name) {
		if (!doesTileTypeAlreadyExist(name)) {
			tilenamesarray = Arrays.copyOf(tilenamesarray, tilenamesarray.length + 1);
			tilenamesarray[tilenamesarray.length - 1] = name;
		}
	}
	
	public String[] getTileNames() {
		return tilenamesarray;
	}
	
	public int[][] getTiledBoard() {
		return tileWorld;
	}
	
	public int getTile(int row, int col) {
		return tileWorld[row][col];
	}
	
	public int getRows() {
		return tileWorld.length;
	}
	
	public int getCols() {
		return tileWorld[0].length;
	}
	
	void wrapTileCoordinateToWorldBounds(TileCoordinate c) {
		if (c.row >= getRows()) {
			c.row = 0;
		} else if (c.row < 0) {
			c.row = getRows() - 1;
		}
		
		if (c.col >= getCols()) {
			c.col = 0;
		} else if (c.col < 0) {
			c.col = getCols() - 1;
		}
	}
	
	void swap(TileCoordinate A, TileCoordinate B) {
		int TEMP = tileWorld[A.row][A.col];
		tileWorld[A.row][A.col] = tileWorld[B.row][B.col];
		tileWorld[B.row][B.col] = TEMP;
	}
	
	private boolean doesTileTypeAlreadyExist(String name) {
		for (String s : tilenamesarray) {
			if (s == name) {
				return true;
			}
		}
		return false;
	}
	
}