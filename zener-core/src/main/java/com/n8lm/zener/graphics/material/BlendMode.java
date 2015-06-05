package com.n8lm.zener.graphics.material;

/**
 * Created by Alchemist0823 on 6/4/2015.
 */
public enum BlendMode {
    Normal(BlendFactor.ONE, BlendFactor.ZERO),

    /**
     * Transparency
     */
    Alpha(BlendFactor.SRC_ALPHA, BlendFactor.ONE_MINUS_SRC_ALPHA),

    /**
     * Particle Additive
     */
    Additive(BlendFactor.ONE, BlendFactor.ONE_MINUS_DST_COLOR),

    /**
     * f(a,b) = 1 - (1 - a)(1 - b)
     */
    Screen(BlendFactor.ONE, BlendFactor.ONE_MINUS_SRC_COLOR),

    /**
     * Multiply blend mode multiplies the numbers for each pixel of
     * the top layer with the corresponding pixel for the bottom layer.
     * The result is a darker picture.
     * f(a,b) = ab
     */
    Multiply(BlendFactor.ZERO, BlendFactor.SRC_COLOR);

    public final BlendFactor srcFactor;
    public final BlendFactor dstFactor;

    BlendMode(BlendFactor sf, BlendFactor df) {
        this.srcFactor = sf;
        this.dstFactor = df;
    }
}
