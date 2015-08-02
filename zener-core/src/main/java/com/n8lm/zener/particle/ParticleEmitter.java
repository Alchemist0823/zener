package com.n8lm.zener.particle;

/**
 * This interface defines the Emitter of Particles in
 * a particle system.
 * Created on 2014/11/8.
 *
 * @author Forrest Sun
 */
public interface ParticleEmitter {
    /**
     * Get the count of particles emitted per second at the specific time
     * @param time the specific time.
     * @param delta the duration
     * @return the count of new particles per second
     */
    int getEmitNumber(float time, float delta);

    /**
     * Set the setting of a particle to new generated state
     * @param particle the particle will be set.
     * @param time when the particle will be set
     */
    Particle setNewParticle(Particle particle, float time);
}
