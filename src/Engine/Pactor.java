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
	
	private void requestDirection(String request) {
		if (getValueOf("DIRECTION") != request) {
			setAttribute("REQUESTED_DIRECTION", request);
		}
	}
	
	private void learnBasics() {
		setAttribute("DIRECTION", "NONE");
		setAttribute("REQUESTED_DIRECTION", "NONE");
		
		learnAction("UP", new VoidFunctionPointer() {
			public void call() {
				requestDirection("UP");
			}
		});
		learnAction("DOWN", new VoidFunctionPointer() {
			public void call() {
				requestDirection("DOWN");
			}
		});
		learnAction("RIGHT", new VoidFunctionPointer() {
			public void call() {
				requestDirection("RIGHT");
			}
		});
		learnAction("LEFT", new VoidFunctionPointer() {
			public void call() {
				requestDirection("LEFT");
			}
		});
		learnAction("NONE", new VoidFunctionPointer() {
			public void call() {
				
			}
		});
	}

}