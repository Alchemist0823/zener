package com.n8lm.zener.map;

import com.n8lm.zener.data.ResourceManager;
import com.n8lm.zener.graphics.material.UniformsMaterial;
import com.n8lm.zener.glsl.VarType;

public class TiledMapMaterial extends UniformsMaterial {
	
	public TiledMapMaterial(TileSet ts) {
		super("tiledmap");
		for (int i = 0; i < ts.getTileCount(); i ++) {
			addUniform("TileSet.texture[" + i + "]", VarType.Texture2D, ResourceManager.getInstance().getTexture(ts.getTextureName(i)));
		}
	}
	/*
	*/
}
