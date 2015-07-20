package com.n8lm.zener.math;

/**
 * Created by forrestsun on 7/19/15.
 */
public interface Curve2f {

    void addAnchor(CurveAnchor2f anchor);

    CurveAnchor2f getAnchor(int i);

    int getAnchorCount();

    Range getYBound(Range range);
    Range getXBound(Range range);
}
