package com.n8lm.zener.app;

import com.artemis.World;
import com.n8lm.zener.data.GameInfoManager;
import com.n8lm.zener.data.ResourceManager;

import java.util.Map;
import java.util.TreeMap;

/**
 * Created on 2014/11/1.
 *
 * @author Alchemist
 */
public abstract class BasicServer {


    protected Map<String, World> worlds;
    protected final ResourceManager resourceManager = ResourceManager.getInstance();
    protected GameInfoManager gameInfoManager;
    private ServerContainer container;

    public BasicServer() {
        worlds = new TreeMap<String, World>();
        gameInfoManager = new GameInfoManager();
    }

    public void init(ServerContainer container) {
        this.container = container;
        init();
    }


    /**
     * Initialize the game. This can be used to load static resources. It's called
     * before the game loop starts
     *
     */

    protected abstract void init();

    public void setWorld(String worldName, World world) {
        worlds.put(worldName, world);
    }

    /**
     * Update the game logic here. No rendering should take place in this method
     * though it won't do any harm.
     *
     * @param delta The amount of time thats passed since last update in milliseconds
     */
    public void update(int delta) {
        for (World world : worlds.values()) {
            world.setDelta(delta);
            world.process();
        }
    }

    /**
     * Called to cleanup any resources that this game may own.
     */
    public abstract void destory();

    public World getWorld(String name) {
        return worlds.get(name);
    }


}
