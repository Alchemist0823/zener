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

package com.n8lm.zener.graphics;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.util.glu.GLU;

import java.nio.IntBuffer;
import java.util.EnumSet;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class GLHelper {

    private final EnumSet<GLCaps> caps = EnumSet.noneOf(GLCaps.class);

    private static final Pattern GLVERSION_PATTERN = Pattern.compile(".*?(\\d+)\\.(\\d+).*");
    private static final Logger LOGGER = Logger.getLogger(GLHelper.class
            .getName());

    public static boolean checkGLError() {
        int error = GL11.glGetError();
        if (error != GL11.GL_NO_ERROR) {
            String glerrmsg = GLU.gluErrorString(error);
            LOGGER.warning("OpenGL Error: (" + error + ") " + glerrmsg);
            try {
                throw new Exception();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return true;
        }
        return false;
    }


    public static int extractVersion(String version) {
        Matcher m = GLVERSION_PATTERN.matcher(version);
        if (m.matches()) {
            int major = Integer.parseInt(m.group(1));
            int minor = Integer.parseInt(m.group(2));
            if (minor >= 10 && minor % 10 == 0) {
                // some versions can look like "1.30" instead of "1.3".
                // make sure to correct for this
                minor /= 10;
            }
            return major * 100 + minor * 10;
        } else {
            return -1;
        }
    }

    private void loadCapabilitiesGL() {
        int oglVer = extractVersion(GL11.glGetString(GL11.GL_VERSION));

        if (oglVer >= 200) {
            caps.add(GLCaps.OpenGL20);
            if (oglVer >= 210) {
                caps.add(GLCaps.OpenGL21);
                if (oglVer >= 300) {
                    caps.add(GLCaps.OpenGL30);
                    if (oglVer >= 310) {
                        caps.add(GLCaps.OpenGL31);
                        if (oglVer >= 320) {
                            caps.add(GLCaps.OpenGL32);
                        }
                        if (oglVer >= 330) {
                            caps.add(GLCaps.OpenGL33);
                            caps.add(GLCaps.GeometryShader);
                        }
                        if (oglVer >= 400) {
                            caps.add(GLCaps.OpenGL40);
                            caps.add(GLCaps.TesselationShader);
                        }
                    }
                }
            }
        }

        int glslVer = extractVersion(GL11.glGetString(GL20.GL_SHADING_LANGUAGE_VERSION));

        switch (glslVer) {
            default:
                if (glslVer < 400) {
                    break;
                }
                // so that future OpenGL revisions wont break jme3
                // fall through intentional
            case 400:
                caps.add(GLCaps.GLSL400);
            case 330:
                caps.add(GLCaps.GLSL330);
            case 150:
                caps.add(GLCaps.GLSL150);
            case 140:
                caps.add(GLCaps.GLSL140);
            case 130:
                caps.add(GLCaps.GLSL130);
            case 120:
                caps.add(GLCaps.GLSL120);
            case 110:
                caps.add(GLCaps.GLSL110);
            case 100:
                caps.add(GLCaps.GLSL100);
                break;
        }

        // Workaround, always assume we support GLSL100 & GLSL110
        // Supporting OpenGL 2.0 means supporting GLSL 1.10.
        caps.add(GLCaps.GLSL110);
        caps.add(GLCaps.GLSL100);

        // Fix issue in TestRenderToMemory when GL.GL_FRONT is the main
        // buffer being used.
        //context.initialDrawBuf = getInteger(GL20.GL_DRAW_BUFFER0.GL_DRAW_BUFFER);
        //context.initialReadBuf = getInteger(GL20);

        // XXX: This has to be GL.GL_BACK for canvas on Mac
        // Since initialDrawBuf is GL.GL_FRONT for pbuffer, gotta
        // change this value later on ...
//        initialDrawBuf = GL.GL_BACK;
//        initialReadBuf = GL.GL_BACK;
    }

    private final IntBuffer intBuf16 = BufferUtils.createIntBuffer(16);

    private int getInteger(int en) {
        intBuf16.clear();
        GL11.glGetInteger(en, intBuf16);
        return intBuf16.get(0);
    }
}
