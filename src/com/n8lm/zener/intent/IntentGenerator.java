package com.n8lm.zener.intent;

import com.artemis.World;

abstract public class IntentGenerator {

	protected World world;
	
	public IntentGenerator(World world) {
		this.world = world;
	}

}
