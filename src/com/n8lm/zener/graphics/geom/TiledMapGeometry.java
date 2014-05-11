package com.n8lm.zener.graphics.geom;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.BufferUtils;

import com.n8lm.zener.math.Vector2f;
import com.n8lm.zener.math.Vector3f;
import com.n8lm.zener.math.Vector4f;
import com.n8lm.zener.data.ResourceManager;
import com.n8lm.zener.graphics.VarType;
import com.n8lm.zener.graphics.VertexBuffer;
import com.n8lm.zener.graphics.VertexBuffer.DataType;
import com.n8lm.zener.graphics.VertexBuffer.Type;
import com.n8lm.zener.graphics.VertexBuffer.Usage;
import com.n8lm.zener.map.TileSet;
import com.n8lm.zener.map.TiledMap;
import com.n8lm.zener.systems.SubRenderSystem;
import com.n8lm.zener.utils.BufferTools;
import com.n8lm.zener.utils.Byte4;

public class TiledMapGeometry extends Geometry {
	
	protected final int fragment = 2;

	protected TiledMap map;
	
	public TiledMapGeometry(TiledMap map) {
		super("tiledmap");
		
		this.map = map;
		
		loadTiledMap();
	}
	
	
	private Vector3f getNormal(int x, int y, int fragment, float ftw, float fth, float terrain[][]) {
		Vector3f v = getVertex(x, y, fragment, ftw, fth, terrain);
		
		Vector3f v1 = getVertex(x + 1, y, fragment, ftw, fth, terrain);
		Vector3f v2 = getVertex(x, y + 1, fragment, ftw, fth, terrain);
		Vector3f v3 = getVertex(x - 1, y, fragment, ftw, fth, terrain);
		Vector3f v4 = getVertex(x, y - 1, fragment, ftw, fth, terrain);

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
	
	private Vector3f getVertex(int x, int y, int fragment, float ftw, float fth, float terrain[][]) {
		return new Vector3f((x - fragment/2) * ftw, (y - fragment/2) * fth, terrain[x - fragment/2][y - fragment/2]);
	}
	
	private void loadTiledMap() {

		float tw = map.getTileWidth();
		float th = map.getTileHeight();
		float ta = map.getTileAltitude();
		int faceCount = 0;

		final float seamfix = 1.0f/128;
		final float fgw = (1.0f - seamfix * 2) /fragment;
		
		faceCount = map.getHeight() * map.getWidth() * fragment * fragment * 2;
        vertexCount = faceCount * 3;
        
        FloatBuffer vertices = BufferTools.reserveData(vertexCount * 3);
        FloatBuffer normals = BufferTools.reserveData(vertexCount * 3);
        FloatBuffer textureCoordinates = BufferTools.reserveData(vertexCount * 2);
        ByteBuffer textureIndices = BufferUtils.createByteBuffer(vertexCount * 4);
        FloatBuffer colorBuffer = BufferUtils.createFloatBuffer(vertexCount * 4);
		// default normal 
		//Vector3f normal = new Vector3f(0.0f, 0.0f, 1.0f);

	    List<Vector2f> textureCoordinatesFix = new ArrayList<Vector2f>();
		// Generate Texture Coordinates
		for(int i = 0; i < fragment; i ++)
			for(int j = 0; j < fragment; j ++) {	
				textureCoordinatesFix.add(new Vector2f(seamfix + fgw * i, seamfix + fgw * j));
				textureCoordinatesFix.add(new Vector2f(seamfix + fgw * i, seamfix + fgw * j + fgw));
				textureCoordinatesFix.add(new Vector2f(seamfix + fgw * i + fgw, seamfix + fgw * j + fgw));
				textureCoordinatesFix.add(new Vector2f(seamfix + fgw * i + fgw, seamfix + fgw * j));
			}
		
		float terrain[][] = new float[map.getWidth() * fragment + fragment][map.getHeight() * fragment + fragment];

		// Generate Original Terrain
		for(int i = 0; i < map.getWidth(); i ++)
			for(int j = 0; j < map.getHeight(); j ++) {
				for(int ii = 0; ii < fragment; ii ++) 
					for(int jj = 0; jj < fragment; jj ++) {
						if(i - 1 >= 0 && j - 1 >= 0 && ii == 0 && jj == 0)
							terrain[i * fragment + ii][j * fragment + jj] = (map.getAltitude(i, j) + map.getAltitude(i - 1, j) + map.getAltitude(i, j - 1) + map.getAltitude(i - 1, j - 1)) * ta / 4;
						else if(i - 1 >= 0 && ii == 0)
							terrain[i * fragment + ii][j * fragment + jj] = (map.getAltitude(i, j) + map.getAltitude(i - 1, j)) * ta / 2;
						else if(j - 1 >= 0 && jj == 0)
							terrain[i * fragment + ii][j * fragment + jj] = (map.getAltitude(i, j) + map.getAltitude(i, j - 1)) * ta / 2;
						else
							terrain[i * fragment + ii][j * fragment + jj] = map.getAltitude(i, j) * ta;
						//textureindices[i * fragment + ii][j * fragment + jj] = tidn * map.getData(i, j) + ii * fragment + jj; 
					}
			}
		
		// Filter Terrain
		/*for(int i = 1; i < map.getWidth() * fragment - 1; i ++)
			for(int j = 1; j < map.getHeight() * fragment - 1; j ++) {
				float height = 0.0f;
				for(int ii = -1; ii <= 1; ii ++)
					for(int jj = -1; jj <= 1; jj ++) {
						height += terrain[i + ii][j + jj];
					}
				terrain[i][j] = height / 9;
			}
		*/
		
		float ftw = tw / fragment;
		float fth = th / fragment;
		// Generate GL
		for (int i = 1; i < map.getWidth(); i ++)
			for (int j = 1; j < map.getHeight(); j ++)
				for (int ii = 0; ii < fragment; ii ++)
					for (int jj = 0; jj < fragment; jj ++) {

						int ti = i * fragment + ii;
						int tj = j * fragment + jj;

						Vector3f v1 = getVertex(ti, tj, fragment, ftw, fth, terrain);
						Vector3f v2 = getVertex(ti, tj + 1, fragment, ftw, fth, terrain);
						Vector3f v3 = getVertex(ti + 1, tj + 1, fragment, ftw, fth, terrain);
						Vector3f v4 = getVertex(ti + 1, tj, fragment, ftw, fth, terrain);
						
						Vector3f n1 = getNormal(ti, tj, fragment, ftw, fth, terrain);
						Vector3f n2 = getNormal(ti, tj + 1, fragment, ftw, fth, terrain);
						Vector3f n3 = getNormal(ti + 1, tj + 1, fragment, ftw, fth, terrain);
						Vector3f n4 = getNormal(ti + 1, tj, fragment, ftw, fth, terrain);
						
						vertices.put(BufferTools.asFloats(v3));
			        	vertices.put(BufferTools.asFloats(v2));
			        	vertices.put(BufferTools.asFloats(v1));
						
			        	vertices.put(BufferTools.asFloats(v1));
						vertices.put(BufferTools.asFloats(v4));
						vertices.put(BufferTools.asFloats(v3));
						
						textureIndices.put(BufferTools.asBytes(new Byte4(
								map.getTerrian(i - 1, j - 1),
								map.getTerrian(i - 1, j),
								map.getTerrian(i, j),
								map.getTerrian(i, j - 1))));
						textureIndices.put(BufferTools.asBytes(new Byte4(
								map.getTerrian(i - 1, j - 1),
								map.getTerrian(i - 1, j),
								map.getTerrian(i, j),
								map.getTerrian(i, j - 1))));
						textureIndices.put(BufferTools.asBytes(new Byte4(
								map.getTerrian(i - 1, j - 1),
								map.getTerrian(i - 1, j),
								map.getTerrian(i, j),
								map.getTerrian(i, j - 1))));
						textureIndices.put(BufferTools.asBytes(new Byte4(
								map.getTerrian(i - 1, j - 1),
								map.getTerrian(i - 1, j),
								map.getTerrian(i, j),
								map.getTerrian(i, j - 1))));
						textureIndices.put(BufferTools.asBytes(new Byte4(
								map.getTerrian(i - 1, j - 1),
								map.getTerrian(i - 1, j),
								map.getTerrian(i, j),
								map.getTerrian(i, j - 1))));
						textureIndices.put(BufferTools.asBytes(new Byte4(
								map.getTerrian(i - 1, j - 1),
								map.getTerrian(i - 1, j),
								map.getTerrian(i, j),
								map.getTerrian(i, j - 1))));

						/*
						normals.put(BufferTools.asFloats(normal));
						normals.put(BufferTools.asFloats(normal));
						normals.put(BufferTools.asFloats(normal));
						normals.put(BufferTools.asFloats(normal));
						normals.put(BufferTools.asFloats(normal));
						normals.put(BufferTools.asFloats(normal));*/
						
						normals.put(BufferTools.asFloats(n3));
			        	normals.put(BufferTools.asFloats(n2));
			        	normals.put(BufferTools.asFloats(n1));
			        	normals.put(BufferTools.asFloats(n1));
			        	normals.put(BufferTools.asFloats(n4));
			        	normals.put(BufferTools.asFloats(n3));
						

			        	textureCoordinates.put(BufferTools.asFloats(textureCoordinatesFix.get((ii * fragment + jj) * 4 + 2)));
			        	textureCoordinates.put(BufferTools.asFloats(textureCoordinatesFix.get((ii * fragment + jj) * 4 + 1)));
			        	textureCoordinates.put(BufferTools.asFloats(textureCoordinatesFix.get((ii * fragment + jj) * 4 + 0)));
			        	textureCoordinates.put(BufferTools.asFloats(textureCoordinatesFix.get((ii * fragment + jj) * 4 + 0)));
			        	textureCoordinates.put(BufferTools.asFloats(textureCoordinatesFix.get((ii * fragment + jj) * 4 + 3)));
			        	textureCoordinates.put(BufferTools.asFloats(textureCoordinatesFix.get((ii * fragment + jj) * 4 + 2)));

						colorBuffer.put(BufferTools.asFlippedFloatBuffer(1, 1, 1, 1));
						colorBuffer.put(BufferTools.asFlippedFloatBuffer(1, 1, 1, 1));
						colorBuffer.put(BufferTools.asFlippedFloatBuffer(1, 1, 1, 1));
						colorBuffer.put(BufferTools.asFlippedFloatBuffer(1, 1, 1, 1));
						colorBuffer.put(BufferTools.asFlippedFloatBuffer(1, 1, 1, 1));
						colorBuffer.put(BufferTools.asFlippedFloatBuffer(1, 1, 1, 1));
					
						//faces.add(new Face(vertex1, normalIndices, textureCoord1));
						//faces.add(new Face(vertex2, normalIndices, textureCoord2));
			}
		
        
        vertices.flip();
        vbs.put(VertexBuffer.Type.Position, new VertexBuffer(VertexBuffer.Type.Position, VertexBuffer.Usage.Static, VertexBuffer.DataType.Float, 3, vertexCount, vertices));

        normals.flip();
        vbs.put(VertexBuffer.Type.Normal, new VertexBuffer(VertexBuffer.Type.Normal, VertexBuffer.Usage.Static, VertexBuffer.DataType.Float, 3, vertexCount, normals));

        textureCoordinates.flip();
        vbs.put(VertexBuffer.Type.TexCoord, new VertexBuffer(VertexBuffer.Type.TexCoord, VertexBuffer.Usage.Static, VertexBuffer.DataType.Float, 2, vertexCount, textureCoordinates));
            
        textureIndices.flip();
        vbs.put(VertexBuffer.Type.Custom, new VertexBuffer(VertexBuffer.Type.Custom, VertexBuffer.Usage.Static, VertexBuffer.DataType.Byte, 4, vertexCount, textureIndices));
		//Log.info("model load successfully [vertex:" + vertices.size() + "], [normals:" + normals.size() + "], [faces:" + faces.size() + "]");
        colorBuffer.flip();
		vbs.put(VertexBuffer.Type.Color, new VertexBuffer(VertexBuffer.Type.Color, VertexBuffer.Usage.Dynamic, VertexBuffer.DataType.Float, 4, vertexCount, colorBuffer));
	
	}


	@Override
	public void update(SubRenderSystem subRenderSystem) {
		// TODO Auto-generated method stub
		
	}

	public void updateHighlightArea(Vector4f color[][]) {

        FloatBuffer colorBuffer = BufferUtils.createFloatBuffer(vertexCount * 4);
		
		for (int i = 1; i < map.getWidth(); i ++)
			for (int j = 1; j < map.getHeight(); j ++)
				for (int ii = 0; ii < fragment; ii ++)
					for (int jj = 0; jj < fragment; jj ++) {
						Vector4f colorV = null;
						if(ii == 0 && jj == 0)
							colorV = color[i - 1][j - 1];
						else if(ii == 1 && jj == 0)
							colorV = color[i][j - 1];
						else if(ii == 0 && jj == 1)
							colorV = color[i - 1][j];
						else if(ii == 1 && jj == 1)
							colorV = color[i][j];
						colorBuffer.put(BufferTools.asFloats(colorV));
						colorBuffer.put(BufferTools.asFloats(colorV));
						colorBuffer.put(BufferTools.asFloats(colorV));
						colorBuffer.put(BufferTools.asFloats(colorV));
						colorBuffer.put(BufferTools.asFloats(colorV));
						colorBuffer.put(BufferTools.asFloats(colorV));
					}
		
		colorBuffer.flip();
		VertexBuffer vb = this.vbs.get(Type.Color);
		vb.setData(colorBuffer);
		vb.updateAllData();
	}
}
