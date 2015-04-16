package AWTPacDaddy;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

import datastructures.Table;
import base.TickingLoop;
import functionpointers.VoidFunctionPointer;
import Engine.PacDaddyGame;
import PacDaddyApplicationInterfaces.PacDaddyApplication;
import PacDaddyApplicationInterfaces.PacDaddyBoardReader;
import PacDaddyApplicationInterfaces.PacDaddyInput;
import PacDaddyApplicationInterfaces.PacDaddyAttributeReader;

public class AWTPacDaddyGameLauncher {
	
	final static PacDaddyApplication game = new PacDaddyGame();
	final static PacDaddyBoardReader boardReader = game.getBoardReader();
	final static PacDaddyInput inputProcessor = game.getInputProcessor();
	final static PacDaddyAttributeReader attributeReader = game.getGameAttributeReader();
	
	final static Table<Color> colormap = new Table<Color>();
	
	final static int SCREEN_WIDTH = 400;
	final static int SCREEN_HEIGHT = 400;
	
	public static void main(String[] args) {
		colormap.insert("PLAYER", Color.YELLOW);
		colormap.insert("WALL", Color.WHITE);
		colormap.insert("FLOOR", Color.BLACK);
		
		final JFrame frame = new JFrame();
		frame.setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
		frame.setTitle("Pac Daddy");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		final JPanel panel = new JPanel();
		
		KeyListener keylistener = new KeyListener() {
	
			public void keyPressed(KeyEvent e) {
				switch(e.getKeyChar()) {
				case 'w':	inputProcessor.sendCommand("UP");
					break;
				case 'a':	inputProcessor.sendCommand("LEFT");
					break;
				case 's':	inputProcessor.sendCommand("DOWN");
					break;
				case 'd':	inputProcessor.sendCommand("RIGHT");
					break;
				case 'p':	if ((boolean)attributeReader.getValueOf("IS_PAUSED")) {
								inputProcessor.sendCommand("PLAY");
							} else {
								inputProcessor.sendCommand("PAUSE");
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
				
				final int[][] board = boardReader.getTiledBoard();
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
		
		((PacDaddyGame)game).addComponent("TESTDRAWER", testDrawer);
		((PacDaddyGame)game).start();
	}
	
	private static Color getTileColor(int tileNum) {
		String name = boardReader.getTileNames()[tileNum];
		if (!colormap.contains(name)) {
			return Color.GRAY;
		}
		return colormap.get(name);
	}
}