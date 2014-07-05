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
