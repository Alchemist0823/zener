package com.n8lm.zener.components;

import com.artemis.Component;
import com.n8lm.zener.particle.Particle;
import com.n8lm.zener.particle.ParticleController;

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
