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

public class FireParticleController implements ParticleController {


	static final float plife = 2;
	static final Vector3f g = new Vector3f(0.0f, 0.0f, -1f);
    static final Vector4f color = new Vector4f(1f, 1f, 1f, 0.5f);
	static final Vector4f color1 = new Vector4f(248.0f/256, 229.0f/256, 13.0f/256, 100f/256);
	static final Vector4f color2 = new Vector4f(239.0f/256, 97.0f/256, 11.0f/256, 0);

    private int atlasCount = 1;

    public FireParticleController(int atlasCount) {
        this.atlasCount = atlasCount;
    }

	@Override
	public void process(Particle p, float delta) {
        p.speed.addLocal(g.mult(delta * 0.5f));
        p.pos.addLocal(p.speed.mult(delta));
        //p.color.interpolateLocal(color1, color2, (plife - p.life) / plife);
        //(int) ((plife - p.life) / plife * atlasCount);
    }

	@Override
	public Particle setNewParticle(Particle p) {
		p.pos.set(0, 0, 0);
		p.speed.set((float) (Math.random() * 1 - 0.5)/3, (float) (Math.random() * 1 - 0.5)/3, (float) (Math.random() * 3 + 2)/3);
		p.size = (float) (Math.random() * 0.1 + 0.3);
        p.color.set(color);
        p.color.z = (float) (Math.random() * 0.5 + 0.2);
        p.texIndex = (int) (Math.random() * atlasCount);
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
