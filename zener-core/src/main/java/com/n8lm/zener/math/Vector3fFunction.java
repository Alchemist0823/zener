package com.n8lm.zener.math;

/**
 * Created by forrestsun on 7/19/15.
 */
public class Vector3fFunction {
    private CurveFunction xFunction;
    private CurveFunction yFunction;
    private CurveFunction zFunction;

    public CurveFunction getxFunction() {
        return xFunction;
    }

    public void setxFunction(CurveFunction xFunction) {
        this.xFunction = xFunction;
    }

    public CurveFunction getyFunction() {
        return yFunction;
    }

    public void setyFunction(CurveFunction yFunction) {
        this.yFunction = yFunction;
    }

    public CurveFunction getzFunction() {
        return zFunction;
    }

    public void setzFunction(CurveFunction zFunction) {
        this.zFunction = zFunction;
    }

    public Vector3fFunction(CurveFunction xFunction, CurveFunction yFunction, CurveFunction zFunction) {
        this.xFunction = xFunction;
        this.yFunction = yFunction;
        this.zFunction = zFunction;
    }

    public Vector3f getVector3f(float x, Vector3f result) {
        if (result == null)
            result = new Vector3f();

        result.x = xFunction.getYFromX(x);
        result.y = yFunction.getYFromX(x);
        result.z = zFunction.getYFromX(x);

        return result;
    }
}
