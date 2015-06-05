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

package com.n8lm.zener.particle;

import com.n8lm.zener.assets.Material;
import com.n8lm.zener.glsl.VarType;
import com.n8lm.zener.graphics.Texture;
import com.n8lm.zener.graphics.material.UniformsMaterial;

/**
 * The Material for Particle System Geometry. Except the diffuse map,
 * It also includes a Uniform called AtlasRowCount which defines the
 * number of subImages per row of an atlas.
 *
 * Created on 2014/6/28.
 * @author Forrest Sun
 */
public class ParticleMaterial extends UniformsMaterial {

    /**
     * @param material the diffuse atlas texture material
     * @param atlasCount the texture count in the atlas
     */
    public ParticleMaterial(Material material, int atlasCount) {
        addUniform("Material_DiffuseMap", VarType.Texture2D, material.diffuseTexture);
        addUniform("AtlasRowCount", VarType.Int, ((int) Math.sqrt(atlasCount)));
        blendMode = material.blendMode;
    }

    @Override
    public String getShaderName() {
        return "particle";
    }

    public void setDiffuseTexture(Texture texture) {
        uniforms.get("Material_DiffuseMap").setValue(texture);
    }
}
