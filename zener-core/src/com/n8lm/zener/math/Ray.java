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

import com.n8lm.zener.collision.AABBBoundingBox;
import com.n8lm.zener.collision.Collidable;
import com.n8lm.zener.collision.CollisionResult;
import com.n8lm.zener.collision.CollisionResults;

public class Ray implements Cloneable, java.io.Serializable, Collidable{
	
	static final long serialVersionUID = 1;
	
	/** 
	 * The ray's begining point. 
	 */
	public Vector3f origin = new Vector3f();
	
	/** 
	 * The direction of the ray. 
	 */
	public Vector3f direction = new Vector3f(0, 0, 1);
	
	
	public float limit = Float.POSITIVE_INFINITY;
	
	/**
	 * Constructor instantiates a new <code>Ray</code> object. As default, the
	 * origin is (0,0,0) and the direction is (0,0,1).
	 *
	 */
	public Ray() {
	}
	
	/**
	 * Constructor instantiates a new <code>Ray</code> object. The origin and
	 * direction are given.
	 * @param origin the origin of the ray.
	 * @param direction the direction the ray travels in.
	 */
	public Ray(Vector3f origin, Vector3f direction) {
	    setOrigin(origin);
	    setDirection(direction);
	}
	
    /**
     * <code>intersects</code> does the actual intersection work.
     *
     * @param v0
     *            first point of the triangle.
     * @param v1
     *            second point of the triangle.
     * @param v2
     *            third point of the triangle.
     * @param store
     *            storage vector - if null, no intersection is calc'd
     * @param doPlanar
     *            true if we are calcing planar results.
     * @param quad
     * @return true if ray intersects triangle
     */
    public boolean intersects(Vector3f v0, Vector3f v1, Vector3f v2,
            Vector3f store, boolean doPlanar, boolean quad) {

        Vector3f tempVa = new Vector3f(),
                tempVb = new Vector3f(),
                tempVc = new Vector3f(),
                tempVd = new Vector3f();

        Vector3f diff = origin.subtract(v0, tempVa);
        Vector3f edge1 = v1.subtract(v0, tempVb);
        Vector3f edge2 = v2.subtract(v0, tempVc);
        Vector3f norm = edge1.cross(edge2, tempVd);

        float dirDotNorm = direction.dot(norm);
        float sign;
        if (dirDotNorm > MathUtil.FLT_EPSILON) {
            sign = 1;
        } else if (dirDotNorm < -MathUtil.FLT_EPSILON) {
            sign = -1f;
            dirDotNorm = -dirDotNorm;
        } else {
            // ray and triangle/quad are parallel
            return false;
        }

        float dirDotDiffxEdge2 = sign * direction.dot(diff.cross(edge2, edge2));
        if (dirDotDiffxEdge2 >= 0.0f) {
            float dirDotEdge1xDiff = sign
                    * direction.dot(edge1.crossLocal(diff));

            if (dirDotEdge1xDiff >= 0.0f) {
                if (!quad ? dirDotDiffxEdge2 + dirDotEdge1xDiff <= dirDotNorm : dirDotEdge1xDiff <= dirDotNorm) {
                    float diffDotNorm = -sign * diff.dot(norm);
                    if (diffDotNorm >= 0.0f) {
                        // this method always returns

                        // ray intersects triangle
                        // if storage vector is null, just return true,
                        if (store == null) {
                            return true;
                        }

                        // else fill in.
                        float inv = 1f / dirDotNorm;
                        float t = diffDotNorm * inv;
                        if (!doPlanar) {
                            store.set(origin).addLocal(direction.x * t,
                                    direction.y * t, direction.z * t);
                        } else {
                            // these weights can be used to determine
                            // interpolated values, such as texture coord.
                            // eg. texcoord s,t at intersection point:
                            // s = w0*s0 + w1*s1 + w2*s2;
                            // t = w0*t0 + w1*t1 + w2*t2;
                            float w1 = dirDotDiffxEdge2 * inv;
                            float w2 = dirDotEdge1xDiff * inv;
                            //float w0 = 1.0f - w1 - w2;
                            store.set(t, w1, w2);
                        }
                        return true;
                    }
                }
            }
        }
        return false;
    }
	
    public float intersects(Vector3f v0, Vector3f v1, Vector3f v2) {
        float edge1X = v1.x - v0.x;
        float edge1Y = v1.y - v0.y;
        float edge1Z = v1.z - v0.z;

        float edge2X = v2.x - v0.x;
        float edge2Y = v2.y - v0.y;
        float edge2Z = v2.z - v0.z;

        float normX = ((edge1Y * edge2Z) - (edge1Z * edge2Y));
        float normY = ((edge1Z * edge2X) - (edge1X * edge2Z));
        float normZ = ((edge1X * edge2Y) - (edge1Y * edge2X));

        float dirDotNorm = direction.x * normX + direction.y * normY + direction.z * normZ;

        float diffX = origin.x - v0.x;
        float diffY = origin.y - v0.y;
        float diffZ = origin.z - v0.z;

        float sign;
        if (dirDotNorm > MathUtil.FLT_EPSILON) {
            sign = 1;
        } else if (dirDotNorm < -MathUtil.FLT_EPSILON) {
            sign = -1f;
            dirDotNorm = -dirDotNorm;
        } else {
            // ray and triangle/quad are parallel
            return Float.POSITIVE_INFINITY;
        }

        float diffEdge2X = ((diffY * edge2Z) - (diffZ * edge2Y));
        float diffEdge2Y = ((diffZ * edge2X) - (diffX * edge2Z));
        float diffEdge2Z = ((diffX * edge2Y) - (diffY * edge2X));

        float dirDotDiffxEdge2 = sign * (direction.x * diffEdge2X
                + direction.y * diffEdge2Y
                + direction.z * diffEdge2Z);

        if (dirDotDiffxEdge2 >= 0.0f) {
            diffEdge2X = ((edge1Y * diffZ) - (edge1Z * diffY));
            diffEdge2Y = ((edge1Z * diffX) - (edge1X * diffZ));
            diffEdge2Z = ((edge1X * diffY) - (edge1Y * diffX));

            float dirDotEdge1xDiff = sign * (direction.x * diffEdge2X
                    + direction.y * diffEdge2Y
                    + direction.z * diffEdge2Z);

            if (dirDotEdge1xDiff >= 0.0f) {
                if (dirDotDiffxEdge2 + dirDotEdge1xDiff <= dirDotNorm) {
                    float diffDotNorm = -sign * (diffX * normX + diffY * normY + diffZ * normZ);
                    if (diffDotNorm >= 0.0f) {
                        // ray intersects triangle
                        // fill in.
                        float inv = 1f / dirDotNorm;
                        float t = diffDotNorm * inv;
                        return t;
                    }
                }
            }
        }

        return Float.POSITIVE_INFINITY;
    }

	@Override
	public int collideWith(Collidable other, CollisionResults results) {
		if (other instanceof AABBBoundingBox) {
			return other.collideWith(this, results);
		}
		return 0;
	}
	/**
    *
    * <code>getOrigin</code> retrieves the origin point of the ray.
    *
    * @return the origin of the ray.
    */
   public Vector3f getOrigin() {
       return origin;
   }

   /**
    *
    * <code>setOrigin</code> sets the origin of the ray.
    * @param origin the origin of the ray.
    */
   public void setOrigin(Vector3f origin) {
       this.origin.set(origin);
   }

   /**
    * <code>getLimit</code> returns the limit of the ray, aka the length.
    * If the limit is not infinity, then this ray is a line with length <code>
    * limit</code>.
    * 
    * @return the limit of the ray, aka the length.
    */
   public float getLimit() {
       return limit;
   }

   /**
    * <code>setLimit</code> sets the limit of the ray.
    * @param limit the limit of the ray.
    * @see Ray#getLimit() 
    */
   public void setLimit(float limit) {
       this.limit = limit;
   }

   /**
    *
    * <code>getDirection</code> retrieves the direction vector of the ray.
    * @return the direction of the ray.
    */
   public Vector3f getDirection() {
       return direction;
   }

   /**
    *
    * <code>setDirection</code> sets the direction vector of the ray.
    * @param direction the direction of the ray.
    */
   public void setDirection(Vector3f direction) {
       assert direction.isUnitVector();
       this.direction.set(direction);
   }

   /**
    * Copies information from a source ray into this ray.
    * 
    * @param source
    *            the ray to copy information from
    */
   public void set(Ray source) {
       origin.set(source.getOrigin());
       direction.set(source.getDirection());
   }

   public String toString() {
       return getClass().getSimpleName() + " [Origin: " + origin + ", Direction: " + direction + "]";
   }

   @Override
   public Ray clone() {
       try {
           Ray r = (Ray) super.clone();
           r.direction = direction.clone();
           r.origin = origin.clone();
           return r;
       } catch (CloneNotSupportedException e) {
           throw new AssertionError();
       }
   }
}
