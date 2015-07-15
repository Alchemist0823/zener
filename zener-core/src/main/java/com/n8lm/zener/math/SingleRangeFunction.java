package com.n8lm.zener.math;

/**
 * Created by Alchemist0823 on 6/7/2015.
 */
public class SingleRangeFunction {
    private CurveFunction upper;
    private CurveFunction lower;

    public CurveFunction getUpper() {
        return upper;
    }

    public void setUpper(CurveFunction upper) {
        this.upper = upper;
    }

    public CurveFunction getLower() {
        return lower;
    }

    public void setLower(CurveFunction lower) {
        this.lower = lower;
    }

    public SingleRangeFunction(CurveFunction lower, CurveFunction upper) {
        this.lower = lower;
        this.upper = upper;
    }

    public float randomValue(float x) {
        return MathUtil.nextRandomFloat(lower.getYfromX(x), upper.getYfromX(x));
    }
}
