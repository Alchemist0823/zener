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

import com.artemis.Component;

public class ParticleSystemComponent extends Component {

	private int count;
	private Particle[] particles;
	private double time;
	private ParticleController controller;
	
	public ParticleSystemComponent(ParticleController controller) {
		particles = new Particle[controller.getMaxCount()];
		count = 0;
		time = 0;
		this.controller = controller;
		controller.init();
	}

	public Particle[] getParticles() {
		return particles;
	}
	
	public int getCount() {
		return count;
	}
	
	public void setCount(int count) {
		this.count = count;
	}

	public ParticleController getController() {
		return controller;
	}

	public void setController(ParticleController controller) {
		this.controller = controller;
	}

	public double getTime() {
		return time;
	}

	public void passTime(double time) {
		this.time += time;
	}
}
