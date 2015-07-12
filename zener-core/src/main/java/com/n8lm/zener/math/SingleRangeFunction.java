package com.n8lm.zener.math;

/**
 * Created by Alchemist0823 on 6/7/2015.
 */
public class SingleRangeFunction {
    private BezierFunction upper;
    private BezierFunction lower;

    public BezierFunction getUpper() {
        return upper;
    }

    public void setUpper(BezierFunction upper) {
        this.upper = upper;
    }

    public BezierFunction getLower() {
        return lower;
    }

    public void setLower(BezierFunction lower) {
        this.lower = lower;
    }

    public SingleRangeFunction(BezierFunction lower, BezierFunction upper) {
        this.lower = lower;
        this.upper = upper;
    }

    public float randomValue(float x) {
        return MathUtil.nextRandomFloat(lower.getYfromX(x), upper.getYfromX(x));
    }
}
