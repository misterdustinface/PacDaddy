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
	
	public PacDaddyWorld() {
		tilenames = new ArrayList<String>();
		pactors = new Table<Pactor>();
		
		addTileType("FLOOR");
		addTileType("WALL");
		addTileType("PLAYER");
		
		addPactor("PLAYER", new Pactor());
		
		loadFromString("1111\n"
					 + "1001\n"
					 + "1001\n"
					 + "1111\n");
	}
	
//	public void newEnemy(String name) {
//	Pactor newEnemy = new Pactor();
//  newEnemy.setAttribute("IS_ENEMY", true);
//	newEnemy.learnAction("ATTACK_PLAYER", new VoidFunctionPointer() {
//		public void call() {
//		}
//	});
//	addPactor(name, newEnemy);
//}
	
	public void loadFromString(String worldstring) {
		wallworld = Utilities.StringToIntArray(worldstring);
	}
	
	public void tick() {
		for (String name : pactors.getNames()) {
			movePactor(name);
		}
	}
	
	final public void addPactor(String name, Pactor p) {
		pactors.insert(name, p);
	}
	
	final public void removePactor(String name) {
		pactors.remove(name);
	}
	
	final public Pactor getPactor(String name) {
		return pactors.get(name);
	}
	
	public PacDaddyAttributeReader getAttributeReaderAtTile(int row, int col) {
		return pactors.get("PLAYER"); // TODO FIXME - make search algorithm to grab pactor at coordinate
	}
	
	public void addTileType(String name) {
		tilenames.add(name);
		tilenamesarray = tilenames.toArray(new String[]{});
	}
	
	public int[][] getTiledBoard() {
		
		int[][] worldRepresentation = getWallWorldCopy();
		
		TileCoordinate actorCoordinate = getPactor("PLAYER").getTileCoordinate();
		worldRepresentation[actorCoordinate.row][actorCoordinate.col] = tilenames.indexOf("PLAYER");
		
		for (String name : pactors.getNames()) {
			Pactor p = pactors.get(name);
			if (p.getValueOf("IS_ENEMY") != null) {
				actorCoordinate = p.getTileCoordinate();
				worldRepresentation[actorCoordinate.row][actorCoordinate.col] = tilenames.indexOf("ENEMY");
			}
		}
		
		return worldRepresentation;
	}
	
	public String[] getTileNames() {
		return tilenamesarray;
	}
	
	private void movePactor(String name) {
		Pactor toMove = getPactor(name);
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