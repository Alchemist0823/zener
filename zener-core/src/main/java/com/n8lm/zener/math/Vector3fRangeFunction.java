package com.n8lm.zener.math;

/**
 * Created by Alchemist0823 on 7/20/2015.
 */
public class Vector3fRangeFunction {
    private SingleRangeFunction functionX;
    private SingleRangeFunction functionY;
    private SingleRangeFunction functionZ;

    public SingleRangeFunction getFunctionX() {
        return functionX;
    }

    public void setFunctionX(SingleRangeFunction functionX) {
        this.functionX = functionX;
    }

    public SingleRangeFunction getFunctionY() {
        return functionY;
    }

    public void setFunctionY(SingleRangeFunction functionY) {
        this.functionY = functionY;
    }

    public SingleRangeFunction getFunctionZ() {
        return functionZ;
    }

    public void setFunctionZ(SingleRangeFunction functionZ) {
        this.functionZ = functionZ;
    }

    public Vector3fRangeFunction(SingleRangeFunction functionX, SingleRangeFunction functionY, SingleRangeFunction functionZ) {
        this.functionX = functionX;
        this.functionY = functionY;
        this.functionZ = functionZ;
    }

    public Vector3fRangeFunction() {
        this(new SingleRangeFunction(), new SingleRangeFunction(), new SingleRangeFunction());
    }


    public Vector3f getVector3f(float x, Vector3f result) {
        if (result == null)
            result = new Vector3f();

        result.x = functionX.getYFromX(x);
        result.y = functionY.getYFromX(x);
        result.z = functionZ.getYFromX(x);

        return result;
    }
}
