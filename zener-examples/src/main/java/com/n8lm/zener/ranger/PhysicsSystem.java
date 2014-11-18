package com.n8lm.zener.ranger;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.systems.EntityProcessingSystem;
import com.n8lm.zener.collision.CollisionResult;
import com.n8lm.zener.collision.CollisionSystem;
import com.n8lm.zener.general.TransformComponent;
import com.n8lm.zener.math.Ray;
import com.n8lm.zener.math.Vector3f;

/**
 * Created on 2014/7/12.
 *
 * @author Alchemist
 */
public class PhysicsSystem extends EntityProcessingSystem{

    @Mapper
    ComponentMapper<TransformComponent> tm;
    @Mapper
    ComponentMapper<VelocityComponent> vm;
    @Mapper
    ComponentMapper<CharacterComponent> cm;

    private CollisionSystem collisionSystem;

    public PhysicsSystem(CollisionSystem collisionSystem) {
        super(Aspect.getAspectForAll(VelocityComponent.class, TransformComponent.class));
        this.collisionSystem = collisionSystem;
    }

    @Override
    protected void process(Entity e) {
        float second = world.getDelta() / 1000f;
        vm.get(e).getVelocity().addLocal(new Vector3f(0, 0, -5f).mult(second));
        //System.out.println(tm.get(e).getLocalTransform().getTranslation());

        collisionSystem.setRay(new Ray(tm.get(e).getWorldTransform().getTranslation(),
                tm.get(e).getWorldTransform().getTranslation().add(vm.get(e).getVelocity())));
        collisionSystem.process();
        CollisionResult cr = collisionSystem.getCollisionResults().getClosestCollision();
        if (cr != null) {
            tm.get(e).getLocalTransform().getTranslation().set(cr.getContactPoint());
            cm.get(cr.getEntity()).setHealth(cm.get(cr.getEntity()).getHealth() - 2 * vm.get(e).getVelocity().distance(Vector3f.ZERO));
            vm.get(e).getVelocity().set(0f, 0f, 0f);
            e.removeComponent(VelocityComponent.class);
            world.changedEntity(e);
            System.out.println(cm.get(cr.getEntity()).getHealth());
        } else {
            tm.get(e).getLocalTransform().getTranslation().addLocal(vm.get(e).getVelocity().mult(second));
            tm.get(e).getLocalTransform().getRotation().lookAt(vm.get(e).getVelocity(), Vector3f.UNIT_Z);
        }
    }
}
