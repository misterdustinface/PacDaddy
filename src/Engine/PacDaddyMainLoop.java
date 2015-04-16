package Engine;

import InternalInterfaces.PlayerController;
import base.TickingLoop;
import functionpointers.VoidFunctionPointer;

public class PacDaddyMainLoop extends TickingLoop implements PlayerController {
	
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
	
	final public void sendCommandToPlayer(String command) {
		getPlayer().performAction(command);
	}
	
	final public String[] getPlayerCommands() {
		return (String[]) getPlayer().getActions().toArray(new String[]{});
	}
	
	private Pactor getPlayer() {
		return worldRef.getPactor("PLAYER");
	}
}