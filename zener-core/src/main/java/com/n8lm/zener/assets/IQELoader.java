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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Logger;

import com.n8lm.zener.math.Transform;
import com.n8lm.zener.math.Vector2f;
import com.n8lm.zener.math.Vector3f;
import com.n8lm.zener.animation.Animation;
import com.n8lm.zener.animation.Joint;
import com.n8lm.zener.animation.PosesKeyFrame;
import com.n8lm.zener.animation.Skeleton;
import com.n8lm.zener.data.ResourceManager;
import com.n8lm.zener.utils.Byte4;
import com.n8lm.zener.utils.StringUtil;

public class IQELoader {

	private final static Logger LOGGER = Logger.getLogger(IQELoader.class
			.getName());

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

		String[] strs = new String[20];
		String line;
		while ((line = reader.readLine()) != null) {
			if (line.startsWith("#")) {
				continue;
			}

            int n = StringUtil.splitOnWhitespace(line, strs);
            if (n == 0)
                continue;
			if (strs[0].equals("joint")) {

				Joint j = new Joint();
				j.name = getStringValue(strs[1]);
				j.parent = Integer.parseInt(strs[2]);

				if ((line = reader.readLine()) != null) {

					n = StringUtil.splitOnWhitespace(line, strs);
					if (strs[0].equals("pq")) {
						readTransform(j.pose, strs, n);
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

				while (n < 9) {
                    strs[n ++] = "0";
				}

				Byte4 index = new Byte4(Byte.parseByte(strs[1]),
						Byte.parseByte(strs[3]), Byte.parseByte(strs[5]),
						Byte.parseByte(strs[7]));
				Byte4 weight = new Byte4(
						(int) (Float.parseFloat(strs[2]) * 255),
						(int) (Float.parseFloat(strs[4]) * 255),
						(int) (Float.parseFloat(strs[6]) * 255),
						(int) (Float.parseFloat(strs[8]) * 255));

				mesh.boneIndices.add(index);
				mesh.weights.add(weight);
			} else if (strs[0].equals("fm")) {
				mesh.faces.add(readFace(strs));
			} else if (strs[0].equals("mesh")) {

				LOGGER.info(line);
				if ((line = reader.readLine()) != null) {

                    StringUtil.splitOnWhitespace(line, strs);
					if (strs[0].equals("material")) {

						strs[1] = getStringValue(strs[1]);
						if (strs[1].endsWith("mtl")) {
							MTLLoader.loadMaterialFile(
									ResourceManager.getInstance()
											.getResourceAsStream(strs[1]), mdl
											.getMaterial());
						} else {
							Material mat = mdl.getMaterial();

							ResourceManager.getInstance().loadImage(
									strs[1],
									strs[1]);
							mat.diffuseTexture = ResourceManager.getInstance()
									.getTexture(strs[1]);
							/*
							 * TextureLoader.getTexture("PNG",
							 * ResourceLoader.getResourceAsStream
							 * (mat.diffuseTextureName));
							 */
						}
					}
				}
			} else if (strs[0].equals("animation")) {
				if (anim != null) {
					LOGGER.info("Load Animation: " + anim.getName() + " "
							+ anim.getTotalFrame());
					mdl.add(anim);
					frame = null;
				}
				anim = new Animation<>(getStringValue(strs[1]));
			} else if (strs[0].equals("frame")) {
				if (frame != null) {
					anim.addFrame(frame);
				}
				frame = new PosesKeyFrame(anim.getTotalFrame());
			} else if (strs[0].equals("pq")) {

				Transform p = new Transform();
				readTransform(p, strs, n);

				frame.add(p);
			}
		}

		if (frame != null) {
			anim.addFrame(frame);
		}

		if (anim != null) {
			LOGGER.info("Load Animation: " + anim.getName() + " "
					+ anim.getTotalFrame());
			mdl.add(anim);
		}

		preCalcMatrices(skl, mdl.getAnimations());

		// for animation

		return mdl;
	}

	private static void preCalcMatrices(Skeleton skl,
			Map<String, Animation<PosesKeyFrame>> anims) {

		skl.calcBaseMatrix();
		for (Animation<PosesKeyFrame> anim : anims.values())
			for (int i = 0; i < anim.getTotalFrame(); i++)
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
		return new Vector2f(Float.parseFloat(strs[1]),
				Float.parseFloat(strs[2]));
	}

	private static Vector3f readVec3f(String[] strs) {
		return new Vector3f(Float.parseFloat(strs[1]),
				Float.parseFloat(strs[2]), Float.parseFloat(strs[3]));
	}

	private static void readTransform(Transform p, String[] strs, int n) {
		p.getTranslation().x = Float.parseFloat(strs[1]);
		p.getTranslation().y = Float.parseFloat(strs[2]);
		p.getTranslation().z = Float.parseFloat(strs[3]);

		p.getRotation().set(Float.parseFloat(strs[4]),
				Float.parseFloat(strs[5]), Float.parseFloat(strs[6]),
				Float.parseFloat(strs[7]));

		// p.rotation.normalise(p.rotation);

		if (n > 8) {
			p.getScale().x = Float.parseFloat(strs[8]);
			p.getScale().y = Float.parseFloat(strs[9]);
			p.getScale().z = Float.parseFloat(strs[10]);
		} else {
			p.getScale().set(1.0f, 1.0f, 1.0f);
		}
	}
}
