package com.n8lm.zener.graphics;

import com.artemis.Component;

public class LightComponent extends Component {
	private float[] diffuse = new float[]{1.0f, 1.0f, 1.0f, 1f};
	private float[] ambient = new float[]{0.3f, 0.3f, 0.3f, 1f};
	private boolean isPoint = true;
	
	public LightComponent() {
	}

	public LightComponent(boolean isPoint) {
		this.isPoint = isPoint;
	}

	public boolean isPoint() {
		return isPoint;
	}
	
	public void setPoint(boolean isPoint) {
		this.isPoint = isPoint;
	}

	public float[] getAmbient() {
		return ambient;
	}

	public void setAmbient(float[] ambient) {
		this.ambient = ambient;
	}

	public float[] getDiffuse() {
		return diffuse;
	}

	public void setDiffuse(float[] diffuse) {
		this.diffuse = diffuse;
	}
}
