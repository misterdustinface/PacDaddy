package AWTPacDaddy;

import Engine.Utilities;

public class AWTPacDaddyGameLauncher {
	
	public static void main(String[] args) {
		Utilities.runLuaScript("luasrc/AWTPacDaddyGame.lua");
		Utilities.runLuaScript("luasrc/SetGameFeatures.lua");
	}

}