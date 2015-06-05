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

import com.n8lm.zener.data.ResourceManager;
import com.n8lm.zener.graphics.material.BlendMode;
import com.n8lm.zener.utils.StringUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Logger;

public class MTLLoader {
	
	public static final Logger LOGGER = Logger.getLogger(MTLLoader.class.getName());
	
    public static void loadMaterialFile(InputStream input, Material parseMaterial) throws NumberFormatException, IOException {
        // String materialFileName = line.split(" ")[1];
        // File materialFile = new File(f.getParentFile().getAbsolutePath() + "/" + materialFileName);
        /* if(materialFileName.startsWith("./"))
        	materialFileName = materialFileName.substring(2);*/
        
        // ResourceManager.getInstance().getResourceAsStream(materialFileName)
        // Material parseMaterial = mdl.getMaterial();
        // String parseMaterialName = "";
    	
        BufferedReader materialFileReader = new BufferedReader(
        		new InputStreamReader(input));
        String line;
        String strs[] = new String[20];

        boolean hasBlendMode = false;

        while ((line = materialFileReader.readLine()) != null) {
            if (line.startsWith("#")) {
                continue;
            }
            StringUtil.splitOnWhitespace(line, strs);


            if (line.startsWith("newmtl ")) {
                //parseMaterialName = materialLine.split(" ")[1];
                //parseMaterial = new Material();
            } else if (line.startsWith("Bm ")) {
                try {
                    parseMaterial.blendMode = BlendMode.valueOf(strs[1]);
                    hasBlendMode = true;
                } catch (IllegalArgumentException e) {
                    LOGGER.warning("[MTL] Unknown BlendMode: " + line);
                }
            } else if (line.startsWith("Ns ")) {
                parseMaterial.specularCoefficient = Float.valueOf(strs[1]);
            } else if (line.startsWith("Ka ")) {
                parseMaterial.ambientColor.x = Float.valueOf(strs[1]);
                parseMaterial.ambientColor.y = Float.valueOf(strs[2]);
                parseMaterial.ambientColor.z = Float.valueOf(strs[3]);
            } else if (line.startsWith("Ks ")) {
                parseMaterial.specularColor.x = Float.valueOf(strs[1]);
                parseMaterial.specularColor.y = Float.valueOf(strs[2]);
                parseMaterial.specularColor.z = Float.valueOf(strs[3]);
            } else if (line.startsWith("Kd ")) {
                parseMaterial.diffuseColor.x = Float.valueOf(strs[1]);
                parseMaterial.diffuseColor.y = Float.valueOf(strs[2]);
                parseMaterial.diffuseColor.z = Float.valueOf(strs[3]);
            } else if (line.startsWith("map_Kd ")) {

            	String textureName = strs[1];
    			ResourceManager.getInstance().loadImage(textureName, textureName);
                parseMaterial.diffuseTexture = ResourceManager.getInstance().getTexture(textureName);
                		//TextureLoader.getTexture("PNG",
                        /*new FileInputStream(new File(f.getParentFile().getAbsolutePath() + "/" + materialLine
                                .split(" ")[1])));*/
                		//ResourceLoader.getResourceAsStream(parseMaterial.diffuseTextureName));
            } else if (line.startsWith("map_bump ")) {

                ResourceManager.getInstance().loadImage(strs[1], strs[1]);
                parseMaterial.normalTexture = ResourceManager.getInstance().getTexture(strs[1]);
            
            } else {
            	LOGGER.warning("[MTL] Unknown Line: " + line);
            }
        }

        if (!hasBlendMode)
            LOGGER.warning("[MTL] no blend mode ");

        materialFileReader.close();
    	
    }
}
