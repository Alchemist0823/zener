package com.n8lm.zenertest;

import com.artemis.World;
import com.artemis.managers.TagManager;
import com.n8lm.zener.app.BasicGame;

/**
 * Created on 2014/7/4.
 * @author Alchemist
 */
public class ExampleBasicGame extends BasicGame{

    protected String gameName;
    protected World world;

    public ExampleBasicGame(String gameName, String gameTitle) {
        super(gameTitle);
        this.gameName = gameName;
    }

    @Override
    protected void init() {

        resourceManager.loadDataConfig("./res/" + this.gameName + "/config.cfg");

        world = new World();
        world.setManager(new TagManager());

        setWorld("game", world);
    }

    @Override
    public void destory() {

    }
}
