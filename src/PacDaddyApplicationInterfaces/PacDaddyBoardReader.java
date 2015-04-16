package PacDaddyApplicationInterfaces;

public interface PacDaddyBoardReader {
	int[][] getTiledBoard();
	String[] getTileNames();
	PacDaddyAttributeReader getAttributeReaderAtTile(int row, int col);
}
