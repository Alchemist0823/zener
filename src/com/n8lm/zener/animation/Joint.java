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

package com.n8lm.zener.animation;

import com.n8lm.zener.math.Matrix4f;
import com.n8lm.zener.math.Transform;

/**
 * a joint or a bone in Skeleton
 * @author Alchemist
 *
 */
public class Joint {

	public String name;
	public int parent;
	
	public Transform pose;
	public Matrix4f base;
	public Matrix4f inverseBase;
	
	public Joint() {
		pose = new Transform();
	}
}
