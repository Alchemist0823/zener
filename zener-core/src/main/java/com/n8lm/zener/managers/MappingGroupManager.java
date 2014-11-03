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

package com.n8lm.zener.managers;

import java.util.HashMap;
import java.util.Map;

import com.artemis.Manager;
import com.artemis.Entity;
import com.artemis.utils.Bag;
import com.artemis.utils.ImmutableBag;

/**
 * one Entity can only belong to a group
 * @author Alchemist
 *
 */
public abstract class MappingGroupManager<T> extends Manager {

	private Map<T, Bag<Entity>> entitiesByGroup;
    private Map<Entity, T> groupByEntity;

    public MappingGroupManager() {
        entitiesByGroup = new HashMap<T, Bag<Entity>>();
        groupByEntity = new HashMap<Entity, T>();
    }
    

    @Override
    protected void initialize() {
    }
    
    
    /**
     * Set the group of the entity.
     * 
     * @param group group to add the entity into.
     * @param e entity to add into the group.
     */
    public void set(Entity e, T group) {
    	
        remove(e);
    	
        Bag<Entity> entities = entitiesByGroup.get(group);
        if(entities == null) {
                entities = new Bag<Entity>();
                entitiesByGroup.put(group, entities);
        }
        entities.add(e);
        
        groupByEntity.put(e, group);
    }
    
    /**
     * Remove the entity from the group.
     * @param e
     * @param group
     */
    public void remove(Entity e) {
    	
		T group = groupByEntity.get(e);
	
        Bag<Entity> entities = entitiesByGroup.get(group);
        if(entities != null) {
                entities.remove(e);
        }
        
        groupByEntity.remove(e);
    }
    
    /**
     * Get all entities that belong to the provided group.
     * @param group name of the group.
     * @return read-only bag of entities belonging to the group.
     */
    public ImmutableBag<Entity> getEntities(T group) {
        Bag<Entity> entities = entitiesByGroup.get(group);
        if(entities == null) {
                entities = new Bag<Entity>();
                entitiesByGroup.put(group, entities);
        }
        return entities;
    }
    
    /**
     * @param e entity
     * @return the group the entity belongs to, null if none.
     */
    public T getGroup(Entity e) {
        return groupByEntity.get(e);
    }
    
    /**
     * Checks if the entity belongs to any group.
     * @param e the entity to check.
     * @return true if it is in any group, false if none.
     */
    public boolean isInAnyGroup(Entity e) {
        return getGroup(e) != null;
    }
    
    /**
     * Check if the entity is in the supplied group.
     * @param group the group to check in.
     * @param e the entity to check for.
     * @return true if the entity is in the supplied group, false if not.
     */
    public boolean isInGroup(Entity e, T group) {
        return (group.equals(groupByEntity.get(e)));
    }

    @Override
	public void deleted(Entity e) {
    	remove(e);
    }
    
}
