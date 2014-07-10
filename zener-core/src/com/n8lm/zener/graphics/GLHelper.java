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

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

import java.util.logging.Logger;


public class GLHelper {


    private final static Logger LOGGER = Logger.getLogger(GLHelper.class
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
}
