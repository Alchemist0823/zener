package com.n8lm.zener.map;

public enum Direction {

	NORTH(0),
	EAST(1),
	SOUTH(2),
	WEST(3);
	
	private final static int dir[][] = new int[][]{{0, 1}, {1, 0}, {0, -1}, {-1, 0}};

	public int id;
	
	Direction(int id) {
		this.id = id;
	}
	
	Direction() {
		this(0);
	}

	public Location getLocation() {
		return new Location(dir[id][0], dir[id][1]);
	}
	
	static public Direction getDirection(Location loc) {
		for (int i = 0; i < 4; i ++)
			if(dir[i][0] == loc.x && dir[i][1] == loc.y)
				return Direction.values()[i];
		return Direction.values()[0];
	}
}
