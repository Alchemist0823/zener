package com.n8lm.zener.math;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created on 2014/11/8.
 *
 * @author Alchemist
 */
public class BezierObject2D implements Serializable{

    protected List<CurveAnchor2f> anchors;

    public BezierObject2D(List<CurveAnchor2f> anchors) {
        this.anchors = anchors;
    }

    public BezierObject2D() {
        anchors = new ArrayList<>();
    }

    public void addAnchor(CurveAnchor2f anchor) {
        this.anchors.add(anchor);
    }

    public CurveAnchor2f getAnchor(int i) {
        return anchors.get(i);
    }

    public int getAnchorCount() {
        return anchors.size();
    }
}
