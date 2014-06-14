package com.n8lm.zener.graphics.geom;

import java.nio.FloatBuffer;

import com.n8lm.zener.graphics.ViewRenderSystem;
import com.n8lm.zener.graphics.Texture;
import com.n8lm.zener.graphics.VertexBuffer;
import com.n8lm.zener.math.Vector2f;
import com.n8lm.zener.math.Vector3f;
import com.n8lm.zener.utils.BufferTools;

public class PlaneGeometry extends Geometry {

	public PlaneGeometry(String shader, boolean hasNormal, boolean hasTextureCoordinates) {
		super(shader);
		createVertexBuffer(hasNormal, hasTextureCoordinates);
	}

	private void createVertexBuffer(boolean hasNormal, boolean hasTextureCoordinates) {
        FloatBuffer vertices = null;
        FloatBuffer normals = null;
        FloatBuffer textureCoordinates = null;
        
        vertices = BufferTools.reserveData(4 * 3);
        
        if (hasNormal) {
        	normals = BufferTools.reserveData(4 * 3);
        }
        
        if (hasTextureCoordinates) {
        	textureCoordinates = BufferTools.reserveData(4 * 2);
        }

        vertices.put(BufferTools.asFloats(new Vector3f(-0.5f, -0.5f, 0f)));
        vertices.put(BufferTools.asFloats(new Vector3f(0.5f, -0.5f, 0f)));
        vertices.put(BufferTools.asFloats(new Vector3f(-0.5f, 0.5f, 0f)));
        vertices.put(BufferTools.asFloats(new Vector3f(0.5f, 0.5f, 0f)));

        if (hasNormal) {
        	normals.put(BufferTools.asFloats(new Vector3f(0f, 0f, 1f)));
        	normals.put(BufferTools.asFloats(new Vector3f(0f, 0f, 1f)));
        	normals.put(BufferTools.asFloats(new Vector3f(0f, 0f, 1f)));
        	normals.put(BufferTools.asFloats(new Vector3f(0f, 0f, 1f)));
        }
        
        if (hasTextureCoordinates) {
            textureCoordinates.put(BufferTools.asFloats(new Vector2f(0f,0f)));
        	textureCoordinates.put(BufferTools.asFloats(new Vector2f(1f,0f)));
        	textureCoordinates.put(BufferTools.asFloats(new Vector2f(0f,1f)));
        	textureCoordinates.put(BufferTools.asFloats(new Vector2f(1f,1f)));
        }   

        vertexCount = 4;
        
        vertices.flip();
        vbs.put(VertexBuffer.Type.Position, new VertexBuffer(VertexBuffer.Type.Position, VertexBuffer.Usage.Static, VertexBuffer.DataType.Float, 3, vertexCount, vertices));
        if (hasNormal) {
        	normals.flip();
        	vbs.put(VertexBuffer.Type.Normal, new VertexBuffer(VertexBuffer.Type.Normal, VertexBuffer.Usage.Static, VertexBuffer.DataType.Float, 3, vertexCount, normals));
        }
        if (hasTextureCoordinates) {
        	textureCoordinates.flip();
        	vbs.put(VertexBuffer.Type.TexCoord, new VertexBuffer(VertexBuffer.Type.TexCoord, VertexBuffer.Usage.Static, VertexBuffer.DataType.Float, 2, vertexCount, textureCoordinates));
        }
        
        this.primitiveType = PrimitiveType.Triangles;
	}

	@Override
	public void update(ViewRenderSystem subRenderSystem) {
		// TODO Auto-generated method stub
		
	}

}
