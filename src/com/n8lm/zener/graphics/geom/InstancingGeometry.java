package com.n8lm.zener.graphics.geom;

import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.opengl.GL33.glVertexAttribDivisor;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.n8lm.zener.graphics.VertexBuffer;
import com.n8lm.zener.graphics.VertexBuffer.Type;

public abstract class InstancingGeometry extends Geometry {

	protected Map<VertexBuffer.Type, Integer> divisors;
	
	protected int instancesCount;
	
	public InstancingGeometry(String shader) {
		super(shader);
		divisors = new HashMap<VertexBuffer.Type, Integer>();
		instancesCount = 0;
	}

	@Override
	public void createGL() {
		super.createGL();
		
		glBindVertexArray(id);

		for (Entry<Type, VertexBuffer> vb: vbs.entrySet()) {
			
			glVertexAttribDivisor(vb.getKey().id, divisors.get(vb.getKey()).intValue());
		}
		
		glBindVertexArray(0);
		
	}

	
	public int getInstancesCount() {
		return instancesCount;
	}

}
