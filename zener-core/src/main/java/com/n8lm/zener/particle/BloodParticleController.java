/*
 * This file is part of Zener.
 *
 * Zener is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of
 * the License, or any later version.
 *
 * Zener is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General
 * Public License along with Zener.  If not, see
 * <http://www.gnu.org/licenses/>.
 */

package com.n8lm.zener.particle;

import com.n8lm.zener.math.ColorRGBA;
import com.n8lm.zener.math.Vector3f;

public class BloodParticleController implements ParticleController {

	static final float plife = 2;
	static final Vector3f g = new Vector3f(0.0f, 0.0f, -10f);
	static final ColorRGBA color1 = new ColorRGBA(100.0f/256f, 0, 0, 1);
	static final ColorRGBA color2 = new ColorRGBA(50.0f/256f, 0, 0, 0);
	
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
        p.velocity.addLocal(g.mult(delta));
        p.velocity.x -= p.velocity.x/100;
        p.velocity.y -= p.velocity.y/100;
        p.position.addLocal(p.velocity.mult(delta));
        
        if (p.position.z < -1)
        	p.position.z = -1;
        
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
		p.position.set(0, 0, 0);
		p.velocity.set(dir.x + (float) (Math.random() * vary.x * 2 - vary.x),
				dir.y + (float) (Math.random() * vary.y * 2 - vary.y),
				dir.z + (float) (Math.random() * vary.z * 2 - vary.z));
		p.size = (float) (Math.random() * 0.05 + 0.05);
		p.life = plife;
		return p;
	}

	@Override
	public void init() {
	}
	
    /*
	@Override
	public boolean isEnd(double time) {
		if (time < duration + plife + startTime)
			return false;
		else
			return true;
	}*/

}
