package com.n8lm.zener.legacy.particle;

import com.n8lm.zener.math.Vector3f;

/**
 * Created on 2014/11/12.
 *
 * @author Forrest Sun
 */
public class GravityField implements ParticleField {

    protected final Vector3f gravity;

    public GravityField() {
        this(new Vector3f(0, 0, -9.8f));
    }

    public GravityField(Vector3f gravity) {
        this.gravity = gravity;
    }

    @Override
    public void apply(Particle p, float delta) {
        p.velocity.x += gravity.x * delta;
        p.velocity.y += gravity.y * delta;
        p.velocity.z += gravity.z * delta;
    }
}
