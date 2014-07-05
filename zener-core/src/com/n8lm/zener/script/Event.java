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

package com.n8lm.zener.script;

import com.artemis.Entity;

public class Event {
	
	public static final String WORLD_UPDATE = "worldUpdate";

	private String type;
	private Entity dispatcher;
	
	public Event(String type, Entity dispatcher) {
		this.type = type;
		this.dispatcher = dispatcher;
	}

	public Entity getDispatcher() {
		return dispatcher;
	}

	public String getType() {
		return type;
	}
	
}
