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

import com.n8lm.zener.graphics.GLObject;
import com.n8lm.zener.graphics.VertexBuffer;
import com.n8lm.zener.graphics.VertexBuffer.Type;
import com.n8lm.zener.graphics.ViewRenderSystem;
import com.n8lm.zener.math.Vector2f;
import com.n8lm.zener.math.Vector3f;
import com.n8lm.zener.utils.BufferTools;

import java.nio.FloatBuffer;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Logger;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.*;

public abstract class Geometry extends GLObject {

    private final static Logger LOGGER = Logger.getLogger(Geometry.class
            .getName());

    public enum PrimitiveType {
        Triangles(GL_TRIANGLES),
        TriangleStrip(GL_TRIANGLE_STRIP),
        Lines(GL_LINE),
        LineStrip(GL_LINE_STRIP),
        Points(GL_POINTS);

        int glcode;

        PrimitiveType(int glcode) {
            this.glcode = glcode;
        }

        public int getGLCode() {
            return glcode;
        }
    }

    //protected int vaoHandle;
    protected String name;
    protected PrimitiveType primitiveType;
    protected int vertexCount;
    protected Map<VertexBuffer.Type, VertexBuffer> vbs;
    protected String shader;
    //protected Indexbuffer ib;

    public Geometry(String name, String shader) {
        super();
        this.name = name;
        this.vbs = new HashMap<VertexBuffer.Type, VertexBuffer>();
        //this.vaoHandle = -1;
        this.vertexCount = 0;
        this.shader = shader;
        this.primitiveType = PrimitiveType.Triangles;
    }
    //glVertexAttribDivisor

    public String getName() {
        return name;
    }

    public void createGL() {

        id = glGenVertexArrays();
        glBindVertexArray(id);

        for (Entry<Type, VertexBuffer> vb : vbs.entrySet()) {

            glBindBuffer(GL_ARRAY_BUFFER, vb.getValue().getID());
            switch (vb.getValue().getDataType()) {
                case Float:
                    glVertexAttribPointer(vb.getKey().id, vb.getValue().getComponents(), GL_FLOAT, false, 0, 0);
                    break;
                case Byte:
                    if (vb.getValue().getType() == VertexBuffer.Type.BoneWeight)
                        glVertexAttribPointer(vb.getKey().id, vb.getValue().getComponents(), GL_UNSIGNED_BYTE, true, 0, 0);
                    else
                        glVertexAttribPointer(vb.getKey().id, vb.getValue().getComponents(), GL_UNSIGNED_BYTE, false, 0, 0);
                    break;
                case Int:
                    glVertexAttribPointer(vb.getKey().id, vb.getValue().getComponents(), GL_INT, false, 0, 0);
                    break;
                default:
                    break;
            }
        }

        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glBindVertexArray(0);

        log();
        // index
        /*
        int handle = glGenBuffers();
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, handle);
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, (FloatBuffer) ib.getData(), GL_STATIC_DRAW);
		
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);*/
    }

    public void generateTangent() {
        FloatBuffer vertices = (FloatBuffer) vbs.get(VertexBuffer.Type.Position).getData();
        FloatBuffer normals = (FloatBuffer) vbs.get(VertexBuffer.Type.Normal).getData();
        FloatBuffer textureCroods = (FloatBuffer) vbs.get(VertexBuffer.Type.TexCoord).getData();

        FloatBuffer tangents = BufferTools.reserveData(vertexCount * 4);

        for (int i = 0; i < vertexCount / 3; i++) {
            Vector3f v0 = new Vector3f(vertices.get(), vertices.get(), vertices.get());
            Vector3f v1 = new Vector3f(vertices.get(), vertices.get(), vertices.get());
            Vector3f v2 = new Vector3f(vertices.get(), vertices.get(), vertices.get());

            Vector2f uv0 = new Vector2f(textureCroods.get(), textureCroods.get());
            Vector2f uv1 = new Vector2f(textureCroods.get(), textureCroods.get());
            Vector2f uv2 = new Vector2f(textureCroods.get(), textureCroods.get());

            Vector3f deltaPos1 = v1.subtract(v0);
            Vector3f deltaPos2 = v2.subtract(v0);

            Vector2f deltaUV1 = uv1.subtract(uv0);
            Vector2f deltaUV2 = uv2.subtract(uv0);

            float r = 1 / (deltaUV1.x * deltaUV2.y - deltaUV1.y * deltaUV2.x);
            Vector3f tangent = deltaPos1.mult(deltaUV2.y).subtract(deltaPos2.mult(deltaUV1.y)).mult(r);
            Vector3f bitangent = deltaPos2.mult(deltaUV1.x).subtract(deltaPos1.mult(deltaUV2.x)).mult(r);
            //http://www.opengl-tutorial.org/intermediate-tutorials/tutorial-13-normal-mapping/

            for (int j = 0; j < 3; j++) {

                Vector3f normal = new Vector3f(normals.get(), normals.get(), normals.get());

                Vector3f tangentNormalised = tangent.subtract(normal.mult(normal.dot(tangent))).normalize();

                System.out.println(tangentNormalised);

                float det = normal.cross(tangent).dot(bitangent);
                if (det < 0.0f) {
                    det = -1.0f;
                } else {
                    det = 1.0f;
                }

                tangents.put(BufferTools.asFloats(tangentNormalised));
                tangents.put(det);
            }
            //http://antongerdelan.net/opengl/normal_mapping.html
        }

        textureCroods.rewind();
        vertices.rewind();
        normals.rewind();

        tangents.flip();
        vbs.put(VertexBuffer.Type.Tangent, new VertexBuffer(
                VertexBuffer.Type.Tangent, VertexBuffer.Usage.Static,
                VertexBuffer.DataType.Float, 4, vertexCount, tangents));
    }

    public boolean hasTangent() {
        return vbs.containsKey(VertexBuffer.Type.Tangent);
    }

    protected void log() {

        for (VertexBuffer vb : vbs.values()) {
            LOGGER.info("VBO: ID " + vb.getID() + " " + vb.getType() + " " + vb.getSize() + " " + vb.getComponents() + " ");
        }
        LOGGER.info("VAO: ID " + id);
        //LOGGER.info("Uniform: " + uniformGroup);
    }

    public int getVertexCount() {
        return vertexCount;
    }

    public Map<VertexBuffer.Type, VertexBuffer> getVertexBuffers() {
        return vbs;
    }

    public String getShaderName() {
        return shader;
    }

    public void setShaderName(String shader) {
        this.shader = shader;
    }

    public PrimitiveType getPrimitiveType() {
        return primitiveType;
    }

    abstract public void update(ViewRenderSystem subRenderSystem);

    @Override
    public void deleteObject() {
        if (id != INVALID_ID) {

            for (Entry<Type, VertexBuffer> vb : vbs.entrySet()) {
                vb.getValue().deleteObject();
            }
            glDeleteVertexArrays(id);
        }
    }
}
