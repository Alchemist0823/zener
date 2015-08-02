package com.n8lm.zener.particle;

import com.n8lm.zener.math.ColorGradient;
import com.n8lm.zener.math.Vector3f;
import com.n8lm.zener.math.Vector3fFunction;

/**
 * Created by Alchemist0823 on 8/1/2015.
 */
public class CustomParticleModifier implements ParticleModifier {

    private Vector3fFunction velocityOverLife;
    private ColorGradient colorOverLife;

    private Vector3f tempvec3 = new Vector3f();

    public CustomParticleModifier(Vector3fFunction velocityOverLife, ColorGradient colorOverLife) {
        this.velocityOverLife = velocityOverLife;
        this.colorOverLife = colorOverLife;
    }

    @Override
    public void apply(Particle p, float delta) {
        colorOverLife.getYfromX(p.time / p.life, p.color);
        p.position.addLocal(velocityOverLife.getVector3f(p.time / p.life, tempvec3));
    }

    @Override
    public void frameStarted() {

    }
}
