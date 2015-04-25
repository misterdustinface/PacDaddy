

import FeatureLoader.FeatureLoader;

public class PacDaddyGameLauncher {

	final protected FeatureLoader loader;
	
	public PacDaddyGameLauncher() {
		loader = new FeatureLoader();
		loader.loadFeatures("wrapPacDaddyGame");
		loader.loadFeatures("features");
	}
	
	public static void main(String[] args) {
		new PacDaddyGameLauncher();
	}
	
}
