package PacDaddyApplicationInterfaces;

import java.util.ArrayList;

import Engine.GameAttributes;

public interface PacDaddyBoardReader {
	int[][] getTiledBoard();
	String[] getTileNames();
	ArrayList<GameAttributes> getInfoForAllPactorsWithAttribute(String attribute);
}
