package com.n8lm.zener.general;

import com.artemis.Aspect;
import com.artemis.Entity;
import com.artemis.EntitySystem;
import com.artemis.utils.ArrayBag;
import com.artemis.utils.ImmutableBag;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * The purpose of this class is to allow systems to execute at varying intervals.
 * 
 * An example system would be an ExpirationSystem, that deletes entities after a certain
 * lifetime. Instead of running a system that decrements a timeLeft value for each
 * entity, you can simply use this system to execute in a future at a time of the shortest
 * lived entity, and then reset the system to run at a time in a future at a time of the
 * shortest lived entity, etc.
 * 
 * Another example system would be an AnimationSystem. You know when you have to animate
 * a certain entity, e.g. in 300 milliseconds. So you can set the system to run in 300 ms.
 * to perform the animation.
 * 
 * This will save CPU cycles in some scenarios.
 * 
 * Implementation notes:
 * In order to start the system you need to override the inserted(Entity e) method,
 * look up the delay time from that entity and offer it to the system by using the 
 * offerDelay(float delay) method.
 * Also, when processing the entities you must also call offerDelay(float delay)
 * for all valid entities.
 * 
 * @author Forrest Sun
 *
 */
public abstract class DelayedEntityProcessingSystem extends EntitySystem {

    static class DelayedEntry implements Comparable<DelayedEntry>{
        public long time;
        public Entity entity;

        DelayedEntry(long time, Entity entity) {
            this.time = time;
            this.entity = entity;
        }

        @Override
        public int compareTo(DelayedEntry e) {
            return (int) (time - e.time);
        }
    }

    private PriorityQueue<DelayedEntry> actives;

	public DelayedEntityProcessingSystem(Aspect aspect) {
		super(aspect);
        actives = new PriorityQueue<>();
	}

    @Override
	protected final void processEntities() {

        if (!actives.isEmpty()) {
            long current = System.currentTimeMillis();
            while (!actives.isEmpty() && actives.peek().time < current) {
                processExpired(actives.peek().entity);
                actives.poll();
            }
        }
    }
	
	@Override
	protected void inserted(Entity e) {
        actives.add(new DelayedEntry(System.currentTimeMillis() + getRemainingDelay(e), e));
	}

    @Override
    protected void removed(Entity e) {
        actives.remove(e);
    }

	
	/**
	 * Return the delay in millisecond until this entity should be processed.
	 * 
	 * @param e entity
	 * @return delay
	 */
	protected abstract long getRemainingDelay(Entity e);
	
	@Override
	protected final boolean checkProcessing() {
        return true;
	}

	protected abstract void processExpired(Entity e);

}
