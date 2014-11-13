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
     * Generate a new particle at specific time
     * @param time when the particle will be generated
     * @return the new particle
     */
    public Particle newParticle(float time);

    /**
     * Get the count of particles emitted per second at the specific time
     * @param time the specific time.
     * @return the count of new particles per second
     */
    int getEmitSpeed(float time);

    /**
     * Set the setting of a particle to new generated state
     * @param particle the particle will be set.
     * @param time when the particle will be set
     */
    public void setNewParticle(Particle particle, float time);


    /**
     * @return the atlas texture count
     */
    public int getAtlasCount();

    /**
     * Get the longest life of the particle used for texture
     * altas calculation
     * @return the life time in second
     */
    public float getFullLife();
}
