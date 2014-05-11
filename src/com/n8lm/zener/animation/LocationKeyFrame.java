package com.n8lm.zener.animation;

import com.n8lm.zener.map.Location;

public class LocationKeyFrame extends KeyFrame {

	Location loc;
	
	public LocationKeyFrame(int time, Location loc) {
		super(time);
		this.loc = loc;
	}

}
