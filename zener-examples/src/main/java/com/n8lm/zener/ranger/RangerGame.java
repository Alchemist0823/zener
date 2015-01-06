package com.n8lm.zener.ranger;

import com.artemis.Entity;
import com.n8lm.zener.ExampleBasicApp;
import com.n8lm.zener.animation.AnimationComponent;
import com.n8lm.zener.animation.AnimationSystem;
import com.n8lm.zener.app.AppContainer;
import com.n8lm.zener.collision.AABBBoundingBox;
import com.n8lm.zener.collision.CollidableComponent;
import com.n8lm.zener.general.TreeAttachSystem;
import com.n8lm.zener.collision.CollisionSystem;
import com.n8lm.zener.general.DelayedSystem;
import com.n8lm.zener.general.TransformComponent;
import com.n8lm.zener.graphics.*;
import com.n8lm.zener.map.*;
import com.n8lm.zener.math.*;
import com.n8lm.zener.script.Event;
import com.n8lm.zener.script.GlobalScriptSystem;
import com.n8lm.zener.script.ScriptComponent;
import com.n8lm.zener.script.ScriptHelper;
import com.n8lm.zener.utils.EntityFactory;
import com.n8lm.zener.utils.ZenerException;
import org.lwjgl.input.Mouse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created on 2014/7/11.
 *
 * @author Alchemist
 */
public class RangerGame extends ExampleBasicApp {

    public RangerGame() {
        super("ranger", "Ranger Game");
    }

    Entity cam, mainCharacter, mapEntity, light1;


    private Entity createCharacter() {
        // add character entity
        AbilityData ad = new AbilityData();
        CharacterComponent cc = new CharacterComponent(100, ad);
        ad.set("strength", 10);
        ad.set("agility", 10);
        Entity character = world.createEntity();
        EntityFactory.addDisplayObjectComponents(character, "human", false, false);
        character.addComponent(cc);
        character.addComponent(new AnimationComponent());
        //character.addComponent(new VelocityComponent(new Vector3f()));
        character.addComponent(new TransformComponent());
        character.addComponent(new MapPositionComponent());
        character.addComponent(new CollidableComponent(new AABBBoundingBox(new Vector3f(0f, 0f, 0.9f), 0.4f, 0.2f, 0.9f)));

        world.addEntity(character);

        // add weapon entity
        Entity weapon = world.createEntity();
        EntityFactory.addDisplayObjectComponents(weapon, "bow", false, false);

        Quaternion q = new Quaternion();
        q.fromAngles(0, MathUtil.HALF_PI, 0);

        weapon.addComponent(new TransformComponent(Vector3f.ZERO, q, Vector3f.UNIT_XYZ));
        world.getSystem(TreeAttachSystem.class).setParent(character, weapon, "Weapon.L");
        world.addEntity(weapon);

        cc.setWeaponEntity(weapon);
        return character;
    }

    @Override
    protected void init() {
        super.init();

        TiledMap map = null;
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(resourceManager.getResourceAsStream("map.txt")));
            map = TiledMap.readFromText(reader);
        } catch (IOException e) {
            e.printStackTrace();
        }

        world.setSystem(new GlobalScriptSystem());
        world.setSystem(new TiledMapPositionSystem(map));
        world.setSystem(new TiledMapRenderingSystem());
        world.setSystem(new CharacterSystem(world.getSystem(TiledMapRenderingSystem.class).getMapEntity()));
        world.setSystem(new DelayedSystem());
        world.setSystem(new CollisionSystem(), true);
        world.setSystem(new PhysicsSystem(world.getSystem(CollisionSystem.class)));
        world.setSystem(new AnimationSystem());
        world.setSystem(new TreeAttachSystem());
        world.setSystem(new GLRenderSystem(world));

        world.initialize();

        EntityFactory.setWorld(world);
        // add Map
        int vis[][] = new int[40][40];
        for (int i = 0; i < vis.length; i ++)
            for (int j = 0; j < vis[i].length; j ++) {
                vis[i][j] = 1;
            }
        /*Vector4f color[][] = new Vector4f[40][40];
        for (int i = 0; i < vis.length; i ++)
            for (int j = 0; j < vis[i].length; j ++) {
                color[i][j] = new Vector4f(1.0f, 1.0f, 1.0f, 1.0f);
            }*/

        world.getSystem(TiledMapRenderingSystem.class).init(map);
        world.getSystem(TiledMapRenderingSystem.class).updateVisibleArea(vis);
        //world.getSystem(TiledMapRenderingSystem.class).updateHighlightArea(color);

        // add the structure entity of the character
        /*
        controlCenter = world.createEntity();
        Transform characterTransform = new Transform(10, 10, 0);
        controlCenter.addComponent(new TransformComponent(mapEntity, characterTransform));
        controlCenter.addComponent(new MapPositionComponent());
        world.addEntity(controlCenter);*/

        // ad.set("")

        // add camera entity
        cam = world.createEntity();
        cam.addComponent(new ViewComponent(new PerspectiveProjection()));
        Transform camTransform = new Transform(0, -3, 3);
        camTransform.getRotation().lookAt(new Vector3f(0, 3, 0), new Vector3f(0, 0, 3));
        cam.addComponent(new TransformComponent(camTransform));
        world.getSystem(TreeAttachSystem.class).setParent(mapEntity, cam);
        world.addEntity(cam);

        mainCharacter = createCharacter();

        Entity secondCharacter = createCharacter();
        secondCharacter.getComponent(TransformComponent.class).getLocalTransform().getTranslation().set(10f, 10f, 0);

        // add first light entity
        LightComponent lc1 = new LightComponent();
        lc1.setDiffuse(new Vector3f(0.8f, 0.8f, 0.8f));
        light1 = world.createEntity();
        light1.addComponent(lc1);
        light1.addComponent(new TransformComponent(new Transform(0, 0, 10)));
        world.getSystem(TreeAttachSystem.class).setParent(mainCharacter, light1);
        world.addEntity(light1);

        Mouse.setGrabbed(true);

        CharacterInputAdapter cia = new CharacterInputAdapter(world, mainCharacter, cam, mapEntity);
        getContainer().getInput().addListener(cia);

        mainCharacter.addComponent(new ScriptComponent(Event.WORLD_UPDATE, cia));

        ScriptHelper.setup(world);
    }

    public static void main(String[] args) throws ZenerException {
        RangerGame game = new RangerGame();
        AppContainer container = new AppContainer(game);
        container.setDisplayMode(800, 600, false);
        container.setAlwaysRender(true);
        container.setVSync(true);
        container.setTargetFrameRate(60);
        container.start();
    }
}
