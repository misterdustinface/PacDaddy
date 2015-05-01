package Engine;

import InternalInterfaces.WriteAccessor;
import PacDaddyApplicationInterfaces.PacDaddyApplication;
import PacDaddyApplicationInterfaces.PacDaddyAttributeReader;
import PacDaddyApplicationInterfaces.PacDaddyBoardReader;
import PacDaddyApplicationInterfaces.PacDaddyInput;
import base.Application;

final public class PacDaddyGame extends Application implements PacDaddyApplication, WriteAccessor {
	
	final private PacDaddyWorld	world;
	final private GameAttributes gameAttributes;
	final private FunctionDispatchCommandProcessor inputProcessor;
	final private PacDaddyMainLoop mainLoop;
	final private PactorController pactorController;
	
	public PacDaddyGame() {
		world            = new PacDaddyWorld();
		gameAttributes   = new GameAttributes();
		inputProcessor   = new FunctionDispatchCommandProcessor();
		mainLoop         = new PacDaddyMainLoop();
		pactorController = new PactorController();
		mainLoop.setWorld(world);
		mainLoop.setUpdatesPerSecond(0);
		setMain(mainLoop);
	}
	
	public PacDaddyBoardReader getBoardReader() {
		return world;
	}

	public PacDaddyAttributeReader getGameAttributeReader() {
		return gameAttributes;
	}

	public PacDaddyInput getInputProcessor() {
		return inputProcessor;
	}
	
	final static String[] WRITABLES = new String[] {"WORLD", "ATTRIBUTES", "INPUT_PROCESSOR", "MAINLOOP", "PACTOR_CONTROLLER"};
	
	public Object getWritable(String name) {
		switch(name) {
		case "WORLD":            	return world;
		case "ATTRIBUTES":       	return gameAttributes;
		case "INPUT_PROCESSOR":   	return inputProcessor;
		case "MAINLOOP":         	return mainLoop;
		case "PACTOR_CONTROLLER": 	return pactorController;
		default:                 	throw new RuntimeException("Writable by name " + name + " does not exist for this game.");
		}
	}
	
	public String[] getWritables() {
		return WRITABLES;
	}
	
}