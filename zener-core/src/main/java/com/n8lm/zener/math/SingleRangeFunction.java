package com.n8lm.zener.math;

/**
 * Created by Alchemist0823 on 6/7/2015.
 */
public class SingleRangeFunction {
    private EditableCurveFunction upper;
    private EditableCurveFunction lower;

    public EditableCurveFunction getUpper() {
        return upper;
    }

    public void setUpper(EditableCurveFunction upper) {
        this.upper = upper;
    }

    public EditableCurveFunction getLower() {
        return lower;
    }

    public void setLower(EditableCurveFunction lower) {
        this.lower = lower;
    }

    public SingleRangeFunction(EditableCurveFunction lower, EditableCurveFunction upper) {
        this.lower = lower;
        this.upper = upper;
    }

    public float randomValue(float x) {
        return MathUtil.nextRandomFloat(lower.getYfromX(x), upper.getYfromX(x));
    }
}
