package com.n8lm.zenertest.ranger;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.systems.EntityProcessingSystem;
import com.n8lm.zener.general.TransformComponent;
import com.n8lm.zener.map.TiledMap;
import com.n8lm.zener.math.Vector3f;

/**
 * Created on 2014/7/11.
 *
 * @author Alchemist
 */
public class TiledMapPositionSystem extends EntityProcessingSystem{

    @Mapper ComponentMapper<MapPositionComponent> cm;
    @Mapper ComponentMapper<TransformComponent> tm;

    private TiledMap map;

    public TiledMapPositionSystem(TiledMap map) {
        super(Aspect.getAspectForAll(MapPositionComponent.class));
        this.map = map;
    }

    @Override
    protected void process(Entity e) {

        Vector3f p = e.getComponent(TransformComponent.class).getLocalTransform().getTranslation();
        p.z = map.getPositionAltitude(p.x, p.y);
    }
}
