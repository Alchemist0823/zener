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
        poseMatrices = new Matrix4f[skeleton.totalJoints()];
		SkeletonHelper.calcTransformMatrix(poses, skeleton, poseMatrices);
	}

	public Matrix4f[] getPoseMatrices() {
		return poseMatrices;
	}

	public void setTransformMatrices(Matrix4f[] poseMatrices) {
		this.poseMatrices = poseMatrices;
	}

}
