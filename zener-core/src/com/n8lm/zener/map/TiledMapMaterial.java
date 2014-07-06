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

import com.n8lm.zener.data.ResourceManager;
import com.n8lm.zener.graphics.material.UniformsMaterial;
import com.n8lm.zener.glsl.VarType;

public class TiledMapMaterial extends UniformsMaterial {
	
	public TiledMapMaterial(TileSet ts) {
		for (int i = 0; i < ts.getTileCount(); i ++) {
			addUniform("TileSet.texture[" + i + "]", VarType.Texture2D, ResourceManager.getInstance().getTexture(ts.getTextureName(i)));
		}
	}


    @Override
    public String getShaderName() {
        return "tiledmap";
    }
}
