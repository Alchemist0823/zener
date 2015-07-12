package com.n8lm.zener.legacy.particle;

/**
 * This interface defines a Field in a particle system.
 * A Field will affect every particle for every game cycle in the system.
 * Generally, a ParticleField can be GravityField, ResistanceField
 * or NoiseField, etc.
 * <p>A Particle System can contain one or more ParticleField</p>
 *
 * Created on 2014/11/12.
 *
 * @author Forrest Sun
 */
public interface ParticleField {

    /**
     * Apply the field to a particle
     *
     * @param p the particle be affected
     * @param delta the time of this game cycle
     */
    public void apply(Particle p, float delta);
}
