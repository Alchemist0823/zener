package com.n8lm.zener.script;

import com.artemis.Entity;
import com.n8lm.zener.animation.Animation;

public class AnimationEvent extends Event {

	public static final String END = "animationEnd";
	public static final String START = "animationSTART";
	public static final String STOP = "animationSTOP";
	
	private Animation<?> anim;
	
	public AnimationEvent(String type, Entity dispatcher, Animation<?> anim) {
		super(type, dispatcher);
		this.anim = anim;
	}

	public Animation<?> getAnim() {
		return anim;
	}

}
