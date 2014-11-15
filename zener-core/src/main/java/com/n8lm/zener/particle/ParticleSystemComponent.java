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

import com.artemis.Component;
import com.artemis.utils.ArrayBag;
import com.artemis.utils.Bag;

/**
 * A ParticleSystemComponent is a Component
 * which contains the setting of a Particle System
 *
 * @author Forrest Sun
 */
public class ParticleSystemComponent extends Component {

    /**
     * the count of the particles
     */
	private int count;

    /**
     * the total time from start
     */
    private float time;

    /**
     * the count of the particles which is produced in this second
     */
    private int countPerSecond;

    /**
     * the duration of this system
     */
    private float duration;

    /**
     * the maximum number of the particles
     */
    private int maxCount;

    /**
     * the array of particles
     */
    private final Particle[] particles;
    private final ParticleEmitter emitter;
    private final Bag<ParticleField> fields;

    public ParticleSystemComponent(ParticleEmitter emitter) {
        this(emitter, 500);
    }

    public ParticleSystemComponent(ParticleEmitter emitter, int maxCount) {
        this(emitter, maxCount, 0);
    }
	
	public ParticleSystemComponent(ParticleEmitter emitter, int maxCount, float duration) {
        this.emitter = emitter;
        this.duration = duration;
        this.maxCount = maxCount;
        this.fields = new ArrayBag<>();
        this.particles = new Particle[maxCount];
		this.count = 0;
		this.time = 0;
        this.countPerSecond = 0;
	}

    public ParticleSystemComponent(ParticleEmitter emitter, int maxCount, float duration, ParticleField field) {
        this(emitter, maxCount, duration);
        this.fields.add(field);
    }

    public int getCountPerSecond() {
        return countPerSecond;
    }

    public void setCountPerSecond(int countPerSecond) {
        this.countPerSecond = countPerSecond;
    }

	public Particle[] getParticles() {
		return particles;
	}
	
	public int getCount() {
		return count;
	}
	
	public void setCount(int count) {
		this.count = count;
	}

	public float getTime() {
		return time;
	}

	public void passTime(float time) {
        if ((int)(this.time) < (int)(this.time + time))
            countPerSecond = 0;
		this.time += time;
	}

    public double getDuration() {
        return duration;
    }

    public void setDuration(float duration) {
        this.duration = duration;
    }

    public int getMaxCount() {
        return maxCount;
    }

    public void setMaxCount(int maxCount) {
        this.maxCount = maxCount;
    }

    public ParticleEmitter getEmitter() {
        return emitter;
    }

    public Bag<ParticleField> getFields() {
        return fields;
    }
}
