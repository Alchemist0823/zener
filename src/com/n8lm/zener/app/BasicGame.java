package com.n8lm.zener.app;

import com.artemis.World;
import com.n8lm.zener.data.GameInfoManager;
import com.n8lm.zener.data.ResourceManager;

/**
 * Basic Game
 * Any game class should extend this class
 * @author Alchemist
 *
 */
public abstract class BasicGame {
	
	static protected BasicGame game;
	
	protected World world;
	protected GameContainer container;
	protected ResourceManager resourceManager;
	protected GameInfoManager gameInfoManager;
	protected String title;

	protected boolean gameStarted;
	protected boolean running;
	protected boolean isCloseRequested;
	
	static public BasicGame getInstance() {
		return game;
	}
	
	public BasicGame(String title) {
		super();
		this.title = title;
		game = this;
	}


	public boolean isGameRunning() {
		return running;
	}

	public boolean isGameStarted() {
		return gameStarted;
	}
	
	public void startNewGame() {
		gameStarted = true;
		running = true;
	}
	
	/**
	 * Initialize the game. This can be used to load static resources. It's called
	 * before the game loop starts
	 * 
	 * @param container The container holding the game
	 */
	public void init(GameContainer container) {
		this.container = container;
		
		gameStarted = running = isCloseRequested = false;
		
		resourceManager = new ResourceManager();
		gameInfoManager = new GameInfoManager();
		
        world = new World();
        
		initManagers();
		
		initDatabase();
        
        initSystems();
        
        resourceManager.initialize();

        world.initialize();
        
        initEntities();
	}

	/**
	 * Set up Database Resource
	 */
	protected abstract void initDatabase();

	/**
	 * Set up Manager
	 */
	protected abstract void initManagers();

	/**
	 * Set up EntitySystems
	 */
	protected abstract void initSystems();
	
	/**
	 * Initialize entities
	 */
	protected abstract void initEntities();
	
	/**
	 * Update the game logic here. No rendering should take place in this method
	 * though it won't do any harm. 
	 * 
	 * @param container The container holing this game
	 * @param delta The amount of time thats passed since last update in milliseconds
	 */
	public void update(GameContainer container, int delta) {
        world.setDelta(delta);
        world.process();
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

	public World getWorld() {
		return world;
	}
	
}
