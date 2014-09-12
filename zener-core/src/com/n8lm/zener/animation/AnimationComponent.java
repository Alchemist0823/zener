/*
 * This file is part of Zener.
 *
 * Zener is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of
 * the License, or any later version.
 *
 * Zener is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General
 * Public License along with Zener.  If not, see
 * <http://www.gnu.org/licenses/>.
 */

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

    public AnimationComponent(AnimationController<?> ac) {
        animControllers = new LinkedList<AnimationController<?>>();
        add(ac);
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
