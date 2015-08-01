package com.n8lm.zener.math;

import java.util.List;

/**
 * Created by Alchemist0823 on 7/19/2015.
 */
public class ReadonlyCurveFunction extends SimpleCurve2f implements CurveFunction {

    public ReadonlyCurveFunction() {
        this(1.0f);
    }

    public ReadonlyCurveFunction(float constant) {
        this(constant, constant);
    }

    public ReadonlyCurveFunction(float minY, float maxY) {
        this(0.0f, minY, 1.0f, maxY);
    }

    public ReadonlyCurveFunction(float minX, float minY, float maxX, float maxY) {
        float offsetX = (maxX - minX) / 3.0f;
        float offsetY = (maxY - minY) / 3.0f;
        super.addAnchor(new CurveAnchor2f(new Vector2f(minX, minY), new Vector2f(minX - offsetX, minY - offsetY)));
        super.addAnchor(new CurveAnchor2f(new Vector2f(maxX, maxY), new Vector2f(maxX - offsetX, maxY - offsetY)));
    }

    ReadonlyCurveFunction(List<CurveAnchor2f> anchors) {
        this.anchors = anchors;
    }

    @Override
    public void addAnchor(CurveAnchor2f anchor) {
        new IllegalAccessException("can not add anchor");
    }
}
