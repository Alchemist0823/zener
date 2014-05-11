package com.n8lm.zener.assets;

import com.n8lm.zener.graphics.Texture;
import com.n8lm.zener.math.Vector3f;

public class Material implements Cloneable{

    /** Between 0 and 1000. */
    public float specularCoefficient = 100;
    public Vector3f ambientColor = new Vector3f(0.2f, 0.2f, 0.2f);
    public Vector3f diffuseColor = new Vector3f(1.0f, 1.0f, 1.0f);
    public Vector3f specularColor = new Vector3f(1.0f, 1.0f, 1.0f);
    public String diffuseTextureName;
    public Texture diffuseTexture;
    
	Material () {
		
	}
	
    @Override
    public String toString() {
        return "Material{" +
                "specularCoefficient=" + specularCoefficient +
                ", ambientColour=" + ambientColor +
                ", diffuseColour=" + diffuseColor +
                ", specularColour=" + specularColor +
                ", diffuseTexture=" + diffuseTextureName +
                '}';
    }
    
    @Override
    public Material clone() {
    	Material mat = new Material();
    	
    	mat.specularCoefficient = this.specularCoefficient;
    	mat.diffuseColor = new Vector3f(diffuseColor);
    	mat.ambientColor = new Vector3f(ambientColor);
    	mat.specularColor = new Vector3f(specularColor);
    	mat.diffuseTextureName = this.diffuseTextureName;
    	mat.diffuseTexture = this.diffuseTexture;
    	
    	return mat;
    }
}