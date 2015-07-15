package com.n8lm.zener.math;

/**
 * Created by Alchemist0823 on 7/14/2015.
 */
public class EditableCurveAnchor2f extends CurveAnchor2f {

    public enum ControlType {
        VECTOR,
        FREE,
        ALIGN,
        AUTO,
        AUTO_ANIM
    }

    protected ControlType type1;
    protected ControlType type2;
    protected boolean selectedP, selected1, selected2;

    public EditableCurveAnchor2f(Vector2f point) {
        this(point, new Vector2f(point.x - 1f, point.y));
    }

    public EditableCurveAnchor2f(Vector2f point, Vector2f control1) {
        this(point, control1, new Vector2f(point.x * 2 - control1.x, point.y * 2 - control1.y));
    }

    public EditableCurveAnchor2f(Vector2f point, Vector2f control1, Vector2f control2) {
        this(point, control1, control2, ControlType.ALIGN, ControlType.ALIGN);
    }

    public EditableCurveAnchor2f(Vector2f point, Vector2f control1, Vector2f control2, ControlType type1, ControlType type2) {
        super(point, control1, control2);
        this.type1 = type1;
        this.type2 = type2;
        this.selected1 = this.selected2 = this.selectedP = false;
    }

    public EditableCurveAnchor2f(Vector2f point, ControlType type1, ControlType type2) {
        this(point, new Vector2f(point), new Vector2f(point), type1, type2);
    }

    public ControlType getType1() {
        return type1;
    }

    public ControlType getType2() {
        return type2;
    }

    public void setType1(ControlType type1) {
        this.type1 = type1;
    }

    public void setType2(ControlType type2) {
        this.type2 = type2;
    }

    public boolean isSelectedP() {
        return selectedP;
    }

    public void setSelectedP(boolean selectedP) {
        this.selectedP = selectedP;
    }

    public boolean isSelected1() {
        return selected1;
    }

    public void setSelected1(boolean selected1) {
        this.selected1 = selected1;
    }

    public boolean isSelected2() {
        return selected2;
    }

    public void setSelected2(boolean selected2) {
        this.selected2 = selected2;
    }
}
