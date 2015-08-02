package com.n8lm.zener.particle;

import com.n8lm.zener.math.Vector3f;

/**
 * Created on 2014/11/12.
 *
 * @author Forrest Sun
 */
public class GravityModifier implements ParticleModifier {

    protected final Vector3f gravity;

    public GravityModifier() {
        this(new Vector3f(0, 0, -9.8f));
    }

    public GravityModifier(Vector3f gravity) {
        this.gravity = gravity;
    }

    @Override
    public void apply(Particle p, float delta) {
        p.velocity.x += gravity.x * delta;
        p.velocity.y += gravity.y * delta;
        p.velocity.z += gravity.z * delta;
    }

    @Override
    public void frameStarted() {

    }
}
