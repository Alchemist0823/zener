package com.n8lm.zener.graphics;

import com.n8lm.zener.math.Matrix4f;

public class OrthographicProjection implements Projection {

	private float aspectRatio;
	private float zNear;
	private float zFar;
	private float size;
	
	public OrthographicProjection(){
		this(1.0f, 10f, 0, 100.0f);
	}
	

	public OrthographicProjection(float size){
		this(1.0f, size, 0, 100.0f);
	}
	
	public OrthographicProjection(float aspectRatio, float size, float zNear, float zFar) {
		this.aspectRatio = aspectRatio;
		this.zNear = zNear;
		this.zFar = zFar;
		this.size = size;
	}
	
	@Override
	public Matrix4f getProjectionMatrix(Matrix4f projectionMat) {
		
		if (projectionMat == null)
			projectionMat = new Matrix4f();
		
		projectionMat.set(Matrix4f.IDENTITY);

		float top = size;
		float bottom = -size;
		float right = aspectRatio * top;
		float left = -aspectRatio * top;
		
		
		projectionMat.m00 = 2 / (right - left);
		projectionMat.m11 = 2 / (top - bottom);
		projectionMat.m22 = - 2 / (zFar - zNear);
		projectionMat.m33 = 1;
		projectionMat.m03 = - (right + left) / (right - left);
		projectionMat.m13 = - (top + bottom) / (top - bottom);
		projectionMat.m23 = - (zFar + zNear) / (zFar - zNear);
		
		return projectionMat;
	}

	@Override
	public float getAspectRatio() {
		return aspectRatio;
	}

	@Override
	public void setAspectRatio(float aspectRatio) {
		this.aspectRatio = aspectRatio;
	}

}
