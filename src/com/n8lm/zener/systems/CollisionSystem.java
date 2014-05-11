package com.n8lm.zener.systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.systems.EntityProcessingSystem;
import com.n8lm.zener.collision.*;
import com.n8lm.zener.components.CollidableComponent;
import com.n8lm.zener.components.TransformComponent;
import com.n8lm.zener.math.Ray;
import com.n8lm.zener.math.Vector3f;

public class CollisionSystem extends EntityProcessingSystem {

	@Mapper ComponentMapper<CollidableComponent> cm;
	@Mapper ComponentMapper<TransformComponent> tm;
	
	private Ray ray;
	private CollisionResults crs;
	
	public CollisionSystem() {
		super(Aspect.getAspectForAll(CollidableComponent.class, TransformComponent.class));
		crs = new CollisionResults();
		this.setPassive(true);
	}

	@Override
	protected void process(Entity e) {
		if (cm.get(e).isActive()) {
			Collidable collidable = cm.get(e).getCollidable();
			if (collidable instanceof AABBBoundingBox)
			{
				 AABBBoundingBox box = ((AABBBoundingBox) collidable).transform(tm.get(e).getWorldTransform(), null);
				 //System.out.println(box);
				 collideWithRay(box, ray, e, crs);
			}
		}
	}
	
    /**
     * <code>clip</code> determines if a line segment intersects the current
     * test plane.
     * 
     * @param denom
     *            the denominator of the line segment.
     * @param numer
     *            the numerator of the line segment.
     * @param t
     *            test values of the plane.
     * @return true if the line segment intersects the plane, false otherwise.
     */
    private boolean clip(float denom, float numer, float[] t) {
        // Return value is 'true' if line segment intersects the current test
        // plane. Otherwise 'false' is returned in which case the line segment
        // is entirely clipped.
        if (denom > 0.0f) {
            // This is the old if statement...
            // if (numer > denom * t[1]) {
            //
            // The problem is that what is actually stored is
            // numer/denom.  In non-floating point, this math should
            // work out the same but in floating point there can
            // be subtle math errors.  The multiply will exaggerate
            // errors that may have been introduced when the value
            // was originally divided.  
            //
            // This is especially true when the bounding box has zero
            // extents in some plane because the error rate is critical.
            // comparing a to b * c is not the same as comparing a/b to c
            // in this case.  In fact, I tried converting this method to 
            // double and the and the error was in the last decimal place. 
            //
            // So, instead, we now compare the divided version to the divided
            // version.  We lose some slight performance here as divide
            // will be more expensive than the divide.  Some microbenchmarks
            // show divide to be 3x slower than multiple on Java 1.6.
            // BUT... we also saved a multiply in the non-clipped case because 
            // we can reuse the divided version in both if checks.
            // I think it's better to be right in this case.
            //
            // Bug that I'm fixing: rays going right through quads at certain
            // angles and distances because they fail the bounding box test.
            // Many Bothans died bring you this fix. 
            //    -pspeed            
            float newT = numer / denom;
            if (newT > t[1]) {
                return false;
            }
            if (newT > t[0]) {
                t[0] = newT;
            }
            return true;
        } else if (denom < 0.0f) {
            // Old if statement... see above
            // if (numer > denom * t[0]) {
            //
            // Note though that denom is always negative in this block.
            // When we move it over to the other side we have to flip
            // the comparison.  Algebra for the win.
            float newT = numer / denom;
            if (newT < t[0]) {            
                return false;
            }
            if (newT < t[1]) {
                t[1] = newT;
            }
            return true;
        } else {
            return numer <= 0.0;
        }
    }

    /**
     */
    private int collideWithRay(AABBBoundingBox box, Ray ray, Entity e, CollisionResults results) {

    	Vector3f vect1 = new Vector3f();
    	Vector3f vect2 = new Vector3f();
    	
        Vector3f diff = vect1.set(ray.origin).subtractLocal(box.getCenter());
        Vector3f direction = vect2.set(ray.direction);

        float[] t = {0f, Float.POSITIVE_INFINITY};

        float saveT0 = t[0], saveT1 = t[1];
        boolean notEntirelyClipped = clip(+direction.x, -diff.x - box.getXExtent(), t)
                && clip(-direction.x, +diff.x - box.getXExtent(), t)
                && clip(+direction.y, -diff.y - box.getYExtent(), t)
                && clip(-direction.y, +diff.y - box.getYExtent(), t)
                && clip(+direction.z, -diff.z - box.getZExtent(), t)
                && clip(-direction.z, +diff.z - box.getZExtent(), t);

        if (notEntirelyClipped && (t[0] != saveT0 || t[1] != saveT1)) {
            if (t[1] > t[0]) {
                float[] distances = t;
                Vector3f[] points = new Vector3f[]{
                    new Vector3f(ray.direction).multLocal(distances[0]).addLocal(ray.origin),
                    new Vector3f(ray.direction).multLocal(distances[1]).addLocal(ray.origin)
                };

                CollisionResult result = new CollisionResult(e, points[0], distances[0], 0);
                results.addCollision(result);
                result = new CollisionResult(points[1], distances[1]);
                results.addCollision(result);
                return 2;
            }

            Vector3f point = new Vector3f(ray.direction).multLocal(t[0]).addLocal(ray.origin);
            CollisionResult result = new CollisionResult(e, point, t[0], 0);
            results.addCollision(result);
            return 1;
        }
        return 0;
    }
    
	public void setRay(Ray ray) {
		this.ray = ray;
	}
	
	public CollisionResults getCollisionResults() {
		return crs;
	}
	

	@Override
	protected boolean checkProcessing() {
		if (ray != null)
			return true;
		else
			return false;
	}
	
	@Override
	protected void begin() {
		super.begin();
		crs.clear();
		//System.out.print("asdas");
	}

	@Override
	protected void end() {
		super.end();
	}
}
