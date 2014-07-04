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

import com.artemis.Entity;
import com.n8lm.zener.math.Vector3f;

/**
 * A <code>CollisionResult</code> represents a single collision instance
 * between two {@link Collidable}. A collision check can result in many
 * collision instances (places where collision has occured).
 *
 * @author Kirill Vainer
 * @author Alchemist
 */
public class CollisionResult implements Comparable<CollisionResult> {

    private Entity entity;
    private Vector3f contactPoint;
    private Vector3f contactNormal;
    private float distance;
    private int triangleIndex;

    public CollisionResult(Entity entity, Vector3f contactPoint, float distance, int triangleIndex) {
        this.entity = entity;
        this.contactPoint = contactPoint;
        this.distance = distance;
        this.triangleIndex = triangleIndex;
    }

    public CollisionResult(Vector3f contactPoint, float distance) {
        this.contactPoint = contactPoint;
        this.distance = distance;
    }

    public CollisionResult(){
    }

    public void setEntity(Entity entity){
        this.entity = entity;
    }

    public void setContactNormal(Vector3f norm){
        this.contactNormal = norm;
    }

    public void setContactPoint(Vector3f point){
        this.contactPoint = point;
    }

    public void setDistance(float dist){
        this.distance = dist;
    }

    public void setTriangleIndex(int index){
        this.triangleIndex = index;
    }

    /*
    public Triangle getTriangle(Triangle store){
        if (store == null)
            store = new Triangle();

        Mesh m = geometry.getMesh();
        m.getTriangle(triangleIndex, store);
        store.calculateCenter();
        store.calculateNormal();
        return store;
    }*/

    public int compareTo(CollisionResult other) {
        return Float.compare(distance, other.distance);
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof CollisionResult){
            return ((CollisionResult)obj).compareTo(this) == 0;
        }
        return super.equals(obj);
    }

    public Vector3f getContactPoint() {
        return contactPoint;
    }

    public Vector3f getContactNormal() {
        return contactNormal;
    }

    public float getDistance() {
        return distance;
    }

    public Entity getEntity() {
        return entity;
    }

    public int getTriangleIndex() {
        return triangleIndex;
    }

    public String toString() {
        return "CollisionResult[Entity=" + entity
                                + ", contactPoint=" + contactPoint
                                + ", contactNormal=" + contactNormal
                                + ", distance=" + distance
                                + ", triangleIndex=" + triangleIndex
                                + "]";
    }
}