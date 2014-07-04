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

package com.n8lm.zener.map;

import java.io.BufferedReader;
import java.io.IOException;

public class TileSet{

	/*
	
	public static final int BUSH = 10;
	public static final int TREE = 11;
	public static final int PINETREE = 12;
	public static final int COCOPALM = 13;
	public static final int WALL = 14;
	*/
	//private String[] tileNames;
	private String[] textureNames;
	private boolean[] isUnmovable;
	private int[] consumeStep;

	private String[] structureNames;
	private boolean[] isObstacle;
	private int[] crossStep;
	
	private int tileCount;
	private int structCount;
	
	public TileSet() {
		textureNames = new String[100];
		isUnmovable = new boolean[100];
		consumeStep = new int[100];

		structureNames = new String[100];
		isObstacle = new boolean[100];
		crossStep = new int[100];
	}
	/*
	public static int getStep(int id) {
		if (id == BUSH)
			return 2;
		//else if (id == SAND)
		//	return 2;
		else
			return 1;
	}*/
	public String getTextureName(int id) {
		return textureNames[id];
	}
	
	public String getStructureName(int structure) {
		return structureNames[structure];
	}
	
	/*
	public int getStep(int id) {
		return consumeStep[id];
	}
	public boolean isAvaliable(int id) {
		return !isObstacle[id];
	}*/
	
	public int getTileCount() {
		return tileCount;
	}
	public void setTileCount(int tileCount) {
		this.tileCount = tileCount;
	}
	
	public int getGroundStep(int ground) {
		return this.consumeStep[ground];
	}

	public boolean isAvaliableGround(int ground) {
		return !isUnmovable[ground];
	}

	public int getStructCount() {
		return structCount;
	}
	public void setStructCount(int structCount) {
		this.structCount = structCount;
	}
	
	public void readFromText(BufferedReader reader) throws IOException {
		String line;
		int maxId = 0;
		while (!(line = reader.readLine()).startsWith("-")) {
			String str[] = line.split(" ");
			int id = Integer.parseInt(str[0]);
			textureNames[id] = str[1];
			isUnmovable[id] = Boolean.parseBoolean(str[2]);
			consumeStep[id] = Integer.parseInt(str[3]);
			if(id > maxId)
				maxId = id;
		}
		tileCount = maxId + 1;

		maxId = 0;
		while (!(line = reader.readLine()).startsWith("-")) {
			String str[] = line.split(" ");
			int id = Integer.parseInt(str[0]);
			structureNames[id] = str[1];
			isObstacle[id] = Boolean.parseBoolean(str[2]);
			crossStep[id] = Integer.parseInt(str[3]);
			if(id > maxId)
				maxId = id;
		}
		structCount = maxId + 1;
	}
	/*
	@Override
	public void read(InputStream input) throws IOException {

		DataInputStream reader = new DataInputStream(input);
		tileCount = reader.readInt();
		reader.readChar();
		
	}
	@Override
	public void write(OutputStream output) throws IOException {
		// TODO Auto-generated method stub
		
	}*/
	public boolean isObstacleStructure(int structure) {
		return isObstacle[structure];
	}
	
	public int getStructureCrossStep(int structure) {
		return crossStep[structure];
	}
}
