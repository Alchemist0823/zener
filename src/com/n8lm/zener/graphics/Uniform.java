package com.n8lm.zener.graphics;

public class Uniform {
	
	protected int location = -2;
	final protected String name;
	
	public Uniform(String name) {
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
