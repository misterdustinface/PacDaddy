package Engine;

import datastructures.Table;
import PacDaddyApplicationInterfaces.PacDaddyAttributeReader;
import functionpointers.VoidFunctionPointer;

public class Pactor extends Actor implements PacDaddyAttributeReader {

	final private Table<Object> attributes;
	final private TileCoordinate position;
	
	Pactor() {
		attributes = new Table<Object>();
		position = new TileCoordinate();
		learnBasics();
	}
	
	public Object getValueOf(String attributeName) {
		return attributes.get(attributeName);
	}

	public String[] getAttributes() {
		return (String[]) attributes.getNames().toArray();
	}
	
	public TileCoordinate getTileCoordinate() {
		return position;
	}
	
	private void learnBasics() {
		learnAction("NONE", new VoidFunctionPointer() {
			public void call() {
				attributes.insert("DIRECTION", "NONE");
			}
		});
		learnAction("UP", new VoidFunctionPointer() {
			public void call() {
				attributes.insert("DIRECTION", "UP");
			}
		});
		learnAction("DOWN", new VoidFunctionPointer() {
			public void call() {
				attributes.insert("DIRECTION", "DOWN");
			}
		});
		learnAction("RIGHT", new VoidFunctionPointer() {
			public void call() {
				attributes.insert("DIRECTION", "RIGHT");
			}
		});
		learnAction("LEFT", new VoidFunctionPointer() {
			public void call() {
				attributes.insert("DIRECTION", "LEFT");
			}
		});
		
		performAction("NONE");
	}

}