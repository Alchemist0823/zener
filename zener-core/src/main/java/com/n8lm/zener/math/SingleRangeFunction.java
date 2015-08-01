package com.n8lm.zener.math;

/**
 * Created by Alchemist0823 on 6/7/2015.
 */
public class SingleRangeFunction implements Function {
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

    public SingleRangeFunction() {
        this(1.0f);
    }

    public SingleRangeFunction(float constant) {
        this(constant, constant);
    }

    public SingleRangeFunction(float lowY, float upperY) {
        this(lowY, lowY, upperY, upperY);
    }

    public SingleRangeFunction(float lowerMinY, float lowerMaxY, float upperMinY, float upperMaxY) {
        this(0.0f, 1.0f, lowerMinY, lowerMaxY, upperMinY, upperMaxY);
    }

    public SingleRangeFunction(float minX, float maxX, float lowerMinY, float lowerMaxY, float upperMinY, float upperMaxY) {
        this(new ReadonlyCurveFunction(minX, lowerMinY, maxX, lowerMaxY), new ReadonlyCurveFunction(minX, upperMinY, maxX, upperMaxY));
    }

    @Override
    public Range getXBound(Range range) {
        return lower.getXBound(range);
    }

    @Override
    public Range getYBound(Range range) {
        return lower.getYBound(range).combine(upper.getYBound(null));
    }

    @Override
    public float getYFromX(float x) {
        return MathUtil.nextRandomFloat(lower.getYFromX(x), upper.getYFromX(x));
    }
}
