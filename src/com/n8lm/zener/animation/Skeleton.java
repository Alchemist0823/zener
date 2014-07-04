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

import java.util.ArrayList;
import java.util.List;

import com.n8lm.zener.math.Matrix4f;

/**
 * A Skeleton contains many bones. it is a structure of 
 * a bone-based animation
 * @author Alchemist
 *
 */
public class Skeleton {

	private List<Joint> joints; 
	
	public Skeleton() {
		joints = new ArrayList<Joint>();
	}
	
	public void addJoint(Joint joint) {
		this.joints.add(joint);
	}
	
	public List<Joint> getJoints() {
		return joints;
	}
	
	public void calcBaseMatrix(){

	    for (int i = 0; i < joints.size(); i ++) {
	        Joint j = joints.get(i);

			j.base = new Matrix4f();
			j.base.setTransform(j.pose);
			j.inverseBase = j.base.invert();
			
	        if(j.parent >= 0) {
				joints.get(j.parent).base.mult(j.base, j.base);
				j.inverseBase.mult(joints.get(j.parent).inverseBase, j.inverseBase);
	        }
	    }
	}

	public int totalJoints() {
		return joints.size();
	}
}
