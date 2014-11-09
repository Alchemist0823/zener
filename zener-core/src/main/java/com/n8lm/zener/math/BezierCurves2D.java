package com.n8lm.zener.math;

import java.io.Serializable;
import java.util.List;

/**
 * Created on 2014/11/8.
 *
 * @author Alchemist
 */
public class BezierCurves2D implements Serializable{

    protected List<CurveAnchor2f> anchors;

    public BezierCurves2D(List<CurveAnchor2f> anchors) {
        this.anchors = anchors;
    }

    public void addAnchor(CurveAnchor2f anchor) {
        this.anchors.add(anchor);
    }
}
