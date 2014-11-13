package com.n8lm.zener.psdemo;

import com.artemis.Entity;
import com.n8lm.zener.ExampleBasicGame;
import com.n8lm.zener.app.AppGameContainer;
import com.n8lm.zener.general.TransformComponent;
import com.n8lm.zener.general.TreeAttachSystem;
import com.n8lm.zener.graphics.*;
import com.n8lm.zener.math.Transform;
import com.n8lm.zener.math.Vector3f;
import com.n8lm.zener.particle.*;
import com.n8lm.zener.utils.ZenerException;

/**
 * Created on 2014/11/12.
 *
 * @author Forrest Sun
 */
public class ParticleEditor extends ExampleBasicGame{

    public ParticleEditor() {
        super("particle", "Particle System Editor");
    }

    @Override
    protected void init() {
        super.init();

        world.setSystem(new PSProcessingSystem());
        world.setSystem(new TreeAttachSystem());
        world.setSystem(new GLRenderSystem(world));

        world.initialize();



        // create a particle system
        CustomParticleEmitter emitter = new CustomParticleEmitter();
        emitter.setInitialVelocity(new Vector3f(0.0f, 0.0f, 1.0f));
        emitter.setVelocityScatter(new Vector3f(0.5f, 0.5f, 0.0f));
        emitter.setPositionScatter(new Vector3f(1.0f, 1.0f, 0.0f));
        emitter.setInitialEmitSpeed(120f);
        emitter.setLifeReducer(1.0f);
        emitter.setAtlasCount(64);

        ParticleSystemComponent psc = new ParticleSystemComponent(emitter, 1000);
        Entity ps = world.createEntity();
        ps.addComponent(new GeometryComponent(new ParticleSystemGeometry("fire", psc, resourceManager.getModel("fire").getMesh()), false));
        ps.addComponent(new MaterialComponent(new ParticleMaterial(resourceManager.getModel("fire").getMaterial(), 64), false, true));
        ps.addComponent(new TransformComponent());
        ps.addComponent(psc);
        world.addEntity(ps);

        // add a camera entity
        Entity cam = world.createEntity();
        cam.addComponent(new ViewComponent(new PerspectiveProjection()));
        Transform camTransform = new Transform(0, -3, 0);
        camTransform.getRotation().lookAt(new Vector3f(0, 3, 0), new Vector3f(0, 0, 3));
        cam.addComponent(new TransformComponent(camTransform));
        world.addEntity(cam);

        // add a light entity
        LightComponent lc1 = new LightComponent();
        lc1.setDiffuse(new Vector3f(0.8f, 0.8f, 0.8f));
        Entity light1 = world.createEntity();
        light1.addComponent(lc1);
        light1.addComponent(new TransformComponent(new Transform()));
        world.addEntity(light1);
    }



    public static void main(String[] args) throws ZenerException {
        ParticleEditor game = new ParticleEditor();
        AppGameContainer container = new AppGameContainer(game);
        container.setDisplayMode(800, 600, false);
        container.setAlwaysRender(true);
        //container.setVSync(true);
        container.setTargetFrameRate(60);
        container.start();
    }
}
