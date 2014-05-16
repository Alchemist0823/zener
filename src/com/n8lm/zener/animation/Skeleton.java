package com.n8lm.zener.animation;

import java.util.ArrayList;
import java.util.List;

import com.n8lm.zener.math.Matrix4f;

import com.n8lm.zener.math.MathUtil;

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
