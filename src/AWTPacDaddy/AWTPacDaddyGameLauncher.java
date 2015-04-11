package AWTPacDaddy;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Arrays;

import javax.swing.JFrame;
import javax.swing.JPanel;

import base.TickingLoop;
import functionpointers.VoidFunctionPointer;
import Engine.PacDaddyGame;

public class AWTPacDaddyGameLauncher {
	public static void main(String[] args) {
		final PacDaddyGame game = new PacDaddyGame();
		final JFrame frame = new JFrame();
		frame.setSize(400, 400);
		frame.setTitle("Pac Daddy");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		final JPanel panel = new JPanel();
		
		KeyListener keylistener = new KeyListener() {
	
			public void keyPressed(KeyEvent e) {
				switch(e.getKeyChar()) {
				case 'w':	game.sendCommand("UP");
					break;
				case 'a':	game.sendCommand("LEFT");
					break;
				case 's':	game.sendCommand("DOWN");
					break;
				case 'd':	game.sendCommand("RIGHT");
					break;
				case 'p':	//game.sendCommand(command);
					break;
				}
			}
	
	
			public void keyReleased(KeyEvent e) {
	
			}
	
			public void keyTyped(KeyEvent e) {
	
			}
	
		};

		frame.addKeyListener(keylistener);
		frame.add(panel);
		
		final int WIDTH = 400;
		final int HEIGHT = 400;
		
		final TickingLoop testDrawer = new TickingLoop();
		testDrawer.setUpdatesPerSecond(20);
		testDrawer.addFunction(new VoidFunctionPointer() {
			public void call() {
				
				final int[][] board = game.getTiledBoard();
				Graphics2D g = (Graphics2D)panel.getGraphics();
				g.setColor(Color.BLACK);
				g.fillRect(0, 0, WIDTH, HEIGHT);

				for (int row = 0; row < board.length; row++) {
					for (int col = 0; col < board[row].length; col++) {
						int TILEWIDTH = WIDTH/board[row].length;
						int TILEHEIGHT = HEIGHT/board.length;
						if (board[row][col] == 3) {
							g.setColor(Color.YELLOW);
							g.fillRect(col * TILEWIDTH, row * TILEHEIGHT, TILEWIDTH, TILEHEIGHT);
						}
					}
				}
				
			}
		});
		
		game.addComponent("TESTDRAWER", testDrawer);
		game.start();
	}
	
}