package com.n8lm.zener.glsl;

public class VariableDef {
    private VarType type;
    private String name;
	
	public VariableDef(VarType type, String name) {
		this.type = type;
		this.name = name;
	}

    public VarType getType() {
        return type;
    }

    public void setType(VarType type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
