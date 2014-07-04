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

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.systems.EntityProcessingSystem;

public class GlobalScriptSystem extends EntityProcessingSystem {

	protected @Mapper ComponentMapper<ScriptComponent> sm;
	
	@SuppressWarnings("unchecked")
	public GlobalScriptSystem() {
		super(Aspect.getAspectForAll(ScriptComponent.class));
	}

	@Override
	protected void process(Entity e) {
		runUpdateScripts(e);
	}

	public void runUpdateScripts(Entity e) {
		if (sm.get(e).hasEventScripts(Event.WORLD_UPDATE))
			for (Script script : sm.get(e).getScriptsByEvent(Event.WORLD_UPDATE)) {
				script.run(world, new Event(Event.WORLD_UPDATE, e));
			}
	}
}
