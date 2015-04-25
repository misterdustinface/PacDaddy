

import Engine.PacDaddyGame;
import FeatureLoader.FeatureLoader;
import PacDaddyApplicationInterfaces.PacDaddyApplication;

final public class PacDaddyGameLauncher {

	final private FeatureLoader loader;
	final private PacDaddyGame  game;
	
	public PacDaddyGameLauncher() {
		game = new PacDaddyGame();
		loader = new FeatureLoader();
		loader.setApplication(game);
		loader.loadFeatures("features");
		game.start();
		game.getInputProcessor().sendCommand("PLAY");
	}
	
	public PacDaddyApplication getApplication() {
		return game;
	}
	
	public static void main(String[] args) {
		new PacDaddyGameLauncher();
	}
	
}
