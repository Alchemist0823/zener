package com.n8lm.zener.components;

import com.n8lm.zener.math.Quaternion;
import com.n8lm.zener.math.Vector3f;

import com.artemis.Component;

public class VelocityComponent extends Component {
	private Vector3f velocity;
	private Quaternion angleVelocity;

	public VelocityComponent() {
		this.velocity = new Vector3f(0, 0, 0);
		this.angleVelocity = new Quaternion(0, 0, 0, 0);
	}
	
	public VelocityComponent(Vector3f velocity, Quaternion angleVelocity) {
		this.velocity = velocity;
		this.angleVelocity = angleVelocity;
	}

	public Vector3f getVelocity() {
		return velocity;
	}

	public void setVelocity(Vector3f velocity) {
		this.velocity = velocity;
	}

	public Quaternion getAngleVelocity() {
		return angleVelocity;
	}

	public void setAngleVelocity(Quaternion angleVelocity) {
		this.angleVelocity = angleVelocity;
	}
	/*
    public void pitch(float a) {
    	angleVelocity.x += a;
    }

    public void yaw(float a) {
    	angleVelocity.y += a;
    }

    public void roll(float a) {
    	angleVelocity.z += a;
    }*/

}
