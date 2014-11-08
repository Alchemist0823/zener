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

import com.artemis.Entity;
import com.n8lm.zener.general.TransformComponent;
import com.n8lm.zener.graphics.ViewComponent;

import java.util.Random;

public class MathUtil {


    /** A "close to zero" double epsilon value for use*/
    public static final double DBL_EPSILON = 2.220446049250313E-16d;
    /** A "close to zero" float epsilon value for use*/
    public static final float FLT_EPSILON = 1.1920928955078125E-7f;
    /** A "close to zero" float epsilon value for use*/
    public static final float ZERO_TOLERANCE = 0.0001f;
    public static final float ONE_THIRD = 1f / 3f;
    /** The value PI as a float. (180 degrees) */
    public static final float PI = (float) Math.PI;
    /** The value 2PI as a float. (360 degrees) */
    public static final float TWO_PI = 2.0f * PI;
    /** The value PI/2 as a float. (90 degrees) */
    public static final float HALF_PI = 0.5f * PI;
    /** The value PI/4 as a float. (45 degrees) */
    public static final float QUARTER_PI = 0.25f * PI;
    /** The value 1/PI as a float. */
    public static final float INV_PI = 1.0f / PI;
    /** The value 1/(2PI) as a float. */
    public static final float INV_TWO_PI = 1.0f / TWO_PI;
    /** A value to multiply a degree value by, to convert it to radians. */
    public static final float DEG_TO_RAD = PI / 180.0f;
    /** A value to multiply a radian value by, to convert it to degrees. */
    public static final float RAD_TO_DEG = 180.0f / PI;
    /** A precreated random object for random numbers. */
    public static final Random rand = new Random(System.currentTimeMillis());
    /** A "close to zero" float epsilon value for use*/

    private MathUtil() {
        new AssertionError();
    }

    /**
     * Returns true if the number is a power of 2 (2,4,8,16...)
     *
     * A good implementation found on the Java boards. note: a number is a power
     * of two if and only if it is the smallest number with that number of
     * significant bits. Therefore, if you subtract 1, you know that the new
     * number will have fewer bits, so ANDing the original number with anything
     * less than it will give 0.
     *
     * @param number
     *            The number to test.
     * @return True if it is a power of two.
     */
    public static boolean isPowerOfTwo(int number) {
        return (number > 0) && (number & (number - 1)) == 0;
    }


    public static int nearestPowerOfTwo(int number) {
        return (int) Math.pow(2, Math.ceil(Math.log(number) / Math.log(2)));
    }

    /**
     * Take a float input and clamp it between min and max.
     *
     * @param input
     * @param min
     * @param max
     * @return clamped input
     */
    public static float clamp(float input, float min, float max) {
        return (input < min) ? min : (input > max) ? max : input;
    }

    /**
     * Clamps the given float to be between 0 and 1.
     *
     * @param input
     * @return input clamped between 0 and 1.
     */
    public static float saturate(float input) {
        return clamp(input, 0f, 1f);
    }

    public static int roundUpPOT(int value) {
        return 1 << (32 - Integer.numberOfLeadingZeros(value-1));
    }

    public static float cos(float angle) {
        return (float) Math.cos(angle);
    }

    public static float sin(float angle) {
        return (float) Math.sin(angle);
    }


    /**
     * Returns a random float between 0 and 1.
     *
     * @return A random float between <tt>0.0f</tt> (inclusive) to
     *         <tt>1.0f</tt> (exclusive).
     */
    public static float nextRandomFloat() {
        return rand.nextFloat();
    }

    /**
     * Returns a random integer between min and max.
     *
     * @return A random int between <tt>min</tt> (inclusive) to
     *         <tt>max</tt> (inclusive).
     */
    public static int nextRandomInt(int min, int max) {
        return (int) (nextRandomFloat() * (max - min + 1)) + min;
    }

    public static int nextRandomInt() {
        return rand.nextInt();
    }
	/*public static Matrix4f translateToMatrix4f(float x, float y, float z) {
    	Matrix4f mat = new Matrix4f();

    	mat.m00 = 1.0f;
    	mat.m11 = 1.0f;
    	mat.m22 = 1.0f;
    	mat.m33 = 1.0f;

    	mat.m30 = x;
    	mat.m31 = y;
    	mat.m32 = z;
    	
    	return mat;
    }
    
    public static Matrix4f translateToMatrix4f(Vector3f v) {
    	Matrix4f mat = new Matrix4f();

    	mat.m00 = 1.0f;
    	mat.m11 = 1.0f;
    	mat.m22 = 1.0f;
    	mat.m33 = 1.0f;

    	mat.m30 = v.x;
    	mat.m31 = v.y;
    	mat.m32 = v.z;
    	
    	return mat;
    }*/
    
    /*public static Matrix4f rotate(float degree, float x, float y, float z) {
    	Matrix4f mat = new Matrix4f();
    	degree = (float) Math.toRadians(degree);
    	
    	float u = (float) Math.sqrt(x * x + y * y + z * z);
    	float c = (float) Math.cos(degree);
    	float s = (float) Math.sin(degree);
    	
    	if (u > 0.0f) {
    		x /= u;
    		y /= u;
    		z /= u;
    	}
    	
    	mat.m00 = c + x * x * (1 - c);
    	mat.m10 = x * y * (1 - c) - z * s;
    	mat.m20 = x * z * (1 - c) + y * s; 
    	mat.m30 = 0.0f;
    	
    	mat.m01 = y * x * (1 - c) + z * s;
    	mat.m11 = c + y * y * (1 - c);
    	mat.m21 = y * z * (1 - c) - x * s;
    	mat.m31 = 0.0f;
    	
    	mat.m02 = z * x * (1 - c) - y * s;
    	mat.m12 = z * y * (1 - c) + x * s;
    	mat.m22 = c + z * z * (1 - c);
    	mat.m32 = 0.0f;
    	
    	mat.m03 = 0.0f; mat.m13 = 0.0f; mat.m23 = 0.0f; mat.m33 = 1.0f;
    	
    	return mat;
    }

    public static Matrix4f scaleToMatrix4f(Vector3f v) {
    	Matrix4f mat = new Matrix4f();

    	mat.m00 = v.x;
    	mat.m11 = v.y;
    	mat.m22 = v.z;
    	mat.m33 = 1.0f;
    	
    	return mat;
    }
    
    public static Matrix4f scaleToMatrix4f(float x, float y, float z) {
    	Matrix4f mat = new Matrix4f();

    	mat.m00 = x;
    	mat.m11 = y;
    	mat.m22 = z;
    	mat.m33 = 1.0f;
    	
    	return mat;
    }*/
    /*
    public static Matrix4f quaternionToMatrix4f(Quaternion q)
    {
    	Matrix4f matrix = new Matrix4f();
    	matrix.m00 = 1.0f - 2.0f * ( q.getY() * q.getY() + q.getZ() * q.getZ() );
    	matrix.m01 = 2.0f * (q.getX() * q.getY() + q.getZ() * q.getW());
    	matrix.m02 = 2.0f * (q.getX() * q.getZ() - q.getY() * q.getW());
    	matrix.m03 = 0.0f;

    	// Second row
    	matrix.m10 = 2.0f * ( q.getX() * q.getY() - q.getZ() * q.getW() );
    	matrix.m11 = 1.0f - 2.0f * ( q.getX() * q.getX() + q.getZ() * q.getZ() );
    	matrix.m12 = 2.0f * (q.getZ() * q.getY() + q.getX() * q.getW() );
    	matrix.m13 = 0.0f;

    	// Third row
    	matrix.m20 = 2.0f * ( q.getX() * q.getZ() + q.getY() * q.getW() );
    	matrix.m21 = 2.0f * ( q.getY() * q.getZ() - q.getX() * q.getW() );
    	matrix.m22 = 1.0f - 2.0f * ( q.getX() * q.getX() + q.getY() * q.getY() );
    	matrix.m23 = 0.0f;
    
    	// Fourth row
    	matrix.m30 = 0;
    	matrix.m31 = 0;
    	matrix.m32 = 0;
    	matrix.m33 = 1.0f;

    	return matrix;
    }*/
    
    /*public static Vector3f getNegate(Vector3f v) {
    	return new Vector3f(-v.x, -v.y, -v.z);
    }*/
    
    public static Vector3f getScreenCoordinates(Vector3f worldPosition, Entity camera, Vector3f store) {
        if (store == null) {
            store = new Vector3f();
        }
        

		Matrix4f viewProjectionMatrix = camera.getComponent(ViewComponent.class).getProjection()
				.getProjectionMatrix(null).mult(camera.getComponent(TransformComponent.class).getWorldTransform().getViewMatrix(null)); 
		
		Rectangle2D viewport = camera.getComponent(ViewComponent.class).getViewport();

//        TempVars vars = vars.lock();
//        Quaternion tmp_quat = vars.quat1;
//        tmp_quat.set( worldPosition.x, worldPosition.y, worldPosition.z, 1 );
//        viewProjectionMatrix.mult(tmp_quat, tmp_quat);
//        tmp_quat.multLocal( 1.0f / tmp_quat.getW() );
//        store.x = ( ( tmp_quat.getX() + 1 ) * ( viewPortRight - viewPortLeft ) / 2 + viewPortLeft ) * getWidth();
//        store.y = ( ( tmp_quat.getY() + 1 ) * ( viewPortTop - viewPortBottom ) / 2 + viewPortBottom ) * getHeight();
//        store.z = ( tmp_quat.getZ() + 1 ) / 2;
//        vars.release();

        float w = viewProjectionMatrix.multProj(worldPosition, store);
        store.divideLocal(w);

        store.x = (store.x + 1f) * (viewport.x1 - viewport.x0) / 2f + viewport.x0;
        store.y = (store.y + 1f) * (viewport.y0 - viewport.y1) / 2f + viewport.y1;
        store.z = (store.z + 1f) / 2f;

        return store;
    }

	public static Vector3f getWorldCoordinates(Vector2f screenPosition,
            float projectionZPos, Matrix4f viewProjectionMat, Rectangle2D viewport, Vector3f store) {
        if (store == null) {
            store = new Vector3f();
        }
  
        Matrix4f inverseMat = new Matrix4f(viewProjectionMat);
        inverseMat.invertLocal();

        store.set(
                (screenPosition.x - viewport.x0) / (viewport.x1 - viewport.x0) * 2 - 1,
                (screenPosition.y - viewport.y1) / (viewport.y0 - viewport.y1) * 2 - 1,
                projectionZPos * 2 - 1);
        /*
        store.set(
                (screenPosition.x / (float)Display.getWidth() - viewport.x0) / (viewport.x1 - viewport.x0) * 2 - 1,
                (screenPosition.y / (float)Display.getHeight() - viewport.y1) / (viewport.y0 - viewport.y1) * 2 - 1,
                projectionZPos * 2 - 1);
        */
        //System.out.println(store);

        float w = inverseMat.multProj(store, store);      
        store.multLocal(1f / w);

        return store;
    }
	
	public static Ray getMouseWorldRay(int x, int y, Entity camera) {
		// TODO Auto-generated method stub
		Matrix4f viewProjectionMatrix = camera.getComponent(ViewComponent.class).getProjection()
				.getProjectionMatrix(null).mult(camera.getComponent(TransformComponent.class).getWorldTransform().getViewMatrix(null)); 
		
		Rectangle2D viewport = camera.getComponent(ViewComponent.class).getViewport();


		Vector2f click2d = new Vector2f(x, y);
		Vector3f click3d = MathUtil.getWorldCoordinates(click2d, 0.0f, viewProjectionMatrix, viewport, null);
		Vector3f dir = MathUtil.getWorldCoordinates(click2d, 1.0f, viewProjectionMatrix, viewport, null).subtractLocal(click3d).normalizeLocal();
		
		return new Ray(click3d, dir);
	}
}
