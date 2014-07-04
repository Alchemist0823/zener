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

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.logging.Logger;

import org.lwjgl.BufferUtils;

import com.n8lm.zener.math.MathUtil;

import de.matthiasmann.twl.utils.PNGDecoder;


public class PNGLoader {
	
	private final static Logger LOGGER = Logger.getLogger(PNGLoader.class
		      .getName());

	public static Image loadPNGImage() {
		return null;
	}
	
	public static Image loadPNGImage(InputStream input) throws IOException {
		return loadPNGImage(input, null);
	}

	public static Image loadPNGImage(InputStream input, int[] transparent) throws IOException {

		PNGDecoder decoder = new PNGDecoder(input);
		
		int width = decoder.getWidth();
		int height = decoder.getHeight();
		int texWidth = MathUtil.roundUpPOT(width);
		int texHeight = MathUtil.roundUpPOT(height);
		Image.Format format;

        final PNGDecoder.Format decoderFormat;
        if (true) {
            if (decoder.isRGB()) {
                decoderFormat = decoder.decideTextureFormat(PNGDecoder.Format.RGBA);
            } else {
                decoderFormat = decoder.decideTextureFormat(PNGDecoder.Format.LUMINANCE_ALPHA);
            }
        } /*else {
            decoderFormat = decoder.decideTextureFormat(PNGDecoder.Format.LUMINANCE);
        }*/

        switch (decoderFormat) {
            case RGB:
                format = Image.Format.RGB;
                break;
            case RGBA:
                format = Image.Format.RGBA;
                break;
            case BGRA:
                format = Image.Format.BGRA;
                break;
            case LUMINANCE:
                format = Image.Format.Luminance;
                break;
            case LUMINANCE_ALPHA:
                format = Image.Format.LuminanceAlpha;
                break;
            default:
                throw new IOException("Unsupported Image format.");
        }
		
		int perPixel = format.getComponents();
		
		// Get a pointer to the image memory
		ByteBuffer scratch = BufferUtils.createByteBuffer(texWidth * texHeight * perPixel);

		decoder.decode(scratch, texWidth * perPixel, decoderFormat);

		if (height < texHeight-1) {
			int topOffset = (texHeight-1) * (texWidth*perPixel);
			int bottomOffset = (height-1) * (texWidth*perPixel);
			for (int x=0;x<texWidth;x++) {
				for (int i=0;i<perPixel;i++) {
					scratch.put(topOffset+x+i, scratch.get(x+i));
					scratch.put(bottomOffset+(texWidth*perPixel)+x+i, scratch.get(bottomOffset+x+i));
				}
			}
		}
		if (width < texWidth-1) {
			for (int y=0;y<texHeight;y++) {
				for (int i=0;i<perPixel;i++) {
					scratch.put(((y+1)*(texWidth*perPixel))-perPixel+i, scratch.get(y*(texWidth*perPixel)+i));
					scratch.put((y*(texWidth*perPixel))+(width*perPixel)+i, scratch.get((y*(texWidth*perPixel))+((width-1)*perPixel)+i));
				}
			}
		}

        scratch.position(0);
		
		if (transparent != null) {
			// components will now be + 1
			final int components = format.getComponents();

            if (transparent.length != components - 1) {
            	LOGGER.warning("The amount of color components of the transparent color does not fit the number of color components of the actual image.");
            }

            if (transparent.length < components - 1) {
                LOGGER.severe("Failed to apply transparent color, not enough color values in color definition.");
            } else {

                final int size = texWidth * texHeight * components;
                boolean match;

                for (int i = 0; i < size; i += components) {
                    match = true;
                    for (int c = 0; c < components - 1; c++) {
                        if (toInt(scratch.get(i + c)) != transparent[c]) {
                            match = false;
                            break;
                        }
                    }
                    if (match) {
                        scratch.put(i + components - 1, (byte) 0);
                    }
                }
            }
		}

		scratch.position(0);
		
		Image image = new Image(format, texWidth, texHeight, scratch);
		return image;
	}
	
	/**
	 * Safe convert byte to int
	 *  
	 * @param b The byte to convert
	 * @return The converted byte
	 */
	private static int toInt(byte b) {
		if (b < 0) {
			return 256+b;
		}
		
		return b;
	}

}
