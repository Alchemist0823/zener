package com.n8lm.zener.math;

/**
 * Created by forrestsun on 7/19/15.
 */
public interface Function {
    Range getXBound(Range range);
    Range getYBound(Range range);
    float getYfromX(float x);
}
