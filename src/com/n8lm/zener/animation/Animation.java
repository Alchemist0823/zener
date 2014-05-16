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
	protected int totalTime;
	
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
	
	public int getTotalTime() {
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
