package com.n8lm.zener;

import com.artemis.World;
import com.artemis.managers.TagManager;
import com.n8lm.zener.app.BasicApp;

/**
 * Created on 2014/7/4.
 * @author Alchemist
 */
public class ExampleBasicApp extends BasicApp {

    protected String gameName;
    protected World world;

    public ExampleBasicApp(String gameName, String gameTitle) {
        super(gameTitle);
        this.gameName = gameName;
    }

    @Override
    protected void init() {

        resourceManager.loadDataConfig("./res/" + this.gameName + "/config.cfg");

        world = new World("game");
        world.setManager(new TagManager());

        addWorld(world);
    }

    @Override
    public void destory() {

    }
}
