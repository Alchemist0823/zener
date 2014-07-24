package com.n8lm.zenertest.ranger;

import com.artemis.Entity;
import com.n8lm.zener.animation.AnimationComponent;
import com.n8lm.zener.animation.AnimationSystem;
import com.n8lm.zener.animation.SkeletonComponent;
import com.n8lm.zener.app.AppGameContainer;
import com.n8lm.zener.assets.Model;
import com.n8lm.zener.collision.AABBBoundingBox;
import com.n8lm.zener.collision.CollidableComponent;
import com.n8lm.zener.general.AttachSystem;
import com.n8lm.zener.collision.CollisionSystem;
import com.n8lm.zener.general.ExpirationSystem;
import com.n8lm.zener.general.TransformComponent;
import com.n8lm.zener.graphics.*;
import com.n8lm.zener.graphics.geom.ModelGeometry;
import com.n8lm.zener.graphics.material.NormalMaterial;
import com.n8lm.zener.map.TiledMap;
import com.n8lm.zener.map.TiledMapGeometry;
import com.n8lm.zener.map.TiledMapMaterial;
import com.n8lm.zener.math.Transform;
import com.n8lm.zener.math.Vector3f;
import com.n8lm.zener.script.Event;
import com.n8lm.zener.script.GlobalScriptSystem;
import com.n8lm.zener.script.ScriptComponent;
import com.n8lm.zener.script.ScriptHelper;
import com.n8lm.zener.utils.EntityFactory;
import com.n8lm.zener.utils.ZenerException;
import com.n8lm.zenertest.ExampleBasicGame;
import org.lwjgl.input.Mouse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created on 2014/7/11.
 *
 * @author Alchemist
 */
public class RangerGame extends ExampleBasicGame{

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
        character.addComponent(new TransformComponent(mapEntity, new Transform()));
        character.addComponent(new MapPositionComponent());
        character.addComponent(new CollidableComponent(new AABBBoundingBox(new Vector3f(0f, 0f, 0.9f), 0.5f, 0.2f, 0.9f)));
        world.addEntity(character);

        // add weapon entity
        Entity weapon = world.createEntity();
        EntityFactory.addDisplayObjectComponents(weapon, "bow", false, false);
        weapon.addComponent(new TransformComponent(character, "Weapon.L", new Transform()));
        world.addEntity(weapon);

        cc.setWeaponEntity(weapon);
        return character;
    }

    @Override
    protected void init() {
        super.init();

        TiledMap map = new TiledMap();
        try {
            map.readFromText(new BufferedReader(new InputStreamReader(resourceManager.getResourceAsStream("map.txt"))));
        } catch (IOException e) {
            e.printStackTrace();
        }

        world.setSystem(new GlobalScriptSystem());
        world.setSystem(new TiledMapPositionSystem(map));
        world.setSystem(new ExpirationSystem());
        world.setSystem(new CollisionSystem(), true);
        world.setSystem(new PhysicsSystem(world.getSystem(CollisionSystem.class)));
        world.setSystem(new AnimationSystem());
        world.setSystem(new AttachSystem());
        world.setSystem(new GLRenderSystem(world));

        world.initialize();

        // add Map
        mapEntity = world.createEntity();
        mapEntity.addComponent(new GeometryComponent(new TiledMapGeometry("map", map)));
        mapEntity.addComponent(new MaterialComponent(new TiledMapMaterial(map.getTileSet())));
        mapEntity.addComponent(new TransformComponent(new Transform()));
        world.addEntity(mapEntity);

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
        cam.addComponent(new TransformComponent(mapEntity, camTransform));
        world.addEntity(cam);

        mainCharacter = createCharacter();

        Entity secondCharacter = createCharacter();
        secondCharacter.getComponent(TransformComponent.class).getLocalTransform().getTranslation().set(10f, 10f, 0);

        // add first light entity
        LightComponent lc1 = new LightComponent();
        lc1.setDiffuse(new Vector3f(0.8f, 0.8f, 0.8f));
        light1 = world.createEntity();
        light1.addComponent(lc1);
        light1.addComponent(new TransformComponent(mainCharacter, new Transform(0, 0, 10)));
        world.addEntity(light1);

        Mouse.setGrabbed(true);

        CharacterInputAdapter cia = new CharacterInputAdapter(mainCharacter, cam, mapEntity);
        getContainer().getInput().addListener(cia);

        mainCharacter.addComponent(new ScriptComponent(Event.WORLD_UPDATE, cia));

        ScriptHelper.setup(world);
    }

    public static void main(String[] args) throws ZenerException {
        RangerGame game = new RangerGame();
        AppGameContainer container = new AppGameContainer(game);
        container.setDisplayMode(800, 600, false);
        container.setAlwaysRender(true);
        container.setVSync(true);
        container.setTargetFrameRate(60);
        container.start();
    }
}
