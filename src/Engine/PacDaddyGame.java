package Engine;

import datastructures.Table;
import functionpointers.VoidFunctionPointer;
import PacDaddyApplicationInterfaces.*;
import base.Application;

public class PacDaddyGame extends Application implements PacDaddyInput, PacDaddyBoardReader, PadDaddyAttributeReader {
	
	final private static String[] ATTRIBUTES = {"SCORE", "LIVES", "UPDATES_PER_SECOND"};
	final private static String[] TILE_NAMES = {"FLOOR", "WALL", "GHOST", "PACMAN", "DOT", "BIGDOT", "FRUIT"};
	final private static String[] INPUT_COMMANDS = {"UP", "DOWN", "LEFT", "RIGHT", "PLAY", "PAUSE", "GAMESPEED++", "GAMESPEED--"};
	
	final private PacDaddyMainLoop mainLoop;
	final private Table<Object> attributes;
	final private Table<VoidFunctionPointer> inputFunctions;
	
	private int[][] world;
	
	public PacDaddyGame() {
		world = new int[10][10];
		attributes = new Table<Object>();
		attributes.insert("SCORE", 0);
		attributes.insert("LIVES", 3);
		attributes.insert("UPDATES_PER_SECOND", 60);
		
		inputFunctions = new Table<VoidFunctionPointer>();
		inputFunctions.insert("UP", null);
		inputFunctions.insert("DOWN", null);
		inputFunctions.insert("LEFT", null);
		inputFunctions.insert("RIGHT", null);
		inputFunctions.insert("PLAY", null);
		inputFunctions.insert("PAUSE", null);
		inputFunctions.insert("GAMESPEED++", new VoidFunctionPointer() {
			public void call() {
				mainLoop.setUpdatesPerSecond((Integer)getValueOf("UPDATES_PER_SECOND") + 10);
			}
		});
		inputFunctions.insert("GAMESPEED--", new VoidFunctionPointer() {
			public void call() {
				mainLoop.setUpdatesPerSecond((Integer)getValueOf("UPDATES_PER_SECOND") - 10);
			}
		});
		
		mainLoop = new PacDaddyMainLoop();
		mainLoop.setUpdatesPerSecond((Integer)getValueOf("UPDATES_PER_SECOND"));
		setMain(mainLoop);
	}

	public void sendCommand(String command) {
		
	}
	
	public Object getValueOf(String attributeName) {
		return attributes.get(attributeName);
	}

	public int[][] getTiledBoard() {
		return world.clone();
	}

	public String[] getAttributes() {
		return ATTRIBUTES;
	}
	
	public String[] getTileNames() {
		return TILE_NAMES;
	}
	
	public String[] getCommands() {
		return INPUT_COMMANDS;
	}
	
}
