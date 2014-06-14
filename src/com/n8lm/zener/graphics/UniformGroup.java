package com.n8lm.zener.graphics;

import java.util.Collection;
import java.util.HashMap;

import com.n8lm.zener.math.Vector3f;
import com.n8lm.zener.math.Vector4f;

public class UniformGroup {

	protected HashMap<String, UniformVariable> uniforms;
	
	public UniformGroup() {
		uniforms = new HashMap<String, UniformVariable>();
	}
	
	public void addUniform(String name, VarType type, Object value) {
		uniforms.put(name, new UniformVariable(name, type, value));
	}

	public void set(String name, Object value) {
    	uniforms.get(name).setValue(value);
	}
	
	protected void setVector3f(String name, float x, float y, float z) {
    	UniformVariable uv = uniforms.get(name);
    	Vector3f v = null;
    	if ((v = (Vector3f) uv.getValue()) == null)
    		uv.setValue(new Vector3f(x, y, z));
    	else {
    		v.x = x;
    		v.y = y;
    		v.z = z;
    	}
	}
	
	protected void setVector4f(String name, float x, float y, float z, float w) {
    	UniformVariable uv = uniforms.get(name);
    	Vector4f v = null;
    	if ((v = (Vector4f) uv.getValue()) == null)
    		uv.setValue(new Vector4f(x, y, z, w));
    	else {
    		v.x = x;
    		v.y = y;
    		v.z = z;
    		v.w = w;
    	}
	}
	
	protected void setf(String name, float x) {
		UniformVariable uv = uniforms.get(name);
    	uv.setValue(x);
	}

	public boolean contains(String name) {
		return uniforms.containsKey(name);
	}
	
	public UniformVariable get(String name) {
		return uniforms.get(name);
	}
	
	public Collection<UniformVariable> getUniforms() {
		return uniforms.values();
	}
	
}
