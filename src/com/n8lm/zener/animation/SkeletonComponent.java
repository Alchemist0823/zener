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
