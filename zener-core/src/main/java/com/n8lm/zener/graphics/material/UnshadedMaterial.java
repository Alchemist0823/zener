package com.n8lm.zener.graphics.material;

import com.n8lm.zener.assets.Material;
import com.n8lm.zener.glsl.VarType;
import com.n8lm.zener.graphics.Texture;
import com.n8lm.zener.math.Vector3f;

/**
 * Created on 2014/7/7.
 *
 * @author Alchemist
 */
public class UnshadedMaterial extends UniformsMaterial {

    public UnshadedMaterial(Texture diffuseTexture) {
        addUniform("Material_DiffuseMap", VarType.Texture2D, diffuseTexture);
    }

    public UnshadedMaterial(Material material) {
        addUniform("Material.Kd", VarType.Vector3f, new Vector3f(material.diffuseColor));
        if (material.diffuseTexture != null)
            addUniform("Material_DiffuseMap", VarType.Texture2D, material.diffuseTexture);
        if (material.normalTexture != null)
            addUniform("Material_NormalMap", VarType.Texture2D, material.normalTexture);
    }

    @Override
    public String getShaderName() {
        if (uniforms.containsKey("Material_DiffuseMap"))
            return "unshaded";
        else
            return "unshaded_color";
    }

    public void setColor(float x, float y, float z) {
        setVector3f("Material.Kd", x, y, z);
    }

    public void setColorTexture(Texture texture) {
        uniforms.get("Material_DiffuseMap").setValue(texture);
    }

    public void setNormalTexture(Texture texture) {
        uniforms.get("Material_NormalMap").setValue(texture);
    }
}
