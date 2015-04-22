package Engine;

import java.util.ArrayList;
import java.util.Arrays;

import datastructures.Queue;
import datastructures.Table;
import InternalInterfaces.PactorToTileFunction;
import PacDaddyApplicationInterfaces.PacDaddyBoardReader;
import PacDaddyApplicationInterfaces.PacDaddyAttributeReader;

public class PacDaddyWorld implements PacDaddyBoardReader {

	private String[] tilenamesarray;
	final private ArrayList<String> tilenames;
	final private Table<Integer> tileEnums;
	
	final private Table<Pactor> pactors;	
	final private Table<GameAttributes> worldPactorAttributes;
	private GameAttributes noPactorAvailableTileAttributes;
	
	private PactorToTileFunction pactorToTile;
	private int[][] wallworld;
	
	final private Queue<String> removalQueue;
	
	public PacDaddyWorld() {
		worldPactorAttributes = new Table<GameAttributes>();
		tilenames = new ArrayList<String>();
		tileEnums = new Table<Integer>();
		pactors = new Table<Pactor>();
		noPactorAvailableTileAttributes = new GameAttributes();
		noPactorAvailableTileAttributes.setAttribute("NO_PACTOR_AVAILABLE", true);
		removalQueue = new Queue<String>();
		pactorToTile = new PactorToTileFunction() {
			public String getTileName(Pactor pactor) {
				return "FLOOR";
			}
		};

		addTileType("FLOOR");
		addTileType("WALL");
	}
	
	public void loadFromString(String worldstring) {
		wallworld = Utilities.StringToIntArray(worldstring);
	}
	
	public void tick() {
		for (String name : pactors.getNames()) {
			GameAttributes g = worldPactorAttributes.get(name);
			g.setAttribute("TICK_COUNTER", (int)g.getValueOf("TICK_COUNTER") + 1);
			if ((int)g.getValueOf("TICK_COUNTER") >= (int)g.getValueOf("TICKS_TO_MOVE")) {
				Pactor p = getPactor(name);
				updatePactor(p);
				g.setAttribute("TICK_COUNTER", 0);
			}
		}
		clearRemovalQueue();
	}
	
	private void clearRemovalQueue() {
		while (!removalQueue.isEmpty()) {
			String toRemove = removalQueue.dequeue();
			pactors.remove(toRemove);
			worldPactorAttributes.remove(toRemove);
		}
	}
	
	final public void addPactor(String name, Pactor p) {
		p.setAttribute("NAME", name);
		GameAttributes g = new GameAttributes();
		g.setAttribute("SPAWN", new TileCoordinate());
		g.setAttribute("POSITION", new TileCoordinate());
		g.setAttribute("SPEED__PCT", 1.0f);
		g.setAttribute("TICKS_TO_MOVE", 1);
		g.setAttribute("TICK_COUNTER", 0);
		pactors.insert(name, p);
		worldPactorAttributes.insert(name, g);
	}
	
	final public void removePactor(String name) {
		removalQueue.enqueue(name);
	}
	
	final public Pactor getPactor(String name) {
		return pactors.get(name);
	}
	
	final public int getRowOf(String name) {
		return getPositionFor(name).row;
	}
	
	final public int getColOf(String name) {
		return  getPositionFor(name).col;
	}
	
	final public void setPactorSpawn(String name, int row, int col) {
		TileCoordinate spawn = getSpawnFor(name);
		spawn.row = row;
		spawn.col = col;
	}
	
	final public void respawnPactor(String name) {
		TileCoordinate c = getPositionFor(name);
		TileCoordinate spawn = getSpawnFor(name);
		c.row = spawn.row;
		c.col = spawn.col;
	}
	
	final public void respawnAllPactors() {
		for (String name : pactors.getNames()) {
			respawnPactor(name);
		}
	}
	
	final public void setPactorSpeed(String name, float percent) {
		worldPactorAttributes.get(name).setAttribute("SPEED__PCT", percent);
		worldPactorAttributes.get(name).setAttribute("TICKS_TO_MOVE", (int)(100 / (100 * percent)) );
	}
	
	public PacDaddyAttributeReader getAttributeReaderAtTile(int row, int col) {
		for (String name : pactors.getNames()) {
			TileCoordinate c = getPositionFor(name);
			if (c.row == row && c.col == col) {
				return pactors.get(name);
			}
		}
		return noPactorAvailableTileAttributes;
	}
	
	public void addTileType(String name) {
		if (!tileEnums.contains(name)) {
			tilenames.add(name);
			int enumeration = tilenames.size() - 1;
			tileEnums.insert(name, enumeration);
			tilenamesarray = tilenames.toArray(new String[]{});
		}
	}
	
	public void setPactorToTileFunction(PactorToTileFunction PACTOR_TO_TILE) {
		pactorToTile = PACTOR_TO_TILE;
	}
	
	public void removeWall(int row, int col) {
		wallworld[row][col] = tileEnums.get("FLOOR");
	}
	
	public void placeWall(int row, int col) {
		wallworld[row][col] = tileEnums.get("WALL");
	}
	
	public boolean isWall(int row, int col) {
		return wallworld[row][col] == tileEnums.get("WALL");
	}
	
	public boolean isOutOfBounds(int row, int col) {
		return row < 0 || col < 0 || row >= getRows() || col >= getCols();
	}
	
	public int[][] getTiledBoard() {
		
		int[][] worldRepresentation = getWallWorldCopy();

		for (String name : pactors.getNames()) {
			Pactor p = pactors.get(name);
			String s = pactorToTile.getTileName(p);
			TileCoordinate c = getPositionFor(name);
			worldRepresentation[c.row][c.col] = tileEnums.get(s);
		}
		
		return worldRepresentation;
	}
	
	public String[] getTileNames() {
		return tilenamesarray;
	}
	
	public int getRows() {
		return wallworld.length;
	}
	
	public int getCols() {
		return wallworld[0].length;
	}
	
	private int[][] getWallWorldCopy() {
		int[][] wallWorldCopy = new int[getRows()][];
		for (int row = 0; row < getRows(); row++) {
			wallWorldCopy[row] = Arrays.copyOf(wallworld[row], getCols());
		}
		return wallWorldCopy;
	}
	
	private void updatePactor(Pactor p) {
		String direction = (String) p.getValueOf("REQUESTED_DIRECTION");
		movePactorInDirection(p, direction);
		notifyPactorCollisions(p);
	}
	
	private void movePactorInDirection(Pactor toMove, String direction) {
		String name = (String) toMove.getValueOf("NAME");
		TileCoordinate c = getPositionFor(name);
		TileCoordinate next = new TileCoordinate();
		next.row = direction == "UP"   ? (c.row - 1) : direction == "DOWN"  ? (c.row + 1) : c.row;
		next.col = direction == "LEFT" ? (c.col - 1) : direction == "RIGHT" ? (c.col + 1) : c.col;
		wrapToWorldBounds(next);
		
		if (!isWall(next.row, next.col)) {
			c.row = next.row;
			c.col = next.col;
			toMove.setAttribute("DIRECTION", direction);
		} else if (toMove.getValueOf("DIRECTION") != direction) {
			movePactorInDirection(toMove, (String) toMove.getValueOf("DIRECTION"));
		}
	}
	
	private void notifyPactorCollisions(Pactor p) {
		String name = (String) p.getValueOf("NAME");
		for (String otherName : pactors.getNames()) {
			Pactor other = pactors.get(otherName);
			TileCoordinate myPos = getPositionFor(name);
			TileCoordinate otherPos = getPositionFor(otherName);
			if (p != other && myPos.row == otherPos.row && myPos.col == otherPos.col) {
				p.notifyCollidedWith(other);
				other.notifyCollidedWith(p);
			}
		}
	}
	
	private void wrapToWorldBounds(TileCoordinate c) {
		if (c.row >= getRows()) {
			c.row = 0;
		} else if (c.row < 0) {
			c.row = getRows() - 1;
		}
		
		if (c.col >= getCols()) {
			c.col = 0;
		} else if (c.col < 0) {
			c.col = getCols() - 1;
		}
	}
	
	private TileCoordinate getSpawnFor(String name) {
		return (TileCoordinate) worldPactorAttributes.get(name).getValueOf("SPAWN");
	}
	
	private TileCoordinate getPositionFor(String name) {
		return (TileCoordinate) worldPactorAttributes.get(name).getValueOf("POSITION");
	}
	
}