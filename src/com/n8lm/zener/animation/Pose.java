package com.n8lm.zener.animation;

import com.n8lm.zener.math.*;

public class Pose {

	public Quaternion rotation;
	public Vector3f position;
	public Vector3f scale;
	
	public Pose() {
		rotation = new Quaternion();
		position = new Vector3f();
		scale = new Vector3f();
	}

	public Pose(Pose pose) {
		this();
		set(pose);
	}

	public void set(Pose pose) {
		rotation.set(pose.rotation);
		position.set(pose.position);
		scale.set(pose.scale);
		
	}

}
