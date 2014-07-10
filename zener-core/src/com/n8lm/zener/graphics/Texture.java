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

import com.n8lm.zener.assets.Image;
import com.n8lm.zener.math.MathUtil;
import org.lwjgl.opengl.GLContext;
import org.lwjgl.opengl.Util;

import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.GL_CLAMP_TO_EDGE;

public class Texture extends GLObject {

    public enum Format {
        /**
         * 8-bit alpha
         */
        Alpha8(8, GL_ALPHA8),

        /**
         * 16-bit alpha
         */
        Alpha16(16, GL_ALPHA16),

        /**
         * 8-bit grayscale/luminance.
         */
        Luminance8(8, GL_LUMINANCE8),

        /**
         * 16-bit grayscale/luminance.
         */
        Luminance16(16, GL_LUMINANCE16),

        /**
         * 8-bit luminance/grayscale and 8-bit alpha.
         */
        Luminance8Alpha8(16, GL_LUMINANCE8_ALPHA8),

        /**
         * 16-bit luminance/grayscale and 16-bit alpha.
         */
        Luminance16Alpha16(32, GL_LUMINANCE16_ALPHA16),

        /**
         * 8-bit red, green, and blue.
         */
        RGB8(24, GL_RGB8),

        /**
         * 8-bit red, green, blue, and alpha.
         */
        RGBA8(32, GL_RGBA8),

        RGB5A1(16, GL_RGB5_A1),

        /**
         * Arbitrary depth format. The precision is chosen by the video
         * hardware.
         */
        Depth(0, GL_DEPTH_COMPONENT, true),

        CubeDepth(0, GL_DEPTH_COMPONENT, true),

        CubeColor(0, GL_RGBA8, true);

        private int bpp;
        private int glcode;
        private boolean isDepth;

        private Format(int bpp, int glcode) {
            this.bpp = bpp;
            this.glcode = glcode;
        }

        private Format(int bpp, int glcode, boolean isDepth) {
            this(bpp, glcode);
            this.isDepth = isDepth;
        }

        /**
         * @return bits per pixel.
         */
        public int getBitsPerPixel() {
            return bpp;
        }

        /**
         * @return the GL code
         */
        public int getGLCode() {
            return glcode;
        }

        /**
         * @return True if this format is a depth format, false otherwise.
         */
        public boolean isDepthFormat() {
            return isDepth;
        }
    }

    public enum Type {

        /**
         * Two dimensional texture (default). A rectangle.
         */
        TwoDimensional,

        /**
         * An array of two dimensional textures.
         */
        TwoDimensionalArray,

        /**
         * Three dimensional texture. (A cube)
         */
        ThreeDimensional,

        /**
         * A set of 6 TwoDimensional textures arranged as faces of a cube facing
         * inwards.
         */
        CubeMap;
    }

    public enum MinFilter {
        Nearest,
        Bilinear;
    }

    public enum MagFilter {

        /**
         * Nearest neighbor interpolation is the fastest and crudest filtering
         * mode - it simply uses the color of the texel closest to the pixel
         * center for the pixel color. While fast, this results in texture
         * 'blockiness' during magnification. (GL equivalent: GL_NEAREST)
         */
        Nearest,

        /**
         * In this mode the four nearest texels to the pixel center are sampled
         * (at the closest mipmap level), and their colors are combined by
         * weighted average according to distance. This removes the 'blockiness'
         * seen during magnification, as there is now a smooth gradient of color
         * change from one texel to the next, instead of an abrupt jump as the
         * pixel center crosses the texel boundary. (GL equivalent: GL_LINEAR)
         */
        Bilinear;

    }

    public enum WrapMode {
        /**
         * Only the fractional portion of the coordinate is considered.
         */
        Repeat,
        /**
         * Only the fractional portion of the coordinate is considered, but if
         * the integer portion is odd, we'll use 1 - the fractional portion.
         * (Introduced around OpenGL1.4) Falls back on Repeat if not supported.
         */
        MirroredRepeat,
        /**
         * coordinate will be clamped to [0,1]
         */
        Clamp,
        /**
         * mirrors and clamps the texture coordinate, where mirroring and
         * clamping a value f computes:
         * <code>mirrorClamp(f) = min(1, max(1/(2*N),
         * abs(f)))</code> where N
         * is the size of the one-, two-, or three-dimensional texture image in
         * the direction of wrapping. (Introduced after OpenGL1.4) Falls back on
         * Clamp if not supported.
         */
        MirrorClamp,
        /**
         * coordinate will be clamped to the range [-1/(2N), 1 + 1/(2N)] where N
         * is the size of the texture in the direction of clamping. Falls back
         * on Clamp if not supported.
         */
        BorderClamp,
        /**
         * Wrap mode MIRROR_CLAMP_TO_BORDER_EXT mirrors and clamps to border the
         * texture coordinate, where mirroring and clamping to border a value f
         * computes:
         * <code>mirrorClampToBorder(f) = min(1+1/(2*N), max(1/(2*N), abs(f)))</code>
         * where N is the size of the one-, two-, or three-dimensional texture
         * image in the direction of wrapping." (Introduced after OpenGL1.4)
         * Falls back on BorderClamp if not supported.
         */
        MirrorBorderClamp,
        /**
         * coordinate will be clamped to the range [1/(2N), 1 - 1/(2N)] where N
         * is the size of the texture in the direction of clamping. Falls back
         * on Clamp if not supported.
         */
        EdgeClamp,
        /**
         * mirrors and clamps to edge the texture coordinate, where mirroring
         * and clamping to edge a value f computes:
         * <code>mirrorClampToEdge(f) = min(1-1/(2*N), max(1/(2*N), abs(f)))</code>
         * where N is the size of the one-, two-, or three-dimensional texture
         * image in the direction of wrapping. (Introduced after OpenGL1.4)
         * Falls back on EdgeClamp if not supported.
         */
        MirrorEdgeClamp;
    }

    public enum WrapAxis {
        /**
         * S wrapping (u or "horizontal" wrap)
         */
        S,
        /**
         * T wrapping (v or "vertical" wrap)
         */
        T,
        /**
         * R wrapping (w or "depth" wrap)
         */
        R;
    }

    /**
     * If this texture is a depth texture (the format is Depth*) then
     * this value may be used to compare the texture depth to the R texture
     * coordinate.
     */
    public enum ShadowCompareMode {
        /**
         * Shadow comparison mode is disabled.
         * Texturing is done normally.
         */
        Off,

        /**
         * Compares the 3rd texture coordinate R to the value
         * in this depth texture. If R <= texture value then result is 1.0,
         * otherwise, result is 0.0. If filtering is set to bilinear or trilinear
         * the implementation may sample the texture multiple times to provide
         * smoother results in the range [0, 1].
         */
        LessOrEqual,

        /**
         * Compares the 3rd texture coordinate R to the value
         * in this depth texture. If R >= texture value then result is 1.0,
         * otherwise, result is 0.0. If filtering is set to bilinear or trilinear
         * the implementation may sample the texture multiple times to provide
         * smoother results in the range [0, 1].
         */
        GreaterOrEqual
    }

    /**
     * The image stored in the texture
     */
    protected Image image = null;

    protected int width;
    protected int height;

    /**
     * The name of the texture (if loaded as a resource).
     */
    private String name = null;

    private Format format = Format.RGBA8;
    private MinFilter minificationFilter = MinFilter.Bilinear;
    private MagFilter magnificationFilter = MagFilter.Bilinear;
    private ShadowCompareMode shadowCompareMode = ShadowCompareMode.Off;
    private boolean needCompareModeUpdate = false;
    private int anisotropicFilter;

    public Texture(int width, int height, Format format) {
        super(glGenTextures());

        init();

        this.width = MathUtil.roundUpPOT(width);
        this.height = MathUtil.roundUpPOT(height);
        this.format = format;

        switch (format) {
            case Alpha16:
                break;
            case Alpha8:
                break;
            case Depth:
                glTexImage2D(
                        GL_TEXTURE_2D, 0, GL_DEPTH_COMPONENT, width, height, 0,
                        GL_DEPTH_COMPONENT, GL_UNSIGNED_BYTE, (ByteBuffer) null
                );

	        /*glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_COMPARE_MODE, GL_COMPARE_R_TO_TEXTURE);
            glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_COMPARE_FAIL_VALUE_ARB, 0.5F);
	        glTexParameteri(GL_TEXTURE_2D, GL_DEPTH_TEXTURE_MODE, GL_INTENSITY);*/
                break;
            case Luminance16:
                break;
            case Luminance16Alpha16:
                break;
            case Luminance8:
                break;
            case Luminance8Alpha8:
                break;
            case RGB5A1:
                break;
            case RGB8:
                glTexImage2D(
                        GL_TEXTURE_2D, 0, GL_RGB8, width, height, 0,
                        GL_RGB, GL_FLOAT, (ByteBuffer) null
                );
                break;
            case RGBA8:
                glTexImage2D(
                        GL_TEXTURE_2D, 0, GL_RGBA8, width, height, 0,
                        GL_RGBA, GL_FLOAT, (ByteBuffer) null
                );
                break;
            default:
                break;

        }

        GLHelper.checkGLError();
        //glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_COMPARE_MODE, GL_COMPARE_R_TO_TEXTURE);
        //glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_COMPARE_FUNC, GL_LEQUAL);

        glBindTexture(GL_TEXTURE_2D, 0);

    }


    public Texture(Image image) {
        super(glGenTextures());

        init();

        this.image = image;

        this.width = MathUtil.roundUpPOT(image.getWidth());
        this.height = MathUtil.roundUpPOT(image.getHeight());


        if (width != image.getWidth() || height != image.getHeight()) {
            glTexImage2D(GL_TEXTURE_2D, 0,
                    image.getFormat().getTextureFormat().getGLCode(), width, height,
                    0, image.getFormat().getGLCode(), GL_UNSIGNED_BYTE,
                    (ByteBuffer) null);
            if (image.getData() != null) {
                Util.checkGLError();
                glTexSubImage2D(GL_TEXTURE_2D, 0,
                        0, 0, image.getWidth(), image.getHeight(), image.getFormat().getGLCode(),
                        GL_UNSIGNED_BYTE, image.getData());
            }
        } else {
            glTexImage2D(GL_TEXTURE_2D, 0,
                    image.getFormat().getTextureFormat().getGLCode(), width, height,
                    0, image.getFormat().getGLCode(), GL_UNSIGNED_BYTE, image.getData());
        }

        Util.checkGLError();
        this.setFormat(image.getFormat().getTextureFormat());
        
		/*glTexParameteri(GL_TEXTURE_2D, GL_DEPTH_TEXTURE_MODE, GL_INTENSITY);
        //glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_COMPARE_FAIL_VALUE_ARB, 0.5f);*/
		/*
		glTexGeni(GL_S, GL_TEXTURE_GEN_MODE, GL_EYE_LINEAR);
		glTexGeni(GL_T, GL_TEXTURE_GEN_MODE, GL_EYE_LINEAR);
		glTexGeni(GL_R, GL_TEXTURE_GEN_MODE, GL_EYE_LINEAR);
		glTexGeni(GL_Q, GL_TEXTURE_GEN_MODE, GL_EYE_LINEAR);*/

        glTexImage2D(
                GL_TEXTURE_2D, 0, format.getGLCode(), width, height, 0,
                image.getFormat().getGLCode(), GL_UNSIGNED_BYTE, (ByteBuffer) image.getData()
        );
        glBindTexture(GL_TEXTURE_2D, 0);
    }

    private void init() {
        glBindTexture(GL_TEXTURE_2D, id);

        //glPixelStorei(GL_UNPACK_ROW_LENGTH, 0);
        //glPixelStorei(GL_UNPACK_ALIGNMENT, 1);

        if (GLContext.getCapabilities().OpenGL12) {
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);
        } else {
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP);
        }

        switch (magnificationFilter) {
            case Bilinear:
                glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
                break;
            case Nearest:
                glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
                break;
            default:
                break;
        }

        switch (minificationFilter) {
            case Bilinear:
                glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
                break;
            case Nearest:
                glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
                break;
            default:
                break;
        }
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MagFilter getMagnificationFilter() {
        return magnificationFilter;
    }

    public void setMagnificationFilter(MagFilter magnificationFilter) {
        this.magnificationFilter = magnificationFilter;
    }

    public ShadowCompareMode getShadowCompareMode() {
        return shadowCompareMode;
    }

    public void setShadowCompareMode(ShadowCompareMode shadowCompareMode) {
        this.shadowCompareMode = shadowCompareMode;
    }

    public boolean isNeedCompareModeUpdate() {
        return needCompareModeUpdate;
    }

    public void setNeedCompareModeUpdate(boolean needCompareModeUpdate) {
        this.needCompareModeUpdate = needCompareModeUpdate;
    }

    public int getAnisotropicFilter() {
        return anisotropicFilter;
    }

    public void setAnisotropicFilter(int anisotropicFilter) {
        this.anisotropicFilter = anisotropicFilter;
    }

    public MinFilter getMinificationFilter() {
        return minificationFilter;
    }

    public void setMinificationFilter(MinFilter minificationFilter) {
        this.minificationFilter = minificationFilter;
    }

    public Format getFormat() {
        return format;
    }

    public void setFormat(Format format) {
        this.format = format;
    }

    @Override
    public void deleteObject() {
        glBindTexture(GL_TEXTURE, id);
        glDeleteTextures(id);
    }

}
