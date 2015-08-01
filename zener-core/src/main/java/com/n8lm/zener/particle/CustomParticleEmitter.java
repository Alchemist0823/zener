package com.n8lm.zener.particle;

import com.n8lm.zener.math.ColorRGBA;
import com.n8lm.zener.math.SingleRangeFunction;
import com.n8lm.zener.math.Vector3fRangeFunction;
import com.n8lm.zener.utils.TempVars;

/**
 * CustomParticleEmitter is a particle emitter which has a lots of
 * parameters. it should be defined the User or an class that other
 * Emitters inherited.
 *
 * Created on 2014/11/8.
 *
 * @author Forrest Sun
 */
public class CustomParticleEmitter implements ParticleEmitter {

    private Vector3fRangeFunction initialPosition = new Vector3fRangeFunction();
    private Vector3fRangeFunction initialVelocity = new Vector3fRangeFunction();
    private SingleRangeFunction initialRotation = new SingleRangeFunction();
    private SingleRangeFunction initialSize = new SingleRangeFunction();
    private SingleRangeFunction initialEmitSpeed = new SingleRangeFunction();
    private SingleRangeFunction initialLife = new SingleRangeFunction();
    private ColorRGBA initialColor = new ColorRGBA();

    public CustomParticleEmitter() {
    }

    @Override
    public void setNewParticle(Particle p, float time) {
        TempVars tempVars = TempVars.get();
        p.rotateAngle = initialRotation.getYFromX(time);

        initialPosition.getVector3f(time, p.position);

        initialVelocity.getVector3f(time, p.velocity);

        p.color.set(initialColor);

        p.life = initialLife.getYFromX(time);

        p.size = initialSize.getYFromX(time);

        //p.texIndex = MathUtil.nextRandomInt(0, atlasCount - 1);

        tempVars.release();
    }

    @Override
    public Particle newParticle(float time) {
        Particle p = new Particle();
        setNewParticle(p, time);
        return p;
    }

    @Override
    public int getEmitSpeed(float time) {
        return (int) (initialEmitSpeed.getYFromX(time));
    }

    public Vector3fRangeFunction getInitialPosition() {
        return initialPosition;
    }

    public void setInitialPosition(Vector3fRangeFunction initialPosition) {
        this.initialPosition = initialPosition;
    }

    public Vector3fRangeFunction getInitialVelocity() {
        return initialVelocity;
    }

    public void setInitialVelocity(Vector3fRangeFunction initialVelocity) {
        this.initialVelocity = initialVelocity;
    }

    public SingleRangeFunction getInitialRotation() {
        return initialRotation;
    }

    public void setInitialRotation(SingleRangeFunction initialRotation) {
        this.initialRotation = initialRotation;
    }

    public SingleRangeFunction getInitialSize() {
        return initialSize;
    }

    public void setInitialSize(SingleRangeFunction initialSize) {
        this.initialSize = initialSize;
    }

    public SingleRangeFunction getInitialEmitSpeed() {
        return initialEmitSpeed;
    }

    public void setInitialEmitSpeed(SingleRangeFunction initialEmitSpeed) {
        this.initialEmitSpeed = initialEmitSpeed;
    }

    public SingleRangeFunction getInitialLife() {
        return initialLife;
    }

    public void setInitialLife(SingleRangeFunction initialLife) {
        this.initialLife = initialLife;
    }

    public ColorRGBA getInitialColor() {
        return initialColor;
    }

    public void setInitialColor(ColorRGBA initialColor) {
        this.initialColor = initialColor;
    }

}
