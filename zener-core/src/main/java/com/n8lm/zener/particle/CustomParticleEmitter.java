package com.n8lm.zener.particle;

import com.n8lm.zener.math.ColorRGBA;
import com.n8lm.zener.math.SingleRangeFunction;
import com.n8lm.zener.math.Vector3fRangeFunction;

/**
 * CustomParticleEmitter is a particle emitter which has a lots of
 * parameters. it should be defined the User or an class that other
 * Emitters inherited.
 * <p/>
 * Created on 2014/11/8.
 *
 * @author Forrest Sun
 */
public class CustomParticleEmitter implements ParticleEmitter {

    private Vector3fRangeFunction initialPositionOverTime;
    private Vector3fRangeFunction initialVelocityOverTime;
    private SingleRangeFunction initialRotationOverTime;
    private SingleRangeFunction initialSizeOverTime;
    private SingleRangeFunction initialEmitSpeedOverTime;
    private SingleRangeFunction initialLifeOverTime;
    private ColorRGBA initialColorOverTime;

    private float remainParticle;

    public class Builder {

        private Vector3fRangeFunction initialPositionOverTime;
        private Vector3fRangeFunction initialVelocityOverTime;
        private SingleRangeFunction initialRotationOverTime;
        private SingleRangeFunction initialSizeOverTime;
        private SingleRangeFunction initialEmitSpeedOverTime;
        private SingleRangeFunction initialLifeOverTime;
        private ColorRGBA initialColorOverTime;

        public Builder setInitialPositionOverTime(Vector3fRangeFunction initialPositionOverTime) {
            this.initialPositionOverTime = initialPositionOverTime;
            return this;
        }

        public Builder setInitialVelocityOverTime(Vector3fRangeFunction initialVelocityOverTime) {
            this.initialVelocityOverTime = initialVelocityOverTime;
            return this;
        }

        public Builder setInitialRotationOverTime(SingleRangeFunction initialRotationOverTime) {
            this.initialRotationOverTime = initialRotationOverTime;
            return this;
        }

        public Builder setInitialSizeOverTime(SingleRangeFunction initialSizeOverTime) {
            this.initialSizeOverTime = initialSizeOverTime;
            return this;
        }

        public Builder setInitialEmitSpeedOverTime(SingleRangeFunction initialEmitSpeedOverTime) {
            this.initialEmitSpeedOverTime = initialEmitSpeedOverTime;
            return this;
        }

        public Builder setInitialLifeOverTime(SingleRangeFunction initialLifeOverTime) {
            this.initialLifeOverTime = initialLifeOverTime;
            return this;
        }

        public Builder setInitialColorOverTime(ColorRGBA initialColorOverTime) {
            this.initialColorOverTime = initialColorOverTime;
            return this;
        }

        public CustomParticleEmitter createCustomParticleEmitter() {
            return new CustomParticleEmitter(initialPositionOverTime, initialVelocityOverTime, initialRotationOverTime,
                    initialSizeOverTime, initialEmitSpeedOverTime, initialLifeOverTime, initialColorOverTime);
        }
    }

    public CustomParticleEmitter(Vector3fRangeFunction initialPositionOverTime, Vector3fRangeFunction initialVelocityOverTime,
                                 SingleRangeFunction initialRotationOverTime, SingleRangeFunction initialSizeOverTime,
                                 SingleRangeFunction initialEmitSpeedOverTime, SingleRangeFunction initialLifeOverTime,
                                 ColorRGBA initialColorOverTime) {
        this.initialPositionOverTime = initialPositionOverTime;
        this.initialVelocityOverTime = initialVelocityOverTime;
        this.initialRotationOverTime = initialRotationOverTime;
        this.initialSizeOverTime = initialSizeOverTime;
        this.initialEmitSpeedOverTime = initialEmitSpeedOverTime;
        this.initialLifeOverTime = initialLifeOverTime;
        this.initialColorOverTime = initialColorOverTime;
    }

    @Override
    public Particle setNewParticle(Particle p, float time) {
        p.rotateAngle = initialRotationOverTime.getYFromX(time);
        initialPositionOverTime.getVector3f(time, p.position);
        initialVelocityOverTime.getVector3f(time, p.velocity);
        p.color.set(initialColorOverTime);
        p.life = initialLifeOverTime.getYFromX(time);
        p.size = initialSizeOverTime.getYFromX(time);
        return p;
    }

    @Override
    public int getEmitNumber(float time, float delta) {
        return (int) (initialEmitSpeedOverTime.getYFromX(time + delta / 2) * delta);
    }

    public Vector3fRangeFunction getInitialPositionOverTime() {
        return initialPositionOverTime;
    }

    public void setInitialPositionOverTime(Vector3fRangeFunction initialPositionOverTime) {
        this.initialPositionOverTime = initialPositionOverTime;
    }

    public Vector3fRangeFunction getInitialVelocityOverTime() {
        return initialVelocityOverTime;
    }

    public void setInitialVelocityOverTime(Vector3fRangeFunction initialVelocityOverTime) {
        this.initialVelocityOverTime = initialVelocityOverTime;
    }

    public SingleRangeFunction getInitialRotationOverTime() {
        return initialRotationOverTime;
    }

    public void setInitialRotationOverTime(SingleRangeFunction initialRotationOverTime) {
        this.initialRotationOverTime = initialRotationOverTime;
    }

    public SingleRangeFunction getInitialSizeOverTime() {
        return initialSizeOverTime;
    }

    public void setInitialSizeOverTime(SingleRangeFunction initialSizeOverTime) {
        this.initialSizeOverTime = initialSizeOverTime;
    }

    public SingleRangeFunction getInitialEmitSpeedOverTime() {
        return initialEmitSpeedOverTime;
    }

    public void setInitialEmitSpeedOverTime(SingleRangeFunction initialEmitSpeedOverTime) {
        this.initialEmitSpeedOverTime = initialEmitSpeedOverTime;
    }

    public SingleRangeFunction getInitialLifeOverTime() {
        return initialLifeOverTime;
    }

    public void setInitialLifeOverTime(SingleRangeFunction initialLifeOverTime) {
        this.initialLifeOverTime = initialLifeOverTime;
    }

    public ColorRGBA getInitialColorOverTime() {
        return initialColorOverTime;
    }

    public void setInitialColorOverTime(ColorRGBA initialColorOverTime) {
        this.initialColorOverTime = initialColorOverTime;
    }

}
