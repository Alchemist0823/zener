package com.n8lm.zenertest;

import com.artemis.managers.TagManager;
import com.n8lm.zener.animation.AnimationSystem;
import com.n8lm.zener.app.BasicGame;
import com.n8lm.zener.data.TiledMapDatabase;
import com.n8lm.zener.general.AttachSystem;
import com.n8lm.zener.general.CollisionSystem;
import com.n8lm.zener.general.ExpirationSystem;
import com.n8lm.zener.graphics.GLRenderSystem;
import com.n8lm.zener.nifty.NiftyGUISystem;
import com.n8lm.zener.particle.ParticleProcessingSystem;
import com.n8lm.zener.script.GlobalScriptSystem;

/**
 * Created by Alchemist on 2014/7/4.
 */
public class ExampleBasicGame extends BasicGame{

    private String gameName;

    public ExampleBasicGame(String gameName, String gameTitle) {
        super(gameTitle);
        this.gameName = gameName;
    }

    @Override
    protected void init() {

        resourceManager.loadDataConfig(this.gameName + "/config.cfg");

        world.setManager(new TagManager());


        //world.setSystem(new AnimationSystem());
        //world.setSystem(new ExpirationSystem());

        //world.setSystem(new AttachSystem());
        //world.setSystem(new GLRenderSystem(world)); // new render system
        //world.setSystem(new NiftyGUISystem(this, "screen"));
    }

    @Override
    protected void afterInit() {

    }

    @Override
    public void destory() {

    }

    /*public static void main(String[] args) throws ZenerException {

        LogUtil.setup(true);

        ExampleBasicGame game = new ExampleBasicGame();
        AppGameContainer container = new AppGameContainer(game);
        container.setDisplayMode(1024, 768, false);
        container.setAlwaysRender(true);
        //container.setMultiSample(4);
        //container.setIcon("icon.png");
        //container.setMinimumLogicUpdateInterval(1);
        // container.setMaximumLogicUpdateInterval(1);
        container.setVSync(true);
        container.setTargetFrameRate(60);
        container.start();
    }*/
}
