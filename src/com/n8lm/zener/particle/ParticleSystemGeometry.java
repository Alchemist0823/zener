package com.n8lm.zener.particle;

import java.nio.FloatBuffer;

import com.n8lm.zener.assets.Face;
import com.n8lm.zener.assets.Mesh;
import com.n8lm.zener.graphics.ViewRenderSystem;
import com.n8lm.zener.graphics.VertexBuffer;
import com.n8lm.zener.graphics.VertexBuffer.DataType;
import com.n8lm.zener.graphics.VertexBuffer.Type;
import com.n8lm.zener.graphics.VertexBuffer.Usage;
import com.n8lm.zener.graphics.geom.InstancingGeometry;
import com.n8lm.zener.math.Matrix4f;
import com.n8lm.zener.math.Vector3f;
import com.n8lm.zener.utils.BufferTools;

public class ParticleSystemGeometry extends InstancingGeometry {

	private ParticleSystemComponent particleSystem;
	
	public ParticleSystemGeometry(ParticleSystemComponent particleSystem, Mesh particle) {
		super("particle");
		this.particleSystem = particleSystem;
		generate(particle);
	}
	
	public void setParticleSystemComponent(ParticleSystemComponent particleSystem) {
		this.particleSystem = particleSystem;
	}

	private void generate(Mesh particle) {
        FloatBuffer vertices = null;
        FloatBuffer textureCoordinates = null;
        FloatBuffer positions = null;
        FloatBuffer sizes = null;
        FloatBuffer colors = null;

        vertexCount = particle.faces.size() * 3;
        
        vertices = BufferTools.reserveData(vertexCount * 3);
        if (particle.hasTextureCoordinates()) {
        	textureCoordinates = BufferTools.reserveData(vertexCount * 2);
        }
        
        for (Face face : particle.faces) {
            vertices.put(BufferTools.asFloats(particle.vertices.get(face.getVertexIndices()[0] - 1)));
            vertices.put(BufferTools.asFloats(particle.vertices.get(face.getVertexIndices()[1] - 1)));
            vertices.put(BufferTools.asFloats(particle.vertices.get(face.getVertexIndices()[2] - 1)));
            if (particle.hasTextureCoordinates()) {
            	textureCoordinates.put(BufferTools.asFloats(particle.textureCoordinates.get(face.getTextureCoordinateIndices()[0] - 1)));
            	textureCoordinates.put(BufferTools.asFloats(particle.textureCoordinates.get(face.getTextureCoordinateIndices()[1] - 1)));
            	textureCoordinates.put(BufferTools.asFloats(particle.textureCoordinates.get(face.getTextureCoordinateIndices()[2] - 1)));
            }
        }
        
        vertices.flip();
		this.vbs.put(Type.Position, new VertexBuffer(Type.Position, Usage.Static, DataType.Float, 3, vertexCount, vertices));

        if (particle.hasTextureCoordinates()) {
        	textureCoordinates.flip();
    		this.vbs.put(Type.TexCoord, new VertexBuffer(Type.TexCoord, Usage.Static, DataType.Float, 2, vertexCount, textureCoordinates));
        }
        
        int maxCount = particleSystem.getController().getMaxCount();
        
        positions = BufferTools.reserveData(maxCount * 3);
        sizes = BufferTools.reserveData(maxCount * 1);
        colors = BufferTools.reserveData(maxCount * 4);
        
        this.vbs.put(Type.ParticlePos, new VertexBuffer(Type.ParticlePos, Usage.Stream, DataType.Float, 3, maxCount, positions, false));
        this.vbs.put(Type.ParticleSize, new VertexBuffer(Type.ParticleSize, Usage.Stream, DataType.Float, 1, maxCount, sizes, false));
        this.vbs.put(Type.ParticleColor, new VertexBuffer(Type.ParticleColor, Usage.Stream, DataType.Float, 4, maxCount, colors, false));
        
		this.divisors.put(Type.Position, 0);
		this.divisors.put(Type.TexCoord, 0);
		this.divisors.put(Type.ParticlePos, 1);
		this.divisors.put(Type.ParticleSize, 1);
		this.divisors.put(Type.ParticleColor, 1);
        
        ///this.uniformGroup = new MaterialUniforms(particle.material);
	}
	
	@Override
	public void update(ViewRenderSystem subRenderSystem) {
        FloatBuffer positions = (FloatBuffer) this.vbs.get(Type.ParticlePos).getData();
		FloatBuffer sizes = (FloatBuffer) this.vbs.get(Type.ParticleSize).getData();
		FloatBuffer colors = (FloatBuffer) this.vbs.get(Type.ParticleColor).getData();
		//System.out.println(positions.limit());
		

		positions.clear();
		sizes.clear();
		colors.clear();
		
		Matrix4f modelViewProjectionMatrix = subRenderSystem.getModelViewProjectionMatrix();
		
		Particle[] particles = particleSystem.getParticles();
		int count = particleSystem.getCount();
		for (int i = 0; i < count; i ++) {
        	    Vector3f a = modelViewProjectionMatrix.mult(particles[i].pos);
        	    particles[i].cameradistance = a.z;
		}

		/**
		 * gl blend sort
		 */
	    //Collections.sort(particleSystem.getParticles());

		for (int i = 0; i < count; i ++) {
			positions.put(BufferTools.asFloats(particles[i].pos));
			sizes.put(particles[i].size);
			colors.put(BufferTools.asFloats(particles[i].color));
		}
		
		positions.flip();
		sizes.flip();
		colors.flip();
		
		this.vbs.get(Type.ParticlePos).updateSubData(0);
		this.vbs.get(Type.ParticleSize).updateSubData(0);
		this.vbs.get(Type.ParticleColor).updateSubData(0);
		
		instancesCount = count;
		//this.instancesCount;
	}

}
