package com.n8lm.zener.animation;

import com.artemis.Entity;
import com.n8lm.zener.components.SkeletonComponent;

public class SkeletonAnimationController extends AnimationController<PosesKeyFrame> {

	public SkeletonAnimationController(Animation<PosesKeyFrame> anim) {
		super(anim);
	}

	public SkeletonAnimationController(Animation<PosesKeyFrame> anim,
			boolean isLoop) {
		super(anim, isLoop);
	}

	@Override
	protected void process(int nowIndex, int nextIndex, Entity e) {
		
		PosesKeyFrame keyframe = anim.getFrame(nowIndex);
		
		e.getComponent(SkeletonComponent.class).setCurrentPoseMatrices(keyframe.getPoseMatrices());

		//List<Pose> poses = keyframe.getPoses();
		/*
		for (int i = 0; i < poses.size(); i ++) {
			e.getComponent(SkeletonComponent.class).getSkeleton().getJoints()poses.get(i);
		}*/
		
	}

}
