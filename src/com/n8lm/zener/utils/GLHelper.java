package com.n8lm.zener.utils;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;
import org.newdawn.slick.util.Log;

public class GLHelper {

	public static void checkGLError() {
	    int error = GL11.glGetError();
	    if (error != GL11.GL_NO_ERROR) {
	      String glerrmsg = GLU.gluErrorString(error);
	      Log.warn("OpenGL Error: (" + error + ") " + glerrmsg);
	      try {
	        throw new Exception();
	      } catch (Exception e) {
	        e.printStackTrace();
	      }
	    }
	  }
}
