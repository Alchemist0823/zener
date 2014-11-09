package com.n8lm.zener.particle;

/**
 * Created on 2014/11/8.
 *
 * @author Alchemist
 */
public interface ParticleEmitter {

    public Particle newParticle(float time);
    public void setNewParticle(Particle particle, float time);
}
