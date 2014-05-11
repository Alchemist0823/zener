package com.n8lm.zener.systems;

import java.util.Iterator;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.systems.EntityProcessingSystem;
import com.n8lm.zener.animation.AnimationController;
import com.n8lm.zener.components.AnimationComponent;

public class AnimationSystem extends EntityProcessingSystem {

	@Mapper ComponentMapper<AnimationComponent> am;
	
	public AnimationSystem() {
		super(Aspect.getAspectForAll(AnimationComponent.class));
	}

	@Override
	protected void process(Entity e) {
		AnimationComponent animComp = am.get(e);
		
		Iterator<AnimationController<?>> it = animComp.getAnimationControllers().iterator();
		while (it.hasNext()) {
			if (it.next().process(e))
				it.remove();
		}
	}
	

}
