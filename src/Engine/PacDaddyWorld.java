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
	final private Table<Pactor> pactors;
	final private Table<TileCoordinate> pactorPositions;
	final private Table<TileCoordinate> pactorSpawns;
	final private Table<String>			currentDirection;
	private int[][] wallworld;
	private GameAttributes noPactorAvailableTileAttributes;
	private PactorToTileFunction pactorToTile;
	final private Queue<String> removalQueue;	
	
	public PacDaddyWorld() {
		tilenames = new ArrayList<String>();
		pactors = new Table<Pactor>();
		pactorPositions = new Table<TileCoordinate>();
		pactorSpawns = new Table<TileCoordinate>();
		currentDirection = new Table<String>();
		noPactorAvailableTileAttributes = new GameAttributes();
		noPactorAvailableTileAttributes.setAttribute("NO_PACTOR_AVAILABLE", true);
		pactorToTile = PactorToTileFunction.EMPTY_FUNCTION;
		removalQueue = new Queue<String>();
	}
	
	public void loadFromString(String worldstring) {
		wallworld = Utilities.StringToIntArray(worldstring);
	}
	
	public void tick() {
		for (String name : pactors.getNames()) {
			Pactor toMove = getPactor(name);
			String direction = (String) toMove.getValueOf("DIRECTION");
			movePactorInDirection(toMove, direction);
			checkPactorCollisionsWithPactor(toMove);
		}
		
		removeQueued();
	}
	
	private void removeQueued() {
		while (!removalQueue.isEmpty()) {
			String toRemove = removalQueue.dequeue();
			pactors.remove(toRemove);
			pactorPositions.remove(toRemove);
			pactorSpawns.remove(toRemove);
			currentDirection.remove(toRemove);
		}
	}
	
	final public void addPactor(String name, Pactor p) {
		p.setAttribute("NAME", name);
		pactorSpawns.insert(name, new TileCoordinate());
		pactorPositions.insert(name, new TileCoordinate());
		pactors.insert(name, p);
		currentDirection.insert(name, (String) p.getValueOf("DIRECTION"));
	}
	
	final public void removePactor(String name) {
		removalQueue.enqueue(name);
	}
	
	final public Pactor getPactor(String name) {
		return pactors.get(name);
	}
	
	final public int getPactorRow(Pactor p) {
		return getPactorRow((String) p.getValueOf("NAME"));
	}
	
	final public int getPactorCol(Pactor p) {
		return getPactorCol((String) p.getValueOf("NAME"));
	}
	
	final public int getPactorRow(String name) {
		return pactorPositions.get(name).row;
	}
	
	final public int getPactorCol(String name) {
		return pactorPositions.get(name).col;
	}
	
	final public void setPactorSpawn(Pactor p, int row, int col) {
		setPactorSpawn((String) p.getValueOf("NAME"), row, col);
	}
	
	final public void setPactorSpawn(String name, int row, int col) {
		TileCoordinate spawn = pactorSpawns.get(name);
		spawn.row = row;
		spawn.col = col;
	}
	
	final public void respawnPactor(Pactor p) {
		respawnPactor((String) p.getValueOf("NAME"));
	}
	
	final public void respawnPactor(String name) {
		TileCoordinate c = pactorPositions.get(name);
		TileCoordinate spawn = pactorSpawns.get(name);
		c.row = spawn.row;
		c.col = spawn.col;
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
		tilenames.add(name);
		tilenamesarray = tilenames.toArray(new String[]{});
	}
	
	public void setPactorToTileFunction(PactorToTileFunction PACTOR_TO_TILE) {
		pactorToTile = PACTOR_TO_TILE;
	}
	
	public int[][] getTiledBoard() {
		
		int[][] worldRepresentation = getWallWorldCopy();

		for (String name : pactors.getNames()) {
			Pactor p = pactors.get(name);
			String s = pactorToTile.getTileName(p);
			TileCoordinate c = pactorPositions.get(name);
			worldRepresentation[c.row][c.col] = tilenames.indexOf(s);
		}
		
		return worldRepresentation;
	}
	
	public String[] getTileNames() {
		return tilenamesarray;
	}
	
	private void moveUp(Pactor p) {
		String name = (String) p.getValueOf("NAME");
		TileCoordinate c = pactorPositions.get(name);
		if (!isWall(c.row - 1, c.col)) {
			--c.row;
			currentDirection.insert(name, "UP");
		} else if (currentDirection.get(name) != "UP") {
			movePactorInDirection(p, currentDirection.get(name));
		}
	}
	
	private void moveDown(Pactor p) {
		String name = (String) p.getValueOf("NAME");
		TileCoordinate c = pactorPositions.get(name);
		if (!isWall(c.row + 1, c.col)) {
			++c.row;
			currentDirection.insert(name, "DOWN");
		} else if (currentDirection.get(name) != "DOWN") {
			movePactorInDirection(p, currentDirection.get(name));
		}
	}
	
	private void moveLeft(Pactor p) {
		String name = (String) p.getValueOf("NAME");
		TileCoordinate c = pactorPositions.get(name);
		if (!isWall(c.row, c.col - 1)) {
			--c.col;
			currentDirection.insert(name, "LEFT");
		} else if (currentDirection.get(name) != "LEFT") {
			movePactorInDirection(p, currentDirection.get(name));
		}
	}
	
	private void moveRight(Pactor p) {
		String name = (String) p.getValueOf("NAME");
		TileCoordinate c = pactorPositions.get(name);
		if (!isWall(c.row, c.col + 1)) {
			++c.col;
			currentDirection.insert(name, "RIGHT");
		} else if (currentDirection.get(name) != "RIGHT") {
			movePactorInDirection(p, currentDirection.get(name));
		}
	}
	
	public boolean isWall(int row, int col) {
		return row < 0 || col < 0 || row >= getRows() || col >= getCols() 
			|| wallworld[row][col] == tilenames.indexOf("WALL");
	}
	
	private void movePactorInDirection(Pactor toMove, String direction) {
		switch(direction) {
		case "UP":    moveUp(toMove);
			break;
		case "DOWN":  moveDown(toMove);
			break;
		case "RIGHT": moveRight(toMove);
			break;
		case "LEFT":  moveLeft(toMove);
			break;
		}
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
	
	private void checkPactorCollisionsWithPactor(Pactor p) {
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
	
}