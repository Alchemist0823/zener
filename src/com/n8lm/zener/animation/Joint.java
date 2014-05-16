package com.n8lm.zener.animation;

import com.n8lm.zener.math.Matrix4f;
import com.n8lm.zener.math.Transform;

/**
 * a joint or a bone in Skeleton
 * @author Alchemist
 *
 */
public class Joint {

	public String name;
	public int parent;
	
	public Transform pose;
	public Matrix4f base;
	public Matrix4f inverseBase;
	
	public Joint() {
		pose = new Transform();
	}
}
