package com.n8lm.zener.model;

import com.artemis.Entity;
import com.n8lm.zener.data.ResourceManager;
import com.n8lm.zener.graphics.LightComponent;
import com.n8lm.zener.graphics.MaterialComponent;
import com.n8lm.zener.graphics.material.NormalMaterial;
import com.n8lm.zener.graphics.material.UnshadedMaterial;
import com.n8lm.zener.input.Input;
import com.n8lm.zener.input.InputAdapter;

/**
 * Created on 2014/7/7.
 *
 * @author Alchemist
 */
public class MaterialSwitchInputAdapter extends InputAdapter {

    Entity model;
    Entity light;

    public MaterialSwitchInputAdapter(Entity model, Entity light) {
        this.model = model;
        this.light = light;
    }

    @Override
    public void keyReleased(int key, char c) {
        super.keyReleased(key, c);

        if(key == Input.KEY_1) {
            model.getComponent(MaterialComponent.class).setMaterial(new NormalMaterial(ResourceManager.getInstance().getModel("suzanne").getMaterial()));
        } else if(key == Input.KEY_2) {
            model.getComponent(MaterialComponent.class).setMaterial(new UnshadedMaterial(ResourceManager.getInstance().getModel("suzanne").getMaterial()));
        } else if(key == Input.KEY_A) {
            LightComponent lc = light.getComponent(LightComponent.class);
            lc.setEnable(!lc.isEnable());
        } else if(key == Input.KEY_X) {
            LightComponent lc = light.getComponent(LightComponent.class);
            lc.setAttenuation(lc.getAttenuation() + 0.1f);
        } else if(key == Input.KEY_Z) {
            LightComponent lc = light.getComponent(LightComponent.class);
            if (lc.getAttenuation() > 0)
                lc.setAttenuation(lc.getAttenuation() - 0.1f);
        }
    }
}
