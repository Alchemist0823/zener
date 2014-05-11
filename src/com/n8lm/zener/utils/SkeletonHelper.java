package com.n8lm.zener.utils;

import java.util.List;

import com.n8lm.zener.math.Matrix4f;

import com.n8lm.zener.animation.Joint;
import com.n8lm.zener.animation.Pose;
import com.n8lm.zener.animation.Skeleton;

public class SkeletonHelper {


	public static Matrix4f[] calcPoseMatrix(List<Pose> poses, Skeleton skeleton) {

		List<Joint> joints = skeleton.getJoints(); 
		Matrix4f[] m4a = new Matrix4f[skeleton.totalJoints()];
		for (int i = 0; i < m4a.length; i ++) {

			m4a[i] = new Matrix4f();
			m4a[i].setTransform(poses.get(i).position, poses.get(i).scale, poses.get(i).rotation.toRotationMatrix());
			
			if (joints.get(i).parent >= 0)
				joints.get(joints.get(i).parent).base.mult(m4a[i], m4a[i]);
			
			m4a[i].mult(joints.get(i).inverseBase, m4a[i]);
		}
		
		for (int i = 0; i < m4a.length; i ++) {
			if (joints.get(i).parent >= 0)
				m4a[joints.get(i).parent].mult(m4a[i], m4a[i]);
		}
		return m4a;
	}
}
