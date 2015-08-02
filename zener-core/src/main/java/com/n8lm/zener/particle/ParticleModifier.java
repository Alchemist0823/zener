package com.n8lm.zener.particle;

/**
 * This interface defines a Modifer in a particle system.
 * A Modifer will affect every particle for every game cycle in the system.
 * Generally, a ParticleModifier can be GravityModifier, ResistanceModifer
 * or NoiseModifer, etc.
 * <p>A Particle System can contain one or more ParticleModifier</p>
 *
 * Created on 2014/11/12.
 *
 * @author Forrest Sun
 */
public interface ParticleModifier {

    /**
     * Apply the field to a particle
     *
     * @param p the particle be affected
     * @param delta the time of this game cycle
     */
    void apply(Particle p, float delta);

    /**
     * A new frame start callback
     */
    void frameStarted();
}
