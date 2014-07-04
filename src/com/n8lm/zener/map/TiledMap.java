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
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Properties;
import java.util.Scanner;

import com.artemis.Entity;
import com.n8lm.zener.data.ResourceManager;
import com.n8lm.zener.data.Savable;
import com.n8lm.zener.data.TiledMapDatabase;

/**
 * handle the information of tiled map, can not operate components of entity.
 */
public class TiledMap implements Savable, Cloneable{

	protected String name;
	/** The width of the map */
	protected int width;
	/** The height of the map */
	protected int height;
	/** The width of the tiles used on the map */
	protected float tileWidth;
	/** The height of the tiles used on the map */
	protected float tileHeight;
	/** The altitude of the tiles used on the map */
	protected float tileAltitude;

	/** The location prefix where we can find tileset images */
	protected String tilesLocation;

	/** the id of the tile*/
	protected int terrian[][];
	
	/** the altitude of the tile*/
	protected int altitude[][];
	
	/** the structure on the tile*/
	protected int structure[][];
	
	/** the properties of the map */
	//protected Properties props;
	
	/** the tile set of the map*/
	protected TileSet tileSet;
	
	public TiledMap() {
		
		tileWidth = tileHeight = 2;
		tileAltitude = 0.8f;
		tileSet = new TileSet();
	}
	
	/**
	 * need revision
	 */
	public TiledMap clone() {
        try {
            TiledMap tilemap =  (TiledMap) super.clone();
            //tilemap.props = (Properties) props.clone();
            return tilemap;
        } catch (CloneNotSupportedException e) {        
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

	public void readFromText(BufferedReader reader) throws IOException {
        
		tileSet.readFromText(reader);
		
		name = reader.readLine();
		
        String[] strs = reader.readLine().split(" ");
        width = Integer.parseInt(strs[0]);
        height = Integer.parseInt(strs[1]);
        
		terrian = new int[width][height];
		altitude = new int[width][height];
		structure = new int[width][height];

        for (int i = 0; i < width; i ++) {
        	strs = reader.readLine().split(" ");
        	for (int j = 0; j < height; j ++)
        	{
        		terrian[i][j] = Integer.parseInt(strs[j]);
        	}
        }

        reader.readLine();

        for (int i = 0; i < width; i ++) {
        	strs = reader.readLine().split(" ");
        	for (int j = 0; j < height; j ++)
        	{
        		altitude[i][j] = Integer.parseInt(strs[j]);
        	}
        }

        reader.readLine();

        for (int i = 0; i < width; i ++) {
        	strs = reader.readLine().split(" ");
        	for (int j = 0; j < height; j ++)
        	{
        		structure[i][j] = Integer.parseInt(strs[j]);
        	}
        }
	}
	
	@Override
	public void read(InputStream input) throws IOException {
        
		//DataInputStream dataInputStream
		DataInputStream reader = new DataInputStream(input);
		
		char ch;
		String str = "";
		while ((ch = reader.readChar()) != '\n')
			str += ch;
		name = str;
		
		tileSet = ResourceManager.getInstance().getDatabase(TiledMapDatabase.class).getTiledMap(name).getTileSet();
		
        width = reader.readInt();
        height = reader.readInt();
        
		terrian = new int[width][height];
		altitude = new int[width][height];
		structure = new int[width][height];

        for (int i = 0; i < width; i ++) {
        	for (int j = 0; j < height; j ++)
        	{
        		terrian[i][j] = reader.readInt();
        	}
        }
        for (int i = 0; i < width; i ++) {
        	for (int j = 0; j < height; j ++)
        	{
        		structure[i][j] = reader.readInt();
        	}
        }
        for (int i = 0; i < width; i ++) {
        	for (int j = 0; j < height; j ++)
        	{
        		altitude[i][j] = reader.readInt();
        	}
        }
	}

	
	public void writeToText(OutputStream output) throws IOException {

		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(output));

		writer.write(width + " " + height);
		writer.newLine();

		for (int i = 0; i < width; i ++) {
        	for (int j = 0; j < height; j ++) {
        		writer.write(terrian[i][j] + " ");
        	}
        	writer.newLine();
        }
    	writer.newLine();
		for (int i = 0; i < width; i ++) {
        	for (int j = 0; j < height; j ++) {
        		writer.write(structure[i][j] + " ");
        	}
        	writer.newLine();
        }
    	writer.newLine();
		for (int i = 0; i < width; i ++) {
        	for (int j = 0; j < height; j ++) {
        		writer.write(altitude[i][j] + " ");
        	}
        	writer.newLine();
        }
		writer.flush();
		
	}
	
	@Override
	public void write(OutputStream output) throws IOException {

		DataOutputStream writer = new DataOutputStream(output);

		writer.writeChars(name);
		writer.writeChar('\n');
		writer.writeInt(width);
		writer.writeInt(height);

		for (int i = 0; i < width; i ++) {
        	for (int j = 0; j < height; j ++) {
        		writer.writeInt(terrian[i][j]);
        	}
        }
		for (int i = 0; i < width; i ++) {
        	for (int j = 0; j < height; j ++) {
        		writer.writeInt(structure[i][j]);
        	}
        }
		for (int i = 0; i < width; i ++) {
        	for (int j = 0; j < height; j ++) {
        		writer.writeInt(altitude[i][j]);
        	}
        }
		//writer.flush();
	}


	public boolean inBorder(Location l) {
		return (l.x < width) && (l.y < height) && (l.x >= 0) && (l.y >= 0);
	}

	public int getStructure(Location l) {
		return structure[l.x][l.y];
	}

	public int getStructure(int x, int y) {
		return structure[x][y];
	}

	/*public void setStructure(Location l, int d) {
		this.structure[l.x][l.y] = d;
	}*/

	public int getTerrian(Location l) {
		return terrian[l.x][l.y];
	}

	public int getTerrian(int x, int y) {
		return terrian[x][y];
	}

	/*public void setTerrian(Location l, int d) {
		this.terrian[l.x][l.y] = d;
	}*/


	public int getAltitude(Location l) {
		return altitude[l.x][l.y];
	}
	

	public int getAltitude(int x, int y) {
		return altitude[x][y];
	}



	/*public void setAltitude(Location l, int a) {
		this.altitude[l.x][l.y] = a;
	}*/


	public int getWidth() {
		return width;
	}


	public void setWidth(int width) {
		this.width = width;
	}


	public int getHeight() {
		return height;
	}


	public void setHeight(int height) {
		this.height = height;
	}


	public float getTileWidth() {
		return tileWidth;
	}


	public void setTileWidth(float tileWidth) {
		this.tileWidth = tileWidth;
	}


	public float getTileHeight() {
		return tileHeight;
	}


	public void setTileHeight(float tileHeight) {
		this.tileHeight = tileHeight;
	}
	
	public float getTileAltitude() {
		return tileAltitude;
	}

	public void setTileAltitude(float tileAltitude) {
		this.tileAltitude = tileAltitude;
	}

	public int getGroundStep(Location l) {
		return tileSet.getGroundStep(terrian[l.x][l.y]);//, structure[l.x][l.y]);
	}

	public boolean isGroundAvaliable(Location l) {
		return tileSet.isAvaliableGround(terrian[l.x][l.y]);//, structure[l.x][l.y]);
	}
	
	public TileSet getTileSet() {
		return tileSet;
	}
}
