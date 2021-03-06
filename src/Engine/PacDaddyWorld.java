package Engine;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;

import PacDaddyApplicationInterfaces.PacDaddyBoardReader;
import datastructures.Table;

final public class PacDaddyWorld extends TileWorld implements PacDaddyBoardReader {

	private PactorCloud pactorCloud;
	volatile private Table<GameAttributes> hiddenPactorAttributes;
	volatile private ConcurrentHashMap<TileCoordinate, ConcurrentSkipListSet<String>> sharedPactorLocationBuckets;
	
	public PacDaddyWorld() {
		pactorCloud = new PactorCloud();
		hiddenPactorAttributes = new Table<GameAttributes>();
		sharedPactorLocationBuckets = new ConcurrentHashMap<TileCoordinate, ConcurrentSkipListSet<String>>();
	}
	
	public void addPactor(String name, Pactor p) {
		pactorCloud.addPactor(name, p);
		hiddenPactorAttributes.insert(name, new GameAttributes());
		setupHiddenWorldPactorAttributes(name);
		forceProperPactorAttributes(name);
		setPactorInCollisionBucket(name);
	}
	
	public void removePactor(String name) {
		removePactorFromCollisionBucket(name);
		pactorCloud.removePactor(name);
		hiddenPactorAttributes.remove(name);
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
		removePactorFromCollisionBucket(name);
		TileCoordinate c = getPositionFor(name);
		TileCoordinate spawn = getSpawnFor(name);
		c.row = spawn.row;
		c.col = spawn.col;
		setPactorInCollisionBucket(name);
	}
	
	public void respawnAllPactors() {
		for (String name : pactorCloud) {
			respawnPactor(name);
		}
	}
	
	public void setPactorSpeed(String name, float speed__pct) {
		getPactor(name).setAttribute("SPEED__PCT", speed__pct);
	}
	
	public boolean isTraversableForPactor(int row, int col, String pactorname) {
		int tilenum = getTile(row, col);
		String tilename = getTileNames()[tilenum];
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
		Pactor p = getPactor(pactorname);
		return p.getValueOf(attribute) != null;
	}
	
	public GameAttributes getWorldInfoForPactor(String name) {
		GameAttributes info = new GameAttributes();
		TileCoordinate c = getPositionFor(name);
		info.setAttribute("ROW",        c.row);
		info.setAttribute("COL",        c.col);
		info.setAttribute("DIRECTION",  getPactorDirection(name));
		info.setAttribute("SPEED__PCT", getPactorSpeed(name));
		info.setAttribute("NAME",       name);
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
	
	public void swap(int rowA, int colA, int rowB, int colB) {
		TileCoordinate A = new TileCoordinate();
		A.row = rowA;
		A.col = colA;
		TileCoordinate B = new TileCoordinate();
		B.row = rowB;
		B.col = colB;
		
		super.swap(A, B);
		swapPactorCollisionBuckets(A, B);
	}
	
	void tick() {
		for (String name : pactorCloud) {
			tickPactor(name);
		}
	}
	
	private void forceProperPactorAttributes(String name) {
		getPactor(name).setAttribute("NAME", name);
		if (!doesPactorHaveAttribute(name, "SPEED__PCT")) {
            setPactorSpeed(name, 1.0f);
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
		removePactorFromCollisionBucket(pactorName);
		attemptToMovePactorInDirection(pactorName, requestedDirection);
		setPactorInCollisionBucket(pactorName);
		notifyPactorCollisions(pactorName);
	}
	
	private void createCollisionBucketForTileCoordinateIfBucketAbsent(TileCoordinate tc) {
		if (!sharedPactorLocationBuckets.containsKey(tc)) {
			sharedPactorLocationBuckets.put(tc, new ConcurrentSkipListSet<String>());
		}
	}
	
	private void removePactorFromCollisionBucket(String pactorName) {
		TileCoordinate tc = getPositionFor(pactorName);
		createCollisionBucketForTileCoordinateIfBucketAbsent(tc);
		sharedPactorLocationBuckets.get(tc).remove(pactorName);
	}
	
	private void setPactorInCollisionBucket(String pactorName) {
		TileCoordinate tc = getPositionFor(pactorName);
		createCollisionBucketForTileCoordinateIfBucketAbsent(tc);
		sharedPactorLocationBuckets.get(tc).add(pactorName);
	}
	
	private ConcurrentSkipListSet<String> getPotentialCollisionsForPactor(String pactorName) {
		TileCoordinate tc = getPositionFor(pactorName);
		return getPactorCollisionBucket(tc);
	}
	
	private ConcurrentSkipListSet<String> getPactorCollisionBucket(TileCoordinate tc) {
		ConcurrentSkipListSet<String> bucket = sharedPactorLocationBuckets.get(tc);
		if (bucket == null) {
			bucket = new ConcurrentSkipListSet<String>();
		}
		return bucket;
	}
	
	private void setPactorCollisionBucket(TileCoordinate tc, ConcurrentSkipListSet<String> bucket) {
		sharedPactorLocationBuckets.put(tc, bucket);
	}
	
	private void swapPactorCollisionBuckets(TileCoordinate A, TileCoordinate B) {
		ConcurrentSkipListSet<String> SetA = getPactorCollisionBucket(A);
		ConcurrentSkipListSet<String> SetB = getPactorCollisionBucket(B);
		for (String name : SetA) {
			setPactorPosition(name, B);
		}
		for (String name : SetB) {
			setPactorPosition(name, A);
		}
		setPactorCollisionBucket(A, SetB);
		setPactorCollisionBucket(B, SetA);
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
		wrapTileCoordinateToWorldBounds(adjacentTile);
		return adjacentTile;
	}
	
	private void setPactorPosition(String pactorName, TileCoordinate newPosition) {
		TileCoordinate pactorPosition = getPositionFor(pactorName);
		pactorPosition.row = newPosition.row;
		pactorPosition.col = newPosition.col;
	}
	
	private void setPactorDirection(String pactorName, String direction) {
		Pactor p = getPactor(pactorName);
		p.setAttribute("DIRECTION", direction);
	}
	
	private String getPactorDirection(String pactorName) {
		Pactor p = getPactor(pactorName);
		return (String) p.getValueOf("DIRECTION");
	}
	
	private String getRequestedPactorDirection(String pactorName) {
		Pactor p =  getPactor(pactorName);
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
		Pactor pactorA = getPactor(A);
		Pactor pactorB = getPactor(B);
		pactorA.notifyCollidedWith(pactorB);
		pactorB.notifyCollidedWith(pactorA);
	}
	
	private float getPactorSpeed(String name) {
		return ((Number) getPactor(name).getValueOf("SPEED__PCT")).floatValue();
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
		return ((int)g.getValueOf("TICK_COUNTER") >= (int)g.getValueOf("TICKS_TO_MOVE"))
			&& (int)g.getValueOf("TICKS_TO_MOVE") > 0;
	}
	
	private void resetPactorTicker(String name) {
		GameAttributes g = hiddenPactorAttributes.get(name);
		g.setAttribute("TICK_COUNTER", 0);
	}
	
	private void calculatePactorTickerTrigger(String name) {
		GameAttributes g = hiddenPactorAttributes.get(name);
		float speed__pct = getPactorSpeed(name);
		
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