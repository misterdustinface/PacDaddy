package FeatureLoader;

import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.JsePlatform;

public class PacDaddyFeatureLoader {
	
	public static String FEATURE_LOADER_ENGINE_SCRIPT_FILEPATH = "luasrc/FeatureLoaderEngine/FeatureLoader.lua";
	public static String GAME_WRAPPER_SCRIPT_FILE_PATH = "luasrc/FeatureLoaderEngine/PacDaddyGameWrapper";
	
	public static void loadFeatures(String featuresFolder) {
		Globals globals = JsePlatform.standardGlobals();
		LuaValue chunk = globals.loadfile(FEATURE_LOADER_ENGINE_SCRIPT_FILEPATH);
		chunk.call( LuaValue.valueOf(GAME_WRAPPER_SCRIPT_FILE_PATH), LuaValue.valueOf(featuresFolder) );
	}
	
}
