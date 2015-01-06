package com.n8lm.zener;

import com.artemis.World;
import com.artemis.managers.TagManager;
import com.n8lm.zener.app.AbstractAppContainer;
import com.n8lm.zener.app.AppState;
import com.n8lm.zener.app.AppStateManager;
import com.n8lm.zener.app.BasicApp;

/**
 * Created on 2014/7/4.
 * @author Alchemist
 */
public abstract class ExampleBasicApp extends BasicApp implements AppState{

    protected String gameName;
    protected World world;

    public ExampleBasicApp(String gameName, String gameTitle) {
        super(gameTitle);
        this.gameName = gameName;
    }

    @Override
    protected void init() {

        resourceManager.loadDataConfig("./res/" + this.gameName + "/config.cfg");

        appStateManager.attach(this);
        /*
        world = new World("game");
        world.setManager(new TagManager());

        addWorld(world);*/
    }

    @Override
    public void initialize(AppStateManager appStateManager, BasicApp app) {
        world = new World("game");
        world.setManager(new TagManager());

        app.addWorld(world);
    }

    @Override
    public void destory() {

    }


    @Override
    public void update(int delta) {
        world.setDelta(delta);
        world.process();
    }
}
