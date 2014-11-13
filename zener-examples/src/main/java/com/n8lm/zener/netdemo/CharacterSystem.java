package com.n8lm.zener.netdemo;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.systems.EntityProcessingSystem;
import com.n8lm.zener.general.TransformComponent;

import java.util.HashMap;
import java.util.Map;

/**
 * Created on 2014/11/2.
 *
 * @author Alchemist
 */
public class CharacterSystem extends EntityProcessingSystem{

    Map<Integer, Entity> idMap;

    @Mapper
    private ComponentMapper<CharacterComponent> cm;

    @Mapper
    private ComponentMapper<TransformComponent> tm;

    public CharacterSystem() {
        super(Aspect.getAspectForAll(CharacterComponent.class));
        idMap = new HashMap<>();
    }

    @Override
    protected void inserted(Entity e) {
        super.inserted(e);
        idMap.put(cm.get(e).getId(), e);
    }

    @Override
    protected void removed(Entity e) {
        idMap.remove(cm.get(e).getId());
        super.removed(e);
    }

    @Override
    protected void process(Entity e) {
        if (tm.has(e)) {
            CharacterComponent cc = cm.get(e);
            tm.get(e).getLocalTransform().getTranslation().set(cc.getX(), cc.getY(), 0);
        }
    }

    public int getCharacterCount() {
        return this.getActives().size();
    }

    public Entity getEntityByCharacterID(int id){
        return idMap.get(id);
    }

    public void updateCharacter(int id, float x, float y) {
        if (idMap.containsKey(id)) {
            CharacterComponent cc = cm.get(idMap.get(id));
            cc.setX(x);
            cc.setY(y);
        }
    }
}
