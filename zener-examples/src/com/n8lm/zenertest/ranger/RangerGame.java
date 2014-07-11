package com.n8lm.zenertest.ranger;

import com.artemis.Entity;
import com.n8lm.zener.animation.AnimationComponent;
import com.n8lm.zener.animation.AnimationSystem;
import com.n8lm.zener.animation.SkeletonComponent;
import com.n8lm.zener.app.AppGameContainer;
import com.n8lm.zener.assets.Model;
import com.n8lm.zener.general.AttachSystem;
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

    Entity cam, controlCenter, character, mapEntity, light1;
    Entity weapon;

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
        world.setSystem(new AnimationSystem());
        world.setSystem(new TiledMapPositionSystem(map));
        world.setSystem(new AttachSystem());
        world.setSystem(new GLRenderSystem(world));

        Model characterModel = resourceManager.getModel("human");

        world.initialize();

        // add Map
        mapEntity = world.createEntity();
        mapEntity.addComponent(new GeometryComponent(new TiledMapGeometry(map)));
        mapEntity.addComponent(new MaterialComponent(new TiledMapMaterial(map.getTileSet())));
        mapEntity.addComponent(new TransformComponent(new Transform()));
        world.addEntity(mapEntity);

        // add the structure entity of the character
        controlCenter = world.createEntity();
        Transform characterTransform = new Transform(10, 10, 0);
        controlCenter.addComponent(new TransformComponent(mapEntity, characterTransform));
        controlCenter.addComponent(new MapPositionComponent());
        world.addEntity(controlCenter);

        // ad.set("")
        // add character entity
        AbilityData ad = new AbilityData();
        CharacterComponent cc = new CharacterComponent(100, ad);
        ad.set("strength", 10);
        ad.set("agility", 10);
        character = world.createEntity();
        character.addComponent(new GeometryComponent(new ModelGeometry(characterModel.getMesh()), false));
        character.addComponent(new MaterialComponent(new NormalMaterial(characterModel.getMaterial()), false));
        character.addComponent(cc);
        character.addComponent(new AnimationComponent());
        character.addComponent(new VelocityComponent());
        character.addComponent(new SkeletonComponent(characterModel.getSkeleton()));
        character.addComponent(new TransformComponent(controlCenter, new Transform()));
        world.addEntity(character);

        // add weapon entity
        Model weaponModel = resourceManager.getModel("bow");
        weapon = world.createEntity();
        weapon.addComponent(new GeometryComponent(new ModelGeometry(weaponModel.getMesh()), false));
        weapon.addComponent(new MaterialComponent(new NormalMaterial(weaponModel.getMaterial()), false));
        weapon.addComponent(new TransformComponent(character, "Weapon.L", new Transform()));
        world.addEntity(weapon);

        cc.setWeaponEntity(weapon);

        // add camera entity
        cam = world.createEntity();
        cam.addComponent(new ViewComponent(new PerspectiveProjection()));
        Transform camTransform = new Transform(0, -3, 3);
        camTransform.getRotation().lookAt(new Vector3f(0, 3, 0), new Vector3f(0, 0, 3));
        cam.addComponent(new TransformComponent(controlCenter, camTransform));
        world.addEntity(cam);

        // add first light entity
        LightComponent lc1 = new LightComponent();
        lc1.setDiffuse(new Vector3f(0.8f, 0.8f, 0.8f));
        light1 = world.createEntity();
        light1.addComponent(lc1);
        light1.addComponent(new TransformComponent(controlCenter, new Transform(0, 0, 10)));
        world.addEntity(light1);

        Mouse.setGrabbed(true);

        CharacterInputAdapter cia = new CharacterInputAdapter(character, controlCenter, cam);
        getContainer().getInput().addListener(cia);

        character.addComponent(new ScriptComponent(Event.WORLD_UPDATE, cia));
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
