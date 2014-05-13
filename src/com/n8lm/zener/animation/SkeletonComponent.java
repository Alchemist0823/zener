package com.n8lm.zener.animation;

import java.util.ArrayList;
import java.util.List;

import com.n8lm.zener.math.Matrix4f;
import com.artemis.Component;

public class SkeletonComponent extends Component {

	private Matrix4f[] poseMatrices;
	private Skeleton baseSkeleton;
	
	public SkeletonComponent(Skeleton baseSkeleton) {
		this.baseSkeleton = baseSkeleton;
		
		ArrayList<Pose> poses = new ArrayList<Pose>();
		for (int i = 0; i < baseSkeleton.totalJoints(); i ++)
			poses.add(new Pose(baseSkeleton.getJoints().get(i).pose));

		poseMatrices = SkeletonHelper.calcPoseMatrix(poses, baseSkeleton);
		
	}
	
	public void setToBasePose() {
		
		ArrayList<Pose> poses = new ArrayList<Pose>();
		for (int i = 0; i < baseSkeleton.totalJoints(); i ++)
			poses.add(new Pose(baseSkeleton.getJoints().get(i).pose));
		
		poseMatrices = SkeletonHelper.calcPoseMatrix(poses, baseSkeleton);
	}

	public Matrix4f[] getCurrentPoseMatrices() {
		return poseMatrices;
	}
	
	public Skeleton getBaseSkeleton() {
		return baseSkeleton;
	}

	public void setCurrentPoseMatrices(Matrix4f[] poseMatrices) {
		this.poseMatrices = poseMatrices;
	}
}
