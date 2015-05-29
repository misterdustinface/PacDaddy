package Engine;

import base.TickingLoop;
import functionpointers.VoidFunctionPointer;

final public class PacDaddyMainLoop extends TickingLoop {
	
	public void setWorld(PacDaddyWorld WORLDREF) {
		worldRef = WORLDREF;
	}
	
	PacDaddyMainLoop() {
		worldRef = new PacDaddyWorld();
		addFunction(mainTick);
	}
	
	private PacDaddyWorld worldRef;
	
	private VoidFunctionPointer mainTick = new VoidFunctionPointer() {
		public void call() {
			try {
				worldRef.tick();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	};
	
}