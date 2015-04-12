package AWTPacDaddy;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

import base.TickingLoop;
import functionpointers.VoidFunctionPointer;
import Engine.PacDaddyGame;

public class AWTPacDaddyGameLauncher {
	
	final static PacDaddyGame game = new PacDaddyGame();
	final static Color[] colors = new Color[] {
		Color.BLACK, Color.WHITE, Color.BLUE, Color.YELLOW, Color.ORANGE, Color.RED, Color.PINK
	};
	
	final static int SCREEN_WIDTH = 400;
	final static int SCREEN_HEIGHT = 400;
	
	public static void main(String[] args) {

		final JFrame frame = new JFrame();
		frame.setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
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
				case 'p':	if ((boolean)game.getValueOf("IS_PAUSED")) {
								game.sendCommand("PLAY");
							} else {
								game.sendCommand("PAUSE");
							}
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
		
		final TickingLoop testDrawer = new TickingLoop();
		testDrawer.setUpdatesPerSecond(20);
		testDrawer.addFunction(new VoidFunctionPointer() {
			public void call() {
				
				final int[][] board = game.getTiledBoard();
				int TILEWIDTH = panel.getWidth()/board[0].length;
				int TILEHEIGHT = panel.getHeight()/board.length;
				Graphics2D g = (Graphics2D)panel.getGraphics();

				for (int row = 0; row < board.length; row++) {
					for (int col = 0; col < board[row].length; col++) {
						g.setColor(getTileColor(board[row][col]));
						g.fillRect(col * TILEWIDTH, row * TILEHEIGHT, TILEWIDTH, TILEHEIGHT);
					}
				}
				
			}
		});
		
		game.addComponent("TESTDRAWER", testDrawer);
		game.start();
	}
	
	private static Color getTileColor(int tileNum) {
		return colors[tileNum];
	}
}