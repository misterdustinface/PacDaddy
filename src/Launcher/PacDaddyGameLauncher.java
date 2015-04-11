package Launcher;

import java.util.Arrays;

import functionpointers.VoidFunctionPointer;
import base.TickingLoop;
import Engine.PacDaddyGame;

public class PacDaddyGameLauncher {
	public static void main(String[] args) {
		final PacDaddyGame game = new PacDaddyGame();
		
		final TickingLoop testDisplayer = new TickingLoop();
		testDisplayer.setUpdatesPerSecond(4);
		testDisplayer.addFunction(new VoidFunctionPointer() {
			public void call() {
				
				final int[][] board = game.getTiledBoard();
				for (int row = 0; row < board.length; row++) {
					System.out.println(Arrays.toString(board[row]));
				}
				System.out.println();
			}
		});
		
		final TickingLoop testInput = new TickingLoop();
		testInput.setUpdatesPerSecond(1);
		testInput.addFunction(new VoidFunctionPointer() {
			String previous = "UP";
			
			public void call() {
				
				switch(previous) {
				case "UP": 		previous = "RIGHT";
					break;
				case "RIGHT": 	previous = "DOWN";
					break;
				case "DOWN": 	previous = "LEFT";
					break;
				case "LEFT": 	previous = "UP";
					break;
				}
				game.sendCommand(previous);
			}
		});
		
		game.addComponent("TESTDISPLAY", testDisplayer);
		game.addComponent("TESTINPUT", testInput);
		game.start();
	}
	
}