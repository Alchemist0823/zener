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

package com.n8lm.zener.assets;

import com.n8lm.zener.graphics.Texture;
import com.n8lm.zener.math.Vector3f;

public class Material implements Cloneable{

    /** Between 0 and 1000. */
    public float specularCoefficient = 100;
    public Vector3f ambientColor = new Vector3f(0.2f, 0.2f, 0.2f);
    public Vector3f diffuseColor = new Vector3f(0.8f, 0.8f, 0.8f);
    public Vector3f specularColor = new Vector3f(1.0f, 1.0f, 1.0f);
    public Texture diffuseTexture;
	public Texture normalTexture;
    
	Material () {
		diffuseTexture = normalTexture = null;
	}
	
    @Override
    public String toString() {
        String str = "Material{" +
                "specularCoefficient=" + specularCoefficient +
                ", ambientColour=" + ambientColor +
                ", diffuseColour=" + diffuseColor +
                ", specularColour=" + specularColor;
        //+ ", diffuseTexture=" + diffuseTextureName;
        
        if (normalTexture != null)
        	str += ", normalTexture=" + normalTexture.getName() +
                '}';
        return str;
    }
    
    @Override
    public Material clone() {
    	Material mat = new Material();
    	
    	mat.specularCoefficient = this.specularCoefficient;
    	mat.diffuseColor = new Vector3f(diffuseColor);
    	mat.ambientColor = new Vector3f(ambientColor);
    	mat.specularColor = new Vector3f(specularColor);
    	//mat.diffuseTextureName = this.diffuseTextureName;
    	mat.diffuseTexture = this.diffuseTexture;
    	mat.normalTexture = this.normalTexture;
    	
    	return mat;
    }
}