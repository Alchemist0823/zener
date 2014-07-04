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

package com.n8lm.zener.collision;

import com.artemis.Component;

public class CollidableComponent extends Component {

	private Collidable collidable;
	private boolean active;
	
	public CollidableComponent(Collidable collidable) {
		this(collidable, true);
	}

	public CollidableComponent(Collidable collidable, boolean active) {
		this.collidable = collidable;
		this.active = active;
	}

	public Collidable getCollidable() {
		return collidable;
	}

	public void setCollidable(Collidable collidable) {
		this.collidable = collidable;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

}
