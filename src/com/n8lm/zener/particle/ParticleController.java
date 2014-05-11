package com.n8lm.zener.particle;

public interface ParticleController {
	
	/**
	 * @return the number if new particles per second
	 */
	public int getNewCount(double time);
	
	/**
	 * @return the number limitation of particles
	 */
	public int getMaxCount();
	
	/**
	 * Process the property of particle in the delta time interval
	 * If p.life < 0 the particle will die
	 * @param p a particle
	 * @param delta time interval
	 */
	public void process(Particle p, float delta);
	
	/**
	 * set up a new particle. particle emitter in the system
	 * @param p
	 * @return this particle
	 */
	public Particle setNewParticle(Particle p);
	

	/**
	 * tell the controll particle system starts
	 */
	public void init();
	
	/**
	 * @return whether the Particle System is end
	 */
	public boolean isEnd(double time);
	
	/**
	 * @return whether the Particle System is still creating new particles
	 */
	//public boolean isCreating();
}

