/*
 * This file is part of Zener.
 *
 * Zener is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of
 * the License, or any later version.
 *
 * Zener is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General
 * Public License along with Zener.  If not, see
 * <http://www.gnu.org/licenses/>.
 */

package com.n8lm.zener.graphics.geom;

import com.n8lm.zener.graphics.VertexBuffer;
import com.n8lm.zener.graphics.ViewRenderSystem;
import com.n8lm.zener.math.Vector2f;
import com.n8lm.zener.math.Vector3f;
import com.n8lm.zener.utils.BufferTools;

import java.nio.FloatBuffer;

public class PlaneGeometry extends Geometry {

    public PlaneGeometry(String name, String shader, boolean hasNormal, boolean hasTextureCoordinates) {
        super(name, shader);
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
            textureCoordinates.put(BufferTools.asFloats(new Vector2f(0f, 0f)));
            textureCoordinates.put(BufferTools.asFloats(new Vector2f(1f, 0f)));
            textureCoordinates.put(BufferTools.asFloats(new Vector2f(0f, 1f)));
            textureCoordinates.put(BufferTools.asFloats(new Vector2f(1f, 1f)));
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
