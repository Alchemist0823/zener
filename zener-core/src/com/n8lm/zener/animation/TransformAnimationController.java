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

import com.artemis.Entity;
import com.n8lm.zener.general.TransformComponent;
import com.n8lm.zener.math.Transform;

/**
 * Basic transformation animation controller
 * @author Alchemist
 *
 */
public class TransformAnimationController extends
		AnimationController<TransformKeyFrame> {

	private Transform origin;
	
	public TransformAnimationController(Animation<TransformKeyFrame> anim) {
		this(anim, new Transform(), false);
	}
	
	public TransformAnimationController(Animation<TransformKeyFrame> anim, Transform origin) {
		this(anim, origin, false);
	}
	
	public TransformAnimationController(Animation<TransformKeyFrame> anim, Transform origin, boolean isLoop) {
		super(anim, isLoop);
		this.origin = origin;
	}

	@Override
	protected void process(int nowIndex, int nextIndex, Entity e) {
		TransformKeyFrame nowf = anim.getFrame(nowIndex);
		TransformKeyFrame nextf = anim.getFrame(nextIndex);
		
		float totalTime = nextf.getTime() - nowf.getTime();
		float nowTime = time - nowf.getTime();
		
		if (totalTime == 0)
			totalTime = 1;
		e.getComponent(TransformComponent.class).getLocalTransform()
		.interpolateTransforms(nowf.transform, nextf.transform, nowTime / totalTime);

		e.getComponent(TransformComponent.class).getLocalTransform().combineFromParent(origin);
		//System.out.println(nowIndex + " " + nextIndex + " " + time + " " + e.getComponent(TransformComponent.class).getLocalTransform());		
	}

}
