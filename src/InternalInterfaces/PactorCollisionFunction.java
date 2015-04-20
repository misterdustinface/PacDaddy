package InternalInterfaces;

import PacDaddyApplicationInterfaces.PacDaddyAttributeReader;

public interface PactorCollisionFunction {
	
	void call(PacDaddyAttributeReader collidedWith);
	
	public final static PactorCollisionFunction EMPTY_FUNCTION = new PactorCollisionFunction() {
		public void call(PacDaddyAttributeReader collidedWith) {
		}
	};
}
