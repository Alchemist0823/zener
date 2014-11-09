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
import com.n8lm.zener.math.Vector4f;


public class Particle implements Comparable<Particle> {
	public final Vector3f position = new Vector3f();
	public final Vector3f velocity = new Vector3f();
    public float rotation;
    public int texIndex;
	public float size;
	public float life; // Remaining life of the particle. if < 0 : dead and unused.
	public final ColorRGBA color = new ColorRGBA(1.0f, 1.0f, 1.0f, 1.0f);
	public float cameradistance;

    Particle(){

    }

	@Override
	public int compareTo(Particle other) {
		return -Float.compare(this.cameradistance, other.cameradistance);
	}
}