package com.n8lm.zener.graphics.material;

import com.n8lm.zener.graphics.UniformGroup;

public abstract class UniformsMaterial extends UniformGroup {

	protected String shader;
	
	public UniformsMaterial(String shader) {
		this.shader = shader;
	}
	
	public String getShaderName() {
		return shader;
	}
}
