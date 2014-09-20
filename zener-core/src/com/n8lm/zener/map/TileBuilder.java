package com.n8lm.zener.map;

/**
 * Created on 2014/9/20.
 *
 * @author Alchemist
 */
public interface TileBuilder<T extends Tile> {
    public T build(String line);
}
