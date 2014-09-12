package com.n8lm.zener.nifty;

import com.artemis.Component;
import com.artemis.Entity;
import de.lessvoid.nifty.elements.Element;

/**
 * Created on 2014/9/12.
 *
 * @author Alchemist
 */
public class GUIComponent extends Component{

    private Element element;
    private Entity camera;
    private int transformX,transformY;

    public GUIComponent(Element element, Entity camera, int transformX, int transformY) {
        this.element = element;
        this.camera = camera;
        this.transformX = transformX;
        this.transformY = transformY;
    }

    public int getTransformX() {
        return transformX;
    }

    public void setTransformX(int transformX) {
        this.transformX = transformX;
    }

    public int getTransformY() {
        return transformY;
    }

    public void setTransformY(int transformY) {
        this.transformY = transformY;
    }

    public Element getElement() {
        return element;
    }

    public void setElement(Element element) {
        this.element = element;
    }

    public Entity getCamera() {
        return camera;
    }

    public void setCamera(Entity camera) {
        this.camera = camera;
    }

}
