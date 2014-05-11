package com.n8lm.zener.collision;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

/**
 * <code>CollisionResults</code> is a collection returned as a result of a 
 * collision detection operation done by {@link Collidable}.
 * 
 * @author Kirill Vainer
 */
public class CollisionResults implements Iterable<CollisionResult> {

    private final ArrayList<CollisionResult> results = new ArrayList<CollisionResult>();
    private boolean sorted = true;

    /**
     * Clears all collision results added to this list
     */
    public void clear(){
        results.clear();
    }

    /**
     * Iterator for iterating over the collision results.
     * 
     * @return the iterator
     */
    public Iterator<CollisionResult> iterator() {
        if (!sorted){
            Collections.sort(results);
            sorted = true;
        }

        return results.iterator();
    }

    public void addCollision(CollisionResult result){
        results.add(result);
        sorted = false;
    }

    public int size(){
        return results.size();
    }

    public CollisionResult getClosestCollision(){
        if (size() == 0)
            return null;

        if (!sorted){
            Collections.sort(results);
            sorted = true;
        }

        return results.get(0);
    }

    public CollisionResult getFarthestCollision(){
        if (size() == 0)
            return null;

        if (!sorted){
            Collections.sort(results);
            sorted = true;
        }

        return results.get(size()-1);
    }

    public CollisionResult getCollision(int index){
        if (!sorted){
            Collections.sort(results);
            sorted = true;
        }

        return results.get(index);
    }

    /**
     * Internal use only.
     * @param index
     * @return
     */
    public CollisionResult getCollisionDirect(int index){
        return results.get(index);
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("CollisionResults[");
        for (CollisionResult result : results){
            sb.append(result).append(", ");
        }
        if (results.size() > 0)
            sb.setLength(sb.length()-2);

        sb.append("]");
        return sb.toString();
    }

}