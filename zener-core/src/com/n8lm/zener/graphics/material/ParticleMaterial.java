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

package com.n8lm.zener.graphics.material;

import com.n8lm.zener.assets.Material;
import com.n8lm.zener.glsl.VarType;
import com.n8lm.zener.graphics.Texture;

/**
 * Created by Alchemist on 2014/6/28.
 */
public class ParticleMaterial extends UniformsMaterial {

    public ParticleMaterial(Material material) {
        addUniform("Material.DiffuseMap", VarType.Texture2D, material.diffuseTexture);
        //diffuseTextureName = material.diffuseTextureName;
    }

    @Override
    public String getShaderName() {
        return "particle";
    }

    public void setDiffuseTexture(Texture texture) {
        uniforms.get("Material.DiffuseMap").setValue(texture);
    }
}
