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

package com.n8lm.zener.glsl;

public enum VarType {

    Float,
    Vector2f,
    Vector3f,
    Vector4f,

    FloatArray(true,false),
    Vector2Array(true,false),
    Vector3Array(true,false),
    Vector4Array(true,false),

    Boolean,
    
    Int,
    Vector2i,
    Vector3i,
    Vector4i,

    Matrix3(true,false),
    Matrix4(true,false),

    Matrix3Array(true,false),
    Matrix4Array(true,false),

    TextureBuffer(false,true),
    Texture2D(false,true),
    Texture3D(false,true),
    TextureArray(false,true),
    TextureCubeMap(false,true);

    private boolean usesMultiData = false;
    private boolean textureType = false;

    VarType(){
    }

    VarType(boolean multiData, boolean textureType){
        usesMultiData = multiData;
        this.textureType = textureType;
    }

    public boolean isTextureType() {
        return textureType;
    }

    public boolean usesMultiData() {
        return usesMultiData;
    }
}
