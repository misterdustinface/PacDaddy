package Engine;

import base.TickingLoop;
import functionpointers.VoidFunctionPointer;

public class PacDaddyMainLoop extends TickingLoop {
	
	private PacDaddyWorld worldRef;
	
	PacDaddyMainLoop() {
		addFunction(new VoidFunctionPointer() {
			public void call() {
				worldRef.tick();
			}
		});
	}
	
	final public void setWorld(PacDaddyWorld WORLDREF) {
		worldRef = WORLDREF;
	}
}