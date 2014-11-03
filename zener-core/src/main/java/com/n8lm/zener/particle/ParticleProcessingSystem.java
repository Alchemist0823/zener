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

package com.n8lm.zener.particle;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.systems.EntityProcessingSystem;
import com.n8lm.zener.general.TransformComponent;

public class ParticleProcessingSystem extends EntityProcessingSystem {

	@Mapper ComponentMapper<ParticleSystemComponent> pm;
	@Mapper ComponentMapper<TransformComponent> tm;
	
	public ParticleProcessingSystem() {
		super(Aspect.getAspectForAll(ParticleSystemComponent.class));
	}
	
	@Override
	protected void process(Entity e) {

		ParticleSystemComponent ps = pm.get(e);
		ParticleController pc =  ps.getController();
		
		if (pc.isEnd(ps.getTime()))
			return;
		
		Particle[] particles = ps.getParticles();
	    float delta = world.getDelta() / 1000f;
		int count = ps.getCount();
		int maxSize = pc.getMaxCount();

        double time = ps.getTime();
        int numSecond = (int) Math.round(pc.getNewCount(ps.getTime()) * (time - (int)(time)));

		int newparticles = numSecond - ps.getCountPerSecond();//Math.round(pc.getNewCount(ps.getTime()) * delta);

        int lastCount = count;

	    for(int i = 0; i < newparticles; i ++){
	    	if (count < maxSize)
	    		particles[count ++] = pc.setNewParticle(new Particle());
	    	else
	    		break;
	    }

        ps.setCountPerSecond(ps.getCountPerSecond() + count - lastCount);
	    
		// Simulate all particles
	    for(int i = 0; i < count; i ++){
	        Particle p = particles[i]; // shortcut
	     
            // Decrease life
            p.life -= delta;
            
            if (p.life > 0.0f){
     
                // Simulate simple physics : gravity only, no collisions
            	pc.process(p, delta);
            } else {
            	Particle tmp = particles[count - 1];
            	particles[count - 1] = particles[i];
            	particles[i] = tmp;
            	
            	count --;
            }
	        //System.out.println(p.pos);
	    }
	    ps.passTime(delta);
	    ps.setCount(count);
	}
}
