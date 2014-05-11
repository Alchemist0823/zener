package com.n8lm.zener.animation;

public abstract class KeyFrame {

	private int time;
	
	protected KeyFrame(int time) {
		this.time = time;
	}

	public int getTime() {
		return time;
	}
	
	public void setTime(int time) {
		this.time = time;
	}
}
