package AWTPacDaddy;

import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.JsePlatform;

public class AWTPacDaddyGameLauncher {
	
	public static void main(String[] args) {
		String script = "luasrc/AWTPacDaddyGame.lua";
		
		// create an environment to run in
		Globals globals = JsePlatform.standardGlobals();
		
		// Use the convenience function on Globals to load a chunk.
		LuaValue chunk = globals.loadfile(script);
		
		// Use any of the "call()" or "invoke()" functions directly on the chunk.
		chunk.call( LuaValue.valueOf(script) );
	}

}