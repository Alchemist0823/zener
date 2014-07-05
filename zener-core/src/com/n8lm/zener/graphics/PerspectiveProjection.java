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

package com.n8lm.zener.graphics;

import com.n8lm.zener.math.Matrix4f;

public class PerspectiveProjection implements Projection {
	
	private float fov;
	private float aspectRatio;
	private float zNear;
	private float zFar;

	public PerspectiveProjection() {
		this(90, 1, 0.3f, 100.0f);
	}
	
	public PerspectiveProjection(float fov, float aspectRatio,
			float zNear, float zFar) {
		this.fov = fov;
		this.aspectRatio = aspectRatio;
		this.zNear = zNear;
		this.zFar = zFar;
	}
	
	@Override
	public Matrix4f getProjectionMatrix(Matrix4f projectionMat) {
		
		if (projectionMat == null)
			projectionMat = new Matrix4f();
		
		projectionMat.set(Matrix4f.IDENTITY);
		
		float f = 1.0f / (float) (Math.tan(Math.toRadians(fov / 2)));
        projectionMat.m00 = f / aspectRatio;
        projectionMat.m11 = f;
        projectionMat.m22 = (zFar + zNear) / (zNear - zFar);
        projectionMat.m32 = -1.0f;
        projectionMat.m23 = 2.0f * (zFar * zNear) / (zNear - zFar);
        projectionMat.m33 = 0.0f;
		
		return projectionMat;
    }

	public float getFov() {
		return fov;
	}

	public void setFov(float fov) {
		this.fov = fov;
	}

	@Override
	public float getAspectRatio() {
		return aspectRatio;
	}

	@Override
	public void setAspectRatio(float aspectRatio) {
		this.aspectRatio = aspectRatio;
	}

	public float getzNear() {
		return zNear;
	}

	public void setzNear(float zNear) {
		this.zNear = zNear;
	}

	public float getzFar() {
		return zFar;
	}

	public void setzFar(float zFar) {
		this.zFar = zFar;
	}
}
