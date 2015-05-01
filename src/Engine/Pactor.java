package Engine;

import functionpointers.VoidFunctionPointer;
import InternalInterfaces.AttributeSetter;
import InternalInterfaces.PactorCollisionFunction;
import PacDaddyApplicationInterfaces.PacDaddyAttributeReader;

final public class Pactor extends Actor implements PacDaddyAttributeReader, AttributeSetter {

	final private GameAttributes attributes;
	private PactorCollisionFunction onCollision;
	
	public Pactor() {
		attributes = new GameAttributes();
		onCollision = PactorCollisionFunction.EMPTY_FUNCTION;
		learnBasics();
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
	
	void notifyCollidedWith(Pactor collidedWith) {
		onCollision.call(collidedWith);
	}
	
	private void requestDirection(String direction) {
		if (getValueOf("DIRECTION") != direction) {
			setAttribute("REQUESTED_DIRECTION", direction);
		}
	}
	
	private void learnBasics() {
		setAttribute("DIRECTION", "NONE");
		setAttribute("REQUESTED_DIRECTION", "NONE");
		learnAction("UP", new VoidFunctionPointer() {
			public void call() { requestDirection("UP"); }
		});
		learnAction("DOWN", new VoidFunctionPointer() {
			public void call() { requestDirection("DOWN"); }
		});
		learnAction("LEFT", new VoidFunctionPointer() {
			public void call() { requestDirection("LEFT"); }
		});
		learnAction("RIGHT", new VoidFunctionPointer() {
			public void call() { requestDirection("RIGHT"); }
		});
		learnAction("NONE", new VoidFunctionPointer() {
			public void call() { }
		});
	}
	
}