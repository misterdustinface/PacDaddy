package FeatureLoader;

import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.JsePlatform;

public class FeatureLoader {
	
	private Globals globals;
	private String featureLoaderEngineScriptFile = "FeatureLoaderEngine/FeatureLoader.lua";
	
	public FeatureLoader() {
		globals = JsePlatform.standardGlobals();
		loadLuaFeatureLoaderFunctionsToGlobalFunctions();
	}
	
	public void setGamePath(String path) {
		callFunctionWithArgument("setGamePath", path);
	}
	
	public void loadFeatures(String featuresFolder) {
		callFunctionWithArgument("loadFeatures", featuresFolder);
	}
	
	private void callFunctionWithArgument(String functionname, String argument) {
		LuaValue function = globals.get(functionname);
		function.call(LuaValue.valueOf(argument));
	}
	
	private void loadLuaFeatureLoaderFunctionsToGlobalFunctions() {
		LuaValue chunk = globals.loadfile(featureLoaderEngineScriptFile);
		chunk.call();
	}
	
}
