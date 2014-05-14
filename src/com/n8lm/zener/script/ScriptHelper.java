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
			LOGGER.warning("Dispatch event to a entity which do not has ScriptComponent");
			
	}
}
