package Engine;

import java.util.ArrayList;
import java.util.Arrays;

import PacDaddyApplicationInterfaces.PacDaddyBoardReader;
import PacDaddyApplicationInterfaces.PacDaddyInput;
import PacDaddyApplicationInterfaces.PadDaddyAttributeReader;
import base.TickingLoop;
import functionpointers.VoidFunctionPointer;

public class PacDaddyMainLoop extends TickingLoop implements PacDaddyInput, PacDaddyBoardReader {
	
	//final private static String[] INPUT_COMMANDS = {"UP", "DOWN", "LEFT", "RIGHT", "NONE"};
	final private static String[] TILE_NAMES = {"FLOOR", "WALL", "GHOST", "PACMAN", "DOT", "BIGDOT", "FRUIT"};
	
	final private Pactor pacman;
	final private ArrayList<Pactor> enemies;
	
	private String input;
	private int ROWS, COLS;
	private int[][] wallworld;
	
	public PacDaddyMainLoop() {
		input = "NONE";
		ROWS = 10; 
		COLS = 10;
		wallworld = new int[ROWS][COLS];
//		wallworld[5][5] = 1;
//		wallworld[4][5] = 1;
//		wallworld[5][4] = 1;
//		wallworld[4][4] = 1;
		pacman = new Pactor(this);
		enemies = new ArrayList<Pactor>();
		
		addFunction(new VoidFunctionPointer() {
			public void call() {
				pacman.performAction(input);
				for (Actor enemy : enemies) {
					enemy.performAction("ATTACK PLAYER");
				}
			}
		});
	}
	
	public PadDaddyAttributeReader getAttributeReaderAtTile(int row, int col) {
		return pacman; // TODO FIXME - make search algorithm to grab pactor at coordinate
	}
	
	
	public boolean isWall(int row, int col) {
		return row < 0 || col < 0 || row >= ROWS || col >= COLS || wallworld[row][col] == 1;
	}

	
	final void newEnemy() {
		Pactor newEnemy = new Pactor(this);
		newEnemy.learnAction("ATTACK PLAYER", new VoidFunctionPointer() {
			public void call() {
				
			}
		});
		enemies.add(newEnemy);
	}
	
	private int[][] getWallWorldCopy() {
		int[][] wallWorldCopy = new int[ROWS][];
		for (int row = 0; row < ROWS; row++) {
			wallWorldCopy[row] = Arrays.copyOf(wallworld[row], wallworld[row].length);
		}
		return wallWorldCopy;
	}
	
	final public int[][] getTiledBoard() {
		
		int[][] worldRepresentation = getWallWorldCopy();
		
		TileCoordinate actorCoordinate = pacman.getTileCoordinate();
		worldRepresentation[actorCoordinate.row][actorCoordinate.col] = 3;
		
		for (Pactor enemy : enemies) {
			actorCoordinate = enemy.getTileCoordinate();
			worldRepresentation[actorCoordinate.row][actorCoordinate.col] = 2;
		}
		
		return worldRepresentation;
	}
	
	final public void sendCommand(String command) {
		input = command;
	}
	
	final public String[] getTileNames() {
		return TILE_NAMES;
	}

	final public String[] getCommands() {
		return (String[]) pacman.getActions().toArray();
	}
	
}
