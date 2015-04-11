package Engine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import functionpointers.VoidFunctionPointer;
import PacDaddyApplicationInterfaces.PacDaddyBoardReader;
import PacDaddyApplicationInterfaces.PacDaddyInput;
import base.TickingLoop;

public class PacDaddyMainLoop extends TickingLoop implements PacDaddyInput, PacDaddyBoardReader {
	
	final private static String[] INPUT_COMMANDS = {"UP", "DOWN", "LEFT", "RIGHT", "NONE"};
	final private static String[] TILE_NAMES = {"FLOOR", "WALL", "GHOST", "PACMAN", "DOT", "BIGDOT", "FRUIT"};
	
	final private Actor pacman;
	final private ArrayList<Actor> enemies;
	final private HashMap<Actor, TileCoordinate> actorPositions;
	
	private String input;
	private int ROWS, COLS;
	private int[][] wallworld;
	
	public PacDaddyMainLoop() {
		input = "NONE";
		ROWS = 10; 
		COLS = 10;
		wallworld = new int[ROWS][COLS];
		actorPositions = new HashMap<Actor, TileCoordinate>();
		pacman = newActor();
		enemies = new ArrayList<Actor>();
		
		addFunction(new VoidFunctionPointer() {
			public void call() {
				pacman.performAction(input);
			}
		});
		
		addFunction(new VoidFunctionPointer() {
			public void call() {
				for (Actor enemy : enemies) {
					enemy.performAction("ATTACK PLAYER");
				}
			}
		});
	}
	
	private boolean isWall(int row, int col) {
		return row < 0 || col < 0 || row >= ROWS || col >= COLS || wallworld[row][col] == 1;
	}
	
	private Actor newActor() {
		final Actor newActor = new Actor();
		
		actorPositions.put(newActor, new TileCoordinate());
		actorPositions.get(newActor).row = 0;
		actorPositions.get(newActor).col = 0;
		
		newActor.learnAction("NONE", new VoidFunctionPointer() {
			public void call() {

			}
		});
		
		newActor.learnAction("UP", new VoidFunctionPointer() {
			public void call() {
				TileCoordinate actorCoordinate = actorPositions.get(newActor);
				if (!isWall (actorCoordinate.row - 1, actorCoordinate.col)) {
					--actorCoordinate.row;
				}
			}
		});
		newActor.learnAction("DOWN", new VoidFunctionPointer() {
			public void call() {
				TileCoordinate actorCoordinate = actorPositions.get(newActor);
				if (!isWall (actorCoordinate.row + 1, actorCoordinate.col)) {
					++actorCoordinate.row;
				}
			}
		});
		newActor.learnAction("RIGHT", new VoidFunctionPointer() {
			public void call() {
				TileCoordinate actorCoordinate = actorPositions.get(newActor);
				if (!isWall (actorCoordinate.row, actorCoordinate.col + 1)) {
					++actorCoordinate.col;
				}
			}
		});
		newActor.learnAction("LEFT", new VoidFunctionPointer() {
			public void call() {
				TileCoordinate actorCoordinate = actorPositions.get(newActor);
				if (!isWall (actorCoordinate.row, actorCoordinate.col - 1)) {
					--actorCoordinate.col;
				}
			}
		});
		return newActor;
	}
	
	final public void newEnemy() {
		Actor newEnemy = newActor();
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
		
		TileCoordinate actorCoordinate = actorPositions.get(pacman);
		worldRepresentation[actorCoordinate.row][actorCoordinate.col] = 3;
		
		for (Actor enemy : enemies) {
			actorCoordinate = actorPositions.get(enemy);
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
		return INPUT_COMMANDS;
	}
	
}
