package com.n8lm.zener.math;

/**
 * Created by forrestsun on 7/19/15.
 */
public interface CurveFunction extends Function, Curve2f {

    default float getYFromX(float x) {
        CurveSegment2D curve = getCurvefromX(x);
        return curve.sampleY(curve.solveTfromX(x, 1e-6f));
    }

    default CurveSegment2D getCurvefromX(float x) {
        int segment = getSegment(x);
        if (segment == -1)
            throw new IllegalArgumentException("x not in function range");
        return new CurveSegment2D(getAnchor(segment), getAnchor(segment + 1));
    }

    default int getSegment(float x) {
        if (getAnchorCount() == 0)
            return -1;
        if (x < getAnchor(0).getPoint().x || getAnchor(getAnchorCount() - 1).getPoint().x < x)
            return -1;

        int i;
        for (i = 0; i < getAnchorCount() - 1; i++) {
            if (getAnchor(i).getPoint().x <= x && x <= getAnchor(i + 1).getPoint().x)
                break;
        }
        return i;
    }

    default Range getXBound(Range range) {
        if (range == null)
            range = new Range();

        if (getAnchorCount() == 0) {
            range.l = range.u = 0;
        }

        range.l = getAnchor(0).getPoint().x;
        range.u = getAnchor(getAnchorCount() - 1).getPoint().x;

        return range;
    }
}
