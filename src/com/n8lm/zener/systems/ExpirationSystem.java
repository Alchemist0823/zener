package com.n8lm.zener.systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.systems.DelayedEntityProcessingSystem;
import com.n8lm.zener.components.ExpireComponent;

public class ExpirationSystem extends DelayedEntityProcessingSystem {

	@Mapper ComponentMapper<ExpireComponent> em;
	
	public ExpirationSystem() {
		super(Aspect.getAspectForAll(ExpireComponent.class));
	}

	@Override
	protected float getRemainingDelay(Entity e) {
		return em.get(e).getRemain() * 1000;
	}

	@Override
	protected void processDelta(Entity e, float accumulatedDelta) {
		em.get(e).substract(accumulatedDelta / 1000f);
	}

	@Override
	protected void processExpired(Entity e) {
		if(em.get(e).getExpireListener() != null)
			em.get(e).getExpireListener().endEvent(e);
		world.deleteEntity(e);		
	}

}
