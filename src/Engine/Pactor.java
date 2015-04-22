package Engine;

import InternalInterfaces.AttributeSetter;
import InternalInterfaces.PactorCollisionFunction;
import PacDaddyApplicationInterfaces.PacDaddyAttributeReader;
import functionpointers.VoidFunctionPointer;

public class Pactor extends Actor implements PacDaddyAttributeReader, AttributeSetter {

	final private GameAttributes attributes;
	private PactorCollisionFunction onCollision;
	
	public Pactor() {
		attributes = new GameAttributes();
		onCollision = PactorCollisionFunction.EMPTY_FUNCTION;
		learnBasics();
	}
	
	void notifyCollidedWith(Pactor collidedWith) {
		onCollision.call(collidedWith);
	}
	
	public void setOnCollisionFunction(PactorCollisionFunction ON_COLLISION) {
		onCollision = ON_COLLISION;
	}
	
	public void setAttribute(String name, Object value) {
		attributes.setAttribute(name, value);
	}
	
	public Object getValueOf(String attributeName) {
		return attributes.getValueOf(attributeName);
	}

	public String[] getAttributes() {
		return attributes.getAttributes();
	}
	
	private void learnBasics() {
		learnAction("NONE", new VoidFunctionPointer() {
			public void call() {
				setAttribute("DIRECTION", "NONE");
			}
		});
		learnAction("UP", new VoidFunctionPointer() {
			public void call() {
				setAttribute("DIRECTION", "UP");
			}
		});
		learnAction("DOWN", new VoidFunctionPointer() {
			public void call() {
				setAttribute("DIRECTION", "DOWN");
			}
		});
		learnAction("RIGHT", new VoidFunctionPointer() {
			public void call() {
				setAttribute("DIRECTION", "RIGHT");
			}
		});
		learnAction("LEFT", new VoidFunctionPointer() {
			public void call() {
				setAttribute("DIRECTION", "LEFT");
			}
		});
		learnAction("STOP", new VoidFunctionPointer() {
			public void call() {
				setAttribute("DIRECTION", "NONE");
			}
		});
		
		performAction("NONE");
	}

}