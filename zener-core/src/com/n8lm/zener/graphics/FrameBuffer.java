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

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.*;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;



import com.n8lm.zener.graphics.Texture.Format;

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
