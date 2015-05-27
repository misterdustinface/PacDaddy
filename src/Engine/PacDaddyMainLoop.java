package Engine;

import base.TickingLoop;
import functionpointers.VoidFunctionPointer;

final public class PacDaddyMainLoop extends TickingLoop {
	
	private PacDaddyWorld worldRef;
	
	PacDaddyMainLoop() {
		worldRef = new PacDaddyWorld();
		
		addFunction(new VoidFunctionPointer() {
			public void call() {
				worldRef.tick();
			}
		});
	}
	
	public void setWorld(PacDaddyWorld WORLDREF) {
		worldRef = WORLDREF;
	}
	
}