package com.n8lm.zener.components;

import com.artemis.Component;
import com.n8lm.zener.math.Vector3f;
import com.n8lm.zener.math.Vector4f;

public class RenderEffectComponent extends Component {
	private final Vector4f multiplyColor;
	private final Vector4f addColor;
	
	public RenderEffectComponent(Vector4f multiplyColor, Vector4f addColor) {
		this.multiplyColor = multiplyColor;
		this.addColor = addColor;
	}
	
	public RenderEffectComponent() {
		this.multiplyColor = new Vector4f(1.0f, 1.0f, 1.0f, 1.0f);
		this.addColor = new Vector4f();
	}

	public Vector4f getMultiplyColor() {
		return multiplyColor;
	}

	public void setMultiplyColor(Vector4f multiplyColor) {
		this.multiplyColor.set(multiplyColor);
	}
	
	public void setMultiplyColor(Vector3f multiplyColor) {
		setMultiplyColor(multiplyColor.x, multiplyColor.y, multiplyColor.z);
	}

	public void setMultiplyColor(float r, float g, float b) {
		this.multiplyColor.set(r, g, b, this.multiplyColor.w);
	}

	public Vector4f getAddColor() {
		return addColor;
	}

	public void setAddColor(Vector4f addColor) {
		this.addColor.set(addColor);
	}
	
	public float getAlpha() {
		return this.multiplyColor.w;
	}
	
	public void setAlpha(float alpha) {
		this.multiplyColor.w = alpha;
	}
}
