package com.n8lm.zener.graphics;

import java.util.logging.Logger;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;


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
