package com.n8lm.zener.map;

import com.artemis.Entity;
import com.artemis.systems.VoidEntitySystem;
import com.n8lm.zener.graphics.GeometryComponent;
import com.n8lm.zener.graphics.MaterialComponent;
import com.n8lm.zener.graphics.VertexBuffer;
import com.n8lm.zener.math.Quaternion;
import com.n8lm.zener.math.Vector2f;
import com.n8lm.zener.math.Vector3f;
import com.n8lm.zener.math.Vector4f;
import com.n8lm.zener.utils.BufferTools;
import com.n8lm.zener.utils.Byte4;
import com.n8lm.zener.utils.EntityFactory;
import org.lwjgl.BufferUtils;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 * Created on 2014/8/27.
 *
 * @author Alchemist
 */
public class TiledMapRenderingSystem extends VoidEntitySystem{

    protected TiledMap tiledMap;
    //private List<Vector2f> textureCoordinatesFix;

    private Entity tiles[][];
    private Entity mapEntity;

    public void init(TiledMap map) {
        this.tiledMap = map;

        /*
        final float seamfix = 1.0f/128;
        final float fgw = (1.0f - seamfix * 2) /fragment;
        textureCoordinatesFix = new ArrayList<Vector2f>();
        for(int i = 0; i < fragment; i ++)
            for(int j = 0; j < fragment; j ++) {
                textureCoordinatesFix.add(new Vector2f(seamfix + fgw * i, seamfix + fgw * j));
                textureCoordinatesFix.add(new Vector2f(seamfix + fgw * i, seamfix + fgw * j + fgw));
                textureCoordinatesFix.add(new Vector2f(seamfix + fgw * i + fgw, seamfix + fgw * j + fgw));
                textureCoordinatesFix.add(new Vector2f(seamfix + fgw * i + fgw, seamfix + fgw * j));
            }*/

        try {
            loadTiledMap();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    protected final int fragment = 2;

    protected float ftw;
    protected float fth;

    protected float terrain[][];

    Vector3f getNormal(int x, int y) {
        Vector3f v = getVertex(x, y);

        Vector3f v1,v2,v3,v4;
        if (x + 1 < terrain.length)
            v1 = getVertex(x + 1, y);
        else
            v1 = v.add(1, 0, 0);

        if (y + 1< terrain[0].length)
            v2 = getVertex(x, y + 1);
        else
            v2 = v.add(0, 1, 0);

        if (x > 0)
            v3 = getVertex(x - 1, y);
        else
            v3 = v.add(-1, 0, 0);

        if (y > 0)
            v4 = getVertex(x, y - 1);
        else
            v4 = v.add(0, -1, 0);

        v1.subtract(v, v1);
        v2.subtract(v, v2);
        v3.subtract(v, v3);
        v4.subtract(v, v4);

        Vector3f normal = new Vector3f();
        Vector3f temp = new Vector3f();

        v1.cross(v2, temp);
        temp.normalize();
        temp.add(normal, normal);
        v2.cross(v3, temp);
        temp.normalize();
        temp.add(normal, normal);
        v3.cross(v4, temp);
        temp.normalize();
        temp.add(normal, normal);
        v4.cross(v1, temp);
        temp.normalize();
        temp.add(normal, normal);
        normal.normalize();
        return normal;
    }

    Vector3f getVertex(int x, int y) {
        return new Vector3f(x * ftw, y * fth, terrain[x][y]);
    }

    private void loadTiledMap() {

        // default normal
        //Vector3f normal = new Vector3f(0.0f, 0.0f, 1.0f);
        // Generate Texture Coordinates

        float tw = tiledMap.getTileWidth();
        float th = tiledMap.getTileHeight();
        float ta = tiledMap.getTileAltitude();

        ftw = tw / fragment;
        fth = th / fragment;

        terrain = new float[tiledMap.getWidth() * fragment][tiledMap.getHeight() * fragment];

        // Generate Original Terrain
        for (int i = 0; i < tiledMap.getWidth() - 1; i++)
            for (int j = 0; j < tiledMap.getHeight() - 1; j++) {
                for (int ii = 0; ii <= fragment; ii++)
                    for (int jj = 0; jj <= fragment; jj++) {

                        float fx = 1.0f / fragment * ii;
                        float fy = 1.0f / fragment * jj;
                        float dx = 1 - fx;
                        float dy = 1 - fy;

                        terrain[i * fragment + ii][j * fragment + jj] = (tiledMap.getAltitude(i, j) * dx * dy + tiledMap.getAltitude(i + 1, j) * (1 - dx) * dy + tiledMap.getAltitude(i, j + 1) * dx * (1 - dy) + tiledMap.getAltitude(i + 1, j + 1) * (1 - dx) * (1 - dy)) * ta;

                        //textureindices[i * fragment + ii][j * fragment + jj] = tidn * tiledMap.getData(i, j) + ii * fragment + jj;
                    }
            }

        tiles = new Entity[tiledMap.getWidth()][tiledMap.getHeight()];
        for (int i = 0; i < tiledMap.getWidth() - 1; i++)
            for (int j = 0; j < tiledMap.getHeight() - 1; j++) {
                Location loc = new Location(i, j);
                tiles[i][j] = EntityFactory.createDisplayObject(Vector3f.ZERO, Quaternion.IDENTITY, new TileGeometry(this, tiledMap, loc),
                        new TileMaterial(tiledMap, loc), false, true, null, null);
                world.addEntity(tiles[i][j]);
            }
        mapEntity = EntityFactory.createAttachableObject(Vector3f.ZERO, Quaternion.IDENTITY, null, null);
        // Filter Terrain
		/*for(int i = 1; i < tiledMap.getWidth() * fragment - 1; i ++)
			for(int j = 1; j < tiledMap.getHeight() * fragment - 1; j ++) {
				float height = 0.0f;
				for(int ii = -1; ii <= 1; ii ++)
					for(int jj = -1; jj <= 1; jj ++) {
						height += terrain[i + ii][j + jj];
					}
				terrain[i][j] = height / 9;
			}
		*/
    }

    public void updateHighlightArea(Vector4f color[][]) {

        for (int i = 0; i < tiledMap.getWidth() - 1; i ++)
            for (int j = 0; j < tiledMap.getHeight() - 1; j ++)
                ((TileGeometry) tiles[i][j].getComponent(GeometryComponent.class).getGeometry()).updateColor(tiledMap, new Location(i, j), color);
    }

    public void updateVisibleArea(int vis[][]) {
        for (int i = 0; i < tiledMap.getWidth() - 1; i ++)
            for (int j = 0; j < tiledMap.getHeight() - 1; j ++) {
                if (vis[i][j] == 1 || vis[i + 1][j] == 1 || vis[i][j + 1] == 1 || vis[i + 1][j + 1] == 1) {
                    tiles[i][j].getComponent(GeometryComponent.class).setVisible(true);
                    ((TileGeometry) tiles[i][j].getComponent(GeometryComponent.class).getGeometry()).updateTexture(tiledMap, new Location(i, j), vis);
                    ((TileMaterial) tiles[i][j].getComponent(MaterialComponent.class).getMaterial()).updateTexture(tiledMap, new Location(i, j), vis);
                } else {
                    tiles[i][j].getComponent(GeometryComponent.class).setVisible(false);
                }
            }

    }

    @Override
    protected void processSystem() {

    }

    public Entity getMapEntity() {
        return mapEntity;
    }

    public TiledMap getTiledMap() {
        return tiledMap;
    }
}
