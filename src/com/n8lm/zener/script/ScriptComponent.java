package com.n8lm.zener.script;

import java.util.*;

import com.artemis.Component;

public class ScriptComponent extends Component {
	private Map<String, List<Script>> scriptsByEvent;

	public ScriptComponent() {
		this.scriptsByEvent = new HashMap<String, List<Script>>();
	}
	
	public ScriptComponent(String event, Script script) {
		this();
		addScript(event, script);
	}
	
	public boolean hasEventScripts(String event) {
		return this.scriptsByEvent.containsKey(event);
	}
	
	public List<Script> getScriptsByEvent(String event) {
		return scriptsByEvent.get(event);
	}

	public void addScript(String event, Script script) {
		
		if (this.scriptsByEvent.containsKey(event))
			scriptsByEvent.get(event).add(script);
		else {
			List<Script> scriptList = new ArrayList<Script>();
			scriptList.add(script);
			scriptsByEvent.put(event, scriptList);
		}
	}
}
