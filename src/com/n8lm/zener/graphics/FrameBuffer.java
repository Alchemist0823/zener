package com.n8lm.zener.graphics;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.*;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;
import org.newdawn.slick.util.Log;

import com.n8lm.zener.graphics.Texture.Format;
import com.n8lm.zener.utils.GLHelper;

public class FrameBuffer extends GLObject{

	private Texture colorTexture = null;
	private Texture depthTexture = null;
	
	public FrameBuffer() {
		super(glGenFramebuffers());
	}
	
	public void setTexture(Texture texture) {
		glBindFramebuffer(GL_FRAMEBUFFER, id);
		glBindTexture(GL_TEXTURE_2D, texture.getID());
		if (texture.getFormat() == Format.Depth) {
			this.depthTexture = texture;
			glFramebufferTexture2D(GL_FRAMEBUFFER, GL_DEPTH_ATTACHMENT, GL_TEXTURE_2D, texture.getID(), 0);
		} else {
			this.colorTexture = texture;
			glFramebufferTexture2D(GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT0, GL_TEXTURE_2D, texture.getID(), 0);
		}
		glBindTexture(GL_TEXTURE_2D, 0);
		glBindFramebuffer(GL_FRAMEBUFFER, 0);
		
		//GLHelper.checkGLError();
	}
	
	public Texture getDepthTexture() { 
		return depthTexture;
	}

	public Texture getColorTexture() { 
		return colorTexture;
	}

	@Override
	public void deleteObject() {
		glBindFramebuffer(GL_FRAMEBUFFER, 0);
		glDeleteFramebuffers(id);
	}

}
