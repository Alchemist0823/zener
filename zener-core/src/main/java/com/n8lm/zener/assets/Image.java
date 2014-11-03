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

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.*;

import java.nio.ByteBuffer;

import org.lwjgl.opengl.EXTAbgr;

import com.n8lm.zener.graphics.Texture;

public class Image {


	public enum Format {
		 
		Alpha(GL_ALPHA, Texture.Format.Alpha8, 1),
		RGB(GL_RGB, Texture.Format.RGB8, 3),
	    RGB_SMALL(GL_RGB, Texture.Format.RGB5A1, 2),
		BGR(GL_BGR, Texture.Format.RGB8, 3),
		RGBA(GL_RGBA, Texture.Format.RGBA8, 4),
		BGRA(GL_BGRA, Texture.Format.RGBA8, 4),
	    ABGR(EXTAbgr.GL_ABGR_EXT, Texture.Format.RGBA8, 4),
		Luminance(GL_LUMINANCE, Texture.Format.Luminance8, 1),
		LuminanceAlpha(GL_LUMINANCE_ALPHA, Texture.Format.Luminance8Alpha8, 2);
		
		private final int glCode;
		private final Texture.Format textureFormat;
        private final int components;
 
		Format(int fmt, Texture.Format tfmt, int comps) {
			this.glCode = fmt;
			this.textureFormat = tfmt;
			this.components = comps;
		}
		
		public int getGLCode() {
			return glCode;
		}

		public Texture.Format getTextureFormat() {
			return textureFormat;
		}

		public int getComponents() {
			return components;
		}
	}

	private Format format;
	private int width;
	private int height;
	private ByteBuffer data;
	
	public Image(Format format, int width, int height, ByteBuffer data) {
		this.format = format;
		this.width = width;
		this.height = height;
		this.data = data;
	}

	public Format getFormat() {
		return format;
	}

	public ByteBuffer getData() {
		return data;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

}
