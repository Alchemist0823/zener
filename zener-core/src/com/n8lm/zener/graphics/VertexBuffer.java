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

package com.n8lm.zener.graphics;
import static org.lwjgl.opengl.GL15.*;

/*
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
*/
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class VertexBuffer extends GLObject {

	/**
	 * This should be corresponding to shaders
	 */
	public static enum Type {
		Position(0),
		TexCoord(1),
		Normal(2),
		Tangent(3),
		BoneIndex(4),
		BoneWeight(5),
		Color(6),
		Custom(7),
		ParticlePos(8),
		ParticleSize(9),
		ParticleColor(10);
		
		public int id;
		
		Type(int id) {
			this.id = id;
		}
	}
	
	public static enum Usage {
        
        /**
         * Mesh data is sent once and very rarely updated.
         */
        Static,

        /**
         * Mesh data is updated occasionally (once per frame or less).
         */
        Dynamic,

        /**
         * Mesh data is updated every frame.
         */
        Stream,

        /**
         * Mesh data is <em>not</em> sent to GPU at all. It is only
         * used by the CPU.
         */
        CpuOnly;
    }
	
	public static enum DataType {
        
        Float,

        Int,

        Byte,
    }
	
	private Type type;
	private final DataType dataType;
	private Usage usage;
	private int components;
	private int size;
	private Buffer data;
	
	public VertexBuffer(Type type, Usage usage, DataType dataType, int components, int size, Buffer data) {
		this(type, usage, dataType, components, size, data, true);
	}
	
	public VertexBuffer(Type type, Usage usage, DataType dataType, int components, int size, Buffer data, boolean fillData) {
		super(glGenBuffers());
		this.type = type;
		this.usage = usage;
		this.dataType = dataType;
		this.components = components;
		this.size = size;
		this.data = data;
		
		if (fillData)
			updateAllData();
		else
			bindSize();
	}
	
	private int getGLUsage() { 
		switch(usage) {
		case Dynamic:
			return GL_DYNAMIC_DRAW;
		case Static:
			return GL_STATIC_DRAW;
		case Stream:
			return GL_STREAM_DRAW;
		default:
			return GL_STATIC_DRAW;
		}
	}
	
	private void bindSize() {

		glBindBuffer(GL_ARRAY_BUFFER, id);
		
		int glUsage = getGLUsage();
		
		switch(dataType) {
		case Float:
			glBufferData(GL_ARRAY_BUFFER, size * components * 4, glUsage);
			break;
		case Byte:
			glBufferData(GL_ARRAY_BUFFER, size * components * 1, glUsage);
			break;
		case Int:
			glBufferData(GL_ARRAY_BUFFER, size * components * 4, glUsage);
			break;
		default:
			break;
		}
		
		glBindBuffer(GL_ARRAY_BUFFER, 0); 
	}
	
	public void updateAllData() {

		glBindBuffer(GL_ARRAY_BUFFER, id);
		int glUsage = getGLUsage();
		
		switch(dataType) {
		case Float:
			glBufferData(GL_ARRAY_BUFFER, (FloatBuffer) data, glUsage);
			break;
		case Byte:
			glBufferData(GL_ARRAY_BUFFER, (ByteBuffer) data, glUsage);
			break;
		case Int:
			glBufferData(GL_ARRAY_BUFFER, (IntBuffer) data, glUsage);
			break;
		default:
			break;
		}
		glBindBuffer(GL_ARRAY_BUFFER, 0); 
	}
	
	public void updateSubData(int offset) {

		glBindBuffer(GL_ARRAY_BUFFER, id);
		switch(dataType) {
		case Float:
			glBufferSubData(GL_ARRAY_BUFFER, offset, (FloatBuffer) data);
			break;
		case Byte:
			glBufferSubData(GL_ARRAY_BUFFER, offset, (ByteBuffer) data);
			break;
		case Int:
			glBufferSubData(GL_ARRAY_BUFFER, offset, (IntBuffer) data);
			break;
		default:
			break;
		}
		glBindBuffer(GL_ARRAY_BUFFER, 0); 
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public Usage getUsage() {
		return usage;
	}

	public void setUsage(Usage usage) {
		this.usage = usage;
	}
	
	public DataType getDataType() {
		return dataType;
	}

	public int getComponents() {
		return components;
	}

	public void setComponents(int components) {
		this.components = components;
	}

	public Buffer getData() {
		return data;
	}

	public void setData(Buffer data) {
		this.data = data;
	}

	public int getSize() {
		return size;
	}
	
	public void setSize(int size) {
		this.size = size;
	}
	
	public void setHandle(int handle) {
		this.id = handle;
	}

	@Override
	public void deleteObject() {
		glDeleteBuffers(id);
	}
}

















