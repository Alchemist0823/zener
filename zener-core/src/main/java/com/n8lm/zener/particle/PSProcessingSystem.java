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
import com.artemis.utils.Bag;
import com.n8lm.zener.general.TransformComponent;
import com.n8lm.zener.utils.TempVars;

/**
 * PSProcessingSystem is a System processing all the ParticleSystemComponent
 * in a World. It simulates the changes of all particles in every
 * ParticleSystemComponent.
 *
 * @see ParticleSystemComponent
 *
 * @author Forrest Sun
 */
public class PSProcessingSystem extends EntityProcessingSystem {

	@Mapper ComponentMapper<ParticleSystemComponent> pm;
	@Mapper ComponentMapper<TransformComponent> tm;
	
	public PSProcessingSystem() {
		super(Aspect.getAspectForAll(ParticleSystemComponent.class));
	}
	
	@Override
	protected void process(Entity e) {

        ParticleSystemComponent ps = pm.get(e);

        ParticleEmitter emitter =  ps.getEmitter();
        Bag<ParticleModifier> modifers = ps.getFields();

		Particle[] particles = ps.getParticles();
	    float delta = world.getDelta() / 1000f;
		int count = ps.getCount();
		int maxSize = ps.getMaxCount();

        float time = ps.getTime();

        // emit new particles
        if (ps.getDuration() == 0 || ps.getTime() < ps.getDuration()) {
            int numSecond = Math.round(emitter.getEmitNumber(time, delta) * (time - (int) (time)));
            int newCount = numSecond - ps.getCountPerSecond();//Math.round(pc.getEmitNumber(ps.getTime()) * delta);
            int lastCount = count;

            for (int i = 0; i < newCount; i++) {
                if (count < maxSize) {
                    if (particles[count] == null)
                        particles[count++] = emitter.setNewParticle(new Particle(), time);
                    else
                        emitter.setNewParticle(particles[count++], time);
                } else
                    break;
            }

            ps.setCountPerSecond(ps.getCountPerSecond() + count - lastCount);
        }

        // apply modifier
        for (int j = 0; j < modifers.size(); j++) {
            ParticleModifier particleModifier = modifers.get(j);
            particleModifier.frameStarted();
            for (int i = 0; i < count; i++) {
                particleModifier.apply(particles[i], delta);
            }
        }

        TempVars tempVars = TempVars.get();
        for (int i = 0; i < count; i++) {
            // Simulate all particles
            Particle p = particles[i]; // shortcut
            // Decrease life
            p.life -= delta;
            if (p.life > 0.0f) {
                p.position.addLocal(p.velocity.mult(delta, tempVars.vect1));
            } else {
                Particle tmp = particles[count - 1];
                particles[count - 1] = particles[i];
                particles[i] = tmp;
                count--;
            }
        }

        tempVars.release();

        //System.out.println(count);
	    ps.passTime(delta);
	    ps.setCount(count);
	}
}
