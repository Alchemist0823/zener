package com.n8lm.zener.intent;

import java.util.HashMap;

import com.artemis.Component;

public class IntentComponent extends Component {

	private HashMap<String, Intent> intents;
	
	public IntentComponent() {
		intents = new HashMap<String, Intent>();
	}

	public IntentComponent(Intent intent) {
		this();
		add(intent);
	}
	
	public void add(Intent intent) {
		intents.put(intent.getName(), intent);
	}
	
	public Intent getIntentByName(String name) {
		return intents.get(name);
	}
	
	public void remove(String name) {
		intents.remove(name);
	}

	public void removeAll() {
		intents.clear();
	}

}
