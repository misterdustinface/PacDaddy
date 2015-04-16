package Engine;

import InternalInterfaces.PlayerController;
import PacDaddyApplicationInterfaces.PacDaddyApplication;
import PacDaddyApplicationInterfaces.PacDaddyBoardReader;
import PacDaddyApplicationInterfaces.PacDaddyInput;
import PacDaddyApplicationInterfaces.PacDaddyAttributeReader;
import base.Application;
import functionpointers.VoidFunctionPointer;

public class PacDaddyGame extends Application implements PacDaddyApplication {
	
	final private PacDaddyWorld	world;
	final private GameAttributes gameAttributes;
	final private FunctionDispatchCommandProcessor inputProcessor;
	
	final private PacDaddyMainLoop mainLoop;
	final private PlayerController playerController;
	
	public PacDaddyGame() {
		
		mainLoop = new PacDaddyMainLoop();
		world = new PacDaddyWorld();
		mainLoop.setWorld(world);
		
		world.addTileType("FLOOR");
		world.addTileType("WALL");
		world.addTileType("PLAYER");
		world.addTileType("ENEMY");
		
		world.loadFromString("1111111111111\n"
				           + "1000000000001"
				           + "1000000000001"
				           + "1000000000001"
				           + "1000110110001"
				           + "1000100010001"
				           + "1000111110001"
				           + "1000000000001"
				           + "1000000000001"
				           + "1000000000001"
				           + "1111111111111\n");
		
		world.getPactor("PLAYER").getTileCoordinate().row = 3;
		world.getPactor("PLAYER").getTileCoordinate().col = 3;
		
		playerController = mainLoop;
		inputProcessor = new FunctionDispatchCommandProcessor();
		gameAttributes = new GameAttributes();
		
		gameAttributes.setAttribute("SCORE", 0);
		gameAttributes.setAttribute("LIVES", 3);
		gameAttributes.setAttribute("GAMESPEED__UPS", 15);
		gameAttributes.setAttribute("IS_PAUSED", false);
		
		inputProcessor.addCommand("UP", new VoidFunctionPointer() {
			public void call() {
				playerController.sendCommandToPlayer("UP");
			}
		});
		inputProcessor.addCommand("DOWN", new VoidFunctionPointer() {
			public void call() {
				playerController.sendCommandToPlayer("DOWN");
			}
		});
		inputProcessor.addCommand("LEFT", new VoidFunctionPointer() {
			public void call() {
				playerController.sendCommandToPlayer("LEFT");
			}
		});
		inputProcessor.addCommand("RIGHT", new VoidFunctionPointer() {
			public void call() {
				playerController.sendCommandToPlayer("RIGHT");
			}
		});
		inputProcessor.addCommand("PLAY", new VoidFunctionPointer() {
			public void call() {
				gameAttributes.setAttribute("IS_PAUSED", false);
				mainLoop.setUpdatesPerSecond(getGameSpeed__ups());
			}
		});
		inputProcessor.addCommand("PAUSE", new VoidFunctionPointer() {
			public void call() {
				gameAttributes.setAttribute("IS_PAUSED", true);
				mainLoop.setUpdatesPerSecond(0);
			}
		});
		inputProcessor.addCommand("GAMESPEED++", new VoidFunctionPointer() {
			public void call() {
				shiftGameSpeed__ups(+10);
				mainLoop.setUpdatesPerSecond(getGameSpeed__ups());
			}
		});
		inputProcessor.addCommand("GAMESPEED--", new VoidFunctionPointer() {
			public void call() {
				shiftGameSpeed__ups(-10);
				mainLoop.setUpdatesPerSecond(getGameSpeed__ups());
			}
		});
		
		mainLoop.setUpdatesPerSecond(getGameSpeed__ups());
		setMain(mainLoop);
	}
	
	final public PacDaddyBoardReader getBoardReader() {
		return world;
	}

	final public PacDaddyAttributeReader getGameAttributeReader() {
		return gameAttributes;
	}

	final public PacDaddyInput getInputProcessor() {
		return inputProcessor;
	}
	
	private void shiftGameSpeed__ups(int shiftamount__ups) {
		gameAttributes.setAttribute("GAMESPEED__UPS", getGameSpeed__ups() + shiftamount__ups);
	}
	
	private int getGameSpeed__ups() {
		return (Integer)gameAttributes.getValueOf("GAMESPEED__UPS");
	}
	
}