package Engine;

import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.JsePlatform;

public class FeatureLoader {
	
	public static void loadFeatures(String featuresFolder) {
		String script = "luasrc/FeatureLoader.lua";
		runLuaScript(script, featuresFolder);
	}
	
	private static void runLuaScript(String script) {
		Globals globals = JsePlatform.standardGlobals();
		LuaValue chunk = globals.loadfile(script);
		chunk.call( LuaValue.valueOf(script) );
	}
	
	private static void runLuaScript(String script, String arg1) {
		Globals globals = JsePlatform.standardGlobals();
		LuaValue chunk = globals.loadfile(script);
		chunk.call( LuaValue.valueOf(script), LuaValue.valueOf(arg1) );
	}
	
}
