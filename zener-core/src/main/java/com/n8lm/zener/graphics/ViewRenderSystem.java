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

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.EntitySystem;
import com.artemis.annotations.Mapper;
import com.artemis.utils.ArrayBag;
import com.artemis.utils.Bag;
import com.artemis.utils.ImmutableBag;
import com.n8lm.zener.animation.SkeletonComponent;
import com.n8lm.zener.data.ResourceManager;
import com.n8lm.zener.general.TransformComponent;
import com.n8lm.zener.glsl.VarType;
import com.n8lm.zener.graphics.geom.Geometry;
import com.n8lm.zener.graphics.geom.InstancingGeometry;
import com.n8lm.zener.graphics.material.UniformsMaterial;
import com.n8lm.zener.math.Matrix4f;
import com.n8lm.zener.math.Rectangle2D;
import com.n8lm.zener.math.Vector3f;
import com.n8lm.zener.math.Vector4f;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Stack;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL31.glDrawArraysInstanced;

public class ViewRenderSystem extends EntitySystem {

    protected
    @Mapper
    ComponentMapper<GeometryComponent> dm;
    protected
    @Mapper
    ComponentMapper<MaterialComponent> mm;
    protected
    @Mapper
    ComponentMapper<SkeletonComponent> sm;
    protected
    @Mapper
    ComponentMapper<TransformComponent> pm;
    protected
    @Mapper
    ComponentMapper<RenderEffectComponent> em;
    protected
    @Mapper
    ComponentMapper<LightComponent> lm;

    protected Matrix4f projectionMat;
    protected Matrix4f viewMat;
    protected Matrix4f modelMat;

    protected ViewComponent vc;
    protected TransformComponent vp;

    protected GlobalUniforms globalUniforms;

    protected Bag<Entity> transEntities;
    protected Bag<Entity> opaqueEntities;
    protected Bag<Entity> lightEntities;

    private Stack<Entity> tmpTransQueue;

    protected RenderMode renderMode;
    private GLRenderSystem vrs;
    private ResourceManager rm;

    public RenderMode getRenderMode() {
        return renderMode;
    }

    public void setRenderMode(RenderMode renderMode) {
        this.renderMode = renderMode;
    }


    enum RenderMode {
        DepthRender,
        NormalRender;
    }

    ViewRenderSystem(GLRenderSystem vrs) {
        super(Aspect.getAspectForAll(TransformComponent.class).one(GeometryComponent.class, LightComponent.class));

        this.vrs = vrs;

        //lightUniforms = new LightUniforms();
        globalUniforms = new GlobalUniforms();

        viewMat = new Matrix4f();
        modelMat = new Matrix4f();
        projectionMat = new Matrix4f();

        globalUniforms.setViewMatrix(viewMat);
        globalUniforms.setModelMatrix(modelMat);
        globalUniforms.setProjectionMatrix(projectionMat);

        opaqueEntities = new ArrayBag<Entity>();
        transEntities = new ArrayBag<Entity>();
        lightEntities = new ArrayBag<Entity>();

        tmpTransQueue = new Stack<Entity>();
    }


        /*
    void calcLightForEachEntity(ImmutableBag<Entity> lightEntities) {
        if (lightEntities.size() == 0)
            return;

        Bag<Vector3f> lightTrans = new Bag<Vector3f>();

        for (int i = 0, s = lightEntities.size(); s > i; i++) {
            lightTrans.add(pm.get(lightEntities.get(i)).getWorldTransform().getTranslation());
        }

        ImmutableBag<Entity> actives = getActives();

        for (int i = 0, s = actives.size(); s > i; i++) {

            Vector3f p = pm.get(actives.get(i)).getWorldTransform().getTranslation();

            float minDist = lightTrans.get(0).distance(p);
            int minIndex = 0;

            for (int j = 1, n = lightTrans.size(); n > j; j++) {
                float dist = lightTrans.get(j).distance(p);
                if (dist < minDist) {
                    minDist = dist;
                    minIndex = j;
                }
            }
        }
    }
*/

    @Override
    protected void processEntities() {

        if (renderMode == RenderMode.NormalRender) {
            for (int i = 0, s = opaqueEntities.size(); s > i; i++) {
                Entity e = opaqueEntities.get(i);

                Vector4f multiplyColor = Vector4f.UNIT_XYZW;
                Vector4f addColor = Vector4f.ZERO;
                if (em.has(e)) {
                    multiplyColor = em.get(e).getMultiplyColor();
                    addColor = em.get(e).getAddColor();
                }

                if (dm.get(e).isVisible() && (multiplyColor.w >= 0.0f || addColor.w >= 0.0f)) {
                    if (multiplyColor.w != 1.0f)
                        tmpTransQueue.push(e);
                    else
                        render(e);
                }
            }

            glEnable(GL_BLEND);
            glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
            glDepthMask(false);

            for (int i = 0, s = transEntities.size(); s > i; i++) {
                render(transEntities.get(i));
            }

            while (!tmpTransQueue.empty())
                render(tmpTransQueue.pop());

            glDepthMask(true);
            glDisable(GL_BLEND);

        } else if (renderMode == RenderMode.DepthRender) {
            for (int i = 0, s = opaqueEntities.size(); s > i; i++) {
                Entity e = opaqueEntities.get(i);

                if (dm.get(e).isShadowCaster()) {
                    if (!em.has(e) || em.get(e).getMultiplyColor().w == 1.0f)
                        render(e);
                }
            }
        }

    }

    @Override
    protected void inserted(Entity e) {
        super.inserted(e);
        if (mm.has(e)) {
            if (mm.get(e).isTransparent()) {
                transEntities.add(e);
            } else {
                opaqueEntities.add(e);
            }
        }
        if (lm.has(e)) {
            lightEntities.add(e);
        }
    }

    @Override
    protected void removed(Entity e) {
        if (mm.has(e)) {
            if (mm.get(e).isTransparent()) {
                transEntities.remove(e);
            } else {
                opaqueEntities.remove(e);
            }
            ResourceManager.getInstance().getGeometryManager().reduceInvokeCount(dm.get(e).getGeometry());
        }
        if (lm.has(e)) {
            lightEntities.remove(e);
        }
        super.removed(e);
        //dm.get(e).getDs().deleteObject();
    }


    @Override
    protected boolean checkProcessing() {
        if (vc != null)
            return true;
        else
            return false;
    }

    public void setView(Entity vce) {
        this.vc = vce.getComponent(ViewComponent.class);
        this.vp = vce.getComponent(TransformComponent.class);
    }

    @Override
    protected void initialize() {
        super.initialize();

        rm = ResourceManager.getInstance();
        /*
        glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);*/
        //standard

    }

    @Override
    protected void begin() {
        Rectangle2D viewPort = vc.getViewport();

        if (vc.isRenderToScreen()) {
            if (vc.isFullscreen()) {
                viewPort.x0 = 0;
                viewPort.y0 = 0;
                viewPort.x1 = Display.getWidth();
                viewPort.y1 = Display.getHeight();
            }
            GL11.glViewport((int) viewPort.x0, (int) (Display.getHeight() - viewPort.y1), (int) (viewPort.x1 - viewPort.x0),
                    (int) (viewPort.y1 - viewPort.y0));
        } else {
            GL11.glViewport((int) viewPort.x0, (int) (viewPort.y0), (int) (viewPort.x1 - viewPort.x0),
                    (int) (viewPort.y1 - viewPort.y0));
        }

        vc.getProjection().setAspectRatio((viewPort.x1 - viewPort.x0) / (viewPort.y1 - viewPort.y0));

        vc.getProjection().getProjectionMatrix(projectionMat);
        vp.getWorldTransform().getViewMatrix(viewMat);

		/*
        if (renderMode == RenderMode.DepthRender) {
			glCullFace(GL_FRONT);
		} else if (renderMode == RenderMode.NormalRender) {
			glCullFace(GL_BACK);
		}*/
		/*
		if (renderMode == RenderMode.NormalRender) {
			//System.out.println(vrs.getDepthPV());
			//System.out.println(vrs.getDepthP());
			//System.out.println(projectionMat.mult(viewMat));
			//System.out.println(projectionMat);
			//System.out.println(vrs.getDepthV());
			//System.out.println(viewMat);
			
		} else 
			if (renderMode == RenderMode.DepthRender) {
			//System.out.println(projectionMat.mult(viewMat));
		}
		*/
    }

    @Override
    protected void end() {

    }

    protected void render(Entity e) {
        Geometry geometry = dm.get(e).getGeometry();
        UniformsMaterial material = mm.get(e).getMaterial();

        TransformComponent tc = pm.get(e);
        tc.getWorldTransform().getModelMatrix(modelMat);
        //setModelMatrix(pm.get(e));

        String vertName = geometry.getShaderName() + ".vert";
        String fragName = material.getShaderName() + ".frag";
        List<String> options = new ArrayList<String>();

        if (material.contains("Material_NormalMap")) {
            if (!geometry.hasTangent()) {
                geometry.generateTangent();
            }
            options.add("NORMAL_MAPPING");
        }

        if (!geometry.isCreatedGL()) {
            //dm.get(e).;
            geometry.createGL();
        }

        geometry.update(this);

        if (sm.has(e)) {
            vertName = "skinning.vert";
        }

        if (mm.get(e).isShadowReceiver() && renderMode == RenderMode.NormalRender && vrs.hasShadow())
            options.add("SHADOW_MAPPING");

        GLProgram program = rm.getProgram(vertName, fragName, options);

        program.bind();
        program.initTextureGroup();

        if (sm.has(e)) {
            Matrix4f[] m4a = sm.get(e).getCurrentPosesMatrices();
            program.setUniform(new UniformVariable(VarType.Matrix4Array, "BoneMatrices",
                    m4a));
        }
        //System.out.println(e.getComponent(TransformComponent.class).getTranslation());

        if (options.contains("SHADOW_MAPPING")) {
            Matrix4f depthBiasMVP = vrs.getDepthPV();
            program.setUniform(new UniformVariable(VarType.Texture2D, "depthMap", vrs.getDepthTexture()));
            program.setUniform(new UniformVariable(VarType.Matrix4, "depthBiasMVP", depthBiasMVP));
        }
        //projectionMat.mult(viewMat.mult(modelMat));
        //depthBiasMVP.multLocal(modelMat);
        //System.out.println(depthBiasMVP);
        //depthBiasMVP = biasMatrix.mult(depthBiasMVP);

        //if (renderMode == RenderMode.NormalRender){
        addRenderEffectUniforms(program, e);

        // apply material uniform
        for (UniformVariable uv : mm.get(e).getMaterial().getUniforms()) {
            program.setUniform(uv);
        }

        // apply light uniforms
        addLightUniforms(program);
        //for (UniformVariable uv : lightUniforms.getUniforms())
        //	program.setUniform(uv);
        //}

        for (UniformVariable uv : globalUniforms.getUniforms()) {
            program.setUniform(uv);
        }

        // Uniform Matrix
        renderVAO(geometry);
        // Vertex Array
        // draw
        program.unbind();
    }

    private void addLightUniforms(GLProgram program) {

        if (program.hasUniform("LightCount")) {
            int lightNum = 0;
            for (int i = 0, s = lightEntities.size(); i < s; i++) {
                Entity light = lightEntities.get(i);
                LightComponent lightInfo = lm.get(light);
                if (!lightInfo.isEnable())
                    continue;

                float isPoint;
                if (lightInfo.isPoint())
                    isPoint = 1.0f;
                else
                    isPoint = 0.0f;

                Vector3f lPos = pm.get(light).getWorldTransform().getTranslation();
                Vector4f pos = vp.getWorldTransform().getViewMatrix(null).mult(new Vector4f(lPos.x, lPos.y, lPos.z, isPoint));
                //program.setUniform(new UniformVariable(VarType.Vector4f, "Light[" + i + "].La", lightInfo.getAmbient()));
                program.setUniform(new UniformVariable(VarType.Vector3f, "Light[" + i + "].Ld", lightInfo.getDiffuse()));
                program.setUniform(new UniformVariable(VarType.Vector3f, "Light[" + i + "].Ls", lightInfo.getSpecular()));
                program.setUniform(new UniformVariable(VarType.Float, "Light[" + i + "].Attenuation", lightInfo.getAttenuation()));
                program.setUniform(new UniformVariable(VarType.Vector4f, "Light[" + i + "].Position", pos));
                lightNum++;
            }
            program.setUniform(new UniformVariable(VarType.Int, "LightCount", lightNum));
        }

        if (program.hasUniform("La"))
            program.setUniform(new UniformVariable(VarType.Vector3f, "La", new Vector3f(0.2f, 0.2f, 0.2f)));
    }

    private void addRenderEffectUniforms(GLProgram program, Entity e) {
        if (em.has(e)) {
            Vector4f multipleColor = em.get(e).getMultiplyColor();
            program.setUniform(new UniformVariable(VarType.Vector4f, "MultipleColor", multipleColor));
            Vector4f addColor = em.get(e).getAddColor();
            program.setUniform(new UniformVariable(VarType.Vector4f, "AddColor", addColor));
        } else {
            program.setUniform(new UniformVariable(VarType.Vector4f, "MultipleColor", Vector4f.UNIT_XYZW));
            program.setUniform(new UniformVariable(VarType.Vector4f, "AddColor", Vector4f.ZERO));
        }
    }

    private void renderVAO(Geometry ds) {
		/*
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ib);
		*/
        glBindVertexArray(ds.getID());

        for (Entry<VertexBuffer.Type, VertexBuffer> vb : ds.getVertexBuffers().entrySet()) {
            glEnableVertexAttribArray(vb.getKey().id);
        }

        //GL11.glDrawElements(GL_TRIANGLES, ds.getTriangleCount(), GL_UNSIGNED_INT, null);
        if (ds instanceof InstancingGeometry) {
            glDrawArraysInstanced(ds.getPrimitiveType().getGLCode(), 0, ds.getVertexCount(), ((InstancingGeometry) ds).getInstancesCount());
        } else {
            glDrawArrays(ds.getPrimitiveType().getGLCode(), 0, ds.getVertexCount());
        }
        glBindVertexArray(0);
		/*
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
		*/
    }

    public Matrix4f getModelViewProjectionMatrix() {
        return projectionMat.mult(viewMat.mult(modelMat));
    }

}
