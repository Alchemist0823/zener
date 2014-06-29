package com.n8lm.zener.graphics.material;

import com.n8lm.zener.assets.Material;
import com.n8lm.zener.glsl.VarType;
import com.n8lm.zener.graphics.Texture;

/**
 * Created by Alchemist on 2014/6/28.
 */
public class ParticleMaterial extends UniformsMaterial{

    public ParticleMaterial(Material material) {
        super("particle");
        addUniform("Material.DiffuseMap", VarType.Texture2D, material.diffuseTexture);
        //diffuseTextureName = material.diffuseTextureName;
    }

    public void setDiffuseTexture(Texture texture) {
        uniforms.get("Material.DiffuseMap").setValue(texture);
    }
}
