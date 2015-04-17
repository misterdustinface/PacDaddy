package AWTPacDaddy;

import Engine.FeatureLoader;

public class AWTPacDaddyGameLauncher {
	
	public static void main(String[] args) {
		FeatureLoader.loadFeatures("features");
		FeatureLoader.loadFeatures("AWTfeatures");
	}

}