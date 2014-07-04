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

package com.n8lm.zener.data;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.n8lm.zener.map.TiledMap;

public class TiledMapDatabase extends GameDatabase {

	Map<String, TiledMap> maps;
	Map<String, String> paths;
	
	public TiledMapDatabase() {
		maps = new TreeMap<String, TiledMap>();
		paths = new TreeMap<String, String>();
	}

	@Override
	public void load(InputStream input) throws IOException {
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(input));
		String line;
		
		while((line = reader.readLine()) != null) {
			paths.put(line.split(" ")[0], line.split(" ")[1]);
		}
	}

	public TiledMap getTiledMap(String name) {
		if (maps.containsKey(name))
			return maps.get(name);
		else if (paths.containsKey(name)) {
			TiledMap map = new TiledMap();
			try {
				map.readFromText(new BufferedReader(new InputStreamReader(ResourceManager.getInstance().getResourceAsStream(paths.get(name)))));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			maps.put(name, map);
			return map;
		} else {
			// TODO Auto-generated catch block
			return null;
		}
	}
	
	public List<String> getMapList() {
		return new ArrayList<String>(paths.keySet());
	}
}
