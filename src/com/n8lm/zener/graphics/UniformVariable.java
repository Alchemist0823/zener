package com.n8lm.zener.graphics;

import com.n8lm.zener.glsl.VarType;
import com.n8lm.zener.math.*;

public class UniformVariable extends Uniform {

	protected Object value = null;
    //protected FloatBuffer multiData = null;

	public UniformVariable(Uniform uniform) {
		super(uniform.varType, uniform.name);
	}

    public UniformVariable(VarType varType, String name) {
        super(varType, name);
    }

    public UniformVariable(String name, VarType varType) {
        super(varType, name);
    }

    public UniformVariable(VarType varType, String name, Object value) {
        this(varType, name);
        setValue(value);
    }

    public UniformVariable( String name, VarType varType, Object value) {
        this(varType, name);
        setValue(value);
    }

    public VarType getVarType() {
        return varType;
    }

    public Object getValue() {
        return value;
    }
    
    public void setValue(Object value) {

        if (value == null)
            throw new NullPointerException("uniform value");

        switch (varType){
        case Texture2D:
        	if (!(value instanceof Texture))
        		throw new IllegalArgumentException("Bad Uniform value");
        	break;
        case Matrix4:
        	if (!(value instanceof Matrix4f))
        		throw new IllegalArgumentException("Bad Uniform value");
        	break;
        case Vector4f:
        	if (!(value instanceof Vector4f))
        		throw new IllegalArgumentException("Bad Uniform value");
        	break;
        case Vector3f:
        	if (!(value instanceof Vector3f))
        		throw new IllegalArgumentException("Bad Uniform value");
        	break;
        case Float:
        	if (!(value instanceof Float))
        		throw new IllegalArgumentException("Bad Uniform value");
        	break;
        case Matrix4Array:
        	if (!(value instanceof Matrix4f[]))
        		throw new IllegalArgumentException("Bad Uniform value");
        	break;/*
        	Matrix4f[] m4a = (Matrix4f[]) value;
	        if (multiData == null) {
	            multiData = BufferUtils.createFloatBuffer(m4a.length * 16);
	        } else {
	            multiData = BufferUtils.ensureLargeEnough(multiData, m4a.length * 16);
	        }
	        for (int i = 0; i < m4a.length; i++) {
	            m4a[i].fillFloatBuffer(multiData, true);
	        }
	        multiData.clear();*/
        default:
            break;
        }
        this.value = value;
        //this.value = value;
    }
    
    @Override
    public String toString() {
        if (value != null)
        	return varType.name() + " " + name + ": " + value.toString();
        else
            return varType.name() + " " + name;
    }
}
