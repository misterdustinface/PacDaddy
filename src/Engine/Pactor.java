package Engine;

import datastructures.Table;
import PacDaddyApplicationInterfaces.PadDaddyAttributeReader;
import functionpointers.VoidFunctionPointer;

public class Pactor extends Actor implements PadDaddyAttributeReader {

	final private Table<Object> attributes;
	final private TileCoordinate position;
	final private PacDaddyMainLoop WORLDREF;
	
	Pactor(PacDaddyMainLoop WORLD) {
		attributes = new Table<Object>();
		position = new TileCoordinate();
		WORLDREF = WORLD;
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
				if (!WORLDREF.isWall(position.row - 1, position.col)) {
					--position.row;
				}
			}
		});
		learnAction("DOWN", new VoidFunctionPointer() {
			public void call() {
				attributes.insert("DIRECTION", "DOWN");
				if (!WORLDREF.isWall(position.row + 1, position.col)) {
					++position.row;
				}
			}
		});
		learnAction("RIGHT", new VoidFunctionPointer() {
			public void call() {
				attributes.insert("DIRECTION", "RIGHT");
				if (!WORLDREF.isWall(position.row, position.col + 1)) {
					++position.col;
				}
			}
		});
		learnAction("LEFT", new VoidFunctionPointer() {
			public void call() {
				attributes.insert("DIRECTION", "LEFT");
				if (!WORLDREF.isWall(position.row, position.col - 1)) {
					--position.col;
				}
			}
		});
	}

}