package com.n8lm.zener.data;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.artemis.World;

public abstract class UniqueGameInfo {

	protected GameInfoManager gameInfoManager;
	protected World world;
	
	protected UniqueGameInfo(World world) {
		this.world = world;
	}

	void setGameInfoManager(GameInfoManager gameInfoManager) {
		this.gameInfoManager = gameInfoManager;
	}
	
	public World getWorld() {
		return world;
	}
	
	public abstract void read(InputStream input) throws IOException;
	public abstract void write(OutputStream output) throws IOException;
	
	public abstract void reset();

}
