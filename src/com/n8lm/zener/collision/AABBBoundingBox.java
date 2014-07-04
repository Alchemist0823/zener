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

package com.n8lm.zener.collision;

import java.nio.FloatBuffer;

import com.n8lm.zener.general.TransformComponent;
import com.n8lm.zener.math.*;

public class AABBBoundingBox implements Collidable {

	/**
	 * the center of the box
	 */
	Vector3f center;
	/**
     * the X-extent of the box (>=0, may be +Infinity)
     */
    float xExtent;
    /**
     * the Y-extent of the box (>=0, may be +Infinity)
     */
    float yExtent;
    /**
     * the Z-extent of the box (>=0, may be +Infinity)
     */
    float zExtent;
    
    /**
     * Instantiate a <code>AABBBoundingBox</code> without initializing it.
     */
    public AABBBoundingBox() {
    	this.center = new Vector3f();
    }

    /**
     * Instantiate a <code>BoundingBox</code> with given center and extents.
     *
     * @param c the coordinates of the center of the box (not null, not altered)
     * @param x the X-extent of the box (>=0, may be +Infinity)
     * @param y the Y-extent of the box (>=0, may be +Infinity)
     * @param z the Z-extent of the box (>=0, may be +Infinity)
     */
    public AABBBoundingBox(Vector3f c, float x, float y, float z) {
    	this();
        this.center.set(c);
        this.xExtent = x;
        this.yExtent = y;
        this.zExtent = z;
    }

    /**
     * Instantiate a <code>BoundingBox</code> equivalent to an existing box.
     *
     * @param source the existing box (not null, not altered)
     */
    public AABBBoundingBox(AABBBoundingBox source) {
        this.center.set(source.center);
        this.xExtent = source.xExtent;
        this.yExtent = source.yExtent;
        this.zExtent = source.zExtent;
    }

    public AABBBoundingBox(Vector3f min, Vector3f max) {
        setMinMax(min, max);
    }

    /*
    public Type getType() {
        return Type.AABB;
    }*/

    /**
     * <code>computeFromPoints</code> creates a new Bounding Box from a given
     * set of points. It uses the <code>containAABB</code> method as default.
     * 
     * @param points
     *            the points to contain.
     */
    public void computeFromPoints(FloatBuffer points) {
        containAABB(points);
    }

    /**
     * <code>computeFromTris</code> creates a new Bounding Box from a given
     * set of triangles. It is used in OBBTree calculations.
     * 
     * @param tris
     * @param start
     * @param end
     */
    /*
    public void computeFromTris(Triangle[] tris, int start, int end) {
        if (end - start <= 0) {
            return;
        }

        Vector3f vect1 = new Vector3f();
        Vector3f vect2 = new Vector3f();

        Vector3f min = vect1.set(new Vector3f(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY));
        Vector3f max = vect2.set(new Vector3f(Float.NEGATIVE_INFINITY, Float.NEGATIVE_INFINITY, Float.NEGATIVE_INFINITY));

        Vector3f point;
        for (int i = start; i < end; i++) {
            point = tris[i].get(0);
            checkMinMax(min, max, point);
            point = tris[i].get(1);
            checkMinMax(min, max, point);
            point = tris[i].get(2);
            checkMinMax(min, max, point);
        }

        center.set(min.addLocal(max));
        center.multLocal(0.5f);

        xExtent = max.x - center.x;
        yExtent = max.y - center.y;
        zExtent = max.z - center.z;

    }*/

    /*
    public void computeFromTris(int[] indices, DisplayObject ds, int start, int end) {
        if (end - start <= 0) {
            return;
        }
        
        Vector3f vect1 = new Vector3f();
        Vector3f vect2 = new Vector3f();
        Triangle triangle = new Triangle();

        Vector3f min = vect1.set(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY);
        Vector3f max = vect2.set(Float.NEGATIVE_INFINITY, Float.NEGATIVE_INFINITY, Float.NEGATIVE_INFINITY);
        Vector3f point;

        for (int i = start; i < end; i++) {
            ds.getTriangle(indices[i], triangle);
            point = triangle.get(0);
            checkMinMax(min, max, point);
            point = triangle.get(1);
            checkMinMax(min, max, point);
            point = triangle.get(2);
            checkMinMax(min, max, point);
        }

        center.set(min.addLocal(max));
        center.multLocal(0.5f);

        xExtent = max.x - center.x;
        yExtent = max.y - center.y;
        zExtent = max.z - center.z;
    }*/

    public static void checkMinMax(Vector3f min, Vector3f max, Vector3f point) {
        if (point.x < min.x) {
            min.x = point.x;
        }
        if (point.x > max.x) {
            max.x = point.x;
        }
        if (point.y < min.y) {
            min.y = point.y;
        }
        if (point.y > max.y) {
            max.y = point.y;
        }
        if (point.z < min.z) {
            min.z = point.z;
        }
        if (point.z > max.z) {
            max.z = point.z;
        }
    }

    /**
     * <code>containAABB</code> creates a minimum-volume axis-aligned bounding
     * box of the points, then selects the smallest enclosing sphere of the box
     * with the sphere centered at the boxes center.
     * 
     * @param points
     *            the list of points.
     */
    public void containAABB(FloatBuffer points) {
        if (points == null) {
            return;
        }

        points.rewind();
        if (points.remaining() <= 2) // we need at least a 3 float vector
        {
            return;
        }

        Vector3f vect1 = new Vector3f();
        
        float[] tmpArray = new float[100];

        float minX = Float.POSITIVE_INFINITY, minY = Float.POSITIVE_INFINITY, minZ = Float.POSITIVE_INFINITY;
        float maxX = Float.NEGATIVE_INFINITY, maxY = Float.NEGATIVE_INFINITY, maxZ = Float.NEGATIVE_INFINITY;
        
        int iterations = (int) Math.ceil(points.limit() / ((float) tmpArray.length));
        for (int i = iterations - 1; i >= 0; i--) {
            int bufLength = Math.min(tmpArray.length, points.remaining());
            points.get(tmpArray, 0, bufLength);

            for (int j = 0; j < bufLength; j += 3) {
                vect1.x = tmpArray[j];
                vect1.y = tmpArray[j+1];
                vect1.z = tmpArray[j+2];
                
                if (vect1.x < minX) {
                    minX = vect1.x;
                }
                if (vect1.x > maxX) {
                    maxX = vect1.x;
                }

                if (vect1.y < minY) {
                    minY = vect1.y;
                }
                if (vect1.y > maxY) {
                    maxY = vect1.y;
                }

                if (vect1.z < minZ) {
                    minZ = vect1.z;
                }
                if (vect1.z > maxZ) {
                    maxZ = vect1.z;
                }
            }
        }

        center.set(minX + maxX, minY + maxY, minZ + maxZ);
        center.multLocal(0.5f);

        xExtent = maxX - center.x;
        yExtent = maxY - center.y;
        zExtent = maxZ - center.z;
    }

    /**
     * <code>transform</code> modifies the center of the box to reflect the
     * change made via a rotation, translation and scale.
     * 
     * @param trans 
     *            the transform to apply
     * @param store
     *            box to store result in
     */
    public AABBBoundingBox transform(Transform trans, AABBBoundingBox store) {

        AABBBoundingBox box;
        if (store == null) {
            box = new AABBBoundingBox();
        } else {
            box = (AABBBoundingBox) store;
        }

        center.mult(trans.getScale(), box.center);
        trans.getRotation().mult(box.center, box.center);
        box.center.addLocal(trans.getTranslation());

        Matrix3f transMatrix = new Matrix3f();
        transMatrix.set(trans.getRotation());
        // Make the rotation matrix all positive to get the maximum x/y/z extent
        transMatrix.absoluteLocal();

        Vector3f vect1 = new Vector3f();
        Vector3f vect2 = new Vector3f();
        
        Vector3f scale = trans.getScale();
        vect1.set(xExtent * scale.x, yExtent * scale.y, zExtent * scale.z);
        transMatrix.mult(vect1, vect2);
        // Assign the biggest rotations after scales.
        box.xExtent = Math.abs(vect2.getX());
        box.yExtent = Math.abs(vect2.getY());
        box.zExtent = Math.abs(vect2.getZ());
        return box;
    }

    public AABBBoundingBox transform(Matrix4f trans, AABBBoundingBox store) {
    	AABBBoundingBox box;
        if (store == null) {
            box = new AABBBoundingBox();
        } else {
            box = (AABBBoundingBox) store;
        }

        float w = trans.multProj(center, box.center);
        box.center.divideLocal(w);

        Matrix3f transMatrix = new Matrix3f();
        trans.toRotationMatrix(transMatrix);

        Vector3f vect1 = new Vector3f();
        // Make the rotation matrix all positive to get the maximum x/y/z extent
        transMatrix.absoluteLocal();

        vect1.set(xExtent, yExtent, zExtent);
        transMatrix.mult(vect1, vect1);

        // Assign the biggest rotations after scales.
        box.xExtent = Math.abs(vect1.getX());
        box.yExtent = Math.abs(vect1.getY());
        box.zExtent = Math.abs(vect1.getZ());

        return box;
    }

    /**
     * <code>whichSide</code> takes a plane (typically provided by a view
     * frustum) to determine which side this bound is on.
     * 
     * @param plane
     *            the plane to check against.
     */
    /*public Plane.Side whichSide(Plane plane) {
        float radius = FastMath.abs(xExtent * plane.getNormal().getX())
                + FastMath.abs(yExtent * plane.getNormal().getY())
                + FastMath.abs(zExtent * plane.getNormal().getZ());

        float distance = plane.pseudoDistance(center);

        //changed to < and > to prevent floating point precision problems
        if (distance < -radius) {
            return Plane.Side.Negative;
        } else if (distance > radius) {
            return Plane.Side.Positive;
        } else {
            return Plane.Side.None;
        }
    }*/

    /**
     * <code>mergeLocal</code> combines this bounding box locally with a second
     * bounding box described by its center and extents.
     *
     * @param c the center of the second box (not null, not altered)
     * @param x the X-extent of the second box
     * @param y the Y-extent of the second box
     * @param z the Z-extent of the second box
     * @return the resulting merged box.
     */
    public AABBBoundingBox mergeLocal(Vector3f c, float x, float y, float z) {
        if (xExtent == Float.POSITIVE_INFINITY
                || x == Float.POSITIVE_INFINITY) {
            center.x = 0;
            xExtent = Float.POSITIVE_INFINITY;
        } else {
            float low = center.x - xExtent;
            if (low > c.x - x) {
                low = c.x - x;
            }
            float high = center.x + xExtent;
            if (high < c.x + x) {
                high = c.x + x;
            }
            center.x = (low + high) / 2;
            xExtent = high - center.x;
        }

        if (yExtent == Float.POSITIVE_INFINITY
                || y == Float.POSITIVE_INFINITY) {
            center.y = 0;
            yExtent = Float.POSITIVE_INFINITY;
        } else {
            float low = center.y - yExtent;
            if (low > c.y - y) {
                low = c.y - y;
            }
            float high = center.y + yExtent;
            if (high < c.y + y) {
                high = c.y + y;
            }
            center.y = (low + high) / 2;
            yExtent = high - center.y;
        }

        if (zExtent == Float.POSITIVE_INFINITY
                || z == Float.POSITIVE_INFINITY) {
            center.z = 0;
            zExtent = Float.POSITIVE_INFINITY;
        } else {
            float low = center.z - zExtent;
            if (low > c.z - z) {
                low = c.z - z;
            }
            float high = center.z + zExtent;
            if (high < c.z + z) {
                high = c.z + z;
            }
            center.z = (low + high) / 2;
            zExtent = high - center.z;
        }

        return this;
    }

    /**
     * <code>clone</code> creates a new BoundingBox object containing the same
     * data as this one.
     * 
     * @param store
     *            where to store the cloned information. if null or wrong class,
     *            a new store is created.
     * @return the new BoundingBox
     */
    public AABBBoundingBox clone(AABBBoundingBox store) {
        if (store != null ) {
        	AABBBoundingBox rVal = (AABBBoundingBox) store;
            rVal.center.set(center);
            rVal.xExtent = xExtent;
            rVal.yExtent = yExtent;
            rVal.zExtent = zExtent;
            //rVal.checkPlane = checkPlane;
            return rVal;
        }

        AABBBoundingBox rVal = new AABBBoundingBox(center.clone(),
                xExtent, yExtent, zExtent);
        return rVal;
    }

    /**
     * <code>toString</code> returns the string representation of this object.
     * The form is: "[Center: <Vector> xExtent: X.XX yExtent: Y.YY zExtent:
     * Z.ZZ]".
     *
     * @return the string representation of this.
     */
    @Override
    public String toString() {
        return getClass().getSimpleName() + " [Center: " + center + "  xExtent: "
                + xExtent + "  yExtent: " + yExtent + "  zExtent: " + zExtent
                + "]";
    }
    /**
     * determines if this bounding box intersects a given bounding sphere.
     */
    /*public boolean intersectsSphere(BoundingSphere bs) {
        assert Vector3f.isValidVector(center) && Vector3f.isValidVector(bs.center);

        if (FastMath.abs(center.x - bs.center.x) < bs.getRadius()
                + xExtent
                && FastMath.abs(center.y - bs.center.y) < bs.getRadius()
                + yExtent
                && FastMath.abs(center.z - bs.center.z) < bs.getRadius()
                + zExtent) {
            return true;
        }

        return false;
    }*/

    /**
     * determines if this bounding box intersects a given bounding box. If the
     * two boxes intersect in any way, true is returned. Otherwise, false is
     * returned.
     * 
     */
    public boolean intersectsBoundingBox(AABBBoundingBox bb) {
        assert Vector3f.isValidVector(center) && Vector3f.isValidVector(bb.center);

        if (center.x + xExtent < bb.center.x - bb.xExtent
                || center.x - xExtent > bb.center.x + bb.xExtent) {
            return false;
        } else if (center.y + yExtent < bb.center.y - bb.yExtent
                || center.y - yExtent > bb.center.y + bb.yExtent) {
            return false;
        } else if (center.z + zExtent < bb.center.z - bb.zExtent
                || center.z - zExtent > bb.center.z + bb.zExtent) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * determines if this bounding box intersects with a given oriented bounding
     * box.
     * 
     * @see com.jme.bounding.BoundingVolume#intersectsOrientedBoundingBox(com.jme.bounding.OrientedBoundingBox)
     */
//    public boolean intersectsOrientedBoundingBox(OrientedBoundingBox obb) {
//        return obb.intersectsBoundingBox(this);
//    }
    /**
     * determines if this bounding box intersects with a given ray object. If an
     * intersection has occurred, true is returned, otherwise false is returned.
     * 
     */
    public boolean intersects(Ray ray) {
        assert Vector3f.isValidVector(center);

        float rhs;

        Vector3f vect1 = new Vector3f();
        Vector3f vect2 = new Vector3f();
        
        
        Vector3f diff = ray.origin.subtract(getCenter(vect2), vect1);

        final float[] fWdU = new float[3];
        final float[] fAWdU = new float[3];
        final float[] fDdU = new float[3];
        final float[] fADdU = new float[3];
        final float[] fAWxDdU = new float[3];

        fWdU[0] = ray.getDirection().dot(Vector3f.UNIT_X);
        fAWdU[0] = Math.abs(fWdU[0]);
        fDdU[0] = diff.dot(Vector3f.UNIT_X);
        fADdU[0] = Math.abs(fDdU[0]);
        if (fADdU[0] > xExtent && fDdU[0] * fWdU[0] >= 0.0) {
            return false;
        }

        fWdU[1] = ray.getDirection().dot(Vector3f.UNIT_Y);
        fAWdU[1] = Math.abs(fWdU[1]);
        fDdU[1] = diff.dot(Vector3f.UNIT_Y);
        fADdU[1] = Math.abs(fDdU[1]);
        if (fADdU[1] > yExtent && fDdU[1] * fWdU[1] >= 0.0) {
            return false;
        }

        fWdU[2] = ray.getDirection().dot(Vector3f.UNIT_Z);
        fAWdU[2] = Math.abs(fWdU[2]);
        fDdU[2] = diff.dot(Vector3f.UNIT_Z);
        fADdU[2] = Math.abs(fDdU[2]);
        if (fADdU[2] > zExtent && fDdU[2] * fWdU[2] >= 0.0) {
            return false;
        }

        Vector3f wCrossD = ray.getDirection().cross(diff, vect2);

        fAWxDdU[0] = Math.abs(wCrossD.dot(Vector3f.UNIT_X));
        rhs = yExtent * fAWdU[2] + zExtent * fAWdU[1];
        if (fAWxDdU[0] > rhs) {
            return false;
        }
        
        fAWxDdU[1] = Math.abs(wCrossD.dot(Vector3f.UNIT_Y));
        rhs = xExtent * fAWdU[2] + zExtent * fAWdU[0];
        if (fAWxDdU[1] > rhs) {
            return false;
        }

        fAWxDdU[2] = Math.abs(wCrossD.dot(Vector3f.UNIT_Z));
        rhs = xExtent * fAWdU[1] + yExtent * fAWdU[0];
        if (fAWxDdU[2] > rhs) {
            return false;
        }

        return true;
    }

    @Override
    public int collideWith(Collidable other, CollisionResults result) {
        /*if (other instanceof Ray) {
            Ray ray = (Ray) other;
            return collideWithRay(ray, result);
        }*/
		return 0;
    }

    /**
     * C code ported from <a href="http://www.cs.lth.se/home/Tomas_Akenine_Moller/code/tribox3.txt">
     * http://www.cs.lth.se/home/Tomas_Akenine_Moller/code/tribox3.txt</a>
     *
     * @param v1 The first point in the triangle
     * @param v2 The second point in the triangle
     * @param v3 The third point in the triangle
     * @return True if the bounding box intersects the triangle, false
     * otherwise.
     */
    /*public boolean intersects(Vector3f v1, Vector3f v2, Vector3f v3) {
        return Intersection.intersect(this, v1, v2, v3);
    }*/

    public boolean contains(Vector3f point) {
        return Math.abs(center.x - point.x) < xExtent
                && Math.abs(center.y - point.y) < yExtent
                && Math.abs(center.z - point.z) < zExtent;
    }

    public boolean intersects(Vector3f point) {
        return Math.abs(center.x - point.x) <= xExtent
                && Math.abs(center.y - point.y) <= yExtent
                && Math.abs(center.z - point.z) <= zExtent;
    }

    public float distanceToEdge(Vector3f point) {
        // compute coordinates of point in box coordinate system
        Vector3f closest = new Vector3f();
        
        point.subtract(center,closest);

        // project test point onto box
        float sqrDistance = 0.0f;
        float delta;

        if (closest.x < -xExtent) {
            delta = closest.x + xExtent;
            sqrDistance += delta * delta;
            closest.x = -xExtent;
        } else if (closest.x > xExtent) {
            delta = closest.x - xExtent;
            sqrDistance += delta * delta;
            closest.x = xExtent;
        }

        if (closest.y < -yExtent) {
            delta = closest.y + yExtent;
            sqrDistance += delta * delta;
            closest.y = -yExtent;
        } else if (closest.y > yExtent) {
            delta = closest.y - yExtent;
            sqrDistance += delta * delta;
            closest.y = yExtent;
        }

        if (closest.z < -zExtent) {
            delta = closest.z + zExtent;
            sqrDistance += delta * delta;
            closest.z = -zExtent;
        } else if (closest.z > zExtent) {
            delta = closest.z - zExtent;
            sqrDistance += delta * delta;
            closest.z = zExtent;
        }
        
        return (float) Math.sqrt(sqrDistance);
    }


    public final Vector3f getCenter() {
        return center;
    }

    public final Vector3f getCenter(Vector3f store) {
        store.set(center);
        return store;
    }

    public final void setCenter(Vector3f newCenter) {
        center.set(newCenter);
    }
    /**
     * Query extent.
     * 
     * @param store
     *            where extent gets stored - null to return a new vector
     * @return store / new vector
     */
    public Vector3f getExtent(Vector3f store) {
        if (store == null) {
            store = new Vector3f();
        }
        store.set(xExtent, yExtent, zExtent);
        return store;
    }

    public float getXExtent() {
        return xExtent;
    }

    public float getYExtent() {
        return yExtent;
    }

    public float getZExtent() {
        return zExtent;
    }

    public void setXExtent(float xExtent) {
        if (xExtent < 0) {
            throw new IllegalArgumentException();
        }

        this.xExtent = xExtent;
    }

    public void setYExtent(float yExtent) {
        if (yExtent < 0) {
            throw new IllegalArgumentException();
        }

        this.yExtent = yExtent;
    }

    public void setZExtent(float zExtent) {
        if (zExtent < 0) {
            throw new IllegalArgumentException();
        }

        this.zExtent = zExtent;
    }

    public Vector3f getMin(Vector3f store) {
        if (store == null) {
            store = new Vector3f();
        }
        store.set(center).subtractLocal(xExtent, yExtent, zExtent);
        return store;
    }

    public Vector3f getMax(Vector3f store) {
        if (store == null) {
            store = new Vector3f();
        }
        store.set(center).addLocal(xExtent, yExtent, zExtent);
        return store;
    }

    public void setMinMax(Vector3f min, Vector3f max) {
        this.center.set(max).addLocal(min).multLocal(0.5f);
        xExtent = Math.abs(max.x - center.x);
        yExtent = Math.abs(max.y - center.y);
        zExtent = Math.abs(max.z - center.z);
    }

    public float getVolume() {
        return (8 * xExtent * yExtent * zExtent);
    }
}
