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
	final Table<Pactor> pactors;
	private int[][] wallworld;
	private GameAttributes noPactorAvailableTileAttributes;
	private PactorToTileFunction pactorToTile;
	final private Queue<String> removalQueue;	
	
	public PacDaddyWorld() {
		tilenames = new ArrayList<String>();
		pactors = new Table<Pactor>();
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
			movePactor(toMove);
			checkPactorCollisionsWithPactor(toMove);
		}
		
		while (!removalQueue.isEmpty()) {
			pactors.remove(removalQueue.dequeue());
		}
	}
	
	final public void addPactor(String name, Pactor p) {
		p.setAttribute("NAME", name);
		p.respawn();
		pactors.insert(name, p);
	}
	
	final public void removePactor(String name) {
		removalQueue.enqueue(name);
	}
	
	final public Pactor getPactor(String name) {
		return pactors.get(name);
	}
	
	public PacDaddyAttributeReader getAttributeReaderAtTile(int row, int col) {
		for (String name : pactors.getNames()) {
			Pactor p = pactors.get(name);
			if (p.getRow() == row && p.getCol() == col) {
				return p;
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
			worldRepresentation[p.getRow()][p.getCol()] = tilenames.indexOf(s);
		}
		
		return worldRepresentation;
	}
	
	public String[] getTileNames() {
		return tilenamesarray;
	}
	
	private void movePactor(Pactor toMove) {
		switch((String) toMove.getValueOf("DIRECTION")) {
		case "UP":
			if (!isWall(toMove.getRow() - 1, toMove.getCol())) {
				toMove.shiftRow(-1);
			}
			break;
		case "DOWN":
			if (!isWall(toMove.getRow() + 1, toMove.getCol())) {
				toMove.shiftRow(+1);
			}
			break;
		case "RIGHT":
			if (!isWall(toMove.getRow(), toMove.getCol() + 1)) {
				toMove.shiftCol(+1);
			}
			break;
		case "LEFT":
			if (!isWall(toMove.getRow(), toMove.getCol() - 1)) {
				toMove.shiftCol(-1);
			}
			break;
		}
	}
	
	private boolean isWall(int row, int col) {
		return row < 0 || col < 0 || row >= getRows() || col >= getCols() || wallworld[row][col] == 1;
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
		for (String otherName : pactors.getNames()) {
			Pactor other = pactors.get(otherName);
			if (p != other && p.getRow() == other.getRow() && p.getCol() == other.getCol()) {
				p.notifyCollidedWith(other);
				other.notifyCollidedWith(p);
			}
		}
	}
	
}