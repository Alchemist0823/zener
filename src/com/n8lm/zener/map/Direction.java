/*
 * This file is part of Zener.
 *
 * Zener is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of
 * the License, or any later version.
 *
 * Zener is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General
 * Public License along with Zener.  If not, see
 * <http://www.gnu.org/licenses/>.
 */

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
