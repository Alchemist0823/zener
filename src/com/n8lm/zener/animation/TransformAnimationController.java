package com.n8lm.zener.animation;

import com.artemis.Entity;
import com.n8lm.zener.components.TransformComponent;
import com.n8lm.zener.math.Transform;

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
