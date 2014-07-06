package com.n8lm.zenertest.graphics;

import com.artemis.Entity;
import com.artemis.World;
import com.artemis.managers.TagManager;
import com.n8lm.zener.app.AppGameContainer;
import com.n8lm.zener.general.AttachSystem;
import com.n8lm.zener.general.TransformComponent;
import com.n8lm.zener.graphics.*;
import com.n8lm.zener.graphics.geom.ModelGeometry;
import com.n8lm.zener.graphics.material.NormalMaterial;
import com.n8lm.zener.math.MathUtil;
import com.n8lm.zener.math.Transform;
import com.n8lm.zener.math.Vector3f;
import com.n8lm.zener.script.Event;
import com.n8lm.zener.script.GlobalScriptSystem;
import com.n8lm.zener.script.NativeScript;
import com.n8lm.zener.script.ScriptComponent;
import com.n8lm.zener.utils.ZenerException;
import com.n8lm.zenertest.ExampleBasicGame;

/**
 * Created on 2014/7/4.
 * @author Alchemist
 */
public class ModelRenderTest extends ExampleBasicGame implements NativeScript {

    public ModelRenderTest() {
        super("modelrender", "Model Render");
    }

    @Override
    protected void init() {
        super.init();

        world.setSystem(new GlobalScriptSystem());
        world.setSystem(new AttachSystem());
        world.setSystem(new GLRenderSystem(world));
    }

    private Entity model;
    private Entity cam;
    private Entity light;

    @Override
    protected void afterInit() {
        model = world.createEntity();
        model.addComponent(new GeometryComponent(new ModelGeometry(resourceManager.getModel("suzanne").getMesh()), false));
        model.addComponent(new MaterialComponent(new NormalMaterial(resourceManager.getModel("suzanne").getMaterial()), false));
        model.addComponent(new TransformComponent(new Transform(0, 0, 0)));
        world.addEntity(model);

        cam = world.createEntity();
        cam.addComponent(new ViewComponent(new PerspectiveProjection()));
        Transform camTransform = new Transform(0, -3, 0);
        camTransform.getRotation().lookAt(new Vector3f(0, 3, 0), new Vector3f(0, 0, 3));
        cam.addComponent(new TransformComponent(camTransform));
        world.addEntity(cam);


        light = world.createEntity();
        light.addComponent(new LightComponent());
        light.addComponent(new TransformComponent(new Transform(0, -10, 0)));
        light.addComponent(new ScriptComponent(Event.WORLD_UPDATE, this));
        world.getManager(TagManager.class).register("MainLight", light);
        world.addEntity(light);

        //getContainer().getInput().addListener(new );
    }

    private int timer = 0;

    @Override
    public void run(World world, Event event) {
        if (event.getType() == Event.WORLD_UPDATE) {
            timer++;
            float angle = timer / 10.0f / 180f * MathUtil.PI;
            model.getComponent(TransformComponent.class).getLocalTransform().getRotation()
                    .lookAt(Vector3f.UNIT_Z, new Vector3f(MathUtil.cos(angle), MathUtil.sin(angle), 0));
            light.getComponent(TransformComponent.class).getLocalTransform().getTranslation()
                    .set(0, MathUtil.cos(angle * 5) * 5, MathUtil.sin(angle * 5) * 5);
        }
    }

    public static void main(String[] args) throws ZenerException {
        ModelRenderTest game = new ModelRenderTest();
        AppGameContainer container = new AppGameContainer(game);
        container.setDisplayMode(800, 600, false);
        container.setAlwaysRender(true);
        //container.setVSync(true);
        container.setTargetFrameRate(60);
        container.start();
    }
}
