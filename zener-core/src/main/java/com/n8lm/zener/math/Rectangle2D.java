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

package com.n8lm.zener.math;

public class Rectangle2D {
	
	public float x0;
	public float x1;
	public float y0;
	public float y1;

    public Rectangle2D() {
        this(0, 0, 0, 0);
    }

    public Rectangle2D(float x, float y, float w, float h) {
        x0 = x;
		y0 = y;
		x1 = x + w;
		y1 = y + h;
	}
}
