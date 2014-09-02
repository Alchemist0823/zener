package com.n8lm.zener.map;

/**
 * Created on 2014/8/27.
 *
 * @author Alchemist
 */
public class MapRenderHelper {

    /**
     * Calculate Tile Texture Position
     * 0 1 2 3
     * 4 5 6 7
     * 8 9 10 11
     * 12 13 14 15
     * @param map Tiled Map
     * @param loc Location of the tile
     * @param texPos output texture postion
     * @param texFI output texture index
     * @return the number of textures
     */
    public static int calcTexturePos(TiledMap map, Location loc, int[] texPos, int[] texFI) {
        return calcTexturePosWithVis(map, loc, null, texPos, texFI);
    }


    private static int getTextureId(TiledMap map, int vis[][], int i, int j) {
        if (vis[i][j] == 1)
            return map.getTerrian(i, j);
        else
            return 9;
    }
    /**
     * Calculate Tile Texture Position With Visibility
     * 0 1 2 3
     * 4 5 6 7
     * 8 9 10 11
     * 12 13 14 15
     * @param map Tiled Map
     * @param loc Location of the tile
     * @param vis visibility of the tiledmap
     * @param texPos output texture position
     * @param texFI output texture index
     * @return the number of textures
     */
    public static int calcTexturePosWithVis(TiledMap map, Location loc, int vis[][], int[] texPos, int[] texFI) {

        int texI[] = new int[4];
        int texP[] = new int[4];
        if (vis != null) {
            texI[0] = getTextureId(map, vis, loc.x, loc.y);
            texI[1] = getTextureId(map, vis, loc.x, loc.y + 1);
            texI[2] = getTextureId(map, vis, loc.x + 1, loc.y + 1);
            texI[3] = getTextureId(map, vis, loc.x + 1, loc.y);
        } else {
            texI[0] = map.getTerrian(loc.x, loc.y);
            texI[1] = map.getTerrian(loc.x, loc.y + 1);
            texI[2] = map.getTerrian(loc.x + 1, loc.y + 1);
            texI[3] = map.getTerrian(loc.x + 1, loc.y);
        }
        texP[0] = 3;
        texP[1] = 1;
        texP[2] = 0;
        texP[3] = 2;

        int tmp;
        for (int i = 0; i < 4; i ++)
            for (int j = 0; j < 3; j ++)
                if (texI[j] > texI[j+1])
                {
                    tmp = texI[j];
                    texI[j] = texI[j+1];
                    texI[j+1] = tmp;

                    tmp = texP[j];
                    texP[j] = texP[j+1];
                    texP[j+1] = tmp;
                }


        final int posc[] = {1, 2, 4, 8};

        int pos = 0;
        int texNum = 0;

        for (int j = 0; j < 4; j ++) {
            pos += posc[texP[j]];

            if (j == 3 || texI[j] != texI[j+1]) {
                if (texNum == 0)
                    texPos[0] = 15;
                else
                    texPos[texNum] = pos;
                texFI[texNum ++] = texI[j];
                pos = 0;
            }
        }

        int k = texNum;

        while (k < 4) {
            texPos[k ++] = -1;
        }

        return texNum;
    }
}
