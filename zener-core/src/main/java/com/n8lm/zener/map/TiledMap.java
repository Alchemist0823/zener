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

import com.n8lm.zener.utils.StringUtil;

import java.io.*;

/**
 * TiledMap is a class which store the data of a tiled map.
 *
 * @author Forrest Sun
 */
public class TiledMap implements Cloneable{

	protected final String name;
	/** The width of the map */
	protected final int width;
	/** The height of the map */
	protected final int height;
	/** The width of the tiles used on the map */
	protected float tileWidth;
	/** The height of the tiles used on the map */
	protected float tileHeight;
	/** The altitude of the tiles used on the map */
	protected float tileAltitude;

	/** The location prefix where we can find tileset images */
	protected String tilesLocation;

    /** the id of the tile*/
    protected final int terrian[][];

    /** the special texture of the tile*/
    protected final int texture[][];
	
	/** the altitude of the tile*/
	protected final int altitude[][];

	/** the properties of the map */
	//protected Properties props;
	
	/** the tile set of the map*/
	protected final TileSet tileSet;
	
	public TiledMap(String name, int width, int height, TileSet<?> tileSet) {

        this.name = name;
        this.width = width;
        this.height = height;

        this.terrian = new int[width][height];
        this.texture = new int[width][height];
        this.altitude = new int[width][height];

        this.tileSet = tileSet;

		tileWidth = tileHeight = 2;
		tileAltitude = 1f;

	}
	/**
	 * TODO: need revision
	 */

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
    /*
	public TiledMap clone() {
        try {
            TiledMap tilemap =  (TiledMap) super.clone();
            //tilemap.props = (Properties) props.clone();
            return tilemap;
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }*/

    public static TiledMap readFromText(BufferedReader reader) throws IOException {

        TileSet<Tile> tileSet = TileSet.readFromText(new Tile.Builder(), reader);

        String name = reader.readLine();
        String[] strs = StringUtil.split(reader.readLine(), ' ');
        TiledMap map = new TiledMap(name, Integer.parseInt(strs[0]), Integer.parseInt(strs[1]), tileSet);

        readContentFromText(reader, map);

        return map;
    }

    protected static void readContentFromText(BufferedReader reader, TiledMap map) throws IOException {
        int width = map.width;
        int height = map.height;
        String[] strs = new String[height + 10];

        reader.readLine();
        for (int i = 0; i < width; i++) {
            StringUtil.splitOnWhitespace(reader.readLine(), strs);
            for (int j = 0; j < height; j++) {
                map.terrian[i][j] = Integer.parseInt(strs[j]);
            }
        }

        reader.readLine();
        for (int i = 0; i < width; i++) {
            StringUtil.splitOnWhitespace(reader.readLine(), strs);
            for (int j = 0; j < height; j++) {
                map.texture[i][j] = Integer.parseInt(strs[j]);
            }
        }

        reader.readLine();
        for (int i = 0; i < width; i++) {
            StringUtil.splitOnWhitespace(reader.readLine(), strs);
            for (int j = 0; j < height; j++) {
                map.altitude[i][j] = Integer.parseInt(strs[j]);
            }
        }
    }

    /**
     * read map without tile sets
     * @throws IOException
     */
    /*
	@Override
	public void read(InputStream input) throws IOException {
        
		//DataInputStream dataInputStream
		DataInputStream reader = new DataInputStream(input);
		
		char ch;
		String str = "";
		while ((ch = reader.readChar()) != '\n')
			str += ch;
		name = str;
		
		//tileSet = ResourceManager.getInstance().getDatabase(TiledMapDatabase.class).getTiledMap(name).getTileSet();
		
        width = reader.readInt();
        height = reader.readInt();
        
		terrian = new int[width][height];
        texture = new int[width][height];
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
                texture[i][j] = reader.readInt();
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
	}*/

	
	public void writeToText(BufferedWriter writer) throws IOException {

        tileSet.writeToText(writer);

        writer.write(name);
        writer.newLine();
		writer.write(width + " " + height);
		writer.newLine();
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
                writer.write(texture[i][j] + " ");
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
        /*
        writer.newLine();
        for (int i = 0; i < width; i ++) {
            for (int j = 0; j < height; j ++) {
                writer.write(structure[i][j] + " ");
            }
            writer.newLine();
        }*/
	}

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
                writer.writeInt(texture[i][j]);
            }
        }
        /*
		for (int i = 0; i < width; i ++) {
        	for (int j = 0; j < height; j ++) {
        		writer.writeInt(structure[i][j]);
        	}
        }*/
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

	public int getTerrian(Location l) {
		return terrian[l.x][l.y];
	}

	public int getTerrian(int x, int y) {
		return terrian[x][y];
	}

	public void setTerrian(Location l, int t) {
		this.terrian[l.x][l.y] = t;
	}

    public int getTexture(int x, int y) {
        return texture[x][y];
    }
    private void setTexture(Location l, int t) {
        this.texture[l.x][l.y] = t;
    }

	public int getAltitude(Location l) {
		return altitude[l.x][l.y];
	}

	public int getAltitude(int x, int y) {
		return altitude[x][y];
	}

	public void setAltitude(Location l, int a) {
		this.altitude[l.x][l.y] = a;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
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

    public Tile getTile(Location l) {
        return tileSet.getTile(terrian[l.x][l.y]);
    }

    public Tile getTile(int x, int y) {
        return tileSet.getTile(terrian[x][y]);
    }
	
	public TileSet getTileSet() {
		return tileSet;
	}

    /*public void setTileSet(TileSet tileSet) {
        this.tileSet = tileSet;
    }*/

    public float getPositionAltitude(float x, float y) {
        if (x < 0 || y < 0 || x > (width - 1) * tileWidth || y > (height - 1) * tileHeight)
            return 0;
        int tx = (int) (x / tileWidth);
        int ty = (int) (y / tileHeight);
        float dx = (tx + 1 - x / tileWidth);
        float dy = (ty + 1 - y / tileHeight);
        return (altitude[tx][ty] * dx * dy + altitude[tx + 1][ty] * (1 - dx) * dy + altitude[tx][ty + 1] * dx * (1 - dy) + altitude[tx + 1][ty + 1] * (1 - dx) * (1 - dy)) * tileAltitude;
    }

}
