package com.n8lm.zener.animation;

import com.n8lm.zener.math.Transform;

/**
 * An key frame stores a transform
 * @author Alchemist
 *
 */
public class TransformKeyFrame extends KeyFrame {

	Transform transform;
	
	public TransformKeyFrame(Transform transform, int time) {
		super(time);
		this.transform = transform;
	}

}
