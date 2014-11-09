/*
 * This file is part of Zener.
 *
 * Zener is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of
 * the License, or any later version.
 *
 * Zener is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General
 * Public License along with Zener.  If not, see
 * <http://www.gnu.org/licenses/>.
 */

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

}

