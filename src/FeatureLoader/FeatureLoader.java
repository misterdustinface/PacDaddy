package FeatureLoader;

import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.JsePlatform;

public class FeatureLoader {
	
	private String featureLoaderEngineScriptFile = "luasrc/FeatureLoaderEngine/FeatureLoader.lua";
	private String gameWrapperScriptPath;
	
	public FeatureLoader() {
		
	}
	
	public void setGamePath(String path) {
		gameWrapperScriptPath = path;
	}
	
	public void loadFeatures(String featuresFolder) {
		Globals globals = JsePlatform.standardGlobals();
		LuaValue chunk = globals.loadfile(featureLoaderEngineScriptFile);
		chunk.call( LuaValue.valueOf(gameWrapperScriptPath), LuaValue.valueOf(featuresFolder) );
	}
	
}
