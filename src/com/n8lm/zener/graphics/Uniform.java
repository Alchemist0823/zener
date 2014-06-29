package com.n8lm.zener.graphics;

import com.n8lm.zener.glsl.VarType;

public class Uniform {
	
	protected int location = -1;
	final protected String name;
	final protected VarType varType;
	
	public Uniform(VarType varType, String name) {
		this.varType = varType;
		this.name = name;
	}
	
    public void setLocation(int location){
        this.location = location;
    }

    public int getLocation(){
        return location;
    }
    
    public String getName(){
        return name;
    }
}
