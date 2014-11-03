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

import java.util.*;

/**
 * Animation data which store all the keyframe
 * @author Alchemist
 *
 * @param <T> KeyFrameType
 */
public class Animation<T extends KeyFrame> {

	protected String name;
	protected List<T> frames;
	protected float totalTime;
	
	public Animation() {
		this.name = "undefine";
		frames = new ArrayList<T>();
	}
	
	public Animation(String name) {
		this();
		this.name = name;
	}
	
	public void addFrame(T frame) {
		if (frame.getTime() >= totalTime) {
			frames.add(frame);
			totalTime = frame.getTime();
		} else {
			for(int i = 0; i < frames.size(); i ++)
				if (frames.get(i).getTime() > frame.getTime())
				{
					frames.add(i, frame);
					break;
				}
		}
	}
	
	public T getFrame(int index) {
		return frames.get(index);
	}
	
	public float getTotalTime() {
		return totalTime;
	}
	
	public int getTotalFrame() {
		return frames.size();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
