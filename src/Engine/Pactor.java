package Engine;

import InternalInterfaces.AttributeSetter;
import PacDaddyApplicationInterfaces.PacDaddyAttributeReader;
import functionpointers.VoidFunctionPointer;

public class Pactor extends Actor implements PacDaddyAttributeReader, AttributeSetter {

	final private GameAttributes attributes;
	final private TileCoordinate position;
	
	public Pactor() {
		attributes = new GameAttributes();
		position = new TileCoordinate();
		learnBasics();
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
	
	public TileCoordinate getTileCoordinate() {
		return position;
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
		
		performAction("NONE");
	}

}