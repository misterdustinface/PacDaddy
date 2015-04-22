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
	final private Table<TileCoordinate> pactorPositions;
	final private Table<TileCoordinate> pactorSpawns;
	private int[][] wallworld;
	private GameAttributes noPactorAvailableTileAttributes;
	private PactorToTileFunction pactorToTile;
	final private Queue<String> removalQueue;
	
	public PacDaddyWorld() {
		tilenames = new ArrayList<String>();
		tileEnums = new Table<Integer>();
		pactors = new Table<Pactor>();
		pactorPositions = new Table<TileCoordinate>();
		pactorSpawns = new Table<TileCoordinate>();
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
			Pactor p = getPactor(name);
			updatePactor(p);
		}
		clearRemovalQueue();
	}
	
	private void clearRemovalQueue() {
		while (!removalQueue.isEmpty()) {
			String toRemove = removalQueue.dequeue();
			pactors.remove(toRemove);
			pactorPositions.remove(toRemove);
			pactorSpawns.remove(toRemove);
		}
	}
	
	final public void addPactor(String name, Pactor p) {
		p.setAttribute("NAME", name);
		pactorSpawns.insert(name, new TileCoordinate());
		pactorPositions.insert(name, new TileCoordinate());
		pactors.insert(name, p);
	}
	
	final public void removePactor(String name) {
		removalQueue.enqueue(name);
	}
	
	final public Pactor getPactor(String name) {
		return pactors.get(name);
	}
	
	final public int getRowOf(String name) {
		return pactorPositions.get(name).row;
	}
	
	final public int getColOf(String name) {
		return pactorPositions.get(name).col;
	}
	
	final public void setPactorSpawn(String name, int row, int col) {
		TileCoordinate spawn = pactorSpawns.get(name);
		spawn.row = row;
		spawn.col = col;
	}
	
	final public void respawnPactor(String name) {
		TileCoordinate c = pactorPositions.get(name);
		TileCoordinate spawn = pactorSpawns.get(name);
		c.row = spawn.row;
		c.col = spawn.col;
	}
	
	final public void respawnAllPactors() {
		for (String name : pactors.getNames()) {
			respawnPactor(name);
		}
	}
	
	public PacDaddyAttributeReader getAttributeReaderAtTile(int row, int col) {
		for (String name : pactors.getNames()) {
			TileCoordinate c = pactorPositions.get(name);
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
			TileCoordinate c = pactorPositions.get(name);
			worldRepresentation[c.row][c.col] = tileEnums.get(s);
		}
		
		return worldRepresentation;
	}
	
	public String[] getTileNames() {
		return tilenamesarray;
	}
	
	private int[][] getWallWorldCopy() {
		int[][] wallWorldCopy = new int[getRows()][];
		for (int row = 0; row < getRows(); row++) {
			wallWorldCopy[row] = Arrays.copyOf(wallworld[row], getCols());
		}
		return wallWorldCopy;
	}
	
	private int getRows() {
		return wallworld.length;
	}
	
	private int getCols() {
		return wallworld[0].length;
	}
	
	private void updatePactor(Pactor p) {
		String direction = (String) p.getValueOf("REQUESTED_DIRECTION");
		movePactorInDirection(p, direction);
		notifyPactorCollisions(p);
	}
	
	private void movePactorInDirection(Pactor toMove, String direction) {
		String name = (String) toMove.getValueOf("NAME");
		TileCoordinate c = pactorPositions.get(name);
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
			TileCoordinate myPos = pactorPositions.get(name);
			TileCoordinate otherPos = pactorPositions.get(otherName);
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
	
}