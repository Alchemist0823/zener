package com.n8lm.zener.map;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * Created on 2014/9/1.
 *
 * @author Alchemist
 */
public class TileMapLoadingHelper {

    public static TileSet<Tile, Structure> readTileSetFromText(BufferedReader reader) throws IOException {
        TileSet<Tile, Structure> tileSet = new TileSet<Tile, Structure>();
        String line;
        while (!(line = reader.readLine()).startsWith("-")) {
            String str[] = line.split(" ");
            int id = Integer.parseInt(str[0]);
            tileSet.add(id, new Tile(id, str[1]));
        }

        while (!(line = reader.readLine()).startsWith("-")) {
            String str[] = line.split(" ");
            int id = Integer.parseInt(str[0]);
            tileSet.add(id, new Structure(id, str[1]));
        }
        return tileSet;
    }
}
