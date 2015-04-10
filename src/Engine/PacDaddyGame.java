package Engine;

import datastructures.Table;
import functionpointers.VoidFunctionPointer;
import PacDaddyApplicationInterfaces.*;
import base.Application;

public class PacDaddyGame extends Application implements PacDaddyInput, PacDaddyBoardReader, PadDaddyAttributeReader {
	
	final private static String[] ATTRIBUTES = {"SCORE", "LIVES", "UPDATES PER SECOND"};
	final private static String[] INPUT_COMMANDS = {"UP", "DOWN", "LEFT", "RIGHT", "PLAY", "PAUSE", "GAMESPEED++", "GAMESPEED--"};
	
	final private PacDaddyMainLoop mainLoop;
	final private Table<Object> attributes;
	final private Table<VoidFunctionPointer> inputFunctions;
	
	public PacDaddyGame() {
		attributes = new Table<Object>();
		attributes.insert("SCORE", 0);
		attributes.insert("LIVES", 3);
		attributes.insert("UPDATES PER SECOND", 2);
		
		inputFunctions = new Table<VoidFunctionPointer>();
		inputFunctions.insert("UP", new VoidFunctionPointer() {
			public void call() {
				mainLoop.sendCommand("UP");
			}
		});
		inputFunctions.insert("DOWN", new VoidFunctionPointer() {
			public void call() {
				mainLoop.sendCommand("DOWN");
			}
		});
		inputFunctions.insert("LEFT", new VoidFunctionPointer() {
			public void call() {
				mainLoop.sendCommand("LEFT");
			}
		});
		inputFunctions.insert("RIGHT", new VoidFunctionPointer() {
			public void call() {
				mainLoop.sendCommand("RIGHT");
			}
		});
		inputFunctions.insert("PLAY", new VoidFunctionPointer() {
			public void call() {
				mainLoop.setUpdatesPerSecond((Integer)getValueOf("UPDATES PER SECOND"));
			}
		});
		inputFunctions.insert("PAUSE", new VoidFunctionPointer() {
			public void call() {
				mainLoop.setUpdatesPerSecond(0);
			}
		});
		inputFunctions.insert("GAMESPEED++", new VoidFunctionPointer() {
			public void call() {
				attributes.insert("UPDATES PER SECOND", (Integer)attributes.get("UPDATES PER SECOND") + 10);
				mainLoop.setUpdatesPerSecond((Integer)getValueOf("UPDATES PER SECOND"));
			}
		});
		inputFunctions.insert("GAMESPEED--", new VoidFunctionPointer() {
			public void call() {
				attributes.insert("UPDATES PER SECOND", (Integer)attributes.get("UPDATES PER SECOND") - 10);
				mainLoop.setUpdatesPerSecond((Integer)getValueOf("UPDATES PER SECOND"));
			}
		});
		
		mainLoop = new PacDaddyMainLoop();
		mainLoop.setUpdatesPerSecond((Integer)getValueOf("UPDATES PER SECOND"));
		setMain(mainLoop);
	}

	public void sendCommand(String command) {
		if (inputFunctions.contains(command)) {
			inputFunctions.get(command).call();
		} else {
			throw new RuntimeException(command + " is not a valid PacDaddyInput command." );
		}
	}
	
	public Object getValueOf(String attributeName) {
		return attributes.get(attributeName);
	}
	
	final public int[][] getTiledBoard() {
		return mainLoop.getTiledBoard();
	}

	public String[] getAttributes() {
		return ATTRIBUTES;
	}
	
	public String[] getTileNames() {
		return mainLoop.getTileNames();
	}
	
	public String[] getCommands() {
		return INPUT_COMMANDS;
	}
	
}
