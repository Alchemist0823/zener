package com.n8lm.zener.graphics;

import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.World;
import com.n8lm.zener.math.Vector4f;

public class RenderEffectHelper {

	private static ComponentMapper<RenderEffectComponent> em;
	
	public static void setMultipleColor(Entity entity, int layer, Vector4f color) {
		if (!em.has(entity)) {
			entity.addComponent(new RenderEffectComponent());
			entity.changedInWorld();
		}
		em.get(entity).setMultiplyColor(layer, color);
	}
	
	public static void removeAllRenderEffect(Entity entity) {
		if (em.has(entity)) {
			entity.removeComponent(RenderEffectComponent.class);
			entity.changedInWorld();
		}
	}
	
	public static void removeRenderEffect(Entity entity, int layer) {
		if (em.has(entity)) {
			em.get(entity).setMultiplyColor(layer, Vector4f.UNIT_XYZW);
			em.get(entity).setAddColor(layer, Vector4f.ZERO);
		}
	}
	
	public static void setup(World world) {
		em = world.getMapper(RenderEffectComponent.class);
	}
	
}
