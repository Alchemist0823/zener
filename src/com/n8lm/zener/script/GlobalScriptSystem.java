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
