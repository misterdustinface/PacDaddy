package Engine;

import InternalInterfaces.AttributeSetter;
import PacDaddyApplicationInterfaces.PacDaddyAttributeReader;
import functionpointers.VoidFunctionPointer;

public class Pactor extends Actor implements PacDaddyAttributeReader, AttributeSetter {

	final private TileCoordinate spawnPos;
	final private TileCoordinate position;
	final private GameAttributes attributes;
	
	public Pactor() {
		spawnPos = new TileCoordinate();
		position = new TileCoordinate();
		attributes = new GameAttributes();
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
	
	public void respawn() {
		position.row = spawnPos.row;
		position.col = spawnPos.col;
	}
	
	public void setSpawn(int row, int col) {
		spawnPos.row = row;
		spawnPos.col = col;
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