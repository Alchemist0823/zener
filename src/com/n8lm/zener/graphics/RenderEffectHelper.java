package com.n8lm.zener.graphics;

import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.World;
import com.n8lm.zener.math.Vector4f;

public class RenderEffectHelper {

	private static ComponentMapper<RenderEffectComponent> em;
	
	public static void setMultipleColor(Entity entity, Vector4f color) {
		if (!em.has(entity)) {
			entity.addComponent(new RenderEffectComponent());
			entity.changedInWorld();
		}
		em.get(entity).setMultiplyColor(color);
	}
	
	public static void removeRenderEffect(Entity entity) {
		if (em.has(entity)) {
			entity.removeComponent(RenderEffectComponent.class);
			entity.changedInWorld();
		}
	}
	
	public static void setup(World world) {
		em = world.getMapper(RenderEffectComponent.class);
	}
	
}
