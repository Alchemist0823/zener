package com.n8lm.zener.map;

import java.io.BufferedWriter;
import java.io.IOException;

/**
 * Created on 2014/8/25.
 *
 * @author Alchemist
 */
public class Tile {

    protected int id;
    protected String textureName;

    protected Tile(int id, String textureName) {
        this.id = id;
        this.textureName = textureName;
    }

    public static class Builder implements TileBuilder<Tile>{
        @Override
        public Tile build(String line) {
            String str[] = line.split(" ");
            return new Tile(Integer.parseInt(str[0]), str[1]);
        }
    }

    public int getId() {
        return id;
    }

    public String getTextureName() {
        return textureName;
    };

    public void write(BufferedWriter writer) throws IOException{
        writer.write(textureName + " ");
    }

    @Override
    public String toString() {
        return textureName;
    }

}
