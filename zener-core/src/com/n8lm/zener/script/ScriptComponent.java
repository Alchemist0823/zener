/*
 * This file is part of Zener.
 *
 * Zener is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of
 * the License, or any later version.
 *
 * Zener is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General
 * Public License along with Zener.  If not, see
 * <http://www.gnu.org/licenses/>.
 */

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
