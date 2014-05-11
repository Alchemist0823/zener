package com.n8lm.zener.assets;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.n8lm.zener.animation.Animation;
import com.n8lm.zener.animation.PosesKeyFrame;
import com.n8lm.zener.animation.Skeleton;

public class Model {

	final private Mesh mesh;
	final private Material material;
	final private Skeleton skeleton;
	final private Map<String, Animation<PosesKeyFrame>> animations;
	
	public Model() {
		mesh = new Mesh();
		skeleton = new Skeleton();
		animations = new HashMap<String, Animation<PosesKeyFrame>>();
		material = new Material();
	}
	
	public void add(Animation<PosesKeyFrame> anim) {
		animations.put(anim.getName(), anim);
	}
	
	public Animation<PosesKeyFrame> getAnimation(String name) {
		return animations.get(name);
	}
	
	public Map<String, Animation<PosesKeyFrame>> getAnimations() {
		return animations;
	}
	
	public Skeleton getSkeleton() {
		return skeleton;
	}

	public Mesh getMesh() {
		return mesh;
	}

	public Material getMaterial() {
		return material;
	}
	
	public String toString() {
		return "Model: Material: " + material.toString();
	}
}
