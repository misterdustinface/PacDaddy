package Engine;

import java.util.ArrayList;
import java.util.Arrays;

import datastructures.Table;
import PacDaddyApplicationInterfaces.PacDaddyBoardReader;
import PacDaddyApplicationInterfaces.PacDaddyAttributeReader;

public class PacDaddyWorld implements PacDaddyBoardReader {

	private String[] tilenamesarray;
	final private ArrayList<String> tilenames;	
	final Table<Pactor> pactors;
	private int[][] wallworld;
	private GameAttributes noPactorAvailableTileAttributes;
	
	public PacDaddyWorld() {
		tilenames = new ArrayList<String>();
		pactors = new Table<Pactor>();
		noPactorAvailableTileAttributes = new GameAttributes();
		noPactorAvailableTileAttributes.setAttribute("NO_PACTOR_AVAILABLE", true);
		
		loadFromString("1111\n"
					 + "1001\n"
					 + "1001\n"
					 + "1111\n");
	}
	
	public void loadFromString(String worldstring) {
		wallworld = Utilities.StringToIntArray(worldstring);
	}
	
	public void tick() {
		for (String name : pactors.getNames()) {
			Pactor toMove = getPactor(name);
			movePactor(toMove);
		}
	}
	
	final public void addPactor(String name, Pactor p) {
		pactors.insert(name, p);
		p.respawn();
	}
	
	final public void removePactor(String name) {
		pactors.remove(name);
	}
	
	final public Pactor getPactor(String name) {
		return pactors.get(name);
	}
	
	public PacDaddyAttributeReader getAttributeReaderAtTile(int row, int col) {
		for (String name : pactors.getNames()) {
			Pactor p = pactors.get(name);
			if (p.getTileCoordinate().row == row && p.getTileCoordinate().col == col) {
				return p;
			}
		}
		return noPactorAvailableTileAttributes;
	}
	
	public void addTileType(String name) {
		tilenames.add(name);
		tilenamesarray = tilenames.toArray(new String[]{});
	}
	
	public int[][] getTiledBoard() {
		
		TileCoordinate pactorCoordinate;
		int[][] worldRepresentation = getWallWorldCopy();

		for (String name : pactors.getNames()) {
			Pactor p = pactors.get(name);
			pactorCoordinate = p.getTileCoordinate();
			if (p.getValueOf("IS_PLAYER") != null) {
				worldRepresentation[pactorCoordinate.row][pactorCoordinate.col] = tilenames.indexOf("PLAYER");
			}
			if (p.getValueOf("IS_ENEMY") != null) {
				worldRepresentation[pactorCoordinate.row][pactorCoordinate.col] = tilenames.indexOf("ENEMY");
			}
		}
		
		return worldRepresentation;
	}
	
	public String[] getTileNames() {
		return tilenamesarray;
	}
	
	private void movePactor(Pactor toMove) {
		TileCoordinate coordinate = toMove.getTileCoordinate();
		switch((String) toMove.getValueOf("DIRECTION")) {
		case "UP":
			if (!isWall(coordinate.row - 1, coordinate.col)) {
				--coordinate.row;
			}
			break;
		case "DOWN":
			if (!isWall(coordinate.row + 1, coordinate.col)) {
				++coordinate.row;
			}
			break;
		case "RIGHT":
			if (!isWall(coordinate.row, coordinate.col + 1)) {
				++coordinate.col;
			}
			break;
		case "LEFT":
			if (!isWall(coordinate.row, coordinate.col - 1)) {
				--coordinate.col;
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
	
}