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

package com.n8lm.zener.particle;

import com.n8lm.zener.assets.Face;
import com.n8lm.zener.assets.Mesh;
import com.n8lm.zener.graphics.VertexBuffer;
import com.n8lm.zener.graphics.VertexBuffer.DataType;
import com.n8lm.zener.graphics.VertexBuffer.Type;
import com.n8lm.zener.graphics.VertexBuffer.Usage;
import com.n8lm.zener.graphics.ViewRenderSystem;
import com.n8lm.zener.graphics.geom.InstancingGeometry;
import com.n8lm.zener.utils.BufferTools;
import com.n8lm.zener.utils.TempVars;
import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;
import java.util.Arrays;

/**
 * <p>A <code>Geometry</code> of an entire Particle System.
 * It utilizes the Instancing Technology to render the particles.
 * It contains the mesh of a single particle and the VBOs of the
 * information of all the particles.</p>
 * <p>The information of all the particles includes the position, size,
 * rotation, textureIndex and color.</p>
 *
 * @author Forrest Sun
 */
public class ParticleSystemGeometry extends InstancingGeometry {

	private ParticleSystemComponent particleSystem;
	
	public ParticleSystemGeometry(String name, ParticleSystemComponent particleSystem, Mesh particle) {
		super(name, "particle");
		this.particleSystem = particleSystem;
		generate(particle);
	}
	
	public void setParticleSystemComponent(ParticleSystemComponent particleSystem) {
		this.particleSystem = particleSystem;
	}

    /**
     * generates all the VBOs we need and add them to VAO
     * @param particle the mesh of a single particle
     */
	private void generate(Mesh particle) {
        FloatBuffer vertices = null;
        FloatBuffer textureCoordinates = null;
        FloatBuffer textureRect = null;
        FloatBuffer matrices = null;
        FloatBuffer colors = null;

        vertexCount = particle.faces.size() * 3;

        vertices = BufferUtils.createFloatBuffer(vertexCount * 3);
        if (particle.hasTextureCoordinates()) {
            textureCoordinates = BufferUtils.createFloatBuffer(vertexCount * 2);
        }

        for (Face face : particle.faces) {
            BufferTools.fillBuffer(particle.vertices.get(face.getVertexIndices()[0] - 1), vertices);
            BufferTools.fillBuffer(particle.vertices.get(face.getVertexIndices()[1] - 1), vertices);
            BufferTools.fillBuffer(particle.vertices.get(face.getVertexIndices()[2] - 1), vertices);
            if (particle.hasTextureCoordinates()) {
                BufferTools.fillBuffer(particle.textureCoordinates.get(face.getTextureCoordinateIndices()[0] - 1), textureCoordinates);
                BufferTools.fillBuffer(particle.textureCoordinates.get(face.getTextureCoordinateIndices()[1] - 1), textureCoordinates);
                BufferTools.fillBuffer(particle.textureCoordinates.get(face.getTextureCoordinateIndices()[2] - 1), textureCoordinates);
            }
        }

        vertices.flip();
		this.vbs.put(Type.Position, new VertexBuffer(Type.Position, Usage.Static, DataType.Float, 3, vertexCount, vertices));

        if (particle.hasTextureCoordinates()) {
        	textureCoordinates.flip();
    		this.vbs.put(Type.TexCoord, new VertexBuffer(Type.TexCoord, Usage.Static, DataType.Float, 2, vertexCount, textureCoordinates));
        }
        
        int maxCount = particleSystem.getMaxCount();


        if (particle.hasTextureCoordinates()) {
            textureRect = BufferUtils.createFloatBuffer(maxCount * 4);
        }
        matrices = BufferUtils.createFloatBuffer(maxCount * 16);
        colors = BufferUtils.createFloatBuffer(maxCount * 4);

        this.vbs.put(Type.Custom, new VertexBuffer(Type.Custom, Usage.Stream, DataType.Float, 4, maxCount, textureRect, false));
        this.vbs.put(Type.ParticleMatrix, new VertexBuffer(Type.ParticlePos, Usage.Stream, DataType.Float, 16, maxCount, matrices, false));
        this.vbs.put(Type.ParticleColor, new VertexBuffer(Type.ParticleColor, Usage.Stream, DataType.Float, 4, maxCount, colors, false));

		this.divisors.put(Type.Position, 0);
		this.divisors.put(Type.TexCoord, 0);
        this.divisors.put(Type.Custom, 1);
		this.divisors.put(Type.ParticleMatrix, 1);
		this.divisors.put(Type.ParticleColor, 1);

	}

    /**
     * update the VBOs which contains the information of all the particles.
     * @param subRenderSystem the rendering system
     */
	@Override
	public void update(ViewRenderSystem subRenderSystem) {
        FloatBuffer textureRect = (FloatBuffer) this.vbs.get(Type.Custom).getData();
        FloatBuffer matrices = (FloatBuffer) this.vbs.get(Type.ParticleMatrix).getData();
		FloatBuffer colors = (FloatBuffer) this.vbs.get(Type.ParticleColor).getData();

        textureRect.clear();
        matrices.clear();
		colors.clear();

        Particle[] particles = particleSystem.getParticles();
        int count = particleSystem.getCount();

		/**
		 * gl blend sort
		 */
	    Arrays.sort(particleSystem.getParticles(), 0, count);

        TempVars tempVars = TempVars.get();

		for (int i = 0; i < count; i ++) {
            BufferTools.fillBuffer(particles[i].textureCoord, textureRect);
            tempVars.tempTF.setTranslation(particles[i].position);
            tempVars.tempTF.setRotation(particles[i].rotation);
            tempVars.tempTF.setScale(tempVars.vect1.set(particles[i].size, particles[i].size, particles[i].size));
            tempVars.tempTF.getModelMatrix(tempVars.tempMat4);
            tempVars.tempMat4.fillFloatBuffer(matrices);
            BufferTools.fillBuffer(particles[i].color, colors);
        }

        tempVars.release();

        textureRect.flip();
        matrices.flip();
		colors.flip();

        this.vbs.get(Type.Custom).updateSubData(0);
		this.vbs.get(Type.ParticleMatrix).updateSubData(0);
		this.vbs.get(Type.ParticleColor).updateSubData(0);
		
		instancesCount = count;
	}

}
