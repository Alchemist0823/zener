package com.n8lm.zener.animation;

import com.artemis.Entity;
import com.n8lm.zener.graphics.MaterialComponent;
import com.n8lm.zener.graphics.material.UVTransformMaterial;
import com.n8lm.zener.math.Vector2f;

/**
 * Created on 2014/9/8.
 *
 * @author Alchemist
 */
public class UVTransformAnimationController extends AnimationController<UVTransformKeyFrame>{

    public UVTransformAnimationController(Animation<UVTransformKeyFrame> anim, boolean isLoop, float speed, float time) {
        super(anim, isLoop, speed, time);
    }

    @Override
    protected void process(int nowIndex, int nextIndex, Entity e) {

        UVTransformKeyFrame nowf = anim.getFrame(nowIndex);
        UVTransformKeyFrame nextf = anim.getFrame(nextIndex);

        float totalTime = nextf.getTime() - nowf.getTime();
        float nowTime = time - nowf.getTime();

        if (totalTime == 0)
            totalTime = 1;

        Vector2f temp = new Vector2f();
        temp.interpolateLocal(nowf.uvTransform, nextf.uvTransform, nowTime / totalTime);
        System.out.println(temp);
        ((UVTransformMaterial) e.getComponent(MaterialComponent.class).getMaterial()).setUVTransform(temp);
    }
}
