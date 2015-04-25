package FeatureLoader;

import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;
import org.luaj.vm2.lib.jse.JsePlatform;

public class FeatureLoader {
	
	private Globals globals;
	private String featureLoaderEngineScriptFile = "FeatureLoaderEngine/FeatureLoader.lua";
	
	public FeatureLoader() {
		globals = JsePlatform.standardGlobals();
		loadLuaFeatureLoaderFunctionsToGlobalFunctions();
	}
	
	public void setApplication(Object app) {
		LuaValue function = globals.get("setApplication");
		function.call(CoerceJavaToLua.coerce(app));
	}
	
	public Object getApplication() {
		return getGlobalUserObject("APPLICATION");
	}
	
	public void loadFeatures(String featuresFolder) {
		callFunctionWithArgument("loadFeatures", featuresFolder);
	}
	
	public void displayLoadedFiles() {
		callFunction("displayLoadedFiles");
	}
	
	public Object getGlobalUserObject(String name) {
		return globals.get(name).touserdata();
	}
	
	private void callFunction(String functionname) {
		LuaValue function = globals.get(functionname);
		function.call();
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