package com.n8lm.zener.math;

/**
 * Created by Alchemist0823 on 7/14/2015.
 */
public class EditableAnchorData {

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

    public EditableAnchorData() {
        this(ControlType.ALIGN, ControlType.ALIGN);
    }

    public EditableAnchorData(ControlType type1, ControlType type2) {
        this.type1 = type1;
        this.type2 = type2;
        this.selected1 = this.selected2 = this.selectedP = false;
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
