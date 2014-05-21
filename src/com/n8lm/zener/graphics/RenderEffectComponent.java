package com.n8lm.zener.graphics;

import com.artemis.Component;
import com.n8lm.zener.math.Vector3f;
import com.n8lm.zener.math.Vector4f;
import java.util.*;

public class RenderEffectComponent extends Component {
	private final List<Vector4f> multiplyColors;
	private final List<Vector4f> addColors;

	public RenderEffectComponent(Vector4f multiplyColor, Vector4f addColor) {
		this.multiplyColors = new ArrayList<Vector4f>();// (1.0f, 1.0f, 1.0f,
														// 1.0f);
		this.addColors = new ArrayList<Vector4f>();
		this.multiplyColors.add(multiplyColor);
		this.addColors.add(addColor);
	}

	public RenderEffectComponent() {
		this(new Vector4f(Vector4f.UNIT_XYZW), new Vector4f(Vector4f.ZERO));
	}

	public Vector4f getMultiplyColor() {
		Vector4f resultM = new Vector4f(multiplyColors.get(0));
		for (int i = 1; i < multiplyColors.size(); i ++) {
			resultM.multLocal(multiplyColors.get(i));
		}
		return resultM;
	}

	public void setMultiplyColor(int layer, Vector4f multiplyColor) {
		incrementToLayer(layer);
		this.multiplyColors.get(layer).set(multiplyColor);
	}

	public void setMultiplyColor(int layer, Vector3f multiplyColor) {
		setMultiplyColor(layer, multiplyColor.x, multiplyColor.y, multiplyColor.z);
	}

	public void setMultiplyColor(int layer, float r, float g, float b) {
		incrementToLayer(layer);
		this.multiplyColors.get(layer).set(r, g, b, this.multiplyColors.get(layer).w);
	}
	
	private void incrementToLayer(int layer) {
		while (multiplyColors.size() <= layer) {
			this.multiplyColors.add(new Vector4f(Vector4f.UNIT_XYZW));
			this.addColors.add(new Vector4f(Vector4f.ZERO));
		}
	}
	
	public Vector4f getAddColor() {
		Vector4f resultA = new Vector4f(addColors.get(0));
		for (int i = 1; i < multiplyColors.size(); i ++) {
			resultA.multLocal(multiplyColors.get(i - 1)).addLocal(addColors.get(i));
		}
		return resultA;
	}
	
	public void setAddColor(int layer, Vector4f addColor) {
		incrementToLayer(layer);
		this.addColors.get(layer).set(addColor);
	}

	/*public float getAlpha() {
		return this.multiplyColor.w;
	}*/

	public void setAlpha(int layer, float alpha) {
		incrementToLayer(layer);
		this.multiplyColors.get(layer).w = alpha;
	}
}
