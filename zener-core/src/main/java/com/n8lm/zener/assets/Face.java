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

package com.n8lm.zener.assets;

public class Face {

    private final int[] vertexIndices = {-1, -1, -1};
    private final int[] normalIndices = {-1, -1, -1};
    private final int[] textureCoordinateIndices = {-1, -1, -1};
    /*
    private Material material;

    public Material getMaterial() {
        return material;
    }*/

    public boolean hasNormals() {
        return normalIndices[0] != -1;
    }

    public boolean hasTextureCoordinates() {
        return textureCoordinateIndices[0] != -1;
    }

    public int[] getVertexIndices() {
        return vertexIndices;
    }

    public int[] getTextureCoordinateIndices() {
        return textureCoordinateIndices;
    }

    public int[] getNormalIndices() {
        return normalIndices;
    }

    public Face(int[] vertexIndices) {
        this.vertexIndices[0] = vertexIndices[0];
        this.vertexIndices[1] = vertexIndices[1];
        this.vertexIndices[2] = vertexIndices[2];
    }

    public Face(int[] vertexIndices, int[] normalIndices) {
        this.vertexIndices[0] = vertexIndices[0];
        this.vertexIndices[1] = vertexIndices[1];
        this.vertexIndices[2] = vertexIndices[2];
        this.normalIndices[0] = normalIndices[0];
        this.normalIndices[1] = normalIndices[1];
        this.normalIndices[2] = normalIndices[2];
    }

    public Face(int[] vertexIndices, int[] normalIndices, int[] textureCoordinateIndices/*, Material material*/) {
        this.vertexIndices[0] = vertexIndices[0];
        this.vertexIndices[1] = vertexIndices[1];
        this.vertexIndices[2] = vertexIndices[2];
        this.textureCoordinateIndices[0] = textureCoordinateIndices[0];
        this.textureCoordinateIndices[1] = textureCoordinateIndices[1];
        this.textureCoordinateIndices[2] = textureCoordinateIndices[2];
        this.normalIndices[0] = normalIndices[0];
        this.normalIndices[1] = normalIndices[1];
        this.normalIndices[2] = normalIndices[2];
        //this.material = material;
    }
}