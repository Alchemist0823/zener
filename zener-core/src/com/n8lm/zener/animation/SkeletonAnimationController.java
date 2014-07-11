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

/**
 * A controller deals with bone-based skeleton animation
 * @author Alchemist
 *
 */
public class SkeletonAnimationController extends AnimationController<PosesKeyFrame> {

	public SkeletonAnimationController(Animation<PosesKeyFrame> anim) {
		super(anim);
	}

    public SkeletonAnimationController(Animation<PosesKeyFrame> anim,
                                       boolean isLoop) {
        this(anim, isLoop, 1.0f);
    }

	public SkeletonAnimationController(Animation<PosesKeyFrame> anim,
			boolean isLoop, float speed) {
		super(anim, isLoop, speed);
	}

	@Override
	protected void process(int nowIndex, int nextIndex, Entity e) {

        PosesKeyFrame nextFrame = anim.getFrame(nextIndex);
		PosesKeyFrame keyframe = anim.getFrame(nowIndex);
        float delta;
        if (nextFrame.getTime() - keyframe.getTime() == 0)
            delta = 0.0f;
        else
            delta = (time - keyframe.getTime()) / (nextFrame.getTime() - keyframe.getTime());
		e.getComponent(SkeletonComponent.class).setCurrentPosesMatrices(keyframe.getPoseMatrices(), nextFrame.getPoseMatrices(), delta);

		//List<Pose> poses = keyframe.getPoses();
		/*
		for (int i = 0; i < poses.size(); i ++) {
			e.getComponent(SkeletonComponent.class).getSkeleton().getJoints()poses.get(i);
		}*/
		
	}

}
