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

import com.n8lm.zener.assets.Face;
import com.n8lm.zener.assets.Mesh;
import com.n8lm.zener.graphics.VertexBuffer;
import com.n8lm.zener.graphics.ViewRenderSystem;
import com.n8lm.zener.utils.BufferTools;
import org.lwjgl.BufferUtils;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

public class ModelGeometry extends Geometry {

    public ModelGeometry(Mesh model) {
        super("standard");
        loadModel(model);
    }

    protected void loadModel(Mesh mesh) {

        FloatBuffer vertices = null;
        FloatBuffer normals = null;
        FloatBuffer textureCoordinates = null;
        ByteBuffer boneIndices = null;
        ByteBuffer weights = null;

        vertices = BufferTools.reserveData(mesh.faces.size() * 9);

        if (mesh.hasNormals()) {
            normals = BufferTools.reserveData(mesh.faces.size() * 9);
        }

        if (mesh.hasTextureCoordinates()) {
            textureCoordinates = BufferTools.reserveData(mesh.faces.size() * 6);
        }

        if (mesh.hasBoneAssignment()) {
            boneIndices = BufferUtils
                    .createByteBuffer(mesh.faces.size() * 4 * 3);
            weights = BufferUtils.createByteBuffer(mesh.faces.size() * 4 * 3);
        }

        for (Face face : mesh.faces) {
            vertices.put(BufferTools.asFloats(mesh.vertices.get(face
                    .getVertexIndices()[0] - 1)));
            vertices.put(BufferTools.asFloats(mesh.vertices.get(face
                    .getVertexIndices()[1] - 1)));
            vertices.put(BufferTools.asFloats(mesh.vertices.get(face
                    .getVertexIndices()[2] - 1)));

            if (mesh.hasBoneAssignment()) {
                boneIndices.put(BufferTools.asBytes(mesh.boneIndices.get(face
                        .getVertexIndices()[0] - 1)));
                boneIndices.put(BufferTools.asBytes(mesh.boneIndices.get(face
                        .getVertexIndices()[1] - 1)));
                boneIndices.put(BufferTools.asBytes(mesh.boneIndices.get(face
                        .getVertexIndices()[2] - 1)));

                weights.put(BufferTools.asBytes(mesh.weights.get(face
                        .getVertexIndices()[0] - 1)));
                weights.put(BufferTools.asBytes(mesh.weights.get(face
                        .getVertexIndices()[1] - 1)));
                weights.put(BufferTools.asBytes(mesh.weights.get(face
                        .getVertexIndices()[2] - 1)));
            }

            if (mesh.hasNormals()) {
                normals.put(BufferTools.asFloats(mesh.normals.get(face
                        .getNormalIndices()[0] - 1)));
                normals.put(BufferTools.asFloats(mesh.normals.get(face
                        .getNormalIndices()[1] - 1)));
                normals.put(BufferTools.asFloats(mesh.normals.get(face
                        .getNormalIndices()[2] - 1)));
            }
            if (mesh.hasTextureCoordinates()) {
                textureCoordinates.put(BufferTools
                        .asFloats(mesh.textureCoordinates.get(face
                                .getTextureCoordinateIndices()[0] - 1)));
                textureCoordinates.put(BufferTools
                        .asFloats(mesh.textureCoordinates.get(face
                                .getTextureCoordinateIndices()[1] - 1)));
                textureCoordinates.put(BufferTools
                        .asFloats(mesh.textureCoordinates.get(face
                                .getTextureCoordinateIndices()[2] - 1)));
            }
        }

        vertexCount = mesh.faces.size() * 3;

        vertices.flip();
        vbs.put(VertexBuffer.Type.Position, new VertexBuffer(
                VertexBuffer.Type.Position, VertexBuffer.Usage.Static,
                VertexBuffer.DataType.Float, 3, vertexCount, vertices));
        if (mesh.hasNormals()) {
            normals.flip();
            vbs.put(VertexBuffer.Type.Normal, new VertexBuffer(
                    VertexBuffer.Type.Normal, VertexBuffer.Usage.Static,
                    VertexBuffer.DataType.Float, 3, vertexCount, normals));
        }
        if (mesh.hasTextureCoordinates()) {
            textureCoordinates.flip();
            vbs.put(VertexBuffer.Type.TexCoord, new VertexBuffer(
                    VertexBuffer.Type.TexCoord, VertexBuffer.Usage.Static,
                    VertexBuffer.DataType.Float, 2, vertexCount,
                    textureCoordinates));
        }

        if (mesh.hasBoneAssignment()) {
            boneIndices.flip();
            weights.flip();
            vbs.put(VertexBuffer.Type.BoneIndex, new VertexBuffer(
                    VertexBuffer.Type.BoneIndex, VertexBuffer.Usage.Static,
                    VertexBuffer.DataType.Byte, 4, vertexCount, boneIndices));
            vbs.put(VertexBuffer.Type.BoneWeight, new VertexBuffer(
                    VertexBuffer.Type.BoneWeight, VertexBuffer.Usage.Static,
                    VertexBuffer.DataType.Byte, 4, vertexCount, weights));
        }

        // this.uniformGroup = new MaterialUniforms(mesh.material);
    }

	/*
     * public MaterialUniforms getMaterialUniforms() { return (MaterialUniforms)
	 * uniformGroup; }
	 */

    @Override
    public void update(ViewRenderSystem subRenderSystem) {
        // TODO Auto-generated method stub

    }

}
