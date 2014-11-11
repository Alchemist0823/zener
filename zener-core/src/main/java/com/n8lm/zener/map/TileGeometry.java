package com.n8lm.zener.map;

import com.n8lm.zener.graphics.VertexBuffer;
import com.n8lm.zener.graphics.ViewRenderSystem;
import com.n8lm.zener.graphics.geom.Geometry;
import com.n8lm.zener.math.Vector2f;
import com.n8lm.zener.math.Vector3f;
import com.n8lm.zener.math.Vector4f;
import com.n8lm.zener.utils.BufferTools;
import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;

/**
 * Created on 2014/8/27.
 *
 * @author Alchemist
 */
public class TileGeometry extends Geometry {

    protected final int fragment = 2;

    public TileGeometry(TiledMapRenderingSystem tmrs, TiledMap map, Location loc) {
        super("tile_" + loc.x + "x" + loc.y, "tile");

        //float tw = map.getTileWidth();
        //float th = map.getTileHeight();
        //float ta = map.getTileAltitude();
        //float ftw = tw / fragment;
        //float fth = th / fragment;

        final float seamfix = 1.0f/128;
        final float fgw = (1.0f - seamfix * 2) /fragment;
        final float textureHeight = 1.0f / 4;
        final float textureWidth = 1.0f / 8;

        int faceCount = fragment * fragment * 2;
        vertexCount = faceCount * 3;

        FloatBuffer vertices = BufferTools.reserveData(vertexCount * 3);
        FloatBuffer normals = BufferTools.reserveData(vertexCount * 3);
        FloatBuffer colorBuffer = BufferUtils.createFloatBuffer(vertexCount * 4);
        FloatBuffer textureCoordinates[] = new FloatBuffer[4];
        for (int k = 0; k < 4; k ++)
            textureCoordinates[k] = BufferTools.reserveData(vertexCount * 2);

        int x = loc.x;
        int y = loc.y;

        int texPos[] = {0, 0, 0, 0};
        int texFI[] = new int[4];
        int texNum = MapRenderHelper.calcTexturePos(map, loc, texPos, texFI);

        int textureConstant = 0;
        if (texNum == 1 && texPos[0] == 15) {
            texPos[0] = map.getTexture(x, y);
            //System.out.println(texPos[0]);
            textureConstant = 4;
        }

        // Generate GL
        for (int ii = 0; ii < fragment; ii++)
            for (int jj = 0; jj < fragment; jj++) {

                int ti = x * fragment + ii;
                int tj = y * fragment + jj;

                Vector3f v1 = tmrs.getVertex(ti, tj);
                Vector3f v2 = tmrs.getVertex(ti, tj + 1);
                Vector3f v3 = tmrs.getVertex(ti + 1, tj + 1);
                Vector3f v4 = tmrs.getVertex(ti + 1, tj);

                Vector3f n1 = tmrs.getNormal(ti, tj);
                Vector3f n2 = tmrs.getNormal(ti, tj + 1);
                Vector3f n3 = tmrs.getNormal(ti + 1, tj + 1);
                Vector3f n4 = tmrs.getNormal(ti + 1, tj);

                vertices.put(BufferTools.asFloats(v3));
                vertices.put(BufferTools.asFloats(v2));
                vertices.put(BufferTools.asFloats(v1));

                vertices.put(BufferTools.asFloats(v1));
                vertices.put(BufferTools.asFloats(v4));
                vertices.put(BufferTools.asFloats(v3));

                normals.put(BufferTools.asFloats(n3));
                normals.put(BufferTools.asFloats(n2));
                normals.put(BufferTools.asFloats(n1));
                normals.put(BufferTools.asFloats(n1));
                normals.put(BufferTools.asFloats(n4));
                normals.put(BufferTools.asFloats(n3));

                Vector2f otc1 = new Vector2f(seamfix + fgw * ii, seamfix + fgw * jj);
                Vector2f otc2 = new Vector2f(seamfix + fgw * ii, seamfix + fgw * jj + fgw);
                Vector2f otc3 = new Vector2f(seamfix + fgw * ii + fgw, seamfix + fgw * jj + fgw);
                Vector2f otc4 = new Vector2f(seamfix + fgw * ii + fgw, seamfix + fgw * jj);

                for (int k = 0; k < 4; k ++) {

                    if (texPos[k] == -1) {
                        textureCoordinates[k].put(BufferTools.asFloats(new Vector2f(-1.0f, -1.0f)));
                        textureCoordinates[k].put(BufferTools.asFloats(new Vector2f(-1.0f, -1.0f)));
                        textureCoordinates[k].put(BufferTools.asFloats(new Vector2f(-1.0f, -1.0f)));

                        textureCoordinates[k].put(BufferTools.asFloats(new Vector2f(-1.0f, -1.0f)));
                        textureCoordinates[k].put(BufferTools.asFloats(new Vector2f(-1.0f, -1.0f)));
                        textureCoordinates[k].put(BufferTools.asFloats(new Vector2f(-1.0f, -1.0f)));
                    } else {

                        Vector2f tc1 = new Vector2f((texPos[k] % 4 + textureConstant) * textureWidth + otc1.x / 8,
                                (texPos[k] / 4) * textureHeight + otc1.y / 4);
                        Vector2f tc2 = new Vector2f((texPos[k] % 4 + textureConstant) * textureWidth + otc2.x / 8,
                                (texPos[k] / 4) * textureHeight + otc2.y / 4);
                        Vector2f tc3 = new Vector2f((texPos[k] % 4 + textureConstant) * textureWidth + otc3.x / 8,
                                (texPos[k] / 4) * textureHeight + otc3.y / 4);
                        Vector2f tc4 = new Vector2f((texPos[k] % 4 + textureConstant) * textureWidth + otc4.x / 8,
                                (texPos[k] / 4) * textureHeight + otc4.y / 4);

                        textureCoordinates[k].put(BufferTools.asFloats(tc3));
                        textureCoordinates[k].put(BufferTools.asFloats(tc2));
                        textureCoordinates[k].put(BufferTools.asFloats(tc1));

                        textureCoordinates[k].put(BufferTools.asFloats(tc1));
                        textureCoordinates[k].put(BufferTools.asFloats(tc4));
                        textureCoordinates[k].put(BufferTools.asFloats(tc3));
                    }
                }

                colorBuffer.put(BufferTools.asFlippedFloatBuffer(1, 1, 1, 1));
                colorBuffer.put(BufferTools.asFlippedFloatBuffer(1, 1, 1, 1));
                colorBuffer.put(BufferTools.asFlippedFloatBuffer(1, 1, 1, 1));
                colorBuffer.put(BufferTools.asFlippedFloatBuffer(1, 1, 1, 1));
                colorBuffer.put(BufferTools.asFlippedFloatBuffer(1, 1, 1, 1));
                colorBuffer.put(BufferTools.asFlippedFloatBuffer(1, 1, 1, 1));
            }

        vertices.flip();
        vbs.put(VertexBuffer.Type.Position, new VertexBuffer(VertexBuffer.Type.Position, VertexBuffer.Usage.Static, VertexBuffer.DataType.Float, 3, vertexCount, vertices));

        normals.flip();
        vbs.put(VertexBuffer.Type.Normal, new VertexBuffer(VertexBuffer.Type.Normal, VertexBuffer.Usage.Static, VertexBuffer.DataType.Float, 3, vertexCount, normals));

        for (int k = 0; k < 4; k ++) {
            textureCoordinates[k].flip();
            //System.out.println(VertexBuffer.Type.values()[VertexBuffer.Type.TexCoord1.id + k]);
            vbs.put(VertexBuffer.Type.values()[VertexBuffer.Type.TexCoord1.id + k], new VertexBuffer(VertexBuffer.Type.values()[VertexBuffer.Type.TexCoord1.id + k], VertexBuffer.Usage.Static, VertexBuffer.DataType.Float, 2, vertexCount, textureCoordinates[k]));
        }
        //textureIndices.flip();
        //vbs.put(VertexBuffer.Type.Custom, new VertexBuffer(VertexBuffer.Type.Custom, VertexBuffer.Usage.Static, VertexBuffer.DataType.Byte, 4, vertexCount, textureIndices));
        //LOGGER.info("model load successfully [vertex:" + vertices.size() + "], [normals:" + normals.size() + "], [faces:" + faces.size() + "]");
        colorBuffer.flip();
        vbs.put(VertexBuffer.Type.Color, new VertexBuffer(VertexBuffer.Type.Color, VertexBuffer.Usage.Static, VertexBuffer.DataType.Float, 4, vertexCount, colorBuffer));
    }

    @Override
    public void update(ViewRenderSystem subRenderSystem) {

    }

    public void updateColor(TiledMap map, Location location, Vector4f color[][]) {

        FloatBuffer colorBuffer = BufferUtils.createFloatBuffer(vertexCount * 4);

        int i = location.x;
        int j = location.y;
        for (int ii = 0; ii < fragment; ii ++)
            for (int jj = 0; jj < fragment; jj ++) {
                Vector4f colorV = null;
                if(ii == 0 && jj == 0)
                    colorV = color[i][j];
                else if(ii == 1 && jj == 0)
                    colorV = color[i + 1][j];
                else if(ii == 0 && jj == 1)
                    colorV = color[i][j + 1];
                else if(ii == 1 && jj == 1)
                    colorV = color[i + 1][j + 1];
                colorBuffer.put(BufferTools.asFloats(colorV));
                colorBuffer.put(BufferTools.asFloats(colorV));
                colorBuffer.put(BufferTools.asFloats(colorV));
                colorBuffer.put(BufferTools.asFloats(colorV));
                colorBuffer.put(BufferTools.asFloats(colorV));
                colorBuffer.put(BufferTools.asFloats(colorV));
            }

        colorBuffer.flip();
        VertexBuffer vb = this.vbs.get(VertexBuffer.Type.Color);
        vb.setData(colorBuffer);
        vb.updateAllData();
    }

    public void updateTexture(TiledMap map, Location location, int vis[][]) {

        int texPos[] = {0, 0, 0, 0};
        int texFI[] = new int[4];
        int texNum = MapRenderHelper.calcTexturePosWithVis(map, location, vis, texPos, texFI);

        // special texture
        int textureConstant = 0;
        if (texNum == 1 && texPos[0] == 15) {
            texPos[0] = map.getTexture(location.x, location.y);
            //System.out.println(texPos[0]);
            textureConstant = 4;
        }

        FloatBuffer textureCoordinates[] = new FloatBuffer[4];
        for (int k = 0; k < 4; k ++)
            textureCoordinates[k] = BufferTools.reserveData(vertexCount * 2);

        final float seamfix = 1.0f/128;
        final float fgw = (1.0f - seamfix * 2) /fragment;
        final float textureHeight = 1.0f / 4;
        final float textureWidth = 1.0f / 8;
        // Generate GL
        for (int ii = 0; ii < fragment; ii++)
            for (int jj = 0; jj < fragment; jj++) {

                Vector2f otc1 = new Vector2f(seamfix + fgw * ii, seamfix + fgw * jj);
                Vector2f otc2 = new Vector2f(seamfix + fgw * ii, seamfix + fgw * jj + fgw);
                Vector2f otc3 = new Vector2f(seamfix + fgw * ii + fgw, seamfix + fgw * jj + fgw);
                Vector2f otc4 = new Vector2f(seamfix + fgw * ii + fgw, seamfix + fgw * jj);

                for (int k = 0; k < 4; k ++) {

                    if (texPos[k] == -1) {
                        textureCoordinates[k].put(BufferTools.asFloats(new Vector2f(-1.0f, -1.0f)));
                        textureCoordinates[k].put(BufferTools.asFloats(new Vector2f(-1.0f, -1.0f)));
                        textureCoordinates[k].put(BufferTools.asFloats(new Vector2f(-1.0f, -1.0f)));

                        textureCoordinates[k].put(BufferTools.asFloats(new Vector2f(-1.0f, -1.0f)));
                        textureCoordinates[k].put(BufferTools.asFloats(new Vector2f(-1.0f, -1.0f)));
                        textureCoordinates[k].put(BufferTools.asFloats(new Vector2f(-1.0f, -1.0f)));
                    } else {
                        Vector2f tc1 = new Vector2f((texPos[k] % 4 + textureConstant) * textureWidth + otc1.x / 8,
                                (texPos[k] / 4) * textureHeight + otc1.y / 4);
                        Vector2f tc2 = new Vector2f((texPos[k] % 4 + textureConstant) * textureWidth + otc2.x / 8,
                                (texPos[k] / 4) * textureHeight + otc2.y / 4);
                        Vector2f tc3 = new Vector2f((texPos[k] % 4 + textureConstant) * textureWidth + otc3.x / 8,
                                (texPos[k] / 4) * textureHeight + otc3.y / 4);
                        Vector2f tc4 = new Vector2f((texPos[k] % 4 + textureConstant) * textureWidth + otc4.x / 8,
                                (texPos[k] / 4) * textureHeight + otc4.y / 4);

                        textureCoordinates[k].put(BufferTools.asFloats(tc3));
                        textureCoordinates[k].put(BufferTools.asFloats(tc2));
                        textureCoordinates[k].put(BufferTools.asFloats(tc1));

                        textureCoordinates[k].put(BufferTools.asFloats(tc1));
                        textureCoordinates[k].put(BufferTools.asFloats(tc4));
                        textureCoordinates[k].put(BufferTools.asFloats(tc3));
                    }
                }

            }
        for (int k = 0; k < 4; k ++) {
            textureCoordinates[k].flip();
            VertexBuffer vb = this.vbs.get(VertexBuffer.Type.values()[VertexBuffer.Type.TexCoord1.id + k]);
            vb.setData(textureCoordinates[k]);
            vb.updateAllData();
        }
    }
}
