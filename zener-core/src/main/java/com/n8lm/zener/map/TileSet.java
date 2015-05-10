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
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class TileSet<T extends Tile>{


    protected Map<Integer, T> tiles;
    //protected Map<Integer, S> structures;

	public TileSet() {
        tiles = new HashMap<Integer, T>();
        //structures = new HashMap<Integer, S>();
	}

	public int getTileCount() {
		return tiles.size();
	}

	/*public int getStructCount() {
		return structures.size();
	}*/

    /*public void setStructCount(int structCount) {
		this.structCount = structCount;
	}*/

    public void add(int id, T tile) {
        tiles.put(id, tile);
    }

    /*public void add(int id, S structure) {
        structures.put(id, structure);
    }*/

    public T getTile(int tile) {
        return tiles.get(tile);
    }

    public void remove(int tile) {
        tiles.remove(tile);
    }

    /*public S getStructure(int structure) {
        return structures.get(structure);
    }*/
    public static TileSet readFromText(TileBuilder<? extends Tile> tileBuilder, BufferedReader reader) throws IOException {
        TileSet tileSet = new TileSet();
        String line;
        while (!(line = reader.readLine()).startsWith("-")) {
            Tile tile = tileBuilder.build(line);
            tileSet.add(tile.getId(), tile);
        }
        return tileSet;
    }

    public void writeToText(BufferedWriter writer) throws IOException{
        for (Map.Entry<Integer, T> entry : tiles.entrySet()) {
            writer.write(entry.getKey() + " ");
            entry.getValue().write(writer);
            writer.newLine();
        }
        writer.write("-------------------------------------");
        writer.newLine();
        /*for (Map.Entry<Integer, S> entry : structures.entrySet()) {
            writer.write(entry.getKey() + " ");
            entry.getValue().write(writer);
            writer.newLine();
        }
        writer.write("-------------------------------------");
        writer.newLine();*/
    }
}
