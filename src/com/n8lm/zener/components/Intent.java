package com.n8lm.zener.components;

import com.artemis.Entity;

public class Intent {

	private String name;
	private Object param;
	private Entity target;
	
	public Intent(String name, Object param) {
		this.name = name;
		this.param = param;
	}
	
	public Intent(String name, Object param, Entity target) {
		this(name, param);
		this.target = target;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Object getParam() {
		return param;
	}

	public void setParam(Object param) {
		this.param = param;
	}

	public Entity getTarget() {
		return target;
	}

	public void setTarget(Entity target) {
		this.target = target;
	}
}
