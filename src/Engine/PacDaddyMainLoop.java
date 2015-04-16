package Engine;

import base.TickingLoop;
import functionpointers.VoidFunctionPointer;

public class PacDaddyMainLoop extends TickingLoop {
	
	private PacDaddyWorld worldRef;
	
	public PacDaddyMainLoop() {
		
		addFunction(new VoidFunctionPointer() {
			public void call() {
				worldRef.tick();
				
//				for (Actor enemy : worldRef.enemies) {
//					enemy.performAction("ATTACK_PLAYER");
//				}
			}
		});
	}
	
	final public void setWorld(PacDaddyWorld WORLDREF) {
		worldRef = WORLDREF;
	}
}