package com.n8lm.zener.systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.systems.EntityProcessingSystem;
import com.n8lm.zener.components.ParticleSystemComponent;
import com.n8lm.zener.components.TransformComponent;
import com.n8lm.zener.particle.Particle;
import com.n8lm.zener.particle.ParticleController;

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
		
		int newparticles = Math.round(pc.getNewCount(ps.getTime()) * delta);
		
	    for(int i = 0; i < newparticles; i ++){
	    	if (count < maxSize)
	    		particles[count ++] = pc.setNewParticle(new Particle());
	    	else
	    		break;
	    }
	    
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
