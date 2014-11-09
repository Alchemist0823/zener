package com.n8lm.zener.particle;

/**
 * Created on 2014/11/8.
 *
 * @author Alchemist
 */
public class CustomParticleController implements ParticleController {

    @Override
    public int getNewCount(double time) {
        return 0;
    }

    @Override
    public int getMaxCount() {
        return 0;
    }

    @Override
    public void process(Particle p, float delta) {

    }

    @Override
    public Particle setNewParticle(Particle p) {
        return null;
    }

    @Override
    public void init() {

    }
}
