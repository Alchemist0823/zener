package com.n8lm.zener.math;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created on 2014/11/8.
 *
 * @author Alchemist
 */
public class Curve2f<T extends CurveAnchor2f> implements Serializable {

    protected List<T> anchors;

    public Curve2f() {
        anchors = new ArrayList<>();
    }

    public void addAnchor(T anchor) {
        this.anchors.add(anchor);
    }

    public T getAnchor(int i) {
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
            range.u = anchors.get(0).getPoint().y;
            range.l = anchors.get(0).getPoint().y;
        } else {
            Range r = new Range();
            CurveSegment2D seg = new CurveSegment2D();
            for (int i = 0; i < anchors.size() - 1; i++) {
                seg.set(anchors.get(i), anchors.get(i + 1));
                seg.getYBound(r);
                range.combine(r);
            }
        }
        return range;
    }

}
