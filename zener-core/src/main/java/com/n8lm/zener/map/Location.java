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

import java.io.Serializable;

public class Location implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 702942498934280807L;
	
	public int x;
	public int y;
	
	public Location(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public Location(Location o) {
		this(o.x, o.y);
	}
	
	public Location() {
		this(0, 0);
	}
	
	public void substract(Location l) {
		this.x -= l.x;
		this.y -= l.y;
	}
	
	public void add(Location l) {
		this.x += l.x;
		this.y += l.y;
	}
	
	public void add(int i) {
		this.x += i;
		this.y += i;
	}
	
	public static int distance(Location a, Location b) {
		return Math.abs(a.x - b.x) + Math.abs(a.y - b.y);
	}
	
	public void set(Location loc) {
		this.x = loc.x;
		this.y = loc.y;
	}

	@Override
	public String toString() {
		return "(" + x + ", " + y + ")";
	}

	public boolean equals(Location b) {
		return (this.x == b.x) && (this.y == b.y);
	}
}
