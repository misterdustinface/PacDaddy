package Engine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

import PacDaddyApplicationInterfaces.PacDaddyBoardReader;
import datastructures.Queue;
import datastructures.Table;

final public class PacDaddyWorld implements PacDaddyBoardReader {

	volatile private String[] tilenamesarray;
	volatile private Table<Pactor> pactors;	
	volatile private Table<GameAttributes> worldPactorAttributes;
	volatile private int[][] tileWorld;
	volatile private Queue<String> pactorRemovalQueue;
	
	public PacDaddyWorld() {
		worldPactorAttributes = new Table<GameAttributes>();
		pactors = new Table<Pactor>();
		pactorRemovalQueue = new Queue<String>();
		tilenamesarray = new String[]{};
	}
	
	public void loadFromString(String worldstring) {
		tileWorld = Utilities.StringToIntArray(worldstring);
	}
	
	public void tick() {
		for (String name : pactors.getNames()) {
			tickPactor(name);
		}
		performAllRequestedPactorRemovals();
	}
	
	public void addPactor(String name, Pactor p) {
		setupWorldPactorAttributes(name, p);
		forceProperPactorAttributes(name, p);
		pactors.insert(name, p);
	}
	
	public void removePactor(String name) {
		pactorRemovalQueue.enqueue(name);
	}
	
	public Pactor getPactor(String name) {
		return pactors.get(name);
	}
	
	public int getRowOf(String name) {
		return getPositionFor(name).row;
	}
	
	public int getColOf(String name) {
		return  getPositionFor(name).col;
	}
	
	public void setPactorSpawn(String name, int row, int col) {
		TileCoordinate spawn = getSpawnFor(name);
		spawn.row = row;
		spawn.col = col;
	}
	
	public void respawnPactor(String name) {
		TileCoordinate c = getPositionFor(name);
		TileCoordinate spawn = getSpawnFor(name);
		c.row = spawn.row;
		c.col = spawn.col;
	}
	
	public void respawnAllPactors() {
		for (String name : pactors.getNames()) {
			respawnPactor(name);
		}
	}
	
	public void setPactorSpeed(String name, float speed__pct) {
		pactors.get(name).setAttribute("SPEED__PCT", speed__pct);
	}
	
	public void addTileType(String name) {
		if (!doesTileTypeAlreadyExist(name)) {
			tilenamesarray = Arrays.copyOf(tilenamesarray, tilenamesarray.length + 1);
			tilenamesarray[tilenamesarray.length - 1] = name;
		}
	}
	
	public boolean isTraversableForPactor(int row, int col, String pactorname) {
		int tilenum = tileWorld[row][col];
		String tilename = tilenamesarray[tilenum];
		return getTraversableTilesFor(pactorname).contains(tilename);
	}
	
	public void setTileAsTraversableForPactor(String tilename, String pactorname) {
		getTraversableTilesFor(pactorname).add(tilename);
	}
	
	public int[][] getTiledBoard() {
		return tileWorld;
	}
	
	public String[] getTileNames() {
		return tilenamesarray;
	}
	
	public int getRows() {
		return tileWorld.length;
	}
	
	public int getCols() {
		return tileWorld[0].length;
	}
	
	public GameAttributes[] getInfoForAllPactorsWithAttribute(String attribute) {
		ArrayList<GameAttributes> info = new ArrayList<GameAttributes>(); 
		for (String pactor : pactors.getNames()) {
			if (doesPactorHaveAttribute(pactor, attribute)) {
				GameAttributes pactorInfo = getWorldInfoForPactor(pactor);
				info.add(pactorInfo);
			}
		}
		return info.toArray(new GameAttributes[]{});
	}
	
	private boolean doesPactorHaveAttribute(String pactorname, String attribute) {
		Pactor p = pactors.get(pactorname);
		return p.getValueOf(attribute) != null;
	}
	
	private void forceProperPactorAttributes(String name, Pactor p) {
		p.setAttribute("NAME", name);
		if (p.getValueOf("SPEED__PCT") == null) {
			p.setAttribute("SPEED__PCT", 1.0f);
		}
	}
	
	private void setupWorldPactorAttributes(String name, Pactor p) {
		GameAttributes g = new GameAttributes();
		g.setAttribute("SPAWN", new TileCoordinate());
		g.setAttribute("POSITION", new TileCoordinate());
		g.setAttribute("TICK_COUNTER", 0);
		g.setAttribute("TICKS_TO_MOVE", 0);
		g.setAttribute("CAN_TRAVERSE", new HashSet<String>());
		worldPactorAttributes.insert(name, g);
	}
	
	private void tickPactor(String pactorname) {
		updatePactorTicker(pactorname);
		if (isTimeToUpdatePactor(pactorname)) {
			resetPactorTicker(pactorname);
			updatePactor(pactorname);
		}
	}
	
	private void updatePactorTicker(String name) {
		incrementPactorTickCounter(name);
		calculatePactorTickerTrigger(name);
	}
	
	private void performAllRequestedPactorRemovals() {
		while (!pactorRemovalQueue.isEmpty()) {
			String toRemove = pactorRemovalQueue.dequeue();
			pactors.remove(toRemove);
			worldPactorAttributes.remove(toRemove);
		}
	}
	
	private GameAttributes getWorldInfoForPactor(String name) {
		GameAttributes info = new GameAttributes();
		Pactor p = pactors.get(name);
		TileCoordinate c = getPositionFor(name);
		info.setAttribute("ROW", c.row);
		info.setAttribute("COL", c.col);
		info.setAttribute("DIRECTION", p.getValueOf("DIRECTION"));
		info.setAttribute("SPEED__PCT", p.getValueOf("SPEED__PCT"));
		info.setAttribute("NAME", name);
		return info;
	}
	
	private void updatePactor(String pactorName) {
		String direction = getRequestedPactorDirection(pactorName);
		movePactorInDirection(pactorName, direction);
		notifyPactorCollisions(pactorName);
	}
	
	private void movePactorInDirection(String pactorName, String direction) {
		TileCoordinate pactorPosition = getPositionFor(pactorName);
		TileCoordinate adjacentTile = getAdjacentTileCoordinateInDirection(pactorPosition, direction);
		
		if (isTraversableForPactor(adjacentTile.row, adjacentTile.col, pactorName)) {
			setPactorPosition(pactorName, adjacentTile);
			setPactorDirection(pactorName, direction);
		} else if (!isPactorFacingDirection(pactorName, direction)) {
			movePactorInDirection(pactorName, getPactorDirection(pactorName));
		}
	}
	
	private TileCoordinate getAdjacentTileCoordinateInDirection(TileCoordinate current, String direction) {
		TileCoordinate adjacentTile = new TileCoordinate();
		adjacentTile.row = direction == "UP"   ? (current.row - 1) : direction == "DOWN"  ? (current.row + 1) : current.row;
		adjacentTile.col = direction == "LEFT" ? (current.col - 1) : direction == "RIGHT" ? (current.col + 1) : current.col;
		wrapTileCoordinateToWorldBounds(adjacentTile);
		return adjacentTile;
	}
	
	private void setPactorPosition(String pactorName, TileCoordinate newPosition) {
		TileCoordinate pactorPosition = getPositionFor(pactorName);
		pactorPosition.row = newPosition.row;
		pactorPosition.col = newPosition.col;
	}
	
	private void setPactorDirection(String pactorName, String direction) {
		Pactor p = pactors.get(pactorName);
		p.setAttribute("DIRECTION", direction);
	}
	
	private boolean isPactorFacingDirection(String pactorName, String direction) {
		return getPactorDirection(pactorName) == direction;
	}
	
	private String getPactorDirection(String pactorName) {
		Pactor p = pactors.get(pactorName);
		return (String) p.getValueOf("DIRECTION");
	}
	
	private String getRequestedPactorDirection(String pactorName) {
		Pactor p = pactors.get(pactorName);
		return (String) p.getValueOf("REQUESTED_DIRECTION");
	}
	
	private void notifyPactorCollisions(String pactorName) {
		for (String otherName : pactors.getNames()) {
			if (havePactorsCollided(pactorName, otherName)) {
				notifyCollisionBetweenPactors(pactorName, otherName);
			}
		}
	}
	
	private boolean havePactorsCollided(String A, String B) {
		TileCoordinate APos = getPositionFor(A);
		TileCoordinate BPos = getPositionFor(B);
		return (A != B && APos.row == BPos.row && APos.col == BPos.col);
	}
	
	private void notifyCollisionBetweenPactors(String A, String B) {
		Pactor pactorA = pactors.get(A);
		Pactor pactorB = pactors.get(B);
		pactorA.notifyCollidedWith(pactorB);
		pactorB.notifyCollidedWith(pactorA);
	}
	
	private void wrapTileCoordinateToWorldBounds(TileCoordinate c) {
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
	
	@SuppressWarnings("unchecked")
	private HashSet<String> getTraversableTilesFor(String name) {
		return (HashSet<String>) worldPactorAttributes.get(name).getValueOf("CAN_TRAVERSE");
	}
	
	private boolean doesTileTypeAlreadyExist(String name) {
		for (String s : tilenamesarray) {
			if (s == name) {
				return true;
			}
		}
		return false;
	}
	
	private void incrementPactorTickCounter(String name) {
		GameAttributes g = worldPactorAttributes.get(name);
		g.setAttribute("TICK_COUNTER", (int)g.getValueOf("TICK_COUNTER") + 1);
	}
	
	private boolean isTimeToUpdatePactor(String name) {
		GameAttributes g = worldPactorAttributes.get(name);
		return (int)g.getValueOf("TICK_COUNTER") >= (int)g.getValueOf("TICKS_TO_MOVE");
	}
	
	private void resetPactorTicker(String name) {
		GameAttributes g = worldPactorAttributes.get(name);
		g.setAttribute("TICK_COUNTER", 0);
	}
	
	private void calculatePactorTickerTrigger(String name) {
		GameAttributes g = worldPactorAttributes.get(name);
		float speed__pct = (float) pactors.get(name).getValueOf("SPEED__PCT");
		
		if (speed__pct == 0) {
			g.setAttribute("TICKS_TO_MOVE", 0);
		} else {
			g.setAttribute("TICKS_TO_MOVE", (int)(100 / (100 * speed__pct)) );
		}
	}
}