package FeatureLoader;

import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.JsePlatform;

public class PacDaddyFeatureLoader {
	
	public static String FEATURE_LOADER_ENGINE_SCRIPT_FILEPATH = "luasrc/FeatureLoaderEngine/FeatureLoader.lua";
	public static String GAME_WRAPPER_SCRIPT_FILE_PATH = "luasrc/FeatureLoaderEngine/PacDaddyGameWrapper";
	
	public static void loadFeatures(String featuresFolder) {
		runLuaScript(FEATURE_LOADER_ENGINE_SCRIPT_FILEPATH, 
				     GAME_WRAPPER_SCRIPT_FILE_PATH,
				     featuresFolder);
	}
	
//	private static void runLuaScript(String script) {
//		Globals globals = JsePlatform.standardGlobals();
//		LuaValue chunk = globals.loadfile(script);
//		chunk.call( LuaValue.valueOf(script) );
//	}
//	
//	private static void runLuaScript(String script, String arg1) {
//		Globals globals = JsePlatform.standardGlobals();
//		LuaValue chunk = globals.loadfile(script);
//		chunk.call( LuaValue.valueOf(script), LuaValue.valueOf(arg1) );
//	}
	
	private static void runLuaScript(String script, String arg1, String arg2) {
		Globals globals = JsePlatform.standardGlobals();
		LuaValue chunk = globals.loadfile(script);
		chunk.call( LuaValue.valueOf(script), LuaValue.valueOf(arg1), LuaValue.valueOf(arg2) );
	}
	
}
