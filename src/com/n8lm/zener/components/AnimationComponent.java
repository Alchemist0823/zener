package com.n8lm.zener.components;

import java.util.LinkedList;
import java.util.List;

import com.artemis.Component;
import com.n8lm.zener.animation.AnimationController;

public class AnimationComponent extends Component {

	private List<AnimationController<?>> animControllers;
	
	public AnimationComponent() {
		animControllers = new LinkedList<AnimationController<?>>();
	}
	
	public void add(AnimationController<?> ac) {
		animControllers.add(ac);
	}
	
	public List<AnimationController<?>> getAnimationControllers() {
		return animControllers;
	}

}
