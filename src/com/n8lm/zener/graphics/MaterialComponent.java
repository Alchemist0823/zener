package com.n8lm.zener.graphics;

import com.artemis.Component;
import com.n8lm.zener.graphics.material.UniformsMaterial;

public class MaterialComponent extends Component {
	
	private UniformsMaterial material;
	private boolean transparent;
	private boolean shadowReceiver;
	
	public MaterialComponent(UniformsMaterial material) {
        this(material, false, false);
	}

    public MaterialComponent(UniformsMaterial material, boolean shadowReceiver) {
        this(material, shadowReceiver, false);
    }

    public MaterialComponent(UniformsMaterial material, boolean shadowReceiver, boolean transparent) {
        this.material = material;
        this.shadowReceiver = shadowReceiver;
        this.transparent = transparent;
    }

	public UniformsMaterial getMaterial() {
		return material;
	}

	public void setMaterial(UniformsMaterial material) {
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
