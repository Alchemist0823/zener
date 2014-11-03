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

import java.io.*;
import java.util.logging.Logger;

public class OBJLoader {

	private final static Logger LOGGER = Logger.getLogger(OBJLoader.class
		      .getName());

	

    private static Vector3f parseVertex(String line) {
        String[] xyz = line.split(" ");
        float x = Float.valueOf(xyz[1]);
        float y = Float.valueOf(xyz[2]);
        float z = Float.valueOf(xyz[3]);
        return new Vector3f(x, y, z);
    }

    private static Vector3f parseNormal(String line) {
        String[] xyz = line.split(" ");
        float x = Float.valueOf(xyz[1]);
        float y = Float.valueOf(xyz[2]);
        float z = Float.valueOf(xyz[3]);
        return new Vector3f(x, y, z);
    }

    private static Face parseFace(boolean hasNormals, String line) {
        String[] faceIndices = line.split(" ");
        int[] vertexIndicesArray = {Integer.parseInt(faceIndices[1].split("/")[0]),
                Integer.parseInt(faceIndices[2].split("/")[0]), Integer.parseInt(faceIndices[3].split("/")[0])};
        if (hasNormals) {
            int[] normalIndicesArray = new int[3];
            normalIndicesArray[0] = Integer.parseInt(faceIndices[1].split("/")[2]);
            normalIndicesArray[1] = Integer.parseInt(faceIndices[2].split("/")[2]);
            normalIndicesArray[2] = Integer.parseInt(faceIndices[3].split("/")[2]);
            return new Face(vertexIndicesArray, normalIndicesArray);
        } else {
            return new Face((vertexIndicesArray));
        }
    }

    public static Mesh loadModel(InputStream input) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(input));
        Mesh m = new Mesh();
        String line;
        while ((line = reader.readLine()) != null) {
            String prefix = line.split(" ")[0];
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
        while ((line = reader.readLine()) != null) {
            if (line.startsWith("#")) {
                continue;
            }
            if (line.startsWith("mtllib ")) {
                String materialFileName = line.split(" ")[1];
                //File materialFile = new File(f.getParentFile().getAbsolutePath() + "/" + materialFileName);
                /*if(materialFileName.startsWith("./"))
                	materialFileName = materialFileName.substring(2);*/
                
                MTLLoader.loadMaterialFile(ResourceManager.getInstance().getResourceAsStream(materialFileName), mdl.getMaterial());
                
            } else if (line.startsWith("usemtl ")) {
                //currentMaterial = m.getMaterials().get(line.split(" +")[1]);
            } else if (line.startsWith("v ")) {
                String[] xyz = line.split(" +");
                float x = Float.valueOf(xyz[1]);
                float y = Float.valueOf(xyz[2]);
                float z = Float.valueOf(xyz[3]);
                mesh.vertices.add(new Vector3f(x, y, z));
            } else if (line.startsWith("vn ")) {
                String[] xyz = line.split(" +");
                float x = Float.valueOf(xyz[1]);
                float y = Float.valueOf(xyz[2]);
                float z = Float.valueOf(xyz[3]);
                mesh.normals.add(new Vector3f(x, y, z));
            } else if (line.startsWith("vt ")) {
                String[] xyz = line.split(" +");
                float s = Float.valueOf(xyz[1]);
                float t = 1.0f - Float.valueOf(xyz[2]);
                
                // Texture
                
                mesh.textureCoordinates.add(new Vector2f(s, t));
            } else if (line.startsWith("f ")) {
                String[] faceIndices = line.split(" +");
                int[] vertexIndicesArray = {Integer.parseInt(faceIndices[1].split("/")[0]),
                        Integer.parseInt(faceIndices[2].split("/")[0]), Integer.parseInt(faceIndices[3].split("/")[0])};
                int[] textureCoordinateIndicesArray = {-1, -1, -1};
                if (mesh.hasTextureCoordinates()) {
                    textureCoordinateIndicesArray[0] = Integer.parseInt(faceIndices[1].split("/")[1]);
                    textureCoordinateIndicesArray[1] = Integer.parseInt(faceIndices[2].split("/")[1]);
                    textureCoordinateIndicesArray[2] = Integer.parseInt(faceIndices[3].split("/")[1]);
                }
                int[] normalIndicesArray = {0, 0, 0};
                if (mesh.hasNormals()) {
                    normalIndicesArray[0] = Integer.parseInt(faceIndices[1].split("/")[2]);
                    normalIndicesArray[1] = Integer.parseInt(faceIndices[2].split("/")[2]);
                    normalIndicesArray[2] = Integer.parseInt(faceIndices[3].split("/")[2]);
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
    
    /*
    
    public static int createDisplayList(Mesh m) {
        int displayList = glGenLists(1);
        glNewList(displayList, GL_COMPILE);
        {
            glMaterialf(GL_FRONT, GL_SHININESS, 120);
            glColor3f(0.4f, 0.27f, 0.17f);
            glBegin(GL_TRIANGLES);
            for (Face face : m.faces) {
                if (face.hasNormals()) {
                    Vector3f n1 = m.normals.get(face.getNormalIndices()[0] - 1);
                    glNormal3f(n1.x, n1.y, n1.z);
                }
                Vector3f v1 = m.vertices.get(face.getVertexIndices()[0] - 1);
                glVertex3f(v1.x, v1.y, v1.z);
                if (face.hasNormals()) {
                    Vector3f n2 = m.normals.get(face.getNormalIndices()[1] - 1);
                    glNormal3f(n2.x, n2.y, n2.z);
                }
                Vector3f v2 = m.vertices.get(face.getVertexIndices()[1] - 1);
                glVertex3f(v2.x, v2.y, v2.z);
                if (face.hasNormals()) {
                    Vector3f n3 = m.normals.get(face.getNormalIndices()[2] - 1);
                    glNormal3f(n3.x, n3.y, n3.z);
                }
                Vector3f v3 = m.vertices.get(face.getVertexIndices()[2] - 1);
                glVertex3f(v3.x, v3.y, v3.z);
            }
            glEnd();
        }
        glEndList();
        return displayList;
    }
    
    public static int createTexturedDisplayList(Model m) {
        int displayList = glGenLists(1);
        glNewList(displayList, GL_COMPILE);
        {
            glBegin(GL_TRIANGLES);
            for (Face face : m.faces) {
                if (face.hasTextureCoordinates()) {
                    glMaterial(GL_FRONT, GL_DIFFUSE, BufferTools.asFlippedFloatBuffer(face.getMaterial()
                            .diffuseColour[0], face.getMaterial().diffuseColour[1],
                            face.getMaterial().diffuseColour[2], 1));
                    glMaterial(GL_FRONT, GL_AMBIENT, BufferTools.asFlippedFloatBuffer(face.getMaterial()
                            .ambientColour[0], face.getMaterial().ambientColour[1],
                            face.getMaterial().ambientColour[2], 1));
                    glMaterialf(GL_FRONT, GL_SHININESS, face.getMaterial().specularCoefficient);
                }
                if (face.hasNormals()) {
                    Vector3f n1 = m.normals.get(face.getNormalIndices()[0] - 1);
                    glNormal3f(n1.x, n1.y, n1.z);
                }
                if (face.hasTextureCoordinates()) {
                    Vector2f t1 = m.textureCoordinates.get(face.getTextureCoordinateIndices()[0] - 1);
                    glTexCoord2f(t1.x, t1.y);
                }
                Vector3f v1 = m.vertices.get(face.getVertexIndices()[0] - 1);
                glVertex3f(v1.x, v1.y, v1.z);
                if (face.hasNormals()) {
                    Vector3f n2 = m.normals.get(face.getNormalIndices()[1] - 1);
                    glNormal3f(n2.x, n2.y, n2.z);
                }
                if (face.hasTextureCoordinates()) {
                    Vector2f t2 = m.textureCoordinates.get(face.getTextureCoordinateIndices()[1] - 1);
                    glTexCoord2f(t2.x, t2.y);
                }
                Vector3f v2 = m.vertices.get(face.getVertexIndices()[1] - 1);
                glVertex3f(v2.x, v2.y, v2.z);
                if (face.hasNormals()) {
                    Vector3f n3 = m.normals.get(face.getNormalIndices()[2] - 1);
                    glNormal3f(n3.x, n3.y, n3.z);
                }
                if (face.hasTextureCoordinates()) {
                    Vector2f t3 = m.textureCoordinates.get(face.getTextureCoordinateIndices()[2] - 1);
                    glTexCoord2f(t3.x, t3.y);
                }
                Vector3f v3 = m.vertices.get(face.getVertexIndices()[2] - 1);
                glVertex3f(v3.x, v3.y, v3.z);
            }
            glEnd();
        }
        glEndList();
        return displayList;
    }*/
}