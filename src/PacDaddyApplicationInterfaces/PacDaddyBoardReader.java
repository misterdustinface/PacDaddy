package PacDaddyApplicationInterfaces;

import Engine.GameAttributes;

public interface PacDaddyBoardReader {
	int[][] getTiledBoard();
	String[] getTileNames();
	GameAttributes[] getInfoForAllPactorsWithAttribute(String attribute);
}
