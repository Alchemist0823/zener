package com.n8lm.zenertest.ranger;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class AbilityData implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2433466267617524020L;
	
	/*
	private int strength;
	private int intelligence;
	private int agility;
	private int stamina;
	private int luck;
	private int dexterity;
	private int mobility;*/
	private Map<String, Integer> data;

	protected AbilityData() {
		data = new HashMap<String, Integer>();
	}

	public void add(AbilityData ad) {
		for (Entry<String, Integer> e: ad.getData()) {
			if(data.containsKey(e.getKey()))
				data.put(e.getKey(), data.get(e.getKey()) + e.getValue());
			else
				data.put(e.getKey(), e.getValue());
		}
	}
	
	
	public int get(String attrib) {
		return data.get(attrib);
	}
	
	public void set(String attrib, int value) {
		data.put(attrib, value);
	}
	
	public Set<Entry<String, Integer>> getData() {
		return data.entrySet();
	}
	
	@Override
	public String toString() {
		String str = "[";
		for (Entry<String, Integer> e: data.entrySet()) {
			str += e.getKey() + "=" + e.getValue() + ", ";
		}
		return str.substring(0, str.length() - 2) + "]";
	}
}
