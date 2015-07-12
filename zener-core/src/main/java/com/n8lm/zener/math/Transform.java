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

package com.n8lm.zener.math;

import com.n8lm.zener.utils.TempVars;

import java.io.Serializable;

public class Transform implements Serializable {
	
	public static final Transform UNIT = new Transform(Vector3f.ZERO, Quaternion.IDENTITY, Vector3f.UNIT_XYZ);
	
	private final Vector3f trans;
	private final Quaternion rot;
	private final Vector3f scale;

	public Transform() {
		trans = new Vector3f();
		rot = new Quaternion();
		scale = new Vector3f(1,1,1);
	}

	public Transform(float x, float y, float z) {
		this();
		trans.set(x, y, z);
	}
	
	public Transform(Vector3f trans, Quaternion rot, Vector3f scale) {
		this();
		this.set(trans, rot, scale);
	}
	
	public Transform(Transform transform) {
		this();
		set(transform);
	}
	
	public void set(Transform transform) {
		this.trans.set(transform.trans);
		this.rot.set(transform.rot);
		this.scale.set(transform.scale);	
	}
	
	public void set(Vector3f trans, Quaternion rot, Vector3f scale) {
		this.trans.set(trans);
		this.rot.set(rot);
		this.scale.set(scale);
	}

	public Vector3f getTranslation() {
		return trans;
	}

	public void setTranslation(Vector3f trans) {
		this.trans.set(trans);
	}
	
	public Quaternion getRotation() {
		return rot;
	}

	public void setRotation(Quaternion rotation) {
		this.rot.set(rotation);
	}
	
	public Vector3f getScale() {
		return scale;
	}

	public void setScale(Vector3f scale) {
		this.scale.set(scale);
	}
	
	
	public Matrix4f getModelMatrix(Matrix4f modelMat) {
		if (modelMat == null)
			modelMat = new Matrix4f();

        TempVars tempVars = TempVars.get();
        modelMat.setTransform(trans, scale, rot.toRotationMatrix(tempVars.tempMat3));
        tempVars.release();
        return modelMat;
	}

    /** Applies the camera translations and rotations to Matrix. */
    public Matrix4f getViewMatrix(Matrix4f viewMat) {
    	
    	if (viewMat == null) {
    		viewMat = new Matrix4f();
    	}

    	Vector3f up = new Vector3f();
    	Vector3f left = new Vector3f();
    	Vector3f direction = new Vector3f();
    	
    	rot.getRotationColumn(0, left);
    	rot.getRotationColumn(1, up);
    	rot.getRotationColumn(2, direction);
    	
    	viewMat.fromFrame(trans, direction, up, left);
    	/*
    	
    	viewMat.set(Matrix4f.IDENTITY);
    	viewMat.setTranslation(trans.negate());
    	
    	Matrix4f matrix4f = new Matrix4f();
    	matrix4f.setRotationQuaternion(rot.opposite());
    	matrix4f.mult(viewMat, viewMat);
    	*/
    	
    	return viewMat;
    }
    
    /**
     * Sets this matrix to the interpolation between the first matrix and the second by delta amount.
     * @param t1 The begining transform.
     * @param t2 The ending transform.
     * @param delta An amount between 0 and 1 representing how far to interpolate from t1 to t2.
     */
    public void interpolateTransforms(Transform t1, Transform t2, float delta) {
        this.rot.slerp(t1.rot,t2.rot,delta);
        this.trans.interpolateLocal(t1.trans,t2.trans,delta);
        this.scale.interpolateLocal(t1.scale,t2.scale,delta);
    }
    
    /**
     * Changes the values of this matrix acording to it's parent.
     * @param parent The parent matrix.
     * @return This matrix, after combining.
     */
    public Transform combineFromParent(Transform parent) {
        scale.multLocal(parent.scale);
//        rot.multLocal(parent.rot);
        parent.rot.mult(rot, rot);

        // This here, is evil code
//        parent
//            .rot
//            .multLocal(translation)
//            .multLocal(parent.scale)
//            .addLocal(parent.translation);

        trans.multLocal(parent.scale);
        parent
            .rot
            .multLocal(trans)
            .addLocal(parent.trans);
        return this;
    }

	public void moveFromLook(Vector3f d) {
    	/*
        this.position.z += d.x * (float) Math.cos(Math.toRadians(rot.y - 90)) + d.z * Math.cos(Math.toRadians(rot.y));
        this.position.x -= d.x * (float) Math.sin(Math.toRadians(rot.y - 90)) + d.z * Math.sin(Math.toRadians(rot.y));
        this.position.y += d.y * (float) Math.sin(Math.toRadians(rot.x - 90)) + d.z * Math.sin(Math.toRadians(rot.x));
        */
        //float hypotenuseX = dx;
        //float adjacentX = hypotenuseX * (float) Math.cos(Math.toRadians(yaw - 90));
        //float oppositeX = (float) Math.sin(Math.toRadians(yaw - 90)) * hypotenuseX;
        //this.z += adjacentX;
        //this.x -= oppositeX;
        //
        //this.y += dy;
        //
        //float hypotenuseZ = dz;
        //float adjacentZ = hypotenuseZ * (float) Math.cos(Math.toRadians(yaw));
        //float oppositeZ = (float) Math.sin(Math.toRadians(yaw)) * hypotenuseZ;
        //this.z += adjacentZ;
        //this.x -= oppositeZ;
    }
	
	@Override
	public String toString() {
		return "Trans: " + trans.toString() + " Rot: " + rot.toString() + "Scale: " + scale.toString();
	}
}
