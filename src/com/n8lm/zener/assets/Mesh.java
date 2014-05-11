package com.n8lm.zener.assets;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.n8lm.zener.math.Vector2f;
import com.n8lm.zener.math.Vector3f;
import com.n8lm.zener.math.Vector4f;
import com.n8lm.zener.utils.Byte4;

/**
 * 
 * Need cloneable implements
 * 
 * @author Alchemist
 *
 */
public class Mesh{

    public final List<Vector3f> vertices = new ArrayList<Vector3f>();
    public final List<Vector2f> textureCoordinates = new ArrayList<Vector2f>();
    public final List<Vector3f> normals = new ArrayList<Vector3f>();
    
    public final List<Byte4> weights = new ArrayList<Byte4>();
    public final List<Byte4> boneIndices = new ArrayList<Byte4>();
    
    public final List<Face> faces = new ArrayList<Face>();
    //private boolean enableSmoothShading = true;

    /*
    public void enableStates() {
        if (hasTextureCoordinates()) {
            glEnable(GL_TEXTURE_2D);
        }
        if (isSmoothShadingEnabled()) {
            glShadeModel(GL_SMOOTH);
        } else {
            glShadeModel(GL_FLAT);
        }
    }*/

    public boolean hasTextureCoordinates() {
        return textureCoordinates.size() > 0;
    }

    public boolean hasNormals() {
        return normals.size() > 0;
    }
    
    public boolean hasBoneAssignment() {
    	return boneIndices.size() > 0;
    }
}