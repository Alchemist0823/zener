package com.n8lm.zener.assets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;

import com.n8lm.zener.math.Vector2f;
import com.n8lm.zener.math.Vector3f;

import org.newdawn.slick.util.Log;

import com.n8lm.zener.animation.Animation;
import com.n8lm.zener.animation.Joint;
import com.n8lm.zener.animation.Pose;
import com.n8lm.zener.animation.PosesKeyFrame;
import com.n8lm.zener.animation.Skeleton;
import com.n8lm.zener.data.ResourceManager;
import com.n8lm.zener.utils.Byte4;

public class IQELoader {

	public IQELoader() {
		// TODO Auto-generated constructor stub
	}
	
	public static Model loadModel(InputStream input) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(input));

		Model mdl = new Model();
        Mesh mesh = mdl.getMesh();
        Skeleton skl = mdl.getSkeleton();
        Animation<PosesKeyFrame> anim = null;
        PosesKeyFrame frame = null;
        
        String[] strs;
        String line;
        while ((line = reader.readLine()) != null) {
        	if (line.startsWith("#")) {
                continue;
            }
            
            strs = line.trim().split(" +");
            if (strs[0].equals("joint")) {
            	
            	Joint j = new Joint();
            	
            	j.name = getStringValue(strs[1]);
            	j.parent = Integer.parseInt(strs[2]);
            	
            	if((line = reader.readLine()) != null) {
                    strs = line.trim().split(" +");
            		if (strs[0].equals("pq")) {
            			readPose(j.pose, strs);
            		}
            	}
            	
            	skl.addJoint(j);
            } else if (strs[0].equals("vp")) {
            	mesh.vertices.add(readVec3f(strs));
            } else if (strs[0].equals("vt")) {
            	mesh.textureCoordinates.add(readVec2f(strs));
            } else if (strs[0].equals("vn")) {
            	mesh.normals.add(readVec3f(strs));
            } else if (strs[0].equals("vb")) {
            	
            	if (strs.length < 9) {
            		String[] s = {"vb","0","0","0","0","0","0","0","0"};
            		System.arraycopy(strs, 0, s, 0, strs.length);
            		strs = s;
            	}
            	
            	Byte4 index = new Byte4(Byte.parseByte(strs[1]), Byte.parseByte(strs[3]), Byte.parseByte(strs[5]), Byte.parseByte(strs[7]));
            	Byte4 weight = new Byte4((int)(Float.parseFloat(strs[2]) * 255), (int)(Float.parseFloat(strs[4]) * 255), (int)(Float.parseFloat(strs[6]) * 255), (int)(Float.parseFloat(strs[8]) * 255));

            	//System.out.println(index);
            	//System.out.println(weight);
            	
            	mesh.boneIndices.add(index);
            	mesh.weights.add(weight);
            } else if (strs[0].equals("fm")) {
            	mesh.faces.add(readFace(strs));
            } else if (strs[0].equals("mesh")) {
            	
            	System.out.println(line);
            	if((line = reader.readLine()) != null) {
                    strs = line.trim().split(" +");
            		if (strs[0].equals("material")) {
            			Material mat = mdl.getMaterial();
            			mat.diffuseTextureName = getStringValue(strs[1]);
            			ResourceManager.getInstance().loadImage(mat.diffuseTextureName, mat.diffuseTextureName);
            			mat.diffuseTexture = ResourceManager.getInstance().getTexture(mat.diffuseTextureName);
            			/*TextureLoader.getTexture("PNG", 
            					ResourceLoader.getResourceAsStream(mat.diffuseTextureName));*/
            		}
            	}
            } else if (strs[0].equals("animation")) {
            	if (anim != null)
            	{
            		Log.info("Load Animation: " + anim.getName() + " " + anim.getTotalFrame());
            		mdl.add(anim);
            		frame = null;
            	}
            	anim = new Animation<PosesKeyFrame>(getStringValue(strs[1]));
            } else if (strs[0].equals("frame")) {
            	if (frame != null)
            	{
            		anim.addFrame(frame);
            	}
            	frame = new PosesKeyFrame(anim.getTotalFrame());
            }  else if (strs[0].equals("pq")) {
            	
            	Pose p = new Pose();
            	readPose(p, strs);
            	
            	frame.add(p);
            }
        }
        
        if (frame != null)
    	{
    		anim.addFrame(frame);
    	}
    	
        if (anim != null)
    	{
    		Log.info("Load Animation: " + anim.getName() + " " + anim.getTotalFrame());
    		mdl.add(anim);
    	}

        preCalcMatrices(skl, mdl.getAnimations());
        
        // for animation
    	
		return mdl;
	}
	
	private static void preCalcMatrices(Skeleton skl, Map<String,Animation<PosesKeyFrame>> anims) {
		
        skl.calcBaseMatrix();
        for (Animation<PosesKeyFrame> anim : anims.values())
            for (int i = 0 ; i < anim.getTotalFrame(); i ++)
            	anim.getFrame(i).calcPoseMatrices(skl);
	}
	
	private static String getStringValue(String str) {
		return str.substring(1, str.length() - 1);
	}
	
	private static Face readFace(String[] strs) {
		int[] v = new int[3];
		
		// TODO: not reverse
		
		v[0] = Integer.parseInt(strs[3]) + 1;
		v[1] = Integer.parseInt(strs[2]) + 1;
		v[2] = Integer.parseInt(strs[1]) + 1;
		return new Face(v, v, v);
	}
	
	private static Vector2f readVec2f(String[] strs) {
		return new Vector2f(Float.parseFloat(strs[1]), Float.parseFloat(strs[2]));
	}

	private static Vector3f readVec3f(String[] strs) {
		return new Vector3f(Float.parseFloat(strs[1]), Float.parseFloat(strs[2]), Float.parseFloat(strs[3]));
	}
	
	private static void readPose(Pose p, String[] strs) {
		p.position.x = Float.parseFloat(strs[1]);
		p.position.y = Float.parseFloat(strs[2]);
		p.position.z = Float.parseFloat(strs[3]);

		p.rotation.set(Float.parseFloat(strs[4]),
				Float.parseFloat(strs[5]),
				Float.parseFloat(strs[6]),
				Float.parseFloat(strs[7]));
		
		//p.rotation.normalise(p.rotation);

		if (strs.length > 8) {
			p.scale.x = Float.parseFloat(strs[8]);
			p.scale.y = Float.parseFloat(strs[9]);
			p.scale.z = Float.parseFloat(strs[10]);
		} else {
			p.scale.x = p.scale.y = p.scale.z = 1.0f;
		}
	}
}
