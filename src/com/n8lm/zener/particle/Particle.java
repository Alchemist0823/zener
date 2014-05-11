package com.n8lm.zener.particle;

import com.n8lm.zener.math.Vector3f;
import com.n8lm.zener.math.Vector4f;


public class Particle implements Comparable<Particle> {
	public Vector3f pos;
	public Vector3f speed;
	public float size;
	public float life; // Remaining life of the particle. if < 0 : dead and unused.
	public Vector4f color;
	public float cameradistance;
	
	public Particle() {
		pos = new Vector3f();
		speed = new Vector3f();
		size = 0;
		life = 100;
		color = new Vector4f(1.0f, 1.0f, 1.0f, 1.0f);
		cameradistance = 0;
	}

	@Override
	public int compareTo(Particle other) {
		return -Float.compare(this.cameradistance, other.cameradistance);
	}
}