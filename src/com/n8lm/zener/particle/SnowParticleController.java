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

import com.n8lm.zener.math.Vector3f;
import com.n8lm.zener.math.Vector4f;

public class SnowParticleController implements ParticleController {


	static final float plife2 = 8;
	static final Vector3f g = new Vector3f(-1, 0, 0);
	static final Vector4f color1 = new Vector4f(1, 1, 1, 0.8f);
	static final Vector4f color2 = new Vector4f(1, 1, 1, 0.4f);


	@Override
	public void process(Particle p, float delta) {
        p.speed.addLocal(g.mult(delta * 0.5f));
        if(p.pos.z > 0) {
        	p.pos.addLocal(p.speed.mult(delta));
        	if(p.pos.z < 0)
        		p.pos.z = 0;
        }
        p.color.interpolateLocal(color1, color2, (plife2 - p.life) / plife2);
	}

	@Override
	public Particle setNewParticle(Particle p) {
		p.pos.set((float) (Math.random() * 20), (float) (Math.random() * 20), 10);
		p.speed.set((float) (Math.random() * 2 - 1), (float) (Math.random() * 2 - 1), (float) (Math.random() * 1 - 2.5));
		p.size = (float) (Math.random() * 0.02 + 0.02);
		p.life = plife2;
		return p;
	}

	@Override
	public void init() {
		
	}

	@Override
	public int getNewCount(double time) {
		return 300;
	}

	@Override
	public int getMaxCount() {
		return 2400;
	}

	@Override
	public boolean isEnd(double time) {
		return false;
	}

}
