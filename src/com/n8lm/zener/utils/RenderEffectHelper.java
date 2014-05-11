package com.n8lm.zener.utils;

import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.World;
import com.n8lm.zener.components.RenderEffectComponent;
import com.n8lm.zener.math.Vector4f;

public class RenderEffectHelper {

	
	public static void setMultipleColor(Entity entity, 
			ComponentMapper<RenderEffectComponent> em, World world, Vector4f color) {
		if (!em.has(entity)) {
			entity.addComponent(new RenderEffectComponent());
			world.changedEntity(entity);
		}
		em.get(entity).setMultiplyColor(color);
	}
	
	public static void removeRenderEffect(Entity entity, 
			ComponentMapper<RenderEffectComponent> em, World world) {

		if (em.has(entity)) {
			entity.removeComponent(RenderEffectComponent.class);
			world.changedEntity(entity);
		}
	}
	
	
}
