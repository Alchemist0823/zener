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

package com.n8lm.zener.animation;

import com.n8lm.zener.map.Location;

/**
 * A keyframe store the location on a tiled map
 * @author Alchemist
 *
 */
public class LocationKeyFrame extends KeyFrame {

	Location loc;
	
	public LocationKeyFrame(int time, Location loc) {
		super(time);
		this.loc = loc;
	}

}
