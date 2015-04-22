package Engine;

import InternalInterfaces.AttributeSetter;
import InternalInterfaces.PactorCollisionFunction;
import PacDaddyApplicationInterfaces.PacDaddyAttributeReader;

public class Pactor extends Actor implements PacDaddyAttributeReader, AttributeSetter {

	final private GameAttributes attributes;
	private PactorCollisionFunction onCollision;
	
	public Pactor() {
		attributes = new GameAttributes();
		onCollision = PactorCollisionFunction.EMPTY_FUNCTION;
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

}