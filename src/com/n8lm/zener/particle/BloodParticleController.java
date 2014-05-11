package com.n8lm.zener.particle;

import com.n8lm.zener.math.Vector3f;
import com.n8lm.zener.math.Vector4f;

public class BloodParticleController implements ParticleController {

	static final float plife = 2;
	static final Vector3f g = new Vector3f(0.0f, 0.0f, -10f);
	static final Vector4f color1 = new Vector4f(100.0f/256, 0, 0, 1);
	static final Vector4f color2 = new Vector4f(50.0f/256, 0, 0, 0);
	
	private Vector3f dir = new Vector3f();
	private Vector3f vary = new Vector3f();
	
	private float duration = 1f;
	private int amount = 0;
	private float startTime = 0; 
	
	@Override
	public int getNewCount(double time) {
		if (time <= duration + startTime && time >= startTime)
			return amount;
		else
			return 0;
	}

	@Override
	public int getMaxCount() {
		return (int) (amount * plife);
	}

	@Override
	public void process(Particle p, float delta) {
        p.speed.addLocal(g.mult(delta));
        p.speed.x -= p.speed.x/100;
        p.speed.y -= p.speed.y/100;
        p.pos.addLocal(p.speed.mult(delta));
        
        if (p.pos.z < -1)
        	p.pos.z = -1;
        
        p.color.interpolateLocal(color1, color2, (plife - p.life) / plife);
	}
	
	public void setUp(Vector3f dir, Vector3f vary, float startTime, float duration, int amount) {
		this.dir.set(dir);
		this.vary.set(vary);
		this.startTime = startTime;
		this.duration  = duration;
		this.amount = amount;
	}

	@Override
	public Particle setNewParticle(Particle p) {
		p.pos.set(0, 0, 0);
		p.speed.set(dir.x + (float) (Math.random() * vary.x * 2 - vary.x),
				dir.y + (float) (Math.random() * vary.y * 2 - vary.y),
				dir.z + (float) (Math.random() * vary.z * 2 - vary.z));
		p.size = (float) (Math.random() * 0.05 + 0.05);
		p.life = plife;
		return p;
	}

	@Override
	public void init() {
	}
	

	@Override
	public boolean isEnd(double time) {
		if (time < duration + plife + startTime)
			return false;
		else
			return true;
	}

}
