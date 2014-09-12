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
import com.n8lm.zener.math.Vector3f;

public class NormalMaterial extends UniformsMaterial {

    public NormalMaterial(Material material) {
        addUniform("Material.Ka", VarType.Vector3f, new Vector3f(material.ambientColor));
        addUniform("Material.Kd", VarType.Vector3f, new Vector3f(material.diffuseColor));
        addUniform("Material.Ks", VarType.Vector3f, new Vector3f(material.specularColor));
        addUniform("Material.Shininess", VarType.Float, material.specularCoefficient);
        if (material.diffuseTexture != null)
            addUniform("Material.DiffuseMap", VarType.Texture2D, material.diffuseTexture);
        if (material.normalTexture != null)
            addUniform("Material.NormalMap", VarType.Texture2D, material.normalTexture);
        //diffuseTextureName = material.diffuseTextureName;
    }

    @Override
    public String getShaderName() {
        if (uniforms.containsKey("Material.DiffuseMap"))
            return "standard";
        else
            return "standard_color";
    }

    public void setAmbientColor(float x, float y, float z) {
        setVector3f("Material.Ka", x, y, z);
    }

    public void setDiffuseColor(float x, float y, float z) {
        setVector3f("Material.Kd", x, y, z);
    }

    public void setSpecularColor(float x, float y, float z) {
        setVector3f("Material.Ks", x, y, z);
    }

    public void setSpecularCoefficient(float x) {
        setf("Material.Shininess", x);
    }

    public void setDiffuseTexture(Texture texture) {
        uniforms.get("Material.DiffuseMap").setValue(texture);
    }

    public void setNormalTexture(Texture texture) {
        uniforms.get("Material.NormalMap").setValue(texture);
    }
}