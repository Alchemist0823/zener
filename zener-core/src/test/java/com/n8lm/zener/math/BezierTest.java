package com.n8lm.zener.math;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by sunz5 on 11/9/14.
 */
public class BezierTest {

    @Test
    public void testBezierFunctionAddDisordered() {
        System.out.println("testBezierFunctionAddDisordered");
        CurveFunction curveFunction = new CurveFunction();
        curveFunction.addAnchor(new EditableCurveAnchor2f(new Vector2f(0.0f, 0.0f), new Vector2f(-1.0f, 0.0f), new Vector2f(1.0f, 0.0f)));
        curveFunction.addAnchor(new EditableCurveAnchor2f(new Vector2f(4.0f, 0.0f), new Vector2f(3.0f, 0.0f), new Vector2f(5.0f, 0.0f)));
        curveFunction.addAnchor(new EditableCurveAnchor2f(new Vector2f(2.0f, 2.0f), new Vector2f(1.0f, 2.0f), new Vector2f(3.0f, 2.0f)));

        for (float x = 0.0f; x <= 4.0f; x += 0.5f)
            System.out.println("CurveFunction.getYfromX(" + x + ") = " + curveFunction.getYfromX(x));


        for (int i = 0; i < curveFunction.getAnchorCount() - 1; i++) {
            Assert.assertTrue("failure - Bezier Anchors is not in order", curveFunction.getAnchor(i).getPoint().x <= curveFunction.getAnchor(i + 1).getPoint().x);
        }
    }


    @Test
    public void testBezierFunctionValidation() {
        System.out.println("testBezierFunctionValidation");
        CurveFunction curveFunction = new CurveFunction();
        curveFunction.addAnchor(new EditableCurveAnchor2f(new Vector2f(0.0f, 0.0f), new Vector2f(-10.0f, 0.0f), new Vector2f(10.0f, 0.0f)));
        curveFunction.addAnchor(new EditableCurveAnchor2f(new Vector2f(2.0f, 2.0f), new Vector2f(-10.0f, 2.0f), new Vector2f(10.0f, 2.0f)));
        curveFunction.addAnchor(new EditableCurveAnchor2f(new Vector2f(4.0f, 0.0f), new Vector2f(-10.0f, 0.0f), new Vector2f(10.0f, 0.0f)));

        for (int i = 0; i < curveFunction.getAnchorCount(); i++) {
            CurveAnchor2f anchor = curveFunction.getAnchor(i);
            System.out.println(anchor);
        }

        for (float x = 0.0f; x <= 4.0f; x += 0.25f) {
            System.out.println("CurveFunction.getYfromX(" + x + ") = " + curveFunction.getYfromX(x));
        }

        Assert.assertTrue("failure - Bezier is not a function", curveFunction.getAnchor(0).getPoint().x <= curveFunction.getAnchor(1).getControl1().x);
        Assert.assertTrue("failure - Bezier is not a function", curveFunction.getAnchor(0).getControl2().x <= curveFunction.getAnchor(1).getPoint().x);
        Assert.assertTrue("failure - Bezier is not a function", curveFunction.getAnchor(1).getPoint().x <= curveFunction.getAnchor(2).getControl1().x);
        Assert.assertTrue("failure - Bezier is not a function", curveFunction.getAnchor(1).getControl2().x <= curveFunction.getAnchor(2).getPoint().x);
    }
}
