package com.n8lm.zener.particle;

import com.n8lm.zener.math.ColorRGBA;
import com.n8lm.zener.math.MathUtil;
import com.n8lm.zener.math.Vector3f;
import com.n8lm.zener.utils.TempVars;

/**
 * Created on 2014/11/8.
 *
 * @author Alchemist
 */
public class CustomParticleEmitter implements ParticleEmitter{

    private Vector3f initialPosition = new Vector3f();
    private Vector3f positionScatter = new Vector3f();
    private float initialRotation = 0f;
    private float rotationScatter = 0f;
    private float initialSize = 1.0f;
    private float sizeScatter = 0f;

    private ColorRGBA initialColor = new ColorRGBA();
    private Vector3f initialVelocity = new Vector3f();
    private Vector3f velocityScatter = new Vector3f();
    private float initialLife = 60f;

    public CustomParticleEmitter() {
    }

    public void setInitialPosition(Vector3f initialPosition) {
        this.initialPosition = initialPosition;
    }

    public void setPositionScatter(Vector3f positionScatter) {
        this.positionScatter = positionScatter;
    }

    public void setInitialRotation(float initialRotation) {
        this.initialRotation = initialRotation;
    }

    public void setRotationScatter(float rotationScatter) {
        this.rotationScatter = rotationScatter;
    }

    public void setInitialSize(float initialSize) {
        this.initialSize = initialSize;
    }

    public void setSizeScatter(float sizeScatter) {
        this.sizeScatter = sizeScatter;
    }

    public void setInitialColor(ColorRGBA initialColor) {
        this.initialColor = initialColor;
    }

    public void setInitialVelocity(Vector3f initialVelocity) {
        this.initialVelocity = initialVelocity;
    }

    public void setVelocityScatter(Vector3f velocityScatter) {
        this.velocityScatter = velocityScatter;
    }

    public void setInitialLife(float initialLife) {
        this.initialLife = initialLife;
    }

    public void setNewParticle(Particle p, float time) {
        TempVars tempVars = TempVars.get();
        p.rotation = initialRotation + rotationScatter * (MathUtil.nextRandomFloat() - 0.5f);

        MathUtil.nextRandomVector3f(tempVars.vect1);
        tempVars.vect1.subtractLocal(0.5f, 0.5f, 0.5f);
        tempVars.vect1.multLocal(positionScatter);
        p.position.set(initialPosition).addLocal(tempVars.vect1);

        MathUtil.nextRandomVector3f(tempVars.vect1);
        tempVars.vect1.subtractLocal(0.5f, 0.5f, 0.5f);
        tempVars.vect1.multLocal(velocityScatter);
        p.velocity.set(initialVelocity).addLocal(tempVars.vect1);

        p.color.set(initialColor);

        p.life = initialLife;

        p.size = initialSize + sizeScatter * (MathUtil.nextRandomFloat() - 0.5f);

        tempVars.release();
    }

    public Particle newParticle(float time) {
        Particle p = new Particle();
        setNewParticle(p, time);
        return p;
    }
}
