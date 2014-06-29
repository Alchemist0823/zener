package com.n8lm.zener.assets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Logger;

import com.n8lm.zener.data.ResourceManager;

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
        String materialLine;
        
        while ((materialLine = materialFileReader.readLine()) != null) {
            if (materialLine.startsWith("#")) {
                continue;
            }
            if (materialLine.startsWith("newmtl ")) {
                //parseMaterialName = materialLine.split(" ")[1];
                //parseMaterial = new Material();
            } else if (materialLine.startsWith("Ns ")) {
                parseMaterial.specularCoefficient = Float.valueOf(materialLine.split(" +")[1]);
            } else if (materialLine.startsWith("Ka ")) {
                String[] rgb = materialLine.split(" +");
                parseMaterial.ambientColor.x = Float.valueOf(rgb[1]);
                parseMaterial.ambientColor.y = Float.valueOf(rgb[2]);
                parseMaterial.ambientColor.z = Float.valueOf(rgb[3]);
            } else if (materialLine.startsWith("Ks ")) {
                String[] rgb = materialLine.split(" +");
                parseMaterial.specularColor.x = Float.valueOf(rgb[1]);
                parseMaterial.specularColor.y = Float.valueOf(rgb[2]);
                parseMaterial.specularColor.z = Float.valueOf(rgb[3]);
            } else if (materialLine.startsWith("Kd ")) {
                String[] rgb = materialLine.split(" +");
                parseMaterial.diffuseColor.x = Float.valueOf(rgb[1]);
                parseMaterial.diffuseColor.y = Float.valueOf(rgb[2]);
                parseMaterial.diffuseColor.z = Float.valueOf(rgb[3]);
            } else if (materialLine.startsWith("map_Kd")) {

            	String textureName = materialLine.split(" +")[1];
    			ResourceManager.getInstance().loadImage(textureName, textureName);
                parseMaterial.diffuseTexture = ResourceManager.getInstance().getTexture(textureName);
                		//TextureLoader.getTexture("PNG",
                        /*new FileInputStream(new File(f.getParentFile().getAbsolutePath() + "/" + materialLine
                                .split(" ")[1])));*/
                		//ResourceLoader.getResourceAsStream(parseMaterial.diffuseTextureName));
            } else if (materialLine.startsWith("map_bump")) {
            	
            	ResourceManager.getInstance().loadImage(materialLine.split(" +")[1], materialLine.split(" +")[1]);
                parseMaterial.normalTexture = ResourceManager.getInstance().getTexture(materialLine.split(" +")[1]);
            
            } else {
            	LOGGER.warning("[MTL] Unknown Line: " + materialLine);
            }
        }
        materialFileReader.close();
    	
    }
}
