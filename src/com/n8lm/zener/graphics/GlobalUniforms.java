package com.n8lm.zener.graphics;

import com.n8lm.zener.math.Matrix4f;

public class GlobalUniforms extends UniformGroup {

	public GlobalUniforms() {
		super();
    	uniforms.put("g_ViewMatrix", new UniformVariable("g_ViewMatrix", VarType.Matrix4));
    	uniforms.put("g_ModelMatrix", new UniformVariable("g_ModelMatrix", VarType.Matrix4));
    	uniforms.put("g_ProjectionMatrix", new UniformVariable("g_ProjectionMatrix", VarType.Matrix4));
	}

	public void setViewMatrix(Matrix4f value) {
		uniforms.get("g_ViewMatrix").setValue(value);
	}

	public void setModelMatrix(Matrix4f value) {
		uniforms.get("g_ModelMatrix").setValue(value);
	}
	
	public void setProjectionMatrix(Matrix4f value) {
		uniforms.get("g_ProjectionMatrix").setValue(value);
	}
}
