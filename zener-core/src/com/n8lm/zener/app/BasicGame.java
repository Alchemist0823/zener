/*
 * This file is part of Zener.
 *
 * Zener is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of
 * the License, or any later version.
 *
 * Zener is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General
 * Public License along with Zener.  If not, see
 * <http://www.gnu.org/licenses/>.
 */

package com.n8lm.zener.app;

import com.artemis.World;
import com.n8lm.zener.data.GameInfoManager;
import com.n8lm.zener.data.ResourceManager;
import com.n8lm.zener.intent.InputIntentGenerator;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * Basic Game
 * Any game class should extend this class
 * @author Alchemist
 *
 */
public abstract class BasicGame {
	
	static protected BasicGame game;
	
	protected Map<String, World> worlds;
	protected GameContainer container;
	protected ResourceManager resourceManager;
	protected GameInfoManager gameInfoManager;
	protected String title;

	//protected boolean gameStarted;
	//protected boolean running;
	protected boolean isCloseRequested;
	
	static public BasicGame getInstance() {
		return game;
	}
	
	public BasicGame(String title) {
		super();
		this.title = title;
        worlds = new TreeMap<String, World>();
		game = this;
	}
	
	/**
	 * Initialize the game. This can be used to load static resources. It's called
	 * before the game loop starts
	 * 
	 * @param container The container holding the game
	 */
	public void init(GameContainer container) {
		this.container = container;
		
		//gameStarted = running =
        isCloseRequested = false;
		
		resourceManager = new ResourceManager();
		gameInfoManager = new GameInfoManager();

		init();
        
        //afterInit();
	}

    public void setWorld(String worldName, World world) {
        worlds.put(worldName, world);
    }

	/**
	 * Set up Database, Resource, EntitySystems and Managers
	 */
	protected abstract void init();
	
	/**
	 * Initialize entities
	protected abstract void afterInit();
    */
	
	/**
	 * Update the game logic here. No rendering should take place in this method
	 * though it won't do any harm. 
	 * 
	 * @param container The container holing this game
	 * @param delta The amount of time thats passed since last update in milliseconds
	 */
	public void update(GameContainer container, int delta) {
        for (World world : worlds.values()) {
            world.setDelta(delta);
            world.process();
        }
	}
	
	/**
	 * Notification that a game close has been requested
	 * 
	 * @return True if the game should close
	 */
	public boolean closeRequested() {
		return isCloseRequested;
	}
	
	public void close() {
		isCloseRequested = true;
	}

	public GameContainer getContainer() {
		return container;
	}

    /*public void addInputIntentGenerator(InputIntentGenerator iig) {
        this.getContainer().getInput().addListener(iig);
    }*/

	/**
	 * Get the title of this game
	 * 
	 * @return The title of the game
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * Called to cleanup any resources that this game may own.
	 */
	public abstract void destory();

	public World getWorld(String name) {
		return worlds.get(name);
	}
	
}
