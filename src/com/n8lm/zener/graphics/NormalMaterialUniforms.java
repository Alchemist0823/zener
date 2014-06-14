package com.n8lm.zener.graphics;

import com.n8lm.zener.assets.Material;
import com.n8lm.zener.math.Vector3f;

public class NormalMaterialUniforms extends UniformGroup{

    
    public String diffuseTextureName;
	
	/*public NormalMaterialUniforms() {
		super();
    	uniforms.put("Material.Ka", new UniformVariable("Material.Ka", VarType.Vector3f, new Vector3f(0.2f, 0.2f, 0.2f)));
    	uniforms.put("Material.Kd", new UniformVariable("Material.Kd", VarType.Vector3f, new Vector3f(0.5f, 0.5f, 0.5f)));
    	uniforms.put("Material.Ks", new UniformVariable("Material.Ks", VarType.Vector3f, new Vector3f(1.0f, 1.0f, 1.0f)));
    	uniforms.put("Material.Shininess", new UniformVariable("Material.Shininess", VarType.Float, 100));
    	uniforms.put("Material.DiffuseMap", new UniformVariable("Material.DiffuseMap", VarType.Texture2D));
		uniforms.put("Material.NormalMap", new UniformVariable("Material.NormalMap", VarType.Texture2D));
	}*/
	
	public NormalMaterialUniforms(Material material) {
		super();
		addUniform("Material.Ka", VarType.Vector3f, new Vector3f(material.ambientColor));
		addUniform("Material.Kd", VarType.Vector3f, new Vector3f(material.diffuseColor));
		addUniform("Material.Ks", VarType.Vector3f, new Vector3f(material.specularColor));
		addUniform("Material.Shininess", VarType.Float, material.specularCoefficient);
		addUniform("Material.DiffuseMap", VarType.Texture2D, material.diffuseTexture);
		if (material.normalTexture != null)
			addUniform("Material.NormalMap", VarType.Texture2D, material.normalTexture);
    	diffuseTextureName = material.diffuseTextureName;
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