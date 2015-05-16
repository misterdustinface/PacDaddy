## Interfacing

* PacDaddyApplication
	* PacDaddyBoardReader getBoardReader();
	* PacDaddyAttributeReader getGameAttributeReader();
	* PacDaddyInput getInputProcessor();

* PacDaddyBoardReader
	* int[][] getTiledBoard();
	* String[] getTileNames();
	* GameAttributes[] getInfoForAllPactorsWithAttribute(String attribute);

* PacDaddyAttributeReader
  * Object getValueOf(String attributeName);
  * String[] getAttributes();

* PacDaddyInput
  * void sendCommand(String command);
  * String[] getCommands();
