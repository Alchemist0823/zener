package com.n8lm.zener.math;

import com.n8lm.zener.utils.TempVars;

/**
 * Created by Alchemist0823 on 7/14/2015.
 */
public class EditableCurve2f extends Curve2f<EditableCurveAnchor2f> {

    public EditableCurve2f() {
        super();
    }

    @Override
    public void addAnchor(EditableCurveAnchor2f anchor) {
        super.addAnchor(anchor);
        calculate(anchors.size() - 2, anchors.size() - 1);
    }

    protected void calculate(int from, int to) {
        for (int i = from; i <= to; i++)
            calculate(i, true);
    }

    /**
     * Auto set up the control points for bezt
     *
     * @param index the index of the current anchor point
     */
    protected void calculate(int index, boolean isFunction) {
        if (index < 0 || index >= anchors.size())
            return;

        TempVars tempVars = TempVars.get();

        EditableCurveAnchor2f bezt = anchors.get(index);
        EditableCurveAnchor2f prev;
        EditableCurveAnchor2f next;

        if (index > 0)
            prev = anchors.get(index - 1);
        else
            prev = null;

        if (index < anchors.size() - 1)
            next = anchors.get(index + 1);
        else
            next = null;

        if (prev == null && next == null) {
            tempVars.release();
            return;
        }

        Vector2f p1, p2, p3;
        Vector2f pt = tempVars.vect2d;
        float len, len_a, len_b;
        float len_ratio;
        final float eps = 1e-5f;

        p2 = bezt.getPoint();
        Vector2f p2_h1 = bezt.getControl1();
        Vector2f p2_h2 = bezt.getControl2();

        if (prev == null) {
            p3 = next.getPoint();
            pt.x = 2.0f * p2.x - p3.x;
            pt.y = 2.0f * p2.y - p3.y;
            //pt2 = 2.0f * p2.x - p3.x;
            p1 = pt;
        } else {
            p1 = prev.getPoint();
        }

        if (next == null) {
            pt.x = 2.0f * p2.x - p1.x;
            pt.y = 2.0f * p2.y - p1.y;
            //pt2 = 2.0f * p2[2] - p1[2];
            p3 = pt;
        } else {
            p3 = next.getPoint();
        }

        Vector2f dvec_a = tempVars.vect2d2;
        Vector2f dvec_b = tempVars.vect2d3;

        p2.subtract(p1, dvec_a);
        p3.subtract(p2, dvec_b);

        if (isFunction) {
            len_a = dvec_a.x;
            len_b = dvec_b.x;
        } else {
            len_a = dvec_a.distance(Vector2f.ZERO);
            len_b = dvec_b.distance(Vector2f.ZERO);
        }

        if (len_a == 0.0f) len_a = 1.0f;
        if (len_b == 0.0f) len_b = 1.0f;

        len_ratio = len_a / len_b;


        if ((bezt.getType1() == EditableCurveAnchor2f.ControlType.AUTO || bezt.getType1() == EditableCurveAnchor2f.ControlType.AUTO_ANIM)
                || (bezt.getType2() == EditableCurveAnchor2f.ControlType.AUTO || bezt.getType2() == EditableCurveAnchor2f.ControlType.AUTO_ANIM)) {    /* auto */
            Vector2f tvec = tempVars.vect2d4;
            Vector2f tvec2 = tempVars.vect2d5;
            tvec.x = dvec_b.x / len_b + dvec_a.x / len_a;
            tvec.y = dvec_b.y / len_b + dvec_a.y / len_a;
            //tvec[2] = dvec_b[2] / len_b + dvec_a[2] / len_a;

            if (isFunction) {
                len = tvec.x;
            } else {
                len = tvec.distance(Vector2f.ZERO);
            }
            len *= 2.5614f;

            if (len != 0.0f) {
                /* only for fcurves */
                boolean leftviolate = false, rightviolate = false;

                if (len_a > 5.0f * len_b)
                    len_a = 5.0f * len_b;
                if (len_b > 5.0f * len_a)
                    len_b = 5.0f * len_a;

                if (bezt.getType1() == EditableCurveAnchor2f.ControlType.AUTO || bezt.getType1() == EditableCurveAnchor2f.ControlType.AUTO_ANIM) {
                    len_a /= len;
                    p2.add(tvec.mult(-len_a, tvec2), p2_h1);

                    if (bezt.getType1() == EditableCurveAnchor2f.ControlType.AUTO_ANIM && next != null && prev != null) { /* keep horizontal if extrema */
                        float ydiff1 = prev.getPoint().y - bezt.getPoint().y;
                        float ydiff2 = next.getPoint().y - bezt.getPoint().y;
                        if ((ydiff1 <= 0.0f && ydiff2 <= 0.0f) || (ydiff1 >= 0.0f && ydiff2 >= 0.0f)) {
                            bezt.getControl1().y = bezt.getPoint().y;
                        } else { /* handles should not be beyond y coord of two others */
                            if (ydiff1 <= 0.0f) {
                                if (prev.getPoint().y > bezt.getControl1().y) {
                                    bezt.getControl1().y = prev.getPoint().y;
                                    leftviolate = true;
                                }
                            } else {
                                if (prev.getPoint().y < bezt.getControl1().y) {
                                    bezt.getControl1().y = prev.getPoint().y;
                                    leftviolate = true;
                                }
                            }
                        }
                    }
                }
                if (bezt.getType2() == EditableCurveAnchor2f.ControlType.AUTO || bezt.getType2() == EditableCurveAnchor2f.ControlType.AUTO_ANIM) {
                    len_b /= len;
                    p2.add(tvec.mult(len_b, tvec2), p2_h2);

                    if (bezt.getType2() == EditableCurveAnchor2f.ControlType.AUTO_ANIM && next != null && prev != null) { /* keep horizontal if extrema */
                        float ydiff1 = prev.getPoint().y - bezt.getPoint().y;
                        float ydiff2 = next.getPoint().y - bezt.getPoint().y;
                        if ((ydiff1 <= 0.0f && ydiff2 <= 0.0f) || (ydiff1 >= 0.0f && ydiff2 >= 0.0f)) {
                            bezt.getControl2().y = bezt.getPoint().y;
                        } else { /* handles should not be beyond y coord of two others */
                            if (ydiff1 <= 0.0f) {
                                if (next.getPoint().y < bezt.getControl2().y) {
                                    bezt.getControl2().y = next.getPoint().y;
                                    rightviolate = true;
                                }
                            } else {
                                if (next.getPoint().y > bezt.getControl2().y) {
                                    bezt.getControl2().y = next.getPoint().y;
                                    rightviolate = true;
                                }
                            }
                        }
                    }
                }
                if (leftviolate || rightviolate) { /* align left handle */
                    //BLI_assert(isFunction);
                    //#if 0
                    if (isFunction)
                    //#endif
                    {
                        /* simple 2d calculation */
                        float h1_x = p2_h1.x - p2.x;
                        float h2_x = p2.x - p2_h2.x;

                        if (leftviolate) {
                            p2_h2.y = p2.y + ((p2.y - p2_h1.y) / h1_x) * h2_x;
                        } else {
                            p2_h1.y = p2.y + ((p2.y - p2_h2.y) / h2_x) * h1_x;
                        }
                    }
                    /*
                    else {
                        float h1[3], h2[3];
                        float dot;

                        sub_v3_v3v3(h1, p2_h1, p2);
                        sub_v3_v3v3(h2, p2, p2_h2);

                        len_a = normalize_v3(h1);
                        len_b = normalize_v3(h2);

                        dot = dot_v3v3(h1, h2);

                        if (leftviolate) {
                            mul_v3_fl(h1, dot * len_b);
                            sub_v3_v3v3(p2_h2, p2, h1);
                        }
                        else {
                            mul_v3_fl(h2, dot * len_a);
                            add_v3_v3v3(p2_h1, p2, h2);
                        }
                    }
                    */
                }
            }
        }

        Vector2f tvec = tempVars.vect2d3;
        if (bezt.getType1() == EditableCurveAnchor2f.ControlType.VECTOR) {    /* vector */
            p2.add(dvec_a.mult(-1.0f / 3.0f, tvec), p2_h1);
        }
        if (bezt.getType2() == EditableCurveAnchor2f.ControlType.VECTOR) {
            p2.add(dvec_b.mult(-1.0f / 3.0f, tvec), p2_h2);
        }


        if (/*skip_align ||*/
                /* when one handle is free, alignming makes no sense, see: T35952 */
                (bezt.getType1() == EditableCurveAnchor2f.ControlType.FREE || bezt.getType2() == EditableCurveAnchor2f.ControlType.FREE) ||
                /* also when no handles are aligned, skip this step */
                        (!(bezt.getType1() == EditableCurveAnchor2f.ControlType.ALIGN || bezt.getType2() == EditableCurveAnchor2f.ControlType.ALIGN)
                    /*&& !ELEM(HD_ALIGN_DOUBLESIDE, bezt.h1, bezt.h2)*/)) {
            /* handles need to be updated during animation and applying stuff like hooks,
             * but in such situations it's quite difficult to distinguish in which order
             * align handles should be aligned so skip them for now */
            tempVars.release();
            return;
        }

        len_a = p2.distance(p2_h1);
        len_b = p2.distance(p2_h2);

        if (len_a == 0.0f) len_a = 1.0f;
        if (len_b == 0.0f) len_b = 1.0f;

        len_ratio = len_a / len_b;

        if (bezt.isSelected1()) { /* order of calculation */
            if (bezt.getType2() == EditableCurveAnchor2f.ControlType.ALIGN) { /* aligned */
                if (len_a > eps) {
                    len = 1.0f / len_ratio;
                    p2.add(p2.subtract(p2_h1, tvec).multLocal(len), p2_h2);
                }
            }
            if (bezt.getType1() == EditableCurveAnchor2f.ControlType.ALIGN) {
                if (len_b > eps) {
                    len = len_ratio;
                    p2.add(p2.subtract(p2_h2, tvec).multLocal(len), p2_h1);
                }
            }
        } else {
            if (bezt.getType1() == EditableCurveAnchor2f.ControlType.ALIGN) {
                if (len_b > eps) {
                    len = len_ratio;
                    p2.add(p2.subtract(p2_h2, tvec).multLocal(len), p2_h1);
                }
            }
            if (bezt.getType1() == EditableCurveAnchor2f.ControlType.ALIGN) {   /* aligned */
                if (len_a > eps) {
                    len = 1.0f / len_ratio;
                    p2.add(p2.subtract(p2_h1, tvec).multLocal(len), p2_h2);
                }
            }
        }
        tempVars.release();
    }
}
