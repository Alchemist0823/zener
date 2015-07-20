package com.n8lm.zener.math;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created on 2014/11/8.
 *
 * @author Alchemist
 */
public class SimpleCurve2f implements Curve2f, Serializable {

    protected List<CurveAnchor2f> anchors;

    public SimpleCurve2f() {
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

    public Range getYBound(Range range) {
        if (range == null)
            range = new Range();

        if (anchors.isEmpty()) {
            range.u = 0;
            range.l = 0;
        } else if (anchors.size() == 1) {
            range.u = getAnchor(0).getPoint().y;
            range.l = getAnchor(0).getPoint().y;
        } else {
            Range r = new Range();
            CurveSegment2D seg = new CurveSegment2D();
            for (int i = 0; i < getAnchorCount() - 1; i++) {
                seg.set(getAnchor(i), getAnchor(i + 1));
                seg.getYBound(r);
                range.combine(r);
            }
        }
        return range;
    }

    public Range getXBound(Range range) {
        if (range == null)
            range = new Range();

        if (anchors.isEmpty()) {
            range.u = 0;
            range.l = 0;
        } else if (anchors.size() == 1) {
            range.u = getAnchor(0).getPoint().x;
            range.l = getAnchor(0).getPoint().x;
        } else {
            Range r = new Range();
            CurveSegment2D seg = new CurveSegment2D();
            for (int i = 0; i < getAnchorCount() - 1; i++) {
                seg.set(getAnchor(i), getAnchor(i + 1));
                seg.getXBound(r);
                range.combine(r);
            }
        }
        return range;
    }

}
