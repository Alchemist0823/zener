package com.n8lm.zener.graphics.material;

import com.n8lm.zener.data.ResourceManager;
import com.n8lm.zener.graphics.UniformGroup;
import com.n8lm.zener.graphics.VarType;
import com.n8lm.zener.map.TileSet;

public class TiledMapMaterialUniforms extends UniformGroup {
	
	public TiledMapMaterialUniforms(TileSet ts) {
		super();
		for (int i = 0; i < ts.getTileCount(); i ++) {
			addUniform("TileSet.texture[" + i + "]", VarType.Texture2D, ResourceManager.getInstance().getTexture(ts.getTextureName(i)));
		}
	}
	/*
	*/
}
