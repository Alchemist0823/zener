package com.n8lm.zener.input;

import com.artemis.World;

abstract public class InputIntentGenerator implements InputListener {

	protected World world;
	
	public InputIntentGenerator(World world) {
		this.world = world;
	}

}
