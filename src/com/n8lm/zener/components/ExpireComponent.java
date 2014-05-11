package com.n8lm.zener.components;

import com.artemis.Component;
import com.n8lm.zener.utils.EndEventListener;

public class ExpireComponent extends Component {
	private float remain;
	private EndEventListener expireListener;
	
	public ExpireComponent(float time) {
		this(time, null);
	}
	
	public ExpireComponent(float time, EndEventListener expireListener) {
		this.remain = time;
		this.expireListener = expireListener;
	}
	
	public float getRemain() {
		return remain;
	}
	
	public void substract(float time) {
		this.remain -= time;
	}

	public EndEventListener getExpireListener() {
		return expireListener;
	}
}
