package com.n8lm.zener.animation;

import java.util.ArrayList;
import java.util.List;

import com.n8lm.zener.math.Matrix4f;


public class PosesKeyFrame extends KeyFrame {

	private List<Pose> poses;
	private Matrix4f[] poseMatrices;
	
	public PosesKeyFrame(int time) {
		super(time);
		poses = new ArrayList<Pose>();
	}

	public List<Pose> getPoses() {
		return poses;
	}

	public void add(Pose pose) {
		this.poses.add(pose);
	}
	
	public void calcPoseMatrices(Skeleton skeleton) {
		poseMatrices = SkeletonHelper.calcPoseMatrix(poses, skeleton);
	}

	public Matrix4f[] getPoseMatrices() {
		return poseMatrices;
	}

	public void setPoseMatrices(Matrix4f[] poseMatrices) {
		this.poseMatrices = poseMatrices;
	}

}
