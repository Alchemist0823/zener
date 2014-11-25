package com.n8lm.zener.general;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.EntitySystem;
import com.artemis.annotations.Mapper;
import com.artemis.utils.ArrayBag;
import com.artemis.utils.Bag;
import com.n8lm.zener.animation.SkeletonComponent;
import com.n8lm.zener.utils.TempVars;
/**
 * Created on 2014/11/7.
 *
 * @author Alchemist
 */
public class TreeAttachSystem extends EntitySystem {

    @Mapper
    ComponentMapper<TransformComponent> tm;
    @Mapper
    ComponentMapper<SkeletonComponent> sm;

    private Bag<Entity> roots;

    public TreeAttachSystem() {
        super(Aspect.getAspectForAll(TransformComponent.class));
        roots = new ArrayBag<>();
    }

    private void transform(Entity entity) {
        Bag<Entity> children = tm.get(entity).getChildren();
        SkeletonComponent skl = sm.get(entity);
        for (int i = 0, s = children.size(); s > i; i ++) {
            TransformComponent childTC = tm.get(children.get(i));

            childTC.getWorldTransform().set(childTC.getLocalTransform());

            if (childTC.getJoint() != -1 && skl != null) {
                TempVars tempVars = TempVars.get();

                skl.getCurrentPosesMatrices()[childTC.getJoint()].mult(
                        skl.getBaseSkeleton().getJoints().get(childTC.getJoint()).base, tempVars.tempMat4);

                tempVars.tempMat4.toTranslationVector(tempVars.vect1);
                tempVars.tempMat4.toRotationQuat(tempVars.quat1);
                tempVars.tempMat4.toScaleVector(tempVars.vect2);
                tempVars.tempTF.set(tempVars.vect1, tempVars.quat1, tempVars.vect2);
                childTC.getWorldTransform().combineFromParent(tempVars.tempTF);

                tempVars.release();
            } else {
            }
            childTC.getWorldTransform().combineFromParent(tm.get(entity).getWorldTransform());
            transform(children.get(i));
        }
    }

    @Override
    protected void processEntities() {
        for (int i = 0, s = roots.size(); s > i; i++) {
            TransformComponent rootTC = tm.get(roots.get(i));
            rootTC.getWorldTransform().set(rootTC.getLocalTransform());
            transform(roots.get(i));
        }
    }

    @Override
    protected boolean checkProcessing() {
        return true;
    }

    public void setParent(Entity parent, Entity child) {
        setParent(parent, child, null);
    }

    public void setParent(Entity parent, Entity child, String bone) {
        if (tm.get(child).getParent() != null)
            tm.get(tm.get(child).getParent()).removeChild(child);
        else if (parent != null)
            roots.remove(child);
        tm.get(child).setParent(parent, bone);
        if (parent != null) {
            tm.get(parent).addChild(child);
        } else {
            if (!roots.contains(child))
                roots.add(child);
        }
    }

    @Override
    protected void inserted(Entity e) {
        super.inserted(e);
        if (tm.get(e).getParent() != null) {
            tm.get(tm.get(e).getParent()).addChild(e);
        } else
            if (!roots.contains(e))
                roots.add(e);
    }

    @Override
    protected void removed(Entity e) {
        super.removed(e);
        if (tm.get(e).getParent() != null) {
            tm.get(tm.get(e).getParent()).removeChild(e);
        } else
            roots.remove(e);
    }
}
