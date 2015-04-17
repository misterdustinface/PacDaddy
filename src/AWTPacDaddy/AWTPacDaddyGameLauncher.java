package AWTPacDaddy;

import FeatureLoader.PacDaddyFeatureLoader;

public class AWTPacDaddyGameLauncher {
	
	public static void main(String[] args) {
		PacDaddyFeatureLoader.loadFeatures("features");
		PacDaddyFeatureLoader.loadFeatures("AWTfeatures");
	}

}