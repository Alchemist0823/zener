package com.n8lm.zener.netdemo;

import com.artemis.Entity;
import com.n8lm.zener.ExampleBasicApp;
import com.n8lm.zener.app.AppContainer;
import com.n8lm.zener.app.AppStateManager;
import com.n8lm.zener.app.BasicApp;
import com.n8lm.zener.general.TransformComponent;
import com.n8lm.zener.general.TreeAttachSystem;
import com.n8lm.zener.general.ZenerException;
import com.n8lm.zener.graphics.GLRenderSystem;
import com.n8lm.zener.graphics.LightComponent;
import com.n8lm.zener.graphics.PerspectiveProjection;
import com.n8lm.zener.graphics.ViewComponent;
import com.n8lm.zener.graphics.geom.Geometry;
import com.n8lm.zener.graphics.geom.ModelGeometry;
import com.n8lm.zener.math.Transform;
import com.n8lm.zener.math.Vector3f;
import com.n8lm.zener.netdemo.messages.LoginMessage;
import com.n8lm.zener.network.ClientNetworkSystem;
import com.n8lm.zener.network.NetworkConfiguration;
import com.n8lm.zener.script.GlobalScriptSystem;
import com.n8lm.zener.utils.EntityFactory;

import javax.swing.*;

/**
 * Created on 2014/11/1.
 * @author Alchemist
 */
public class NetworkTest extends ExampleBasicApp {

    private String playerName;
    private String host;

    public NetworkTest(String playerName, String host) {
        super("networktest", "Network Test");
        this.playerName = playerName;
        this.host = host;
    }

    private Entity cam;
    private Entity light1;

    @Override
    public void attached(AppStateManager appStateManager) {

    }

    @Override
    public void detached(AppStateManager appStateManager) {

    }

    @Override
    public void initialize(AppStateManager appStateManager, BasicApp app) {
        super.initialize(appStateManager, app);

        NetworkConfiguration config = new MyNetworkConfiguration();

        world.setSystem(new GlobalScriptSystem());
        world.setSystem(new TreeAttachSystem());
        world.setSystem(new GLRenderSystem(world));
        world.setSystem(new CharacterSystem());
        world.setSystem(new ClientNetworkSystem(true, new MyNetworkConfiguration(), new ClientNetworkAdapter(world, this)));

        world.initialize();

        EntityFactory.setWorld(world);

        String modelName = "suzanne";
        Geometry geometry = new ModelGeometry(modelName, resourceManager.getModel(modelName).getMesh());
        resourceManager.getGeometryManager().registerGeometry(geometry);

        world.getSystem(ClientNetworkSystem.class).connet(host);

        world.getSystem(ClientNetworkSystem.class).sendImportantMessage(new LoginMessage(playerName));

        // add camera entity
        cam = world.createEntity();
        cam.addComponent(new ViewComponent(new PerspectiveProjection()));
        Transform camTransform = new Transform(0, -10, 5);
        camTransform.getRotation().lookAt(new Vector3f(0, 10, -5), new Vector3f(0, 0, 10));
        cam.addComponent(new TransformComponent(camTransform));
        world.addEntity(cam);

        // add first light entity
        LightComponent lc1 = new LightComponent();
        lc1.setDiffuse(new Vector3f(0.8f, 0.8f, 0.8f));
        light1 = world.createEntity();
        light1.addComponent(lc1);
        light1.addComponent(new TransformComponent(new Transform(0f, 0f, 10f)));
        world.addEntity(light1);

        getContainer().getInput().addListener(new CharacterInputAdapter(world));
    }

    @Override
    public void cleanup() {

    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public static void main(String[] args) throws ZenerException {


        String host = (String) JOptionPane.showInputDialog(null, "Host:", "Connect to server", JOptionPane.QUESTION_MESSAGE,
                null, null, "localhost");
        String name = (String)JOptionPane.showInputDialog(null, "Name:", "Connect to server", JOptionPane.QUESTION_MESSAGE,
                null, null, "player1");

        NetworkTest game = new NetworkTest(name, host);
        AppContainer container = new AppContainer(game);
        container.setDisplayMode(800, 600, false);
        container.setAlwaysRender(true);
        //container.setVSync(true);
        container.setTargetFrameRate(60);
        container.start();
    }
}
