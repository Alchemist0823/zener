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

package com.n8lm.zener.animation;

import java.util.Iterator;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.systems.EntityProcessingSystem;

/**
 * Animation System process the animation component in every entity
 * @author Alchemist
 *
 */
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
