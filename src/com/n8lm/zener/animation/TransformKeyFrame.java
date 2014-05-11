package com.n8lm.zener.animation;

import com.n8lm.zener.math.Transform;

public class TransformKeyFrame extends KeyFrame {

	Transform transform;
	
	public TransformKeyFrame(Transform transform, int time) {
		super(time);
		this.transform = transform;
	}

}
