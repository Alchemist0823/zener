package com.n8lm.zener.glsl;

import java.util.ArrayList;
import java.util.List;

public class GLSLStructure implements VariableContainer{
	private String name;
	private List<VariableDef> vars;
	
	public GLSLStructure(String name) {
		this.name = name;
		vars = new ArrayList<VariableDef>();
	}

	public void add(VariableDef variable) {
		vars.add(variable);
	}
	
	public String getName() {
		return name;
	}

	public List<VariableDef> getVars() {
		return vars;
	}
}
