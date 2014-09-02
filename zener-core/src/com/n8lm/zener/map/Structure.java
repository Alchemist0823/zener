package com.n8lm.zener.map;

import java.io.BufferedWriter;
import java.io.IOException;

/**
 * Created on 2014/8/25.
 *
 * @author Alchemist
 */
public class Structure {

    protected int id;
    protected String structureName;

    public Structure(int id, String structureName) {
        this.id = id;
        this.structureName = structureName;
    }

    public int getId() {
        return id;
    }

    public String getStructureName() {
        return structureName;
    }

    public void write(BufferedWriter writer) throws IOException {
        writer.write(structureName + " ");
    }
}
