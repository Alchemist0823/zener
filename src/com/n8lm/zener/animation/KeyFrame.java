package com.n8lm.zener.animation;

/**
 * Basic key frame in an animation
 * Any kind of key frame should inherit this class
 * @author Alchemist
 *
 */
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
