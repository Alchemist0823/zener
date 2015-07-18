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

import java.awt.*;
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
     * Returns the square root of a given value.
     *
     * @param fValue The value to sqrt.
     * @return The square root of the given value.
     * @see java.lang.Math#sqrt(double)
     */
    public static float sqrt(float fValue) {
        return (float) Math.sqrt(fValue);
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


    public static float nextRandomFloat(float lower, float upper) {
        return nextRandomFloat() * (upper - lower) + lower;
    }

    /**
     * Returns a random vector3f between (0, 0 ,0) and (1, 1, 1).
     * @return A random float between <tt>(0.0f, 0.0f, 0.0f)</tt> (inclusive) to
     *         <tt>(1.0f, 1.0f, 1.0f)</tt> (exclusive).
     */
    public static Vector3f nextRandomVector3f(Vector3f vec) {
        vec.set(nextRandomFloat(), nextRandomFloat(), nextRandomFloat());
        return vec;
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

    /**Interpolate a spline between at least 4 control points following the Bezier equation.
     * here is the interpolation matrix
     * m = [ -1.0   3.0  -3.0    1.0 ]
     *     [  3.0  -6.0   3.0    0.0 ]
     *     [ -3.0   3.0   0.0    0.0 ]
     *     [  1.0   0.0   0.0    0.0 ]
     * where T is the curve tension
     * the result is a value between p1 and p3, t=0 for p1, t=1 for p3
     * @param u value from 0 to 1
     * @param p0 control point 0
     * @param p1 control point 1
     * @param p2 control point 2
     * @param p3 control point 3
     * @return Bezier interpolation
     */
    public static float interpolateBezier(float u, float p0, float p1, float p2, float p3) {
        float oneMinusU = 1.0f - u;
        float oneMinusU2 = oneMinusU * oneMinusU;
        float u2 = u * u;
        return p0 * oneMinusU2 * oneMinusU
                + 3.0f * p1 * u * oneMinusU2
                + 3.0f * p2 * u2 * oneMinusU
                + p3 * u2 * u;
    }

    /**Interpolate a spline between at least 4 control points following the Bezier equation.
     * here is the interpolation matrix
     * m = [ -1.0   3.0  -3.0    1.0 ]
     *     [  3.0  -6.0   3.0    0.0 ]
     *     [ -3.0   3.0   0.0    0.0 ]
     *     [  1.0   0.0   0.0    0.0 ]
     * where T is the tension of the curve
     * the result is a value between p1 and p3, t=0 for p1, t=1 for p3
     * @param u value from 0 to 1
     * @param p0 control point 0
     * @param p1 control point 1
     * @param p2 control point 2
     * @param p3 control point 3
     * @param store a Vector3f to store the result
     * @return Bezier interpolation
     */
    public static Vector3f interpolateBezier(float u, Vector3f p0, Vector3f p1, Vector3f p2, Vector3f p3, Vector3f store) {
        if (store == null) {
            store = new Vector3f();
        }
        store.x = interpolateBezier(u, p0.x, p1.x, p2.x, p3.x);
        store.y = interpolateBezier(u, p0.y, p1.y, p2.y, p3.y);
        store.z = interpolateBezier(u, p0.z, p1.z, p2.z, p3.z);
        return store;
    }

    /**Interpolate a spline between at least 4 control points following the Bezier equation.
     * here is the interpolation matrix
     * m = [ -1.0   3.0  -3.0    1.0 ]
     *     [  3.0  -6.0   3.0    0.0 ]
     *     [ -3.0   3.0   0.0    0.0 ]
     *     [  1.0   0.0   0.0    0.0 ]
     * where T is the tension of the curve
     * the result is a value between p1 and p3, t=0 for p1, t=1 for p3
     * @param u value from 0 to 1
     * @param p0 control point 0
     * @param p1 control point 1
     * @param p2 control point 2
     * @param p3 control point 3
     * @return Bezier interpolation
     */
    public static Vector3f interpolateBezier(float u, Vector3f p0, Vector3f p1, Vector3f p2, Vector3f p3) {
        return interpolateBezier(u, p0, p1, p2, p3, null);
    }


    /**Interpolate a spline between at least 4 control points following the Bezier equation.
     * here is the interpolation matrix
     * m = [ -1.0   3.0  -3.0    1.0 ]
     *     [  3.0  -6.0   3.0    0.0 ]
     *     [ -3.0   3.0   0.0    0.0 ]
     *     [  1.0   0.0   0.0    0.0 ]
     * where T is the tension of the curve
     * the result is a value between p1 and p3, t=0 for p1, t=1 for p3
     * @param u value from 0 to 1
     * @param p0 control point 0
     * @param p1 control point 1
     * @param p2 control point 2
     * @param p3 control point 3
     * @param store a Vector2f to store the result
     * @return Bezier interpolation
     */
    public static Vector2f interpolateBezier(float u, Vector2f p0, Vector2f p1, Vector2f p2, Vector2f p3, Vector2f store) {
        if (store == null) {
            store = new Vector2f();
        }
        store.x = interpolateBezier(u, p0.x, p1.x, p2.x, p3.x);
        store.y = interpolateBezier(u, p0.y, p1.y, p2.y, p3.y);
        return store;
    }

    /**Interpolate a spline between at least 4 control points following the Bezier equation.
     * here is the interpolation matrix
     * m = [ -1.0   3.0  -3.0    1.0 ]
     *     [  3.0  -6.0   3.0    0.0 ]
     *     [ -3.0   3.0   0.0    0.0 ]
     *     [  1.0   0.0   0.0    0.0 ]
     * where T is the tension of the curve
     * the result is a value between p1 and p3, t=0 for p1, t=1 for p3
     * @param u value from 0 to 1
     * @param p0 control point 0
     * @param p1 control point 1
     * @param p2 control point 2
     * @param p3 control point 3
     * @return Bezier interpolation
     */
    public static Vector2f interpolateBezier(float u, Vector2f p0, Vector2f p1, Vector2f p2, Vector2f p3) {
        return interpolateBezier(u, p0, p1, p2, p3, null);
    }


    /**
     * Compute the length on a Bezier spline between control points 1 and 2.
     * @param p0 control point 0
     * @param p1 control point 1
     * @param p2 control point 2
     * @param p3 control point 3
     * @return the length of the segment
     */
    public static float getBezierP1toP2Length(Vector3f p0, Vector3f p1, Vector3f p2, Vector3f p3) {
        float delta = 0.02f, t = 0.0f, result = 0.0f;
        Vector3f v1 = p0.clone(), v2 = new Vector3f();
        while (t <= 1.0f) {
            interpolateBezier(t, p0, p1, p2, p3, v2);
            result += v1.subtractLocal(v2).length();
            v1.set(v2);
            t += delta;
        }
        return result;
    }


    /**
     * Compute the length on a Bezier spline between control points 1 and 2.
     * @param p0 control point 0
     * @param p1 control point 1
     * @param p2 control point 2
     * @param p3 control point 3
     * @return the length of the segment
     */
    public static float getBezierP1toP2Length(Vector2f p0, Vector2f p1, Vector2f p2, Vector2f p3) {
        float delta = 0.02f, t = 0.0f, result = 0.0f;
        Vector2f v1 = p0.clone(), v2 = new Vector2f();
        while (t <= 1.0f) {
            interpolateBezier(t, p0, p1, p2, p3, v2);
            result += v1.subtractLocal(v2).length();
            v1.set(v2);
            t += delta;
        }
        return result;
    }
    
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

    public static Vector3f hexColorToVector3f(int color) {
        return colorToVector3f(new Color(color));
    }

    public static String vector3fColorToHex(Vector3f color) {
        Color c = vector3fToColor(color);
        return String.format("%02x%02x%02x", c.getRed(), c.getGreen(), c.getBlue());
    }

    public static Color vector3fToColor(Vector3f color) {
        return new Color(color.x, color.y, color.z);
    }

    public static Vector3f colorToVector3f(Color color) {
        float[] comp = new float[4];
        color.getColorComponents(comp);
        return new Vector3f(comp[0], comp[1], comp[2]);
    }
}
