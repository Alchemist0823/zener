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

import java.util.logging.Logger;

import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.World;
import com.n8lm.zener.utils.EntityFactory;

public class ScriptHelper {

	private final static Logger LOGGER = Logger.getLogger(ScriptHelper.class
			.getName());
	
	private static ComponentMapper<ScriptComponent> scm;

	public static void setup(World world) {
		scm = world.getMapper(ScriptComponent.class);
	}
	
	public static void dispatchEvent(World world, Entity entity,
			Event event) {
		if (scm.has(entity) && scm.get(entity).hasEventScripts(event.getType()))
			for (Script script : scm.get(entity).getScriptsByEvent(event.getType())) {
				script.run(world, event);
			}
		else
			LOGGER.warning("Dispatch event " + event.getType() + "  to a entity which do not has ScriptComponent");
			
	}
}
