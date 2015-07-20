package com.n8lm.zener.math;

import java.util.List;

/**
 * Created by Alchemist0823 on 7/19/2015.
 */
public class ReadonlyCurveFunction extends SimpleCurve2f implements CurveFunction {

    public ReadonlyCurveFunction() {
        super();
    }

    ReadonlyCurveFunction(List<CurveAnchor2f> anchors) {
        this.anchors = anchors;
    }

    @Override
    public void addAnchor(CurveAnchor2f anchor) {
        new IllegalAccessException("can not add anchor");
    }
}
