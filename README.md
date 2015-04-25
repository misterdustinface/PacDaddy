## PacDaddy

##### Index
0. [Interfacing](#interfacing)
1. [Features](#features)

##[Interfacing](#index)
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


##[Features](#index) 
  
