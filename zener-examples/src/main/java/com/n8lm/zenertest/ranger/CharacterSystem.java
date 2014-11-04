package com.n8lm.zenertest.ranger;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.systems.EntityProcessingSystem;

import com.n8lm.zener.animation.AnimationComponent;
import com.n8lm.zener.animation.AnimationController;
import com.n8lm.zener.animation.SkeletonAnimationController;
import com.n8lm.zener.assets.Model;
import com.n8lm.zener.data.ResourceManager;
import com.n8lm.zener.general.DelayedComponent;
import com.n8lm.zener.general.TransformComponent;
import com.n8lm.zener.math.Quaternion;
import com.n8lm.zener.math.Transform;
import com.n8lm.zener.math.Vector3f;
import com.n8lm.zener.script.DelayedEvent;
import com.n8lm.zener.script.ExpiredDeleteScript;
import com.n8lm.zener.script.NativeScript;
import com.n8lm.zener.script.ScriptComponent;
import com.n8lm.zener.utils.EntityFactory;
import com.n8lm.zenertest.ranger.CharacterComponent.Action;

import java.util.List;

/**
 * Created on 2014/7/18.
 *
 * @author Alchemist
 */
public class CharacterSystem extends EntityProcessingSystem{

    @Mapper
    ComponentMapper<CharacterComponent> cm;
    @Mapper
    ComponentMapper<TransformComponent> tm;
    @Mapper
    ComponentMapper<AnimationComponent> am;

    private Entity mapEntity;
    private Model model;

    public CharacterSystem(Entity mapEntity) {
        super(Aspect.getAspectForAll(CharacterComponent.class));
        this.mapEntity = mapEntity;
        this.model = ResourceManager.getInstance().getModel("human");
    }

    private Vector3f tempdir = new Vector3f();
    private Quaternion temprot = new Quaternion();

    private NativeScript deleteScript = new ExpiredDeleteScript();

    @Override
    protected void process(Entity e) {
        CharacterComponent cc = cm.get(e);
        Action action = cc.getAction();
        AnimationController<?> ac = am.get(e).getAnimationControllerByName("Attack_bow");

        if (action == Action.Bow) {
            if (cc.getActionTime() == 0) {

                System.out.println("bow2");
                List<AnimationController<?>> acList = am.get(e).getAnimationControllers();
                for (AnimationController<?> aci : acList) {
                    aci.delete();
                }
                am.get(e).add(new SkeletonAnimationController(model.getAnimation("Attack_bow"), false, 1.0f));
            } else if (cc.getActionTime() == 40) {
                if (ac != null) {
                    ac.stop();
                }
            }
        } else if (action == Action.Run) {
            if (cc.getActionTime() == 0) {

            }

        } else if (action == Action.Shoot) {
            if (cc.getActionTime() == 0) {
                if (ac != null) {
                    ac.play();
                }

                System.out.println("shoot1");
                Entity arrow = world.createEntity();
                Helper.angleToVector(cc.getHeadAngles(), tempdir);
                Helper.angleToQuaternion(cc.getHeadAngles(), temprot);
                arrow.addComponent(new VelocityComponent(new Vector3f(tempdir.mult(20f * cc.getActionPower()))));
                arrow.addComponent(new TransformComponent(mapEntity, new Transform(
                        tm.get(e).getLocalTransform().getTranslation().add(0, 0, 1.5f).add(tempdir.mult(1.5f)),
                        temprot,
                        Vector3f.UNIT_XYZ)));
                EntityFactory.addDisplayObjectComponents(arrow, "arrow", false, false);
                //arrow.addComponent(new GeometryComponent(new ModelGeometry(ResourceManager.getInstance().getModel("arrow").getMesh())));
                //arrow.addComponent(new MaterialComponent(new NormalMaterial(ResourceManager.getInstance().getModel("arrow").getMaterial())));
                arrow.addComponent(new ScriptComponent(DelayedEvent.END, deleteScript));
                arrow.addComponent(new DelayedComponent(5f, "delete"));
                world.addEntity(arrow);
            } else if (cc.getActionTime() == 10) {
                cc.setActionTime(0);
                cc.setAction(Action.Idle);
            }
        }

        cc.setActionTime(cc.getActionTime() + 1);
    }

    public void shoot(Entity character) {

        CharacterComponent cc = cm.get(character);
        if (cc.getAction() == CharacterComponent.Action.Bow) {
            if (cc.getActionTime() >= 40) {
                cc.setAction(CharacterComponent.Action.Shoot);
                cc.setActionPower((float) Math.log(cc.getActionTime() / 40f));
                cc.setActionTime(0);
            } else {
                cc.setAction(Action.Idle);
                cc.setActionTime(0);
            }
        }
    }
}
