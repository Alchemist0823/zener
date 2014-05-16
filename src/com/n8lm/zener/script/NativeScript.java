package com.n8lm.zener.script;

import com.artemis.World;

public abstract class NativeScript implements Script {

	@Override
	public final void run(World world, Event event) {
		runScript(world, event);
	}
	
	protected abstract void runScript(World world, Event event);
}
