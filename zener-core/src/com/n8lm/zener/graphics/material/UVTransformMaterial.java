package com.n8lm.zener.graphics.material;

import com.n8lm.zener.assets.Material;
import com.n8lm.zener.glsl.VarType;
import com.n8lm.zener.graphics.Texture;
import com.n8lm.zener.math.Transform;
import com.n8lm.zener.math.Vector2f;

/**
 * Created on 2014/9/8.
 *
 * @author Alchemist
 */
public class UVTransformMaterial extends NormalMaterial {



    public UVTransformMaterial(Material material, Vector2f uvTransform) {
        super(material);
        material.diffuseTexture.setWrapMode(Texture.WrapMode.Repeat);
        addUniform("UVTransform", VarType.Vector2f, uvTransform);
    }

    @Override
    public String getShaderName() {
        return "standard_uv";
    }

    public void setUVTransform(Vector2f uvTransform) {
        setVector2f("UVTransform", uvTransform.x, uvTransform.y);
    }
}
