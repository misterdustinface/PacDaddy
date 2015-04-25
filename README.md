# PacDaddy

## Interfacing
* PacDaddyApplication
	* PacDaddyBoardReader getBoardReader();
	* PacDaddyAttributeReader getGameAttributeReader();
	* PacDaddyInput getInputProcessor();

* PacDaddyBoardReader
	* int[][] getTiledBoard();
	* String[] getTileNames();
	* PacDaddyAttributeReader getAttributeReaderAtTile(int row, int col);

* PacDaddyAttributeReader
  * Object getValueOf(String attributeName);
	* String[] getAttributes();

* PacDaddyInput
  * void sendCommand(String command);
	* String[] getCommands();
