package Engine;

import InternalInterfaces.AttributeSetter;
import InternalInterfaces.PactorCollisionFunction;
import PacDaddyApplicationInterfaces.PacDaddyAttributeReader;
import functionpointers.VoidFunctionPointer;

public class Pactor extends Actor implements PacDaddyAttributeReader, AttributeSetter {

	final private TileCoordinate spawnPos;
	final private TileCoordinate position;
	final private GameAttributes attributes;
	private PactorCollisionFunction onCollision;
	
	public Pactor() {
		spawnPos = new TileCoordinate();
		position = new TileCoordinate();
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
	
	public void respawn() {
		position.row = spawnPos.row;
		position.col = spawnPos.col;
	}
	
	public void setSpawn(int row, int col) {
		spawnPos.row = row;
		spawnPos.col = col;
	}
	
	void shiftRow(int amount) {
		position.row += amount;
	}
	
	void shiftCol(int amount) {
		position.col += amount;
	}
	
	public int getRow() {
		return position.row;
	}
	
	public int getCol() {
		return position.col;
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