package Engine;

import datastructures.Table;
import functionpointers.VoidFunctionPointer;
import PacDaddyApplicationInterfaces.*;
import base.Application;

public class PacDaddyGame extends Application implements PacDaddyInput, PacDaddyBoardReader, PadDaddyAttributeReader {
	
	final private static String[] ATTRIBUTES = {"SCORE", "LIVES", "GAMESPEED__UPS"};
	final private static String[] INPUT_COMMANDS = {"UP", "DOWN", "LEFT", "RIGHT", "PLAY", "PAUSE", "GAMESPEED++", "GAMESPEED--"};
	
	final private PacDaddyMainLoop mainLoop;
	final private Table<Object> attributes;
	final private Table<VoidFunctionPointer> inputFunctions;
	
	public PacDaddyGame() {
		attributes = new Table<Object>();
		attributes.insert("SCORE", 0);
		attributes.insert("LIVES", 3);
		attributes.insert("GAMESPEED__UPS", 15);
		
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
				mainLoop.setUpdatesPerSecond(getGameSpeed__ups());
			}
		});
		inputFunctions.insert("PAUSE", new VoidFunctionPointer() {
			public void call() {
				mainLoop.setUpdatesPerSecond(0);
			}
		});
		inputFunctions.insert("GAMESPEED++", new VoidFunctionPointer() {
			public void call() {
				shiftGameSpeed__ups(+10);
				mainLoop.setUpdatesPerSecond(getGameSpeed__ups());
			}
		});
		inputFunctions.insert("GAMESPEED--", new VoidFunctionPointer() {
			public void call() {
				shiftGameSpeed__ups(-10);
				mainLoop.setUpdatesPerSecond(getGameSpeed__ups());
			}
		});
		
		mainLoop = new PacDaddyMainLoop();
		mainLoop.setUpdatesPerSecond(getGameSpeed__ups());
		setMain(mainLoop);
	}
	
	private void shiftGameSpeed__ups(int shiftamount__ups) {
		attributes.insert("GAMESPEED__UPS", getGameSpeed__ups() + shiftamount__ups);
	}
	
	private int getGameSpeed__ups() {
		return (Integer)getValueOf("GAMESPEED__UPS");
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
