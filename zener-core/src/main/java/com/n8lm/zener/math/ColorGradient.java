package com.n8lm.zener.math;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Forrest Sun on 6/6/2015.
 */
public class ColorGradient {

    /**
     * A control point defining the gradient
     *
     * @author Forrest Sun
     */
    public class ControlPoint {
        /**
         * The color at this control point
         */
        public ColorRGBA col;
        /**
         * The position of this control point (0 -> 1)
         */
        public float pos;

        /**
         * Create a new control point
         *
         * @param col The color at this control point
         * @param pos The position of this control point (0 -> 1)
         */
        private ControlPoint(ColorRGBA col, float pos) {
            this.col = col;
            this.pos = pos;
        }
    }

    public void remove(int selected) {
        list.remove(selected);
    }

    public void setPointPos(int selected, float newPos) {
        list.get(selected).pos = newPos;
    }

    /**
     * Add a control point to the gradient
     *
     * @param pos The position in the gradient (0 -> 1)
     * @param col The color at the new control point
     */
    public void addPoint(float pos, ColorRGBA col) {
        ControlPoint point = new ControlPoint(col, pos);
        for (int i = 0; i < list.size() - 1; i++) {
            ControlPoint now = list.get(i);
            ControlPoint next = list.get(i + 1);
            if ((now.pos <= pos) && (next.pos >= pos)) {
                list.add(i + 1, point);
                break;
            }
        }
    }

    public ColorRGBA getYfromX(float pos, ColorRGBA color) {
        for (int i = 0; i < list.size() - 1; i++) {
            ControlPoint now = list.get(i);
            ControlPoint next = list.get(i + 1);
            if ((now.pos <= pos) && (next.pos >= pos)) {
                color.interpolateLocal(now.col, next.col, (pos - now.pos) / (next.pos - now.pos));
                break;
            }
        }
        return color;
    }


    /**
     * Sort the control points based on their position
     */
    private void sortPoints() {
        final ControlPoint firstPt = list.get(0);
        final ControlPoint lastPt = list.get(list.size() - 1);
        Comparator<ControlPoint> compare = (first, second) -> {
            if (first == firstPt) {
                return -1;
            }
            if (second == lastPt) {
                return -1;
            }
            return (int) ((first.pos - second.pos) * 10000);
        };
        Collections.sort(list, compare);
    }

    /**
     * Set the starting colour
     *
     * @param col The color at the start of the gradient
     */
    public void setStart(ColorRGBA col) {
        list.get(0).col = col;
    }

    /**
     * Set the ending colour
     *
     * @param col The color at the end of the gradient
     */
    public void setEnd(ColorRGBA col) {
        list.get(list.size() - 1).col = col;
    }

    /**
     * Get the number of control points in the gradient
     *
     * @return The number of control points in the gradient
     */
    public int getControlPointCount() {
        return list.size();
    }

    /**
     * Get the graident position of the control point at the specified
     * index.
     *
     * @param index The index of the control point
     * @return The graident position of the control point
     */
    public float getPointPos(int index) {
        return list.get(index).pos;
    }

    /**
     * Get the color of the control point at the specified
     * index.
     *
     * @param index The index of the control point
     * @return The color of the control point
     */
    public ColorRGBA getColor(int index) {
        return list.get(index).col;
    }


    /**
     * The list of control points
     */
    private List<ControlPoint> list = new ArrayList<>();

    public ColorGradient() {
        list.add(0, new ControlPoint(ColorRGBA.White, 0));
        list.add(1, new ControlPoint(ColorRGBA.Black, 1));
    }
}
