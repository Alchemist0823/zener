package com.n8lm.zener.map;

import com.n8lm.zener.data.ResourceManager;
import com.n8lm.zener.glsl.VarType;
import com.n8lm.zener.graphics.material.UniformsMaterial;

/**
 * Created on 2014/8/27.
 *
 * @author Alchemist
 */
public class TileMaterial extends UniformsMaterial {

    public TileMaterial(TiledMap map, Location loc) {

        int texPos[] = {0, 0, 0, 0};
        int texFI[] = new int[4];
        MapRenderHelper.calcTexturePos(map, loc, texPos, texFI);


        for (int i = 0; i < 4; i ++) {
            if (texPos[i] == -1)
                break;
            addUniform("texture[" + i + "]", VarType.Texture2D, ResourceManager.getInstance().getTexture(map.getTileSet().getTile(texFI[i]).getTextureName()));
        }
    }

    @Override
    public String getShaderName() {
        return "tile";
    }

    public void updateTexture(TiledMap tiledMap, Location location, int[][] vis) {

        int texPos[] = {0, 0, 0, 0};
        int texFI[] = new int[4];

        MapRenderHelper.calcTexturePosWithVis(tiledMap, location, vis, texPos, texFI);

        for (int i = 0; i < 4; i ++) {
            if (texPos[i] == -1)
                break;
            if (texFI[i] != 9) {
                //System.out.print(texFI[i]);
                //System.out.println(tiledMap.getTileSet().getTile(texFI[i]).getTextureName());
                setUniform("texture[" + i + "]", VarType.Texture2D, ResourceManager.getInstance().getTexture(tiledMap.getTileSet().getTile(texFI[i]).getTextureName()));
            } else
                setUniform("texture[" + i + "]", VarType.Texture2D, ResourceManager.getInstance().getTexture("warfog"));
        }

    }
}
