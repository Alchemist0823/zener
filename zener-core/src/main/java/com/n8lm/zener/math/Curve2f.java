package com.n8lm.zener.math;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created on 2014/11/8.
 *
 * @author Alchemist
 */
public class Curve2f<T extends CurveAnchor2f> implements Serializable {

    protected List<T> anchors;

    public Curve2f() {
        anchors = new ArrayList<>();
    }

    public void addAnchor(T anchor) {
        this.anchors.add(anchor);
    }

    public T getAnchor(int i) {
        return anchors.get(i);
    }

    public int getAnchorCount() {
        return anchors.size();
    }

}
