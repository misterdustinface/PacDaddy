package PacDaddyApplicationInterfaces;

public interface PacDaddyBoardReader {
	int[][] getTiledBoard();
	String[] getTileNames();
	PadDaddyAttributeReader getAttributeReaderAtTile(int row, int col);
}
