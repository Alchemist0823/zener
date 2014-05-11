package com.n8lm.zener.particle;

import com.n8lm.zener.math.Vector3f;
import com.n8lm.zener.math.Vector4f;

public class FireParticleController implements ParticleController {


	static final float plife = 2;
	static final Vector3f g = new Vector3f(0.0f, 0.0f, -1f);
	static final Vector4f color1 = new Vector4f(248.0f/256, 229.0f/256, 13.0f/256, 100f/256);
	static final Vector4f color2 = new Vector4f(239.0f/256, 97.0f/256, 11.0f/256, 0);
	

	@Override
	public void process(Particle p, float delta) {
        p.speed.addLocal(g.mult(delta * 0.5f));
        p.pos.addLocal(p.speed.mult(delta));
        p.color.interpolateLocal(color1, color2, (plife - p.life) / plife);
	}

	@Override
	public Particle setNewParticle(Particle p) {
		p.pos.set(0, 0, 0);
		p.speed.set((float) (Math.random() * 2 - 1)/3, (float) (Math.random() * 2 - 1)/3, (float) (Math.random() * 3 + 2)/3);
		p.size = (float) (Math.random() * 0.1 + 0.1);
		p.life = plife;
		return p;
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getNewCount(double time) {
		// TODO Auto-generated method stub
		return 60;
	}

	@Override
	public int getMaxCount() {
		// TODO Auto-generated method stub
		return 120;
	}

	@Override
	public boolean isEnd(double time) {
		// TODO Auto-generated method stub
		return false;
	}

}
