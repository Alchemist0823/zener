package com.n8lm.zener.animation;

import java.util.ArrayList;
import java.util.List;

import com.n8lm.zener.math.Matrix4f;
import com.n8lm.zener.math.Transform;

/**
 * A Key Frame contains all the poses of bones in a skeleton
 * @author Alchemist
 *
 */
public class PosesKeyFrame extends KeyFrame {

	private List<Transform> poses;
	private Matrix4f[] poseMatrices;
	
	public PosesKeyFrame(int time) {
		super(time);
		poses = new ArrayList<Transform>();
	}

	public List<Transform> getTransforms() {
		return poses;
	}

	public void add(Transform pose) {
		this.poses.add(pose);
	}
	
	public void calcPoseMatrices(Skeleton skeleton) {
		poseMatrices = SkeletonHelper.calcTransformMatrix(poses, skeleton);
	}

	public Matrix4f[] getPoseMatrices() {
		return poseMatrices;
	}

	public void setTransformMatrices(Matrix4f[] poseMatrices) {
		this.poseMatrices = poseMatrices;
	}

}
