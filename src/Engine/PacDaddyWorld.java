package Engine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import PacDaddyApplicationInterfaces.PacDaddyBoardReader;
import datastructures.Table;

final public class PacDaddyWorld implements PacDaddyBoardReader {

	private TileWorld tileWorld;
	private PactorCloud pactorCloud;
	volatile private Table<GameAttributes> hiddenPactorAttributes;
	volatile private HashMap<Integer, HashSet<String>> pactorLocations;
	
	public PacDaddyWorld() {
		tileWorld = new TileWorld();
		pactorCloud = new PactorCloud();
		hiddenPactorAttributes = new Table<GameAttributes>();
		pactorLocations = new HashMap<Integer, HashSet<String>>();
	}
	
	public void loadFromString(String worldstring) {
		tileWorld.loadFromString(worldstring);
	}
	
	public void addTileType(String name) {
		tileWorld.addTileType(name);
	}
	
	public int[][] getTiledBoard() {
		return tileWorld.getTiledBoard();
	}
	
	public String[] getTileNames() {
		return tileWorld.getTileNames();
	}
	
	public int getRows() {
		return tileWorld.getRows();
	}
	
	public int getCols() {
		return tileWorld.getCols();
	}
	
	public void addPactor(String name, Pactor p) {
		pactorCloud.addPactor(name, p);
		hiddenPactorAttributes.insert(name, new GameAttributes());
		setupHiddenWorldPactorAttributes(name);
		forceProperPactorAttributes(name);
		setPactorLocation(name);
	}
	
	public void removePactor(String name) {
		pactorCloud.removePactor(name);
		hiddenPactorAttributes.remove(name);
		removePactorLocation(name);
	}
	
	public Pactor getPactor(String name) {
		return pactorCloud.getPactor(name);
	}
	
	public int getRowOf(String name) {
		return getPositionFor(name).row;
	}
	
	public int getColOf(String name) {
		return getPositionFor(name).col;
	}
	
	public void setPactorSpawn(String name, int row, int col) {
		TileCoordinate spawn = getSpawnFor(name);
		spawn.row = row;
		spawn.col = col;
	}
	
	public void respawnPactor(String name) {
		removePactorLocation(name);
		TileCoordinate c = getPositionFor(name);
		TileCoordinate spawn = getSpawnFor(name);
		c.row = spawn.row;
		c.col = spawn.col;
		setPactorLocation(name);
	}
	
	public void respawnAllPactors() {
		for (String name : pactorCloud) {
			respawnPactor(name);
		}
	}
	
	public void setPactorSpeed(String name, float speed__pct) {
		pactorCloud.getPactor(name).setAttribute("SPEED__PCT", speed__pct);
	}
	
	public boolean isTraversableForPactor(int row, int col, String pactorname) {
		int tilenum = tileWorld.getTile(row, col);
		String tilename = tileWorld.getTileNames()[tilenum];
		return getTraversableTilesFor(pactorname).contains(tilename);
	}
	
	public void setTileAsTraversableForPactor(String tilename, String pactorname) {
		getTraversableTilesFor(pactorname).add(tilename);
	}
	
	public GameAttributes[] getInfoForAllPactorsWithAttribute(String attribute) {
		ArrayList<GameAttributes> info = new ArrayList<GameAttributes>();
		for (String pactor : pactorCloud) {
			if (doesPactorHaveAttribute(pactor, attribute)) {
				GameAttributes pactorInfo = getWorldInfoForPactor(pactor);
				info.add(pactorInfo);
			}
		}
		return info.toArray(new GameAttributes[]{});
	}
	
	public boolean doesPactorHaveAttribute(String pactorname, String attribute) {
		Pactor p = pactorCloud.getPactor(pactorname);
		return p.getValueOf(attribute) != null;
	}
	
	public GameAttributes getWorldInfoForPactor(String name) {
		GameAttributes info = new GameAttributes();
		Pactor p = pactorCloud.getPactor(name);
		TileCoordinate c = getPositionFor(name);
		info.setAttribute("ROW", c.row);
		info.setAttribute("COL", c.col);
		info.setAttribute("DIRECTION", p.getValueOf("DIRECTION"));
		info.setAttribute("SPEED__PCT", p.getValueOf("SPEED__PCT"));
		info.setAttribute("NAME", name);
		return info;
	}
	
	public int getNumberOfPactorsWithAttribute(String attribute) {
		int count = 0;
		for (String pactor : pactorCloud) {
			if (doesPactorHaveAttribute(pactor, attribute)) {
				count++;
			}
		}
		return count;
	}
	
	public boolean canPactorMoveInDirection(String pactorName, String direction) {
		TileCoordinate pactorPosition = getPositionFor(pactorName);
		TileCoordinate adjacentTile = getAdjacentTileCoordinateInDirection(pactorPosition, direction);
		return isTraversableForPactor(adjacentTile.row, adjacentTile.col, pactorName);
	}
	
	public String[] getPactorNames() {
		return pactorCloud.getPactorNames();
	}
	
	void tick() {
		for (String name : pactorCloud) {
			tickPactor(name);
		}
	}
	
	private void forceProperPactorAttributes(String name) {
		Pactor p = pactorCloud.getPactor(name);
		p.setAttribute("NAME", name);
		if (!doesPactorHaveAttribute(name, "SPEED__PCT")) {
			p.setAttribute("SPEED__PCT", 1.0f);
		}
	}
	
	private void setupHiddenWorldPactorAttributes(String name) {
		GameAttributes g = hiddenPactorAttributes.get(name);
		g.setAttribute("SPAWN", new TileCoordinate());
		g.setAttribute("POSITION", new TileCoordinate());
		g.setAttribute("TICK_COUNTER", 0);
		g.setAttribute("TICKS_TO_MOVE", 0);
		g.setAttribute("FRACTIONAL_TICK_ACCUMULATOR", 0f);
		g.setAttribute("CAN_TRAVERSE", new HashSet<String>());
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
	
	private void updatePactor(String pactorName) {
		String requestedDirection = getRequestedPactorDirection(pactorName);
		removePactorLocation(pactorName);
		attemptToMovePactorInDirection(pactorName, requestedDirection);
		setPactorLocation(pactorName);
		notifyPactorCollisions(pactorName);
	}
	
	private void removePactorLocation(String pactorName) {
		TileCoordinate tc = getPositionFor(pactorName);
		int id = tc.hashCode();
		if (!pactorLocations.containsKey(id)) {
			pactorLocations.put(id, new HashSet<String>());
		}
		pactorLocations.get(id).remove(pactorName);
	}
	
	private void setPactorLocation(String pactorName) {
		TileCoordinate tc = getPositionFor(pactorName);
		int id = tc.hashCode();
		if (!pactorLocations.containsKey(id)) {
			pactorLocations.put(id, new HashSet<String>());
		}
		pactorLocations.get(id).add(pactorName);
	}
	
	private Set<String> getPotentialCollisionsForPactor(String pactorName) {
		TileCoordinate tc = getPositionFor(pactorName);
		int id = tc.hashCode();
		return pactorLocations.get(id);
	}
	
	private void attemptToMovePactorInDirection(String pactorName, String direction) {
		if (canPactorMoveInDirection(pactorName, direction)) {
			movePactorInDirection(pactorName, direction);
		} else {
			maintainCurrentPactorMovementIfPossible(pactorName);
		}
	}
	
	private void movePactorInDirection(String pactorName, String direction) {
		TileCoordinate pactorPosition = getPositionFor(pactorName);
		TileCoordinate adjacentTile = getAdjacentTileCoordinateInDirection(pactorPosition, direction);
		setPactorPosition(pactorName, adjacentTile);
		setPactorDirection(pactorName, direction);
	}

	private void maintainCurrentPactorMovementIfPossible(String pactorName) {
		String currentDirection = getPactorDirection(pactorName);
		if (canPactorMoveInDirection(pactorName, currentDirection)) {
			movePactorInDirection(pactorName, currentDirection);
		}
	}
	
	private TileCoordinate getAdjacentTileCoordinateInDirection(TileCoordinate current, String direction) {
		TileCoordinate adjacentTile = new TileCoordinate();
		adjacentTile.row = direction == "UP"   ? (current.row - 1) : direction == "DOWN"  ? (current.row + 1) : current.row;
		adjacentTile.col = direction == "LEFT" ? (current.col - 1) : direction == "RIGHT" ? (current.col + 1) : current.col;
		tileWorld.wrapTileCoordinateToWorldBounds(adjacentTile);
		return adjacentTile;
	}
	
	private void setPactorPosition(String pactorName, TileCoordinate newPosition) {
		TileCoordinate pactorPosition = getPositionFor(pactorName);
		pactorPosition.row = newPosition.row;
		pactorPosition.col = newPosition.col;
	}
	
	private void setPactorDirection(String pactorName, String direction) {
		Pactor p = pactorCloud.getPactor(pactorName);
		p.setAttribute("DIRECTION", direction);
	}
	
	private String getPactorDirection(String pactorName) {
		Pactor p = pactorCloud.getPactor(pactorName);
		return (String) p.getValueOf("DIRECTION");
	}
	
	private String getRequestedPactorDirection(String pactorName) {
		Pactor p = pactorCloud.getPactor(pactorName);
		return (String) p.getValueOf("REQUESTED_DIRECTION");
	}
	
	private void notifyPactorCollisions(String pactorName) {
		for (String otherName : getPotentialCollisionsForPactor(pactorName)) {
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
		Pactor pactorA = pactorCloud.getPactor(A);
		Pactor pactorB = pactorCloud.getPactor(B);
		pactorA.notifyCollidedWith(pactorB);
		pactorB.notifyCollidedWith(pactorA);
	}
	
	private TileCoordinate getSpawnFor(String name) {
		if (hiddenPactorAttributes.contains(name)) {
			return (TileCoordinate) hiddenPactorAttributes.get(name).getValueOf("SPAWN");
		} else {
			return new TileCoordinate();
		}
	}
	
	private TileCoordinate getPositionFor(String name) {
		if (hiddenPactorAttributes.contains(name)) {
			return (TileCoordinate) hiddenPactorAttributes.get(name).getValueOf("POSITION");
		} else {
			return new TileCoordinate();
		}
	}
	
	@SuppressWarnings("unchecked")
	private HashSet<String> getTraversableTilesFor(String name) {
		return (HashSet<String>) hiddenPactorAttributes.get(name).getValueOf("CAN_TRAVERSE");
	}
	
	private void incrementPactorTickCounter(String name) {
		GameAttributes g = hiddenPactorAttributes.get(name);
		g.setAttribute("TICK_COUNTER", (int)g.getValueOf("TICK_COUNTER") + 1);
	}
	
	private boolean isTimeToUpdatePactor(String name) {
		GameAttributes g = hiddenPactorAttributes.get(name);
		return ((int)g.getValueOf("TICK_COUNTER") >= (int)g.getValueOf("TICKS_TO_MOVE"));
	}
	
	private void resetPactorTicker(String name) {
		GameAttributes g = hiddenPactorAttributes.get(name);
		g.setAttribute("TICK_COUNTER", 0);
	}
	
	private void calculatePactorTickerTrigger(String name) {
		GameAttributes g = hiddenPactorAttributes.get(name);
		float speed__pct = (float) pactorCloud.getPactor(name).getValueOf("SPEED__PCT");
		
		if (speed__pct == 0) {
			g.setAttribute("TICKS_TO_MOVE", 0);
		} else {
			float smoothTicks = (1f / speed__pct);
			int roughTicksLowerBound = (int)Math.floor(smoothTicks);
			float roundOffTicks = smoothTicks - roughTicksLowerBound;
			g.setAttribute("FRACTIONAL_TICK_ACCUMULATOR", (float)g.getValueOf("FRACTIONAL_TICK_ACCUMULATOR") + roundOffTicks);
			int carryTicks = (int)Math.floor((float)g.getValueOf("FRACTIONAL_TICK_ACCUMULATOR"));
			g.setAttribute("FRACTIONAL_TICK_ACCUMULATOR", (float)g.getValueOf("FRACTIONAL_TICK_ACCUMULATOR") - carryTicks);
			roughTicksLowerBound += carryTicks;
			g.setAttribute("TICKS_TO_MOVE", roughTicksLowerBound);
		}
	}
	
}