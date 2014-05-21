package com.n8lm.zener.intent;

import com.artemis.Entity;

public class Intent {

	private String name;
	private Object param;
	private Object param2;
	private Entity target;
	
	public Intent(String name, Object param) {
		this.name = name;
		this.param = param;
	}

	public Intent(String name, Object param, Entity target) {
		this(name, param);
		this.target = target;
	}
	

	public Intent(String name, Object param, Object param2, Entity target) {
		this(name, param, target);
		this.param2 = param2;
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
	
	public Object getParam2() {
		return param2;
	}

	public void setParam(Object param) {
		this.param = param;
	}

	public void setParam2(Object param2) {
		this.param2 = param2;
	}

	public Entity getTarget() {
		return target;
	}

	public void setTarget(Entity target) {
		this.target = target;
	}
}
