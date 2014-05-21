package com.n8lm.zener.animation;

import java.util.ArrayList;
import com.n8lm.zener.math.Matrix4f;
import com.n8lm.zener.math.Transform;
import com.artemis.Component;

/**
 * A component store a skeleton and current poses of bones in the skeleton
 * @author Alchemist
 *
 */
public class SkeletonComponent extends Component {

	private Matrix4f[] poseMatrices;
	private Skeleton baseSkeleton;
	
	public SkeletonComponent(Skeleton baseSkeleton) {
		this.baseSkeleton = baseSkeleton;
		
		ArrayList<Transform> poses = new ArrayList<Transform>();
		for (int i = 0; i < baseSkeleton.totalJoints(); i ++)
			poses.add(new Transform(baseSkeleton.getJoints().get(i).pose));

		poseMatrices = SkeletonHelper.calcTransformMatrix(poses, baseSkeleton);
		
	}
	
	public void setToBasePoses() {
		
		ArrayList<Transform> poses = new ArrayList<Transform>();
		for (int i = 0; i < baseSkeleton.totalJoints(); i ++)
			poses.add(new Transform(baseSkeleton.getJoints().get(i).pose));
		
		poseMatrices = SkeletonHelper.calcTransformMatrix(poses, baseSkeleton);
	}

	public Matrix4f[] getCurrentPosesMatrices() {
		return poseMatrices;
	}
	
	public Skeleton getBaseSkeleton() {
		return baseSkeleton;
	}

	public void setCurrentPosesMatrices(Matrix4f[] poseMatrices) {
		this.poseMatrices = poseMatrices;
	}
}
