package com.n8lm.zener.script;

import com.artemis.World;

interface Script {
	public void run(World world, Event event);
}
