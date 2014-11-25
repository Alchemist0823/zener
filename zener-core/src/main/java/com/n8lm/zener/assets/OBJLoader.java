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

import com.n8lm.zener.math.Vector2f;
import com.n8lm.zener.math.Vector3f;
import com.n8lm.zener.data.ResourceManager;
import com.n8lm.zener.utils.StringUtil;

import java.io.*;
import java.util.logging.Logger;

public class OBJLoader {

	private final static Logger LOGGER = Logger.getLogger(OBJLoader.class
		      .getName());

	

    private static Vector3f parseVertex(String line) {
        String[] strs = new String[10];
        StringUtil.splitOnWhitespace(line, strs);
        float x = Float.valueOf(strs[1]);
        float y = Float.valueOf(strs[2]);
        float z = Float.valueOf(strs[3]);
        return new Vector3f(x, y, z);
    }

    private static Vector3f parseNormal(String line) {
        String[] strs = new String[10];
        StringUtil.splitOnWhitespace(line, strs);
        float x = Float.valueOf(strs[1]);
        float y = Float.valueOf(strs[2]);
        float z = Float.valueOf(strs[3]);
        return new Vector3f(x, y, z);
    }

    private static Face parseFace(boolean hasNormals, String line) {
        String[] strs = new String[10];
        StringUtil.splitOnWhitespace(line, strs);
        int[] vertexIndicesArray = {Integer.parseInt(strs[1].split("/")[0]),
                Integer.parseInt(strs[2].split("/")[0]), Integer.parseInt(strs[3].split("/")[0])};
        if (hasNormals) {
            int[] normalIndicesArray = new int[3];
            normalIndicesArray[0] = Integer.parseInt(strs[1].split("/")[2]);
            normalIndicesArray[1] = Integer.parseInt(strs[2].split("/")[2]);
            normalIndicesArray[2] = Integer.parseInt(strs[3].split("/")[2]);
            return new Face(vertexIndicesArray, normalIndicesArray);
        } else {
            return new Face((vertexIndicesArray));
        }
    }

    public static Mesh loadModel(InputStream input) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(input));
        Mesh m = new Mesh();
        String line;
        String strs[] = new String[20];
        while ((line = reader.readLine()) != null) {
            String prefix = strs[0];
            if (prefix.equals("#")) {
                continue;
            } else if (prefix.equals("v")) {
                m.vertices.add(parseVertex(line));
            } else if (prefix.equals("vn")) {
                m.normals.add(parseNormal(line));
            } else if (prefix.equals("f")) {
                m.faces.add(parseFace(m.hasNormals(), line));
            } else {
                reader.close(); //mine
                throw new RuntimeException("OBJ file contains line which cannot be parsed correctly: " + line);
            }
        }
        reader.close();
        LOGGER.info("model load successfully [vertex:" + m.vertices.size() + "], [normals:" + m.normals.size() + "], [faces:" + m.faces.size() + "]");
        return m;
    }
    

    public static Model loadTexturedModel(InputStream input) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(input));
        
        Model mdl = new Model();
        Mesh mesh = mdl.getMesh();
        //Material currentMaterial = new Material();
        String line;
        String strs[] = new String[20];
        while ((line = reader.readLine()) != null) {
            StringUtil.splitOnWhitespace(line, strs);
            if (line.startsWith("#")) {
                continue;
            }
            if (line.startsWith("mtllib ")) {
                String materialFileName = strs[1];
                //File materialFile = new File(f.getParentFile().getAbsolutePath() + "/" + materialFileName);
                /*if(materialFileName.startsWith("./"))
                	materialFileName = materialFileName.substring(2);*/
                
                MTLLoader.loadMaterialFile(ResourceManager.getInstance().getResourceAsStream(materialFileName), mdl.getMaterial());
                
            } else if (line.startsWith("usemtl ")) {
                //currentMaterial = m.getMaterials().get(line.split(" +")[1]);
            } else if (line.startsWith("v ")) {
                float x = Float.valueOf(strs[1]);
                float y = Float.valueOf(strs[2]);
                float z = Float.valueOf(strs[3]);
                mesh.vertices.add(new Vector3f(x, y, z));
            } else if (line.startsWith("vn ")) {
                float x = Float.valueOf(strs[1]);
                float y = Float.valueOf(strs[2]);
                float z = Float.valueOf(strs[3]);
                mesh.normals.add(new Vector3f(x, y, z));
            } else if (line.startsWith("vt ")) {
                float s = Float.valueOf(strs[1]);
                float t = 1.0f - Float.valueOf(strs[2]);
                
                // Texture
                
                mesh.textureCoordinates.add(new Vector2f(s, t));
            } else if (line.startsWith("f ")) {
                int[] vertexIndicesArray = {Integer.parseInt(strs[1].split("/")[0]),
                        Integer.parseInt(strs[2].split("/")[0]), Integer.parseInt(strs[3].split("/")[0])};
                int[] textureCoordinateIndicesArray = {-1, -1, -1};
                if (mesh.hasTextureCoordinates()) {
                    textureCoordinateIndicesArray[0] = Integer.parseInt(strs[1].split("/")[1]);
                    textureCoordinateIndicesArray[1] = Integer.parseInt(strs[2].split("/")[1]);
                    textureCoordinateIndicesArray[2] = Integer.parseInt(strs[3].split("/")[1]);
                }
                int[] normalIndicesArray = {0, 0, 0};
                if (mesh.hasNormals()) {
                    normalIndicesArray[0] = Integer.parseInt(strs[1].split("/")[2]);
                    normalIndicesArray[1] = Integer.parseInt(strs[2].split("/")[2]);
                    normalIndicesArray[2] = Integer.parseInt(strs[3].split("/")[2]);
                }
                /*if(m.getMaterials().get("Material").diffuseTextureName.equals("LordaeronSummerTree.png"))
                	System.err.println(vertexIndicesArray[0] + " " + textureCoordinateIndicesArray[0]);*/
                mesh.faces.add(new Face(vertexIndicesArray, normalIndicesArray,
                        textureCoordinateIndicesArray/*, currentMaterial*/));
            } else if (line.startsWith("s ")) {
                //boolean enableSmoothShading = !line.contains("off");
                //m.setSmoothShadingEnabled(enableSmoothShading);
            } else {
                //System.err.println("[OBJ] Unknown Line: " + line);
            }
        }
        reader.close();
        LOGGER.info("model load successfully [vertex:" + mesh.vertices.size() + "], [textureCoord: " + mesh.textureCoordinates.size() + "], [normals:" + mesh.normals.size() + "], [faces:" + mesh.faces.size() + "]");
        return mdl;
    }
}