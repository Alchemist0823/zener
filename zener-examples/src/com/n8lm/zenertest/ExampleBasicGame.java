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
 * Created on 2014/7/4.
 * @author Alchemist
 */
public class ExampleBasicGame extends BasicGame{

    private String gameName;

    public ExampleBasicGame(String gameName, String gameTitle) {
        super(gameTitle);
        this.gameName = gameName;
    }

    @Override
    protected void init() {

        resourceManager.loadDataConfig("./res/" + this.gameName + "/config.cfg");

        world.setManager(new TagManager());
    }

    @Override
    protected void afterInit() {

    }

    @Override
    public void destory() {

    }
}
