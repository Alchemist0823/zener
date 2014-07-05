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

import com.artemis.Entity;
import com.n8lm.zener.script.AnimationEvent;
import com.n8lm.zener.script.ScriptHelper;

/**
 * A controller deal with animation data
 * @author Alchemist
 *
 * @param <T>
 */
public abstract class AnimationController<T extends KeyFrame>{

	protected Animation<T> anim;
	
	protected int time;
	protected int frameIndex;
	protected boolean isLoop;
	protected boolean isDeleted;
	
	//private List<EndEventListener> listeners;
	
	public AnimationController(Animation<T> anim) {
		this(anim, false);
	}
	
	public AnimationController(Animation<T> anim, boolean isLoop) {
		if (anim == null)
			throw new IllegalArgumentException("Animation is empty");
		this.anim = anim;
		//listeners = new ArrayList<EndEventListener>();
		time = 0;
		frameIndex  = -1;
		this.isLoop = isLoop;
	}

	/**
	 * 
	 * @param ac
	 * @param e
	 * @return whether the animationController should be removed
	 */
	public boolean process(Entity e) {

		if (isDeleted())
			return true;
		
		if (frameIndex + 1 < anim.getTotalFrame() 
				&& anim.getFrame(frameIndex + 1).getTime() <= time)
			frameIndex ++;
		
		if (frameIndex == -1)
			process(0, 0, e);
		else if (frameIndex + 1 >= anim.getTotalFrame())
			process(frameIndex, frameIndex, e);
		else
			process(frameIndex, frameIndex + 1, e);
		
		time ++;
		
		if(time > anim.getTotalTime())
		{
			if (!isLoop()) {
				invoke(e, AnimationEvent.END);
				return true;
			} else {
				init();
				return false;
			}
		}
		
		return false;
	}
	
	protected abstract void process(int nowIndex, int nextIndex, Entity e);

	protected void invoke(Entity e, String event) {
		ScriptHelper.dispatchEvent(e.getWorld(), e, new AnimationEvent(event, e, anim));
		//for(EndEventListener listener : listeners) {
		//	listener.endEvent(e);
		//}
	}
	
	protected void init() {
		time = 0;
		frameIndex  = -1;
	}
	
	public Animation<T> getAnim() {
		return anim;
	}
	
	public boolean isLoop() {
		return isLoop;
	}
	
	public void delete() {
		isDeleted = true;
	}
	
	public boolean isDeleted() {
		return isDeleted;
	}

	/*public void addEventListener(EndEventListener listener) {
		listeners.add(listener);
	}*/
	

	public int getFrameIndex() {
		return frameIndex;
	}

	public int getTime() {
		return time;
	}

}
