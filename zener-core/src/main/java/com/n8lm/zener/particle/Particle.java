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
import com.n8lm.zener.math.Quaternion;
import com.n8lm.zener.math.Rectangle2D;
import com.n8lm.zener.math.Vector3f;

/**
 * The basic Particle class for the particle system.
 *
 * @author Forrest Sun
 */
public class Particle implements Comparable<Particle> {
	public final Vector3f position = new Vector3f();
	public final Vector3f velocity = new Vector3f();
    public final Quaternion rotation = new Quaternion();
    public final ColorRGBA color = new ColorRGBA(1.0f, 1.0f, 1.0f, 1.0f);
    public final Rectangle2D textureCoord = new Rectangle2D(0.0f, 0.0f, 1.0f, 1.0f);
    public float size;
    public float rotateAngle;

    /**
     * the time since the particle was emitted
     */
    public float time;

    /**
     * the life of the particle
     */
    public float life;
    public float order;

    Particle(){
        size = 1.0f;
        time = 0.0f;
        life = 1.0f;
        order = 0;
    }

	@Override
	public int compareTo(Particle other) {
		return -Float.compare(this.order, other.order);
	}
}