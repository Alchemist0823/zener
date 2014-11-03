package com.n8lm.zener.nifty;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.systems.EntityProcessingSystem;
import com.n8lm.zener.general.TransformComponent;
import com.n8lm.zener.math.MathUtil;
import com.n8lm.zener.math.Vector3f;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.tools.SizeValue;

/**
 * Created on 2014/9/12.
 *
 * @author Alchemist
 */
public class GUITrackingSystem extends EntityProcessingSystem {

    @Mapper
    ComponentMapper<GUIComponent> gm;

    @Mapper
    ComponentMapper<TransformComponent> tm;

    public GUITrackingSystem() {
        super(Aspect.getAspectForAll(GUIComponent.class));
    }

    @Override
    protected void inserted(Entity e) {
        super.inserted(e);
    }

    @Override
    protected void removed(Entity e) {
        super.removed(e);
    }

    @Override
    protected void process(Entity e) {

        Vector3f screenCoord = MathUtil.getScreenCoordinates(tm.get(e)
                .getWorldTransform().getTranslation(), gm.get(e).getCamera(), null);

        Element element = gm.get(e).getElement();

        element.setConstraintX(SizeValue.px((int) (screenCoord.x + gm.get(e).getTransformX())));
        element.setConstraintY(SizeValue.px((int) (screenCoord.y + gm.get(e).getTransformY())));
        element.getParent().layoutElements();
    }
}
