package com.n8lm.zener.animation;

import java.util.LinkedList;
import java.util.List;

import com.artemis.Component;

/**
 * an Animation Component contains many different AnimationControllers 
 * It is processed by AnimationSystem.
 * @author Alchemist
 *
 */
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
	
	public AnimationController<?> getAnimationControllerByType(Class<? extends AnimationController<?>> type){
		for (AnimationController<?> ac: animControllers)
			if (type.isInstance(ac))
				return ac;
		return null;
	}
	
	public void removeAnimationControllerByType(Class<? extends AnimationController<?>> type){
		for (AnimationController<?> ac: animControllers)
			if (type.isInstance(ac))
				ac.delete();
	}
	
	public AnimationController<?> getAnimationControllerByName(String name){
		for (AnimationController<?> ac: animControllers)
			if (ac.anim.getName().equals(name))
				return ac;
		return null;
	}
	
	public void removeAnimationControllerByName(String name){
		//Iterator<AnimationController<?>> it = this.animControllers.iterator();
		for (AnimationController<?> ac: animControllers)
			if (ac.anim.getName().equals(name))
				ac.delete();
	}
	
	
}
