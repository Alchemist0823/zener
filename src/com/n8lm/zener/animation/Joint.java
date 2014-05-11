package com.n8lm.zener.animation;

import com.n8lm.zener.math.Matrix4f;

public class Joint {

	public String name;
	public int parent;
	
	public Pose pose;
	public Matrix4f base;
	public Matrix4f inverseBase;
	
	public Joint() {
		pose = new Pose();
	}
}
