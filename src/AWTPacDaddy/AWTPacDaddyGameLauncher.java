package AWTPacDaddy;

import FeatureLoader.FeatureLoader;

public class AWTPacDaddyGameLauncher {
	
	public static void main(String[] args) {
		FeatureLoader loader = new FeatureLoader();
		loader.setGamePath("luasrc/PacDaddyGameWrapper/PacDaddyGameWrapper");
		loader.loadFeatures("features");
		loader.loadFeatures("AWTfeatures");
	}

}