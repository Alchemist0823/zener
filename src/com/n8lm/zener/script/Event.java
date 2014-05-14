package com.n8lm.zener.script;

import com.artemis.Entity;

public class Event {
	
	public static final String WORLD_UPDATE = "worldUpdate";

	private String type;
	private Entity dispatcher;
	
	public Event(String type, Entity dispatcher) {
		this.type = type;
		this.dispatcher = dispatcher;
	}

	public Entity getDispatcher() {
		return dispatcher;
	}

	public String getType() {
		return type;
	}
	
}
