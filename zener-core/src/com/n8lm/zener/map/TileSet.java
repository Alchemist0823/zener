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
    protected static final int TILE_NUM = 100;

    protected Tile tiles[];
    protected Structure structures[];

	//private String[] tileNames;
    protected int tileCount;
    protected int structCount;
	
	public TileSet() {
        tiles = new Tile[TILE_NUM];
        structures = new Structure[TILE_NUM];
	}

	public int getTileCount() {
		return tileCount;
	}

	public int getStructCount() {
		return structCount;
	}

    /*public void setStructCount(int structCount) {
		this.structCount = structCount;
	}*/
	
	public void readFromText(BufferedReader reader) throws IOException {
		String line;
		int maxId = 0;
		while (!(line = reader.readLine()).startsWith("-")) {
			String str[] = line.split(" ");
			int id = Integer.parseInt(str[0]);
            tiles[id] = new Tile(str[1]);
			if(id > maxId)
				maxId = id;
		}
		tileCount = maxId + 1;

		maxId = 0;
		while (!(line = reader.readLine()).startsWith("-")) {
			String str[] = line.split(" ");
			int id = Integer.parseInt(str[0]);
            structures[id] = new Structure(str[1]);
			if(id > maxId)
				maxId = id;
		}
		structCount = maxId + 1;
	}

    public Tile getTile(int tile) {
        return tiles[tile];
    }

    public Structure getStructure(int structure) {
        return structures[structure];
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
}
