package com.n8lm.zener.components;

import java.util.HashSet;

import com.artemis.Component;

public class ScriptComponent extends Component {
	private HashSet<Script> scripts;

	public ScriptComponent() {
		this.scripts = new HashSet<Script>();
	}
	
	public ScriptComponent(Script script) {
		this();
		this.scripts.add(script);
	}
	
	public HashSet<Script> getScripts() {
		return scripts;
	}

	public void addScript(Script script) {
		this.scripts.add(script);
	}
	
}
