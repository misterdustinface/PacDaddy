

import FeatureLoader.FeatureLoader;

public class PacDaddyGameLauncher {

	final protected FeatureLoader loader;
	
	public PacDaddyGameLauncher() {
		loader = new FeatureLoader();
		loader.setGamePath("PacDaddyGameWrapper/PacDaddyGameWrapper");
		loader.loadFeatures("features");
		loader.loadFeatures("tests");
	}
	
	public static void main(String[] args) {
		new PacDaddyGameLauncher();
	}
	
}
