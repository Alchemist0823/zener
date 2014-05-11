package com.n8lm.zener.graphics;

import com.n8lm.zener.math.Matrix4f;

public interface Projection {
	public Matrix4f getProjectionMatrix(Matrix4f projectionMat);
	public float getAspectRatio();
	public void setAspectRatio(float aspectRatio);
}
