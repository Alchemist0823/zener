package com.n8lm.zener.math;

/**
 * Created by forrestsun on 7/19/15.
 */
public class Vector3fFunction {
    private EditableCurveFunction xFunction;
    private EditableCurveFunction yFunction;
    private EditableCurveFunction zFunction;

    public EditableCurveFunction getxFunction() {
        return xFunction;
    }

    public void setxFunction(EditableCurveFunction xFunction) {
        this.xFunction = xFunction;
    }

    public EditableCurveFunction getyFunction() {
        return yFunction;
    }

    public void setyFunction(EditableCurveFunction yFunction) {
        this.yFunction = yFunction;
    }

    public EditableCurveFunction getzFunction() {
        return zFunction;
    }

    public void setzFunction(EditableCurveFunction zFunction) {
        this.zFunction = zFunction;
    }

    public Vector3fFunction(EditableCurveFunction xFunction, EditableCurveFunction yFunction, EditableCurveFunction zFunction) {
        this.xFunction = xFunction;
        this.yFunction = yFunction;
        this.zFunction = zFunction;
    }

    public Vector3f getVector3f(float x, Vector3f result) {
        if (result == null)
            result = new Vector3f();

        result.x = xFunction.getYfromX(x);
        result.y = yFunction.getYfromX(x);
        result.z = zFunction.getYfromX(x);

        return result;
    }
}
