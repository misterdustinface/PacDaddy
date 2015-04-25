

import Engine.PacDaddyGame;
import FeatureLoader.FeatureLoader;
import PacDaddyApplicationInterfaces.PacDaddyApplication;

public class PacDaddyGameLauncher {

	final protected FeatureLoader loader;
	
	public PacDaddyGameLauncher() {
		loader = new FeatureLoader();
		loader.setApplication(new PacDaddyGame());
		loader.loadFeatures("features");
		getApplication().getInputProcessor().sendCommand("PLAY");
	}
	
	public static void main(String[] args) {
		new PacDaddyGameLauncher();
	}
	
	public PacDaddyApplication getApplication() {
		return (PacDaddyApplication) loader.getApplication();
	}
	
}
