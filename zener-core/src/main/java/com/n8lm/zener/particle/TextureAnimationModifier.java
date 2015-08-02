package com.n8lm.zener.particle;

import com.n8lm.zener.math.ReadonlyCurveFunction;

/**
 * Created by Alchemist0823 on 7/21/2015.
 */
public class TextureAnimationModifier implements ParticleModifier {

    private ReadonlyCurveFunction texIndexOverLife;
    private int row, col;

    public TextureAnimationModifier(ReadonlyCurveFunction texIndexOverLife, int row, int col) {
        this.texIndexOverLife = texIndexOverLife;
        this.row = row;
        this.col = col;
    }

    public TextureAnimationModifier(int row, int col) {
        this(new ReadonlyCurveFunction(0.0f, 0.0f, 1.0f, 1.0f), row, col);
    }

    @Override
    public void apply(Particle p, float delta) {
        int texIndex = Math.round(texIndexOverLife.getYFromX(p.time / p.life));
        int r = texIndex / row;
        int c = texIndex % col;
        p.textureCoord.y0 = r * 1.0f / row;
        p.textureCoord.x0 = c * 1.0f / col;
        p.textureCoord.y1 = (r + 1) * 1.0f / row;
        p.textureCoord.x1 = (c + 1) * 1.0f / col;
    }

    @Override
    public void frameStarted() {

    }
}
