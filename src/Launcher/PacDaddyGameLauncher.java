package Launcher;

import java.util.Arrays;

import functionpointers.VoidFunctionPointer;
import base.TickingLoop;
import Engine.PacDaddyGame;

public class PacDaddyGameLauncher {
	public static void main(String[] args) {
		final PacDaddyGame game = new PacDaddyGame();
		
		final TickingLoop testDisplayer = new TickingLoop();
		testDisplayer.setUpdatesPerSecond(2);
		testDisplayer.addFunction(new VoidFunctionPointer() {
			public void call() {
				synchronized (this) {
					final int[][] board = game.getTiledBoard();
					for (int row = 0; row < board.length; row++) {
						System.out.println(Arrays.toString(board[row]));
					}
					System.out.println();
				}
			}
		});
		
		final TickingLoop testInput = new TickingLoop();
		testInput.setUpdatesPerSecond(1);
		testDisplayer.addFunction(new VoidFunctionPointer() {
			String previous = "UP";
			public void call() {
				
				switch(previous) {
				case "UP": 		game.sendCommand("RIGHT");
								previous = "RIGHT";
					break;
				case "RIGHT": 	game.sendCommand("DOWN");
								previous = "DOWN";
					break;
				case "DOWN": 	game.sendCommand("LEFT");
								previous = "LEFT";
					break;
				case "LEFT": 	game.sendCommand("UP");
								previous = "UP";
					break;
				}
			}
		});
		
		game.addComponent("TESTDISPLAY", testDisplayer);
		game.addComponent("TESTINPUT", testInput);
		game.start();
	}
}
