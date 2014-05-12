package com.n8lm.zener.graphics.geom;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Logger;



import com.n8lm.zener.graphics.GLObject;
import com.n8lm.zener.graphics.UniformGroup;
import com.n8lm.zener.graphics.VertexBuffer;
import com.n8lm.zener.graphics.VertexBuffer.Type;
import com.n8lm.zener.input.Input;
import com.n8lm.zener.systems.SubRenderSystem;

public abstract class Geometry extends GLObject{
	
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
	protected PrimitiveType primitiveType;
	protected int vertexCount;
	protected Map<VertexBuffer.Type, VertexBuffer> vbs;
	//protected UniformGroup uniformGroup;
	protected String shader;
	//protected Indexbuffer ib;
	
	public Geometry(String shader) {
		super();
		this.vbs = new HashMap<VertexBuffer.Type, VertexBuffer>();
		//this.uniformGroup = new UniformGroup();
		//this.vaoHandle = -1;
		this.vertexCount = 0;
		this.shader = shader;
		this.primitiveType = PrimitiveType.Triangles;
	}
	//glVertexAttribDivisor
	
	public void createGL() {
		
		id = glGenVertexArrays();
		glBindVertexArray(id);

		for (Entry<Type, VertexBuffer> vb: vbs.entrySet()) {
			
			glBindBuffer(GL_ARRAY_BUFFER, vb.getValue().getID());
			switch(vb.getValue().getDataType()) {
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

	protected void log() {

		for (VertexBuffer vb: vbs.values()) {
			LOGGER.info("VBO: ID "+ vb.getID() + " " + vb.getType() + " " + vb.getSize() + " " + vb.getComponents() + " ");	
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
	/*
	public UniformGroup getUniformGroup() {
		return uniformGroup;
	}*/
	
	public String getShaderName() {
		return shader;
	}
	
	public void setShaderName(String shader) {
		this.shader = shader;
	}
	
	public PrimitiveType getPrimitiveType() {
		return primitiveType;
	}

	abstract public void update(SubRenderSystem subRenderSystem);
	
	@Override
	public void deleteObject() {
		if (id != INVALID_ID) {

			for (Entry<Type, VertexBuffer> vb: vbs.entrySet()) {
				vb.getValue().deleteObject();
			}
			glDeleteVertexArrays(id);
		}
	}
}
