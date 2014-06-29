package com.n8lm.zener.graphics;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.opengl.GL31.*;

import java.util.*;
import java.util.Map.Entry;

import com.n8lm.zener.glsl.VarType;
import com.n8lm.zener.graphics.material.UniformsMaterial;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

import com.n8lm.zener.math.*;
import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.EntitySystem;
import com.artemis.annotations.Mapper;
import com.artemis.managers.TagManager;
import com.artemis.utils.Bag;
import com.artemis.utils.ImmutableBag;
import com.n8lm.zener.animation.SkeletonComponent;
import com.n8lm.zener.data.ResourceManager;
import com.n8lm.zener.general.TransformComponent;
import com.n8lm.zener.graphics.geom.Geometry;
import com.n8lm.zener.graphics.geom.InstancingGeometry;

public class ViewRenderSystem extends EntitySystem{

	protected @Mapper ComponentMapper<GeometryComponent> dm;
	protected @Mapper ComponentMapper<MaterialComponent> mm;
	protected @Mapper ComponentMapper<SkeletonComponent> sm;
	protected @Mapper ComponentMapper<TransformComponent> pm;
	protected @Mapper ComponentMapper<RenderEffectComponent> em;
	
	protected Matrix4f projectionMat;
	protected Matrix4f viewMat;
	protected Matrix4f modelMat;
	
	protected ViewComponent vc;
	protected TransformComponent vp;
    
    protected LightUniforms lightUniforms;
    protected GlobalUniforms globalUniforms;

    protected Bag<Entity> transEntities;
    protected Bag<Entity> opaqueEntities;

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
		super(Aspect.getAspectForAll(TransformComponent.class, GeometryComponent.class));
		
		this.vrs = vrs;
		//setPassive(true);
		
		lightUniforms = new LightUniforms();
		globalUniforms = new GlobalUniforms();
		
		viewMat = new Matrix4f();
		modelMat = new Matrix4f();
		projectionMat = new Matrix4f();

		globalUniforms.setViewMatrix(viewMat);
		globalUniforms.setModelMatrix(modelMat);
		globalUniforms.setProjectionMatrix(projectionMat);
		
		opaqueEntities = new Bag<Entity>();
		transEntities = new Bag<Entity>();
		
		tmpTransQueue = new Stack<Entity>();
	}
	
	
	@Override
	protected void processEntities(ImmutableBag<Entity> entities) { 
		
		if (renderMode == RenderMode.NormalRender) {
			for (int i = 0, s = opaqueEntities.size(); s > i; i++) {
				Entity e = opaqueEntities.get(i);
				
				Vector4f multiplyColor = new Vector4f(Vector4f.UNIT_XYZW);
				Vector4f addColor = new Vector4f(Vector4f.ZERO);
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
		if (mm.get(e).isTransparent()) {
			transEntities.add(e);
		} else {
			opaqueEntities.add(e);
		}
	}

	@Override
	protected void removed(Entity e) {
		super.removed(e);
		if (mm.get(e).isTransparent()) {
			transEntities.remove(e);
		} else {
			opaqueEntities.remove(e);
		}
		ResourceManager.getInstance().getGeometryManager().reduceInvokeCount(dm.get(e).getGeometry());
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
			viewPort.x0 = 0;
			viewPort.y0 = 0;
			viewPort.x1 = Display.getWidth();
			viewPort.y1 = Display.getHeight();
		}
		
		GL11.glViewport((int)viewPort.x0, (int)viewPort.y0, (int)(viewPort.x1 - viewPort.x0), 
				(int)(viewPort.y1 - viewPort.y0));
		vc.getProjection().setAspectRatio((viewPort.x1 - viewPort.x0) / (viewPort.y1  - viewPort.y0));
		
		vc.getProjection().getProjectionMatrix(projectionMat);
		vp.getWorldTransform().getViewMatrix(viewMat);

		Entity light = world.getManager(TagManager.class).getEntity("MainLight");
		
		if (light != null && light.getComponent(LightComponent.class) != null) {
			
			LightComponent lightInfo = light.getComponent(LightComponent.class);
			
			float isPoint;
			if (lightInfo.isPoint())
				isPoint = 1.0f;
			else
				isPoint = 0.0f;
			
			Transform lightTransform = pm.get(light).getWorldTransform();
			
			lightUniforms.setAmbientColor(lightInfo.getAmbient()[0], lightInfo.getAmbient()[1], lightInfo.getAmbient()[2]);
			lightUniforms.setDiffuseColor(lightInfo.getDiffuse()[0], lightInfo.getDiffuse()[1], lightInfo.getDiffuse()[2]);
			lightUniforms.setSpecularColor(lightInfo.getDiffuse()[0], lightInfo.getDiffuse()[1], lightInfo.getDiffuse()[2]);
			lightUniforms.setPosition(lightTransform.getTranslation().x, 
					lightTransform.getTranslation().y, lightTransform.getTranslation().z, 
					isPoint);

		}
		/*
		if (renderMode == RenderMode.DepthRender) {
			glCullFace(GL_FRONT);
		} else if (renderMode == RenderMode.NormalRender) {
			glCullFace(GL_BACK);
		}*/
		/*
		if (renderMode == RenderMode.NormalRender) {
			System.out.println(vrs.getDepthPV());
			//System.out.println(vrs.getDepthP());
			System.out.println(projectionMat.mult(viewMat));
			//System.out.println(projectionMat);
			//System.out.println(vrs.getDepthV());
			//System.out.println(viewMat);
			//System.out.println("aaa");
			
		} else 
			if (renderMode == RenderMode.DepthRender) {
			//System.out.println(projectionMat.mult(viewMat));
		}
		*/
		//glViewport(0, 0, 700, 700);
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

		if (material.contains("Material.NormalMap")) {
            if (!geometry.hasTangent()) {
                geometry.generateTangent();
                System.out.print("aaa");
            }
            options.add("NORMAL_MAPPING");
		}
		
		if (!geometry.isCreatedGL()) {
            //dm.get(e).;
            geometry.createGL();
        }
		
		geometry.update(this);

        if(sm.has(e)) {
            vertName = "skinning.vert";
        }
            //	System.out.println(e.getComponent(TransformComponent.class).getTranslation());
            //if(ds.getShaderName().equals("simple"))
            //	System.out.println("aa");
        if ( mm.get(e).isShadowReceiver() && renderMode == RenderMode.NormalRender)
            options.add("SHADOW_MAPPING");

        GLProgram program = rm.getProgram(vertName, fragName, options);

        program.bind();

		program.initTextureGroup();
		
        if(sm.has(e)) {
            Matrix4f[] m4a = sm.get(e).getCurrentPosesMatrices();
            program.setUniform(new UniformVariable(VarType.Matrix4Array, "BoneMatrices",
                    m4a));
        }
        //System.out.println(e.getComponent(TransformComponent.class).getTranslation());
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
			if (em.has(e)) {
				Vector4f multipleColor = em.get(e).getMultiplyColor();
				program.setUniform(new UniformVariable(VarType.Vector4f, "MultipleColor", multipleColor));
				Vector4f addColor = em.get(e).getAddColor();
				program.setUniform(new UniformVariable(VarType.Vector4f, "AddColor", addColor));
			} else {
				program.setUniform(new UniformVariable(VarType.Vector4f, "MultipleColor", Vector4f.UNIT_XYZW));
				program.setUniform(new UniformVariable(VarType.Vector4f, "AddColor", Vector4f.ZERO));
			}
		//}

		//if (renderMode == RenderMode.NormalRender){
	        for (UniformVariable uv : mm.get(e).getMaterial().getUniforms()) {
	            program.setUniform(uv);
	        }
			
			for (UniformVariable uv : lightUniforms.getUniforms())
				program.setUniform(uv);
		//}
		
		for (UniformVariable uv : globalUniforms.getUniforms()) {
            program.setUniform(uv);
        }

		// Uniform Matrix
		// glPushMatrix(); applyTranslations(pm.get(e));
		
		renderVAO(geometry);
		
		// Vertex Array
		// draw
		// glPopMatrix();

		program.unbind();
	}
    
	private void renderVAO(Geometry ds) {
		/*
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ib);
		*/
		glBindVertexArray(ds.getID());
		
		for (Entry<VertexBuffer.Type, VertexBuffer> vb: ds.getVertexBuffers().entrySet()) {
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
