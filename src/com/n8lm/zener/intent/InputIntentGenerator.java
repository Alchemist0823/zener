package com.n8lm.zener.intent;

import com.artemis.World;
import com.n8lm.zener.input.InputListener;

abstract public class InputIntentGenerator extends IntentGenerator implements InputListener {

	public InputIntentGenerator(World world) {
		super(world);
	}
}
