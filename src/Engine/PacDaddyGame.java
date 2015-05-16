package Engine;

import datastructures.Table;
import InternalInterfaces.WriteAccessor;
import PacDaddyApplicationInterfaces.PacDaddyApplication;
import PacDaddyApplicationInterfaces.PacDaddyAttributeReader;
import PacDaddyApplicationInterfaces.PacDaddyBoardReader;
import PacDaddyApplicationInterfaces.PacDaddyInput;
import base.Application;

final public class PacDaddyGame extends Application implements PacDaddyApplication, WriteAccessor {
	
	final private Table<Object> pacDaddyElements;
	
	public PacDaddyGame() {
		pacDaddyElements = new Table<Object>();
		pacDaddyElements.insert("WORLD",             new PacDaddyWorld());
		pacDaddyElements.insert("ATTRIBUTES",        new GameAttributes());
		pacDaddyElements.insert("INPUT_PROCESSOR",   new FunctionDispatchCommandProcessor());
		pacDaddyElements.insert("MAINLOOP",          new PacDaddyMainLoop());
		pacDaddyElements.insert("PACTOR_CONTROLLER", new PactorController());
		
		PacDaddyWorld world = (PacDaddyWorld) getWritable("WORLD");
		PacDaddyMainLoop mainLoop = (PacDaddyMainLoop) getWritable("MAINLOOP");
		
		mainLoop.setWorld(world);
		mainLoop.setUpdatesPerSecond(0);
		setMain(mainLoop);
	}
	
	public PacDaddyBoardReader getBoardReader() {
		return (PacDaddyBoardReader) getWritable("WORLD");
	}

	public PacDaddyAttributeReader getGameAttributeReader() {
		return (PacDaddyAttributeReader) getWritable("ATTRIBUTES");
	}

	public PacDaddyInput getInputProcessor() {
		return (PacDaddyInput) getWritable("INPUT_PROCESSOR");
	}
	
	public Object getWritable(String name) {
		Object writableElement = pacDaddyElements.get(name);
		if (writableElement == null) {
			throw new RuntimeException("Writable by name " + name + " does not exist for this game.");
		}
		return writableElement;
	}
	
	public String[] getWritables() {
		return pacDaddyElements.getNames().toArray(new String[] {});
	}
	
}