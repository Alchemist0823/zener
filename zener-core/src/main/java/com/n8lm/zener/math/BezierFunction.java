package com.n8lm.zener.math;

import java.util.List;

/**
 * Created on 2014/11/8.
 *
 * @author Alchemist
 */
public class BezierFunction extends BezierObject2D {

    public BezierFunction(List<CurveAnchor2f> anchors) {
        super(anchors);
        verticalLineCheck();
    }

    public BezierFunction() {
        super();
    }

    public void verticalLineCheck(){
        for (int i = 0; i < anchors.size() - 1; i ++) {
            if (anchors.get(i).getControl2().x > anchors.get(i + 1).getPoint().x)
                anchors.get(i).getControl2().x = anchors.get(i + 1).getPoint().x;
            if (anchors.get(i + 1).getControl1().x < anchors.get(i).getPoint().x)
                anchors.get(i + 1).getControl1().x = anchors.get(i).getPoint().x;
        }
    }

    @Override
    public void addAnchor(CurveAnchor2f anchor) {
        if (anchor.getControl1().x > anchor.getPoint().x)
            anchor.getControl1().x = anchor.getPoint().x * 2 - anchor.getControl1().x;
        if (anchor.getControl2().x < anchor.getPoint().x)
            anchor.getControl2().x = anchor.getPoint().x * 2 - anchor.getControl2().x;

        int segment = getSegment(anchor.getPoint().x);
        if (segment != -1)
            anchors.add(segment + 1, anchor);
        else
            if (!anchors.isEmpty() && anchor.getPoint().x < anchors.get(0).getPoint().x)
                anchors.add(0, anchor);
            else
                anchors.add(anchor);
        verticalLineCheck();
    }

    public int getSegment(float x) {
        if (anchors.isEmpty())
            return -1;
        if (x < anchors.get(0).getPoint().x || anchors.get(anchors.size() - 1).getPoint().x < x)
            return -1;

        int i;
        for (i = 0; i < anchors.size() - 1; i ++) {
            if (anchors.get(i).getPoint().x <= x && x <= anchors.get(i + 1).getPoint().x)
                break;
        }
        return i;
    }

    public float getTfromX(float x) {
        BezierCurve2D curve = getCurvefromX(x);
        return curve.solveTfromX(x, 1e-6f);
    }

    public float getYfromX(float x) {
        BezierCurve2D curve = getCurvefromX(x);
        return curve.sampleY(curve.solveTfromX(x, 1e-6f));
    }

    public BezierCurve2D getCurvefromX(float x) {
        int segment = getSegment(x);
        if (segment == -1)
            throw new IllegalArgumentException("x not in function range");
        return new BezierCurve2D(anchors.get(segment), anchors.get(segment + 1));
    }
    /*
    function loop(){
        var t = (now - animationStartTime) / ( animationDuration*1000 );

        var curve = new UnitBezier(Bx, By, Cx, Cy);
        var t1 = curve.solve(t, UnitBezier.prototype.epsilon);
        var s1 = 1.0-t1;

        // Lerp using solved T
        var finalPosition.x = (startPosition.x * s1) + (endPosition.x * t1);
        var finalPosition.y = (startPosition.y * s1) + (endPosition.y * t1);
    }


while (Math.Abs(x - x0) > 0.0001)
{
    if (itr++ > maxIteration)
    {
        return NoSolution;
    }
    x0 = x;
    func = 0; dFunc = 0;
    for (int i = 0; i < coefficient.Count; i++)
    {
        func += coefficient[i] * Math.Pow(x, coefficient.Count-1 - i);
    }
    for (int i = 0; i < dCoeff.Count; i++)
        dFunc += dCoeff[i] * Math.Pow(x, dCoeff.Count-1 - i);

    if (dFunc != 0)
        x = x - func / dFunc;
    else if (func < 0.0001)
        return x;
    else
        x += 1;
}
// Find new T as a function of Y along curve X
    UnitBezier.prototype.solve = function (x, epsilon) {
        return this.sampleCurveY( this.solveCurveX(x, epsilon) );
    }*/
}
