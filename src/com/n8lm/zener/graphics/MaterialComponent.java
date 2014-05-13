package com.n8lm.zener.graphics;

import com.artemis.Component;

public class MaterialComponent extends Component {
	
	private UniformGroup material;
	private boolean transparent;
	private boolean shadowReceiver;
	
	public MaterialComponent(UniformGroup material) {
		this.material = material;
		this.transparent = false;
		this.shadowReceiver = false;
	}

	public UniformGroup getMaterial() {
		return material;
	}

	public void setMaterial(UniformGroup material) {
		this.material = material;
	}

	public boolean isTransparent() {
		return transparent;
	}

	public void setTransparent(boolean transparent) {
		this.transparent = transparent;
	}

	public boolean isShadowReceiver() {
		return shadowReceiver;
	}

	public void setShadowReceiver(boolean shadowReceiver) {
		this.shadowReceiver = shadowReceiver;
	}
	
}
