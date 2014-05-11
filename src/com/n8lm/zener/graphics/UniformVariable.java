package com.n8lm.zener.graphics;

//import java.nio.FloatBuffer;

import com.n8lm.zener.math.*;

public class UniformVariable extends Uniform {

	protected Object value = null;
    //protected FloatBuffer multiData = null;
	final protected VarType varType;

	public UniformVariable(String name) {
		super(name);
		varType = null;
	}
	
	public UniformVariable(String name, VarType varType) {
		super(name);
		this.varType = varType;
	}
	
	public UniformVariable(String name, VarType varType, Object value) {
		this(name, varType);
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
            throw new NullPointerException();
        /*
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
        	break;
        	Matrix4f[] m4a = (Matrix4f[]) value;
	        if (multiData == null) {
	            multiData = BufferUtils.createFloatBuffer(m4a.length * 16);
	        } else {
	            multiData = BufferUtils.ensureLargeEnough(multiData, m4a.length * 16);
	        }
	        for (int i = 0; i < m4a.length; i++) {
	            m4a[i].fillFloatBuffer(multiData, true);
	        }
	        multiData.clear();
        default:*/
        	this.value = value;
        //    break;
        //}
        //this.value = value;
    }
    
    @Override
    public String toString() {
    	return name + ": " + value.toString();
    }
}
