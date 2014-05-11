package com.n8lm.zener.systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.systems.EntityProcessingSystem;
import com.n8lm.zener.components.Script;
import com.n8lm.zener.components.ScriptComponent;

public class ScriptSystem extends EntityProcessingSystem {

	protected @Mapper ComponentMapper<ScriptComponent> sm;
	
	@SuppressWarnings("unchecked")
	public ScriptSystem() {
		super(Aspect.getAspectForAll(ScriptComponent.class));
	}

	@Override
	protected void process(Entity e) {
		runAllScripts(e);
	}

	public void runAllScripts(Entity e) {
		for (Script script : sm.get(e).getScripts()) {
			script.run(e);
		}
	}
}
