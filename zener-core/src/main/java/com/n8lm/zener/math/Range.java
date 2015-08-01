package com.n8lm.zener.math;

/**
 * Created by Alchemist0823 on 7/17/2015.
 */
public class Range {
    public float u;
    public float l;

    public Range() {
        this(0, 0);
    }

    public Range(float l, float u) {
        this.l = l;
        this.u = u;
    }

    public Range combine(Range range) {
        if (this.l > range.l)
            this.l = range.l;
        if (this.u < range.u)
            this.u = range.u;
        return this;
    }
}
